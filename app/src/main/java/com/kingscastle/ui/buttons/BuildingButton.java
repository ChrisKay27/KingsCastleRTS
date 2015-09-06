package com.kingscastle.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.implementation.ImageDrawable;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.Buildings;
import com.kingscastle.level.GridUtil;
import com.kingscastle.level.PR;
import com.kingscastle.teams.Team;
import com.kingscastle.ui.BuildingBuilder;
import com.kingscastle.ui.InfoDisplay;
import com.kingscastle.ui.Selecter;
import com.kingscastle.ui.UI;


public class BuildingButton extends SButton
{
	private static final String TAG = "BuildingButton";


	protected final Team team;
	@Nullable
    private Buildings assocBuilding;
	private Building building;



	protected BuildingButton( Activity a , Team t ){
		super( a );
		team = t;
	}

	private BuildingButton( @NonNull Activity a , CD cd, GridUtil gUtil , @NonNull Building b, @NonNull Team t, UI ui, @NonNull final InfoDisplay infDisplay , Selecter selecter_ ,BuildingBuilder bb_ ){
		super( a );

		team = t;
		setBuilding(b);
		if( b.getBuildingsName() == null )
			throw new IllegalStateException(" assocBuilding == null for " + b );
		setAssocBuilding( b.getBuildingsName() );

		//Determine what onClickListener to attach
		OnClickListener cl;

		boolean buyable = true;
		String b2 = t.canPurchase( null , b );
		String[] parts = b2.split(":");

		String msg = null;
		if( parts.length == 0 )
			buyable = false;
		else{
			if( "No".equals(parts[0]) ){
				buyable = false;
				if( parts.length>1 )
					msg = parts[1];
			}
		}
		if( msg == null )
			msg = a.getString(R.string.you_cannot_buy_anymore);

		//Log.d( TAG , "Buyable? " + b2 );


		if( !buyable ){
			setGrayedOut( true );
			cl = new MustAdvanceOnClickListener( msg , ui );
		}
		else
			cl = new onPBClickListener( a , ui , cd, gUtil, b , team , selecter_, bb_ );

		setOnClickListener( cl );


		//Add buildings image
		Image img = b.getImage();
		if( img == null )
			throw new NullPointerException("Image for " + b + " is null");

		int x = buttonBase.getWidthDiv2() - img.getWidthDiv2();
		int y = buttonBase.getHeightDiv2() - img.getHeightDiv2();



		ImageDrawable id = new ImageDrawable( img.getBitmap() , x , y , new Paint());
		ImageView iv = new ImageView(a);
		iv.setBackgroundDrawable(id);
		addView(iv);

		addQuestionMark(new OnClickListener() {
			@Override
			public void onClick(View v) {
				infDisplay.showInfoDisplay( building , team , new OnClickListener() {
					@Override
					public void onClick(View v) {
						infDisplay.hide();
					}
				});
			}
		});





		// Add counter showing curr/max number of buildings built
		//		int count = t.getNumberOf    ( building.getBuildingsName() );
		//		int max   = t.getMaxAllowedOf( building.getBuildingsName() );
		//
		//		TextView counter = new TextView( a );
		//		UIUtil.applyKcStyle(counter);
		//		counter.setText(count+"/"+max);
		//		counter.setTranslationX(buttonBase.getWidth()-counter.getPaint().measureText(counter.getText().toString())-Rpg.twoDp);
		//		counter.setTranslationY(buttonBase.getHeight()-counter.getPaint().getFontSpacing()-Rpg.twoDp);
		//		addView(counter);
	}




	@NonNull
    public static BuildingButton getInstance( @NonNull Activity a , @NonNull Building b, @NonNull Team t , UI ui , @NonNull InfoDisplay infDisplay , Selecter selecter_ ,BuildingBuilder bb_ , CD cd, GridUtil gUtil ){
		return new BuildingButton( a , cd, gUtil, b , t ,  ui , infDisplay , selecter_ , bb_ );
	}





	@Nullable
    public Buildings getAssocBuilding()
	{
		return assocBuilding;
	}

	void setAssocBuilding(@Nullable Buildings assocBuilding)
	{
		if( assocBuilding == null )
			throw new IllegalStateException(" assocBuilding == null ");
		this.assocBuilding = assocBuilding;
	}





	@NonNull
    @Override
	public String toString()
	{
		if( assocBuilding == null )
			throw new IllegalStateException(" assocBuilding == null ");
		else
			return assocBuilding.toString() + " Button";
	}





	public Building getBuilding(){
		return building;
	}

	void setBuilding(Building building) {
		this.building = building;
	}








	protected static class onPBClickListener implements OnClickListener{

		private final UI ui;
		private final Building b;
		private final Team team;
		private final Activity a;
		private final Selecter selecter;
		private final BuildingBuilder bb;
		private final CD cd;
		private final GridUtil gUtil;


		public onPBClickListener( Activity a , UI ui,CD cd_, GridUtil gUtil_, Building b , Team team , Selecter selecter_ ,BuildingBuilder bb_ ){
			this.b = b; this.ui = ui;this.team = team; cd = cd_; gUtil = gUtil_; this.a = a;  bb = bb_; selecter = selecter_;
		}


		private long lastClicked;
		@Override
		public void onClick(View v) {
			if( lastClicked + 500 > System.currentTimeMillis() )
				return;
			lastClicked = System.currentTimeMillis();

			final Cost cost = b.getCosts();
			final PR pr = team.getPR();

			final Building pendingBuild = Building.newInstance( b.getClass().getSimpleName() );
			pendingBuild.setTeam(team.getTeamName());
			bb.setPendingBuilding(pendingBuild);
			return;

			//Teams teams = SwitchTeamButton.getInstance().getTeam();
//			pendingBuild.setTeam(team.getTeamName());
//
//			if( !team.canAffordIgnorePop(cost) )
//			{
//				//final int mdCost = MDRC.getMds(cost);
//
//				Log.e( TAG , "team.canAffordIgnorePop(cost) pr.toString()=" + pr.toString() + " cost.toString()=" + cost.toString());
//				String message = "Sorry you cannot afford the "+cost.toResString()+".";// Would you like to spend " + mdCost + " magic dusts to purchase these resources?";
//
//
//				DialogBuilder db = new DialogBuilder( a );
//				db.setNegativeButton( DialogBuilder.BACK, null ).setText( message ).show();
//			}
//			else{
//				if( TowerDefenceGame.testingVersion && Settings.freeMode ){
//					startBuilding( pendingBuild );
//				}
//				else{
//					new DialogBuilder(a)
//					.setText(a.getString( R.string.would_you_like_to_buy_single , b.getName() , cost.toResString() ))
//					.setPositiveButton(DialogBuilder.SURE, new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							startBuilding( pendingBuild );
//						}
//					}).setNegativeButton(DialogBuilder.NO_THANKS, null).show();
//				}
//			}
		}

//		public void startBuilding( Building pendingBuild ){
//
//			if( (TowerDefenceGame.testingVersion && Settings.freeMode) || team.getPR().spend(pendingBuild.getCosts()) ){
//				Building pb = pendingBuild;
//
//				//Find a location for this building
//				TownCenter tc = team.getTownCenter();
//				if( tc != null ){
//					pb.setLoc( tc.getLoc() );
//				}else{
//					ui.getCoordsScreenToMap(Rpg.getWidth()/2, Rpg.getHeight()/2, pb.loc);
//				}
//				final float gridSize = Rpg.gridSize;
//
//				pb.updateArea();
//				gUtil.setProperlyOnGrid(pb.area, gridSize);
//				GridUtil.getLocFromArea(pb.area, pb.getPerceivedArea(), pb.loc);
//
//				final RectF pbArea = pb.area;
//				final RectF pbPercArea = pb.getPerceivedArea();
//				final Vector pbLoc = pb.loc;
//				BuildingPlacer.findAPlaceForThis(gUtil, pbArea, pbPercArea, gridSize, new Checker() {
//					@Override
//					public boolean check() {
//						GridUtil.getLocFromArea(pbArea, pbPercArea, pbLoc);
//
//						b.updateArea();
//						gUtil.setProperlyOnGrid(b.area, gridSize);
//
//						return cd.checkPlaceable2(pbArea, false);
//					}
//				});
//
//
//
//				//Add the building to the game
//				MM.get().add( pb );
//
//
//
//
//
//				//Set the building selected
//				if( !Settings.instantBuild )
//					selecter.setSelected( pb );
//			}
//		}
	}



	protected static class MustAdvanceOnClickListener implements OnClickListener{
		final String msg;
		private final UI ui;
		public MustAdvanceOnClickListener( String msg , UI ui_){
			this.msg = msg; ui = ui_;
		}
		@Override
		public void onClick(View v) {
			ui.warn( msg );
			//InfoMessage.getInstance().setMessage( msg ,  2000 );
		}
	}



}
