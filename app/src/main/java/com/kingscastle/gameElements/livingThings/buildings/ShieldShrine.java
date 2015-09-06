package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.abilities.ShieldBuff;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.BuffAttack;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class ShieldShrine extends Shrine
{

	private static final String TAG = "Shield Shrine";

	public static final Buildings name = Buildings.HealingShrine;

	private static final Image image = Assets.loadImage( R.drawable.satanic_podium );
	private static final Image deadImage = Assets.loadImage(R.drawable.small_rubble);
	private static final Image iconImage = Assets.loadImage( R.drawable.satanic_podium );

	private static RectF staticPerceivedArea = Rpg.oneByOneArea; // this is only the offset from the mapLocation.

	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;

	private static final Cost costs = new Cost( 500 , 0 , 500 , 500 , 0 );

	@NonNull
    @Override
	protected AttackerAttributes getStaticAQ() {
		return STATIC_ATTACKER_ATTRIBUTES;
	}
	@NonNull
    @Override
	protected Attributes getStaticLQ() {
		return STATIC_ATTRIBUTES;
	}

	static
	{

		float dpSquared = Rpg.getDpSquared();
		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared     ( 10 * dpSquared );
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared    ( 10 * dpSquared );
		STATIC_ATTACKER_ATTRIBUTES.setROF( 60000 );

		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 ); // 1 );
		STATIC_ATTRIBUTES.setFullHealth( 500 );
		STATIC_ATTRIBUTES.setHealth( 500 );
		STATIC_ATTRIBUTES.setFullMana( 100 );
		STATIC_ATTRIBUTES.setMana( 100 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 2 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );

		STATIC_ATTRIBUTES.setSpeed( 0 );
	}

	{
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, getLQ().getBonuses() ) );
		ShieldBuff sb = new ShieldBuff(this, null);
		sb.setCaster( this );
		getAQ().setCurrentAttack( new BuffAttack(getMM(), this , sb ) );
	}

	private ShieldShrine()
	{
		super(name);
	}

	public ShieldShrine( @NonNull vector v, Teams t )
	{
		this();
		setTeam(t);
		setLoc(v);
	}
	@Override
	protected void setupAttack() {

	}

	//private TargetingParams targetingParams;

	//	private void createTargetingParams()
	//	{
	//		if( targetingParams == null )
	//		{
	//			targetingParams = new TargetingParams();
	//
	//			targetingParams.setTeamOfInterest( getTeamName() );
	//			targetingParams.setOnThisTeam( true );
	//			targetingParams.setWithinRangeSquared( getAQ().getFocusRangeSquared() );
	//			targetingParams.setFromThisLoc ( loc );
	//		}
	//	}

	@Override
	public void findATarget()
	{
		//		if( lastLookedForTarget + waitLength < GameTime.getTime() )
		//		{
		//			createTargetingParams();
		//
		//			target = TargetFinder.findTarget( targetingParams );
		//
		//			lastLookedForTarget = GameTime.getTime();
		//
		//			////Log.d( TAG ,"Looking for target, found a " + target );
		//		}
	}



	@Override
	public boolean isOutOfRangeOrDead( LivingThing thing1 , LivingThing thingA )
	{
		boolean outOfRange = isOutOfRangeOrDeadORFullHealth( thing1 , thingA );
		////Log.d( TAG ,"Target is out of range or dead or full health" );
		return outOfRange;


		//return isOutOfRangeOrDeadORFullHealth( thing1 , thingA );
	}

	private boolean isOutOfRangeOrDeadORFullHealth( @Nullable LivingThing healer ,
			@Nullable LivingThing healingTarget2 )
	{

		if( healer == null || healingTarget2 == null )
		{
			return true;
		}
		if( healer == healingTarget2 )
		{
			return healingTarget2.lq.getHealth() == healingTarget2.lq.getFullHealth();
		}
		if ( super.isOutOfRangeOrDead ( healer, healingTarget2 ))
		{
			return true;
		}
		else
		{
			if( healingTarget2.lq.getHealth() == healingTarget2.lq.getFullHealth())
			{
				return true;
			}
		}

		return false;
	}





	@Override
	public Image getImage() {
		return image;
	}
	@Override
	public Image getDamagedImage() {
		return image;
	}
	@Override
	public Image getDeadImage() {
		return deadImage;
	}
	@Override
	public Image getIconImage() {
		return iconImage;
	}

	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		return staticPerceivedArea;
	}


	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2)
	{
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}




	@NonNull
    @Override
	public Cost getCosts(){
		return costs;
	}

	@NonNull
    @Override
	public String getName() {
		return TAG;
	}


	@NonNull
    @Override
	public Attributes getNewAttributes() {
		return new Attributes(STATIC_ATTRIBUTES);
	}



}
