package com.kingscastle.gameElements.livingThings.army;

import com.kaebe.kingscastle.R;
import com.kingscastle.effects.animations.DarkStarAnim;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.FourFrameAnimator;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.SoldierType;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.livingThings.attacks.Arms;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.BuildingsUtil;
import com.kingscastle.gameElements.livingThings.buildings.Farm;
import com.kingscastle.gameElements.livingThings.orders.BuildThis;
import com.kingscastle.gameElements.livingThings.orders.CancelAction;
import com.kingscastle.gameElements.livingThings.orders.DepositResources;
import com.kingscastle.gameElements.livingThings.orders.Order;
import com.kingscastle.gameElements.livingThings.orders.Stance;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.movement.pathing.Path;
import com.kingscastle.gameElements.movement.pathing.PathFinder;
import com.kingscastle.gameElements.movement.pathing.PathFoundListener;
import com.kingscastle.gameElements.resources.Job;
import com.kingscastle.gameElements.resources.Workable;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.teams.Teams;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class Worker extends Unit
{
	private static final String TAG = "Worker";


	private static final ImageFormatInfo imageFormatInfo;

	private enum WorkerState{
		COLLECTING , DEPOSITING , RETURNING , NOTHING , GOING_TO_NEW_JOB
	}



	private static final Attributes staticAttributes; @Override
	protected Attributes getStaticLQ() { return staticAttributes; }
	private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES; @Override
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }

	private static Cost cost = new Cost( 0 , 50 , 0 , 1 );

	private static final ArrayList<Order> possibleOrders;


	static
	{
		float dp = Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( R.drawable.nobbynobbs_red , 0 ,
				0 , 0 , 1 , 1 );
		imageFormatInfo.setNumHorzImages( 4 );
		imageFormatInfo.setNumVertImages( 4 );


		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();
		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared( 22500 * dp * dp );
		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared( 22500 * dp * dp );
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );//+ 2*2*Rpg.getDpSquared() );
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 0 );  STATIC_ATTACKER_ATTRIBUTES.setdDamageAge( 0 ); STATIC_ATTACKER_ATTRIBUTES.setdDamageLvl( 0 ); // 0 );
		STATIC_ATTACKER_ATTRIBUTES.setROF( 1000 );


		staticAttributes = new Attributes(); staticAttributes.setRequiresAge(Age.STONE); staticAttributes.setRequiresTcLvl(1);
		staticAttributes.setLevel( 1 );
		staticAttributes.setFullHealth( 100 );
		staticAttributes.setHealth( 100 ); staticAttributes.setdHealthAge( 50 ); staticAttributes.setdHealthLvl( 10 ); //100 );
		staticAttributes.setFullMana( 0 );
		staticAttributes.setMana( 0 );
		staticAttributes.setHpRegenAmount( 1 );
		staticAttributes.setRegenRate( 2000 );
		staticAttributes.setSpeed( 2f * dp );

		possibleOrders = new ArrayList<>();
		possibleOrders.add( new BuildThis()                );
		possibleOrders.add( new BuildDeco()                );
		possibleOrders.add( CancelAction.getInstance()     );
		//possibleOrders.add( DepositResources.getInstance() );
	}


	private Job fJob;
	private Workable job;
	private Building depot;

	private RT resourceType;

	private int collectedResources;
	protected int maxHeldResources = 20;
	private boolean mustDropOffResources;
	public int collectInOneAttempt = 1;

	private long lastActed;
	protected int actEvery = 2000;

	protected WorkerAnimations workerAnims;



	private final Vector directionOfJob = new Vector();

	private Path pathToResource;
	private Path pathFromDepotToResource;
	private Path pathToDepot;

	private long pathFindingTimeOut;
	private long getJobTimeout;
	private long watchDogTimeout = System.currentTimeMillis() + 30000;

	private Forman forman;
	private WorkerState state = WorkerState.NOTHING;

	private boolean waitingForPathingResult;


	private static int dCollectsLvl = 1;
	private static int dCollectsAge = 2;
	private static int dHoldsLvl = 5;
	private static int dHoldsAge = 10;

	{
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, getLQ().getBonuses() ) );
		setStance( Stance.FREE );
	}


	public Worker(){}

	public Worker( Vector loc , Teams team ){
		super(team);
		super.loc.set(loc);
	}

	@Override
	public void finalInit( MM mm )
	{
		super.finalInit( mm );
		workerAnims = new WorkerAnimations( this , mm );

		workerAnims.setAnimation( WorkerAnims.AXE );

		forman = mm.getTM().getTeam( getTeamName() ).getForman();

		findNewJob();
		setStance( Stance.FREE );
		//Log.d( TAG , "finalInit() resType=" + resourceType + ", collected=" + collectedResources );
	}



	@Override
	public boolean act()
	{
		if ( lq.getHealth() <= 0 )
		{
			die();
			return true;
		}

		if( isGarrisoned() )
		{
			if( itsTimeToCollectResources( actEvery ) )
			{
				Building garBuild = (Building) garrisonBuilding;
				if( garBuild != null )//&& garBuild.lq != null )
				{

					//Collect resources if worker is inside a goldmine or lumber mill
					collectedResources += garBuild.removeResources( collectInOneAttempt );
					resourceType = garBuild.getResourceType();
					if( resourceType == RT.GOLD || resourceType == RT.WOOD || resourceType == RT.FOOD )
						job = garBuild;
					depositResources();

					//Clear the collected resources incase the resources werent be deposited
					collectedResources = 0;
				}
			}
			return isDead();
		}

		if( state != WorkerState.NOTHING && (resourceType != RT.BUILDING && resourceType != RT.BUILDING_REPAIR ) && watchDogTimeout < System.currentTimeMillis() )
		{
			clearJobAndPaths();
			watchDogTimeout = System.currentTimeMillis() + 30000;
			//////////Log.d( TAG + getTeamName() , "WatchDog timer ran out, clearing job");
		}

		float jobDist = Float.MAX_VALUE;
		GameElement jobGe = null;


		//check job status
		{
			//Log.d( TAG , "Check job status, currently job = " + job );

			//check to see if job is a newly completed farm or pile-o-corps
			if( job != null && job.isDone() && job instanceof PendingBuilding && BuildingsUtil.isaPendingFarmOrPileOCorps((PendingBuilding) job) )
			{
				//Log.d( TAG + getTeamName() , "food source complete, setting job to food source");
				setJob( ((PendingBuilding)job).getBuildingToBuild() );
			}

			if( Settings.yourBaseMode ){
				job = job != null ? ((job.isDone()) ? null : job) : null;
			}
			else{
				job = job != null ? ((job.isDone()) ? null : job) : null;
			}
			fJob = fJob != null ? (fJob.isDone() ? null : fJob) : null;


			if( job == null )
			{
				unlockLookDirection();
				pathToDepot = null;
				pathToResource = null;
				pathFromDepotToResource = null;
			}
			if( job == null && fJob != null )
			{
				//Log.d( TAG + getTeamName() , "job == null && fJob != null");
				job = fJob.getResource();
				resourceType = job.getResourceType();
				depot = fJob.getDepot();
				pathToDepot = fJob.getPathToDepot();
				pathFromDepotToResource = fJob.getPathToResource();
			}

			if( job != null )
			{
				if( job.getResourceType() == RT.BUILDING || job.getResourceType() == RT.BUILDING_REPAIR )
				{
					fJob = null;
					pathToDepot = null;
					pathFromDepotToResource = null;
				}

				if( job instanceof GameElement )
				{
					jobGe = (GameElement) job;
					jobDist = jobGe.area.contains( loc.x , loc.y ) ? 1  : loc.distanceSquared( jobGe.area );
				}
			}

			if( job == null && resourceType != null )
				findNewJob();

			//Log.d( TAG , "Check job status, now job = " + job );
		}

		Workable j = job;
		Job fJob = this.fJob;

		WorkerState prevState = state;
		WorkerState state;
		this.state = determineState( jobDist , j );

		synchronized( this.state ){
			state = this.state;
		}

		if( state != prevState )
		{
			////Log.d( TAG , "Changing WorkerState from " + prevState + " to " + state );
			watchDogTimeout = System.currentTimeMillis() + 30000;
			setPathToFollow( null );
			destination = null;
			stayHere = null;

			if( state == WorkerState.DEPOSITING )
				unlockLookDirection();
		}


		if( jobDist == Float.MAX_VALUE && j != null )
			jobDist = j.getArea().contains( loc.x , loc.y ) ? 1  : loc.distanceSquared( j.getArea() );


		switch( state )
		{
		case GOING_TO_NEW_JOB:
			if( jobDist < getAQ().getAttackRangeSquared() )
			{
				synchronized( state ){
					state = WorkerState.COLLECTING;
				}
			}
			else if( getPathToFollow() == null )
				findPathToJob( j );
			else
				legsAct();

			break;

		case COLLECTING:

			if( itsTimeToCollectResources( actEvery ) )
			{

				if( !j.isDead() && j.getResourceType() != resourceType )
				{
					collectedResources = 0 ;
					resourceType =  j.getResourceType();
				}

				directionOfJob.set( j.getLoc().x - loc.x , j.getLoc().y - loc.y );
				//getArms().act( directionOfJob );

				collectedResources += j.removeResources( collectInOneAttempt );

				if( resourceType == RT.BUILDING || resourceType == RT.BUILDING_REPAIR )
					collectedResources = 0;
			}

			getArms().act( directionOfJob ); //Moved this out to get the worker to act continuously

			legsAct();
			break;


		case DEPOSITING:

			if( (resourceType == RT.METAL || resourceType == RT.GOLD ) && Math.random() < 0.07 )
			{
				Building dp = forman.getClosestDepot( loc );
				if( dp != depot && dp != null )
				{
					clearJobAndPaths();
					depot = dp;
					job = j;
				}
			}
			if( depot == null )
			{
				if( fJob != null )
					depot = fJob.getDepot();

				if( depot == null )
					depot = forman.getClosestDepot( loc );
			}

			if( depot == null )
				break; // try again next chance


			float depotDist = getDepotDistance();

			if( depotDist < getAQ().getAttackRangeSquared() )
				depositResources();

			else if( getPathToFollow() == null )
			{
				if( depotDist < 2*getAQ().getAttackRangeSquared() )
				{
					legs.moveTowards( depot.loc );
					break;
				}
				else if( pathToDepot != null )
				{
					pathToDepot.setIndexOfNextNode(1);
					setPathToFollow(pathToDepot);
				}
				else if( fJob != null && fJob.getPathToDepot() != null )
				{
					pathToDepot = new Path( fJob.getPathToDepot() );
					pathToDepot.setIndexOfNextNode(1);
					setPathToFollow(pathToDepot);
				}
				else
					findPathToDepot( depot );
			}

			legsAct();

			break;


		case RETURNING:

			if( prevState == WorkerState.DEPOSITING )
			{
				pathToResource = null;

				if( getPathToFollow() == null )
				{

					if( jobDist < getAQ().getAttackRangeSquared() )
					{

					}
					//					else if( jobDist < 3*getAQ().getAttackRangeSquared() )
					//					{
					//						legs.moveTowards( j.getLoc() );
					//						break;
					//					}
					else if( pathFromDepotToResource != null )
					{
						pathFromDepotToResource.setIndexOfNextNode(1);
						setPathToFollow( pathFromDepotToResource );
					}
					else if( fJob != null )
					{
						pathFromDepotToResource = new Path( fJob.getPathToResource() );
						pathFromDepotToResource.setIndexOfNextNode(1);
						setPathToFollow( pathFromDepotToResource );
					}
					else
						findPathFromDepotToJob( j );

				}
			}
			else
			{
				if( getPathToFollow() == null )
				{
					if( jobDist < getAQ().getAttackRangeSquared() )
					{

					}
					//					else if( jobDist < 3*getAQ().getAttackRangeSquared() )
					//					{
					//						legs.moveTowards( j.getLoc() );
					//						break;
					//					}
					else if( pathFromDepotToResource != null )
					{
						pathFromDepotToResource.setIndexOfNextNode( 1 );
						setPathToFollow( pathFromDepotToResource );
					}
					else if( pathToResource == null )
						findPathToJob( j );
					else
						setPathToFollow( pathToResource );

				}
			}
			//
			//			if( depot != null )
			//			{
			//				if( pathFromDepotToResource == null )
			//				{
			//					if( fJob != null ){
			//						pathFromDepotToResource = new Path( fJob.getPathToResource() );
			//						if( prevState != state ){
			//							pathFromDepotToResource.setIndexOfNextNode(1);
			//						}
			//					}
			//					else{
			//						findPathFromDepotToJob( j );
			//					}
			//				}
			//			}



			//
			//			Path pathToFollow = getPathToFollow();
			//
			//			if( pathToFollow == null )
			//			{
			//				if( pathFromDepotToResource != null )
			//				{
			//					setPathToFollow( pathFromDepotToResource );
			//					if( prevState != state ){
			//						pathFromDepotToResource.setIndexOfNextNode(1);
			//					}
			//				}
			//			}

			//			if( jobDist < Rpg.twentyDpSquared )
			//			{
			//				legs.moveTowards( j.getLoc() );
			//			}
			//			else
			//			{
			legsAct();
			//}
			break;


		default:
		case NOTHING:
			//			Path path = getPathToFollow();
			//			if( path != null )
			//			{
			//
			//			}

			legsAct();
			break;

		}

		Arms.makeAttackAct(getAQ().getCurrentAttack());


		checkBeingStupid();
		regen();
		return isDead();
	}




	private WorkerState determineState( float jobDist, Workable j )
	{
		if( collectedResources >= maxHeldResources || mustDropOffResources )
			return WorkerState.DEPOSITING;


		mustDropOffResources = false;

		if( j == null )
			return WorkerState.NOTHING;
		else
		{
			if( jobDist < getAQ().getAttackRangeSquared() )
				return WorkerState.COLLECTING;
			else if( state == WorkerState.GOING_TO_NEW_JOB )
				return WorkerState.GOING_TO_NEW_JOB;
			else
				return WorkerState.RETURNING;
		}
	}


	@Override
	protected void wander() {
	}






	public void findNewJob()
	{
		if( getJobTimeout > System.currentTimeMillis() )
			return;

		if( job != null && !job.isDone() )
			return;


		if( job != null && job instanceof PendingBuilding )
		{
			Building b = ((PendingBuilding) job).getBuildingToBuild();

			switch( b.getBuildingsName() ){
			default: break;
			case Farm:
				Farm farm = ((Farm) b);
				goGarrisonInsideOf(farm );
				return;

			case PileOCorps:
				PileOCorps pileOCorps = ((PileOCorps) b);
				goGarrisonInsideOf( pileOCorps );
				return;
				//				if( pileOCorps.isAvailable() )
				//				{
				//
				//					setJob( pileOCorps );
				//					return;
				//				}
				//				break;
			case LumberMill:
				LumberMill mill = ((LumberMill) b);
				goGarrisonInsideOf(mill);
				return;
			case GoldMineBuilding:
				GoldMineBuilding gMine = ((GoldMineBuilding) b);
				goGarrisonInsideOf(gMine);
				return;
			}
		}

		if( resourceType == null )
			return;


		depot = null;
		if( forman != null )
		{
			forman.heyINeedAJob( this , resourceType );
			getJobTimeout = System.currentTimeMillis() + 5000;
		}
	}




	private void depositResources()
	{
		if( resourceType != RT.BUILDING )
		{
			forman.depositResources( resourceType , collectedResources );

			if( forman.isFullOf( resourceType )){
				clearJobAndPaths();
				collectedResources = 0;
				return;
			}
		}

		collectedResources = 0;

		workerAnims.setAnimation( resourceType , false );

		setPathToFollow( null );
		pathToResource = null;

		mustDropOffResources = false;
	}

	private float getDepotDistance()
	{
		float depoDistanceSquared = 1;
		if( !depot.area.contains( loc.x , loc.y ) )
			depoDistanceSquared = loc.distanceSquared( depot.area );

		return depoDistanceSquared;
	}




	private boolean itsTimeToCollectResources( int actEvery )
	{
		if( lastActed + actEvery < System.currentTimeMillis() )
		{
			lastActed = System.currentTimeMillis();
			return true;
		}
		return false;
	}


	public int getCollectionRate(){
		return collectInOneAttempt;
	}







	public void logThis( Workable job2 )
	{
		if( job2 == null )
			throw new IllegalArgumentException( "tree == null" );

		if( job2 instanceof LumberMill ){
			goGarrisonInsideOf((LumberMill) job2);
			return;
		}


		job = job2 ;
		resourceType = job2.getResourceType();

		workerAnims.setAnimation( WorkerAnims.AXE );

		findPathToJob( job2 );
	}

	public void mineThis( Workable job2 )
	{
		if( job2 == null )
			throw new IllegalArgumentException( "mine == null" );


		if( job2 instanceof GoldMineBuilding ){
			goGarrisonInsideOf((GoldMineBuilding) job2);
			return;
		}

		job = job2 ;
		resourceType = job2.getResourceType();
		workerAnims.setAnimation( WorkerAnims.PICK );



		findPathToJob( job2 );
	}

	public void buildThis( PendingBuilding pendingBuilding )
	{
		if( pendingBuilding == null )
			throw new IllegalArgumentException( "pendingBuilding == null" );

		if( pendingBuilding.getTeamName() != getTeamName() )
			throw new IllegalArgumentException( "pendingBuilding.getTeam() != getTeam()" );


		job = pendingBuilding ;
		//ignoreCollisionDetectionFor = pendingBuilding;
		resourceType = pendingBuilding.getResourceType();


		workerAnims.setAnimation( WorkerAnims.HAMMER );


		findPathToJob( pendingBuilding );
	}

	public void repairThis( Building building )
	{
		if( building == null )
			throw new IllegalArgumentException( " building == null ");


		Attributes lq = building.getLQ();

		if( lq.getFullHealth() == lq.getHealth() )
			return;


		if( building.getTeamName() != team )
			throw new IllegalArgumentException( " building.getTeamName() != team ");

		if( !building.dead )
			if( building instanceof Farm )
				harvestThis( building );


		job = building;
		workerAnims.setAnimation( WorkerAnims.HAMMER );
		resourceType = RT.BUILDING_REPAIR;
		findPathToJob( building );
	}

	public void harvestThis( Workable foodSource )
	{
		if( foodSource == null )
			throw new IllegalArgumentException( "foodSource == null" );


		if( foodSource instanceof Farm )
		{
			Farm farm = (Farm) foodSource;

			if( farm.getTeamName() != getTeamName() && KingsCastle.testingVersion )
				throw new IllegalArgumentException( "((Farm)foodSource).getTeam() != getTeam()" );

			if( foodSource instanceof Farm ){
				goGarrisonInsideOf((Farm) foodSource);
				return;
			}
		}
		else if( foodSource instanceof PileOCorps )
		{
			PileOCorps pileOCorps = (PileOCorps) foodSource;

			if( pileOCorps.getTeamName() != getTeamName()  && KingsCastle.testingVersion )
				throw new IllegalArgumentException( "((PileOCorps)pileOCorps).getTeam() != getTeam()" );


			if( pileOCorps.isAvailable() )
				pileOCorps.setCurrentFarmer( this );
			else
				return;

		}


		job = foodSource ;
		//		if( foodSource instanceof GameElement ) {
		//			ignoreCollisionDetectionFor = (GameElement) foodSource;
		//		}
		resourceType = foodSource.getResourceType();

		workerAnims.setAnimation( WorkerAnims.HOE );

		findPathToJob( foodSource );
	}






	public synchronized void setFJobAndPathThere( Job job2 , Path pathToJob )
	{
		if( job2 == null || pathToJob == null ){
			//////Log.e( TAG , "job2 == "+job2+" || pathToJob == "+pathToJob+"" );
			return;
		}
		setFJob( job2 );
		setPathToFollow( pathToJob );
		synchronized( state ){
			state = WorkerState.GOING_TO_NEW_JOB;
		}
	}

	void setFJob(Job job)
	{
		////////Log.d( TAG + getTeamName() , "setFJob("+job+")" );
		if( job != null )
		{
			synchronized( job )
			{
				fJob = job;

				Job nFJob = fJob;
				if( nFJob != null ){
					if( nFJob.getResource() == null )
						fJob = null;


					waitingForPathingResult = true;
					Workable j = nFJob.getResource();

					if( j == null )
						return;

					setJob( j );
					resourceType = j.getResourceType();

					if( !(j instanceof Building) )
					{
						pathFromDepotToResource = new Path(nFJob.getPathToResource());

						pathToDepot = new Path(nFJob.getPathToDepot());
					}

					waitingForPathingResult = false;
				}
			}
		}
		else
		{
			if( fJob != null )
				synchronized( fJob )
				{
					fJob = null;
				}

		}
	}

	public void setJob( Workable job )
	{
		////////Log.d( TAG + getTeamName() , "setJob("+job+")" );
		if( job == null )
		{
			Job fJob = this.fJob;
			if( fJob != null )
			{
				synchronized( fJob )
				{
					this.fJob = null;
				}
			}
			this.job = null;
			return;
		}

		destination = null;
		stayHere = null;


		switch( job.getResourceType() )
		{
		case BUILDING_REPAIR:
			if( !(job instanceof Building) )
				throw new IllegalArgumentException(" !(job instanceof Building) ");

			repairThis( (Building) job );
			break;

		default:
		case BUILDING:
			buildThis( (PendingBuilding) job);
			break;

		case FOOD:
			harvestThis( job );
			break;

		case GOLD:
		case METAL:
			mineThis( job );
			break;

		case WOOD:
			logThis( job );
			break;
		}

	}




	private void findPathFromDepotToJob( final Workable job )
	{
		if( !waitingForPathingResult && pathFindingTimeOut < System.currentTimeMillis() )
		{
			if( Rpg.getGame().getState() == GameState.InGamePlay )
			{

				if( depot != null )
				{
					////////Log.d( TAG , "Finding path to resource from depot, " + getTeamName() );
					waitingForPathingResult = true;
					pathFindingTimeOut = System.currentTimeMillis() + 2000;


					pathFromDepotToResource = null;
					Vector jobLocTemp = null;
					Vector depotLocTemp = null;
					try
					{
						jobLocTemp = gUtil.getWalkableLocNextToThis( depot.loc , job.getArea() );
						depotLocTemp = gUtil.getWalkableLocNextToThis( jobLocTemp , depot.area );
					}
					catch( Exception e )
					{
						if( KingsCastle.testingVersion )
							e.printStackTrace();
						clearJobAndPaths();
						return;
					}

					if( jobLocTemp == null || depotLocTemp == null ){
						clearJobAndPaths();
						return;
					}

					final Vector jobLoc = jobLocTemp;
					final Vector depotLoc = depotLocTemp;

					PathFinder.findPath(gUtil.getGrid(), depotLoc, jobLoc, new PathFoundListener() {

                        @Override
                        public void onPathFound(Path path) {
                            ////////Log.d( TAG , "onPathFound( " + path + " ), " + getTeamName() );
                            pathFromDepotToResource = path;
                            if (path == null) {
                                clearJobAndPaths();
                                resourceType = null;
                            }
                            waitingForPathingResult = false;
                        }

                        @Override
                        public void cannotPathToThatLocation() {
                            if (KingsCastle.testingVersion) {
                                //////Log.d( TAG , getTeamName() + " cannotPathToThatLocation() job=" + job );
                                DarkStarAnim dsa = new DarkStarAnim(job == null ? loc : job.getLoc());
                                dsa.setAliveTime(60000);
                                dsa.setLooping(true);
                                MM mm = MM.get();
                                if (mm != null)
                                    mm.getEm().add(dsa);
                            }
                            if (fJob != null)
                                forman.cannotMakeItToJob(fJob);
                            clearJobAndPaths();
                            resourceType = null;

                        }
                    });

				}
			}
		}
	}

	private void findPathToJob( final Workable job )
	{
		if( !waitingForPathingResult && pathFindingTimeOut < System.currentTimeMillis() )
		{
			if( Rpg.getGame().getState() == GameState.InGamePlay )
			{
				pathToResource = null;

				////////Log.d( TAG , "Finding path to resource , " + getTeamName() );
				pathFindingTimeOut = System.currentTimeMillis() + 2000;
				waitingForPathingResult = true;



				Vector jobLocTemp = null;
				Vector mLocTemp = null;
				try
				{
					jobLocTemp = gUtil.getWalkableLocNextToThis( loc , job.getArea() );
					mLocTemp = gUtil.getClosestAdjWalkableTile( loc );
				}
				catch( Exception e )
				{
					if( KingsCastle.testingVersion ){

						DarkStarAnim dsa = new DarkStarAnim( job.getLoc() );
						dsa.setLooping( true );
						dsa.setAliveTime( 30000 );
						MM mm = MM.get();
						if( mm != null )
							mm.getEm().add( dsa );
						e.printStackTrace();
					}
					clearJobAndPaths();
					return;
				}
				if( jobLocTemp == null || mLocTemp == null ){
					clearJobAndPaths();
					return;
				}
				final Vector jobLoc = jobLocTemp;
				final Vector mLoc = mLocTemp;

				PathFinder.findPath( gUtil.getGrid(), mLoc , jobLoc , new PathFoundListener(){

					@Override
					public void onPathFound( Path path )
					{
						////////Log.d( TAG , "onPathFound( " + path + " ), " + getTeamName() );
						pathToResource = path;
						setPathToFollow( path );
						if( path == null )
						{
							clearJobAndPaths();
							resourceType = null;
						}
						waitingForPathingResult = false;
					}
					@Override
					public void cannotPathToThatLocation() {
						if( KingsCastle.testingVersion )
						{
							//////Log.d( TAG , getTeamName() + " cannotPathToThatLocation() job=" + job );
							DarkStarAnim dsa = new DarkStarAnim( job == null ? loc : job.getLoc() );
							dsa.setAliveTime( 60000 );
							dsa.setLooping( true );
							MM mm = MM.get();
							if( mm != null )
								mm.getEm().add( dsa );
						}
						if( fJob != null )
							forman.cannotMakeItToJob( fJob );
						clearJobAndPaths();
						resourceType = null;
					}
				});
			}
		}
	}

	private void findPathToDepot( final Building depot )
	{
		if( !waitingForPathingResult && pathFindingTimeOut < System.currentTimeMillis() )
		{
			if( Rpg.getGame().getState() == GameState.InGamePlay )
			{
				//////////Log.d( TAG , "findPathToDepot( "+ depot +" )" );


				if( depot != null )
				{
					////////Log.d( TAG , "Finding path to depot , " + getTeamName() );
					waitingForPathingResult = true;
					pathFindingTimeOut = System.currentTimeMillis() + 2000;



					Vector jobLocTemp = null;
					Vector depotLocTemp = null;
					try
					{
						if( job != null )
							jobLocTemp = gUtil.getWalkableLocNextToThis( depot.loc , job.getArea());
						else
							jobLocTemp = gUtil.getClosestAdjWalkableTile(loc);
						depotLocTemp = gUtil.getWalkableLocNextToThis( jobLocTemp , depot.area);
					}
					catch( Exception e )
					{
						if( KingsCastle.testingVersion )
							e.printStackTrace();
						clearJobAndPaths();
						return;
					}

					if( jobLocTemp == null || depotLocTemp == null ){
						clearJobAndPaths();
						return;
					}

					final Vector jobLoc = jobLocTemp;
					final Vector depotLoc = depotLocTemp;



					PathFinder.findPath( gUtil.getGrid() , jobLoc , depotLoc , new PathFoundListener(){
						@Override
						public void onPathFound(Path path)
						{
							////////Log.d( TAG , "onPathFound( " + path + " ), " + getTeamName() );
							pathToDepot = path ;
							if( path == null )
							{
								clearJobAndPaths();
								resourceType = null;
								Vector v  = new Vector( loc , depot.loc );
								v.divideBy( 10 );
								loc.add( v );
							}
							waitingForPathingResult = false;
						}
						@Override
						public void cannotPathToThatLocation() {
							if( KingsCastle.testingVersion )
							{
								//////Log.d( TAG , getTeamName() + " cannotPathToThatLocation() job=" + job );
								DarkStarAnim dsa = new DarkStarAnim( job == null ? loc : job.getLoc() );
								dsa.setAliveTime( 60000 );
								dsa.setLooping( true );
								MM mm = MM.get();
								if( mm != null )
									mm.getEm().add( dsa );
							}
							if( fJob != null )
								forman.cannotMakeItToJob( fJob );

							clearJobAndPaths();
							resourceType = null;
						}
					});

				}
			}
		}
	}

	protected void addExtraNode( Workable job , Path path )
	{
		if( path != null && path.getPath() != null && !path.getPath().isEmpty() )
		{
			ArrayList<Vector> nodes = path.getPath();

			Vector jobLoc = job.getLoc();
			nodes.add( jobLoc );
		}
	}





	@Override
	public void takeDamage( int damage , LivingThing hurter)
	{
		if( getTeamName() != Teams.BLUE )
		{
			goGarrisonSomewhere();
		}
		super.takeDamage(damage, hurter);
	}



	void goGarrisonSomewhere(){
		forman.helpImBeingAttacked(this);
	}
	@Override
	public void goGarrisonInsideOf( Garrison garrison )
	{
		clearJobAndPaths();
		super.goGarrisonInsideOf(garrison);
	}


	@Override
	public ArrayList<Order> getPossibleOrders()
	{
		//////////Log.v(TAG , "getPossibleOrders()");
		ArrayList<Order> orders = new ArrayList<Order>();

		orders.add( BuildThis.get() );
		orders.add( BuildDeco.get() );

		if( job != null )
		{
			orders.add( CancelAction.getInstance() );
			//////////Log.v(TAG , "job == null, order removed? " + success );
		}
		if( collectedResources != 0 && resourceType != null )
		{
			orders.add( DepositResources.getInstance() );
			//////////Log.v(TAG , "collectedResources == 0 && resourceType != null, order removed? " + success );
		}

		return orders;
	}




	@Override
	public void loadAnimation( MM mm )
	{
		if ( aliveAnim == null )
		{
			aliveAnim = new FourFrameAnimator( this , getImages() );
			mm.getEm().add( aliveAnim , true );

			addHealthBarToAnim( mm.getRace(team));
		}
	}



	public void upgradeToAge( Age age , MM mm )
	{

		aliveAnim.setOver( true );
		aliveAnim = null;

		healthBar.setOver( true );
		healthBar = null;

		loadAnimation( mm );
		workerAnims.setWorkerAnim( getAnim() );
	}


	@Override
	public void saveYourself( BufferedWriter b ) throws IOException
	{
		saveYourself( b , false );
	}

	@Override
	public void saveYourself( BufferedWriter b , boolean ignoreGarrisonCheck ) throws IOException
	{
		if( !ignoreGarrisonCheck && garrisoned )
			return;

		String s;
		if( Settings.savingYourBase )
			s = "<" + getClass().getSimpleName() + " team=\""+ getTeamName() + "\" squad=\"" + getSquad() + "\" squadType=\"-1\" exp=\""+ 0
			+ "\" healthPercent=\"" + getLQ().getHealthPercent() + "\" x=\"" + (int)(loc.x/Rpg.getDp()) + "\" y=\"" + (int)(loc.y/Rpg.getDp()) + "\" "
			+ " resourceType=\"" + resourceType + "\" collectedResources=\"" + collectedResources + "\" />";
		else
			s = "<" + getClass().getSimpleName() + " team=\""+ getTeamName() + "\" squad=\"" + getSquad() + "\" squadType=\"-1\" exp=\""+ 0
			+ "\" healthPercent=\"" + getLQ().getHealthPercent() + "\" x=\"" + loc.getIntX() + "\" y=\"" + loc.getIntY() + "\" "
			+ " resourceType=\"" + resourceType + "\" collectedResources=\"" + collectedResources + "\" />";

		//////Log.d( TAG , s );
		b.write( s , 0 , s.length() );
		b.newLine();

	}




	@Override
	public Attributes getNewAttributes()
	{
		return new Attributes( staticAttributes );
	}

	@Override
	public ImageFormatInfo getImageFormatInfo()
	{
		return imageFormatInfo;
	}

	@Override
	public Image[] getStaticImages(){
		return getImages();
	}

	@Override
	public void setStaticImages( Image[] images )
	{
	}


	public Workable getJob()
	{
		return job;
	}




	public int getCollectedResources()
	{
		return collectedResources;
	}

	public void setCollectedResources(int collectedResources)
	{
		this.collectedResources = collectedResources;
	}




	public int getCollectInOneAttempt() {
		return collectInOneAttempt;
	}

	public void setCollectInOneAttempt(int collectInOneAttempt) {
		this.collectInOneAttempt = collectInOneAttempt;
	}



	public int getMaxHeldResources() {
		return maxHeldResources;
	}

	public void setMaxHeldResources(int maxHeldResources) {
		this.maxHeldResources = maxHeldResources;
	}





	public Building getResourceDepositBuilding()
	{
		return depot;
	}

	public void setResourceDepositBuilding(Building resourceDepositBuilding)
	{
		depot = resourceDepositBuilding;
	}




	@Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost)
	{
		Worker.cost = cost;
	}




	public RT getResourceType() {
		return resourceType;
	}

	public void setResourceType(RT resourceType) {
		this.resourceType = resourceType;
	}




	@Override
	public String getName()
	{
		return TAG;
	}




	@Override
	public boolean thisLocationMayBeOfInterestToYou(Vector vtemp2)
	{
		return false;
	}


	@Override
	public boolean isMovingIntoFormation()
	{
		return isWalking();
	}



	@Override
	public SoldierType getSoldierType(){
		return SoldierType.WORKER;
	}


	public void setMustDropOffResources(boolean b)
	{
		mustDropOffResources = b;
	}


	@Override
	public Image[] getImages()
	{
		return WorkerImages.getImages( this );
	}





	public void clearJobAndPaths()
	{
		////Log.d( TAG + getTeamName() , "clearJobAndPaths()" );
		setFJob( null );
		setJob( null );
		pathToResource = null;
		pathFromDepotToResource = null;
		pathToDepot = null;
		setPathToFollow( null );
		walkToAndStayHere( null );
		synchronized( state )
		{
			state = WorkerState.NOTHING;
		}
		unlockLookDirection();
	}



	@Override
	public void setSelected( boolean b )
	{
		super.setSelected(b);
		if( KingsCastle.testingVersion )
		{
			Workable job = this.job;
			if( job != null )
				job.setSelected( b );
		}
	}



	@Override
	protected void upgrade(){
		super.upgrade();

		int lvl = lq.getLevel();
		collectInOneAttempt = 1 + lvl*dCollectsLvl + dCollectsAge*lq.getAge().ordinal();
		maxHeldResources = 20 + lvl*dHoldsLvl + dHoldsAge*lq.getAge().ordinal();
	}





	public void setForman(Forman forman2) {
		forman = forman2;
	}




	public int getdCollectsLvl() {
		return dCollectsLvl ;
	}

	public int getdHoldsLvl() {
		return dHoldsLvl;
	}





}
