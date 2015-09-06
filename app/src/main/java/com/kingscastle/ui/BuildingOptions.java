package com.kingscastle.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingscastle.Game;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.level.Level;
import com.kingscastle.teams.Team;
import com.kingscastle.ui.buttons.DestroyBuildingButton;
import com.kingscastle.ui.buttons.SButton;

import java.util.ArrayList;
import java.util.List;


public class BuildingOptions
{
	private static final String TAG = "BuildingOptions";
	private static final String UPS = "UnitPurchaseScroller";
	private static final String BS = "BuildingScroller";


	protected final ArrayList<SButton> PSButtons = new ArrayList<>();
	protected final ArrayList<SButton> OSButtons = new ArrayList<>();

	protected final UI ui;
	protected final SelectedUI selUI;
	@NonNull
    protected final MM mm;

	@NonNull
    protected final Level level;
	private Game tdg;


	public BuildingOptions( Game tdg, SelectedUI selUI, UI ui , @NonNull Level level )
	{
		this.tdg = tdg;
		this.ui = ui;
		this.selUI = selUI;
		this.level = level;
		this.mm = level.getMM();
	}


	public boolean act()
	{
		return false;
	}


	public void paint(Graphics g){
	}



	public boolean determineIfAndWhatToDisplay( @NonNull Building selB2 )
	{
		final Activity a = tdg.getActivity();

		final Building selB = selB2;
		if( selB == null ){
			//Log.e( TAG , "determineIfAndWhatToDisplay(null)");
			return false;
		}
		ArrayList<SButton> nOSButtons = new ArrayList<>();

		if( !selB.dead ){
			final Team team = mm.getTeam(selB2.getTeamName());

			if( team.isUpgrading(selB) ){
				Log.d( TAG , selB + " is leveling up");
				/* FIXME Upgrades occur instantly on Tower Defence so no need for this button
				nOSButtons.add( CancelButton.getInstance(a, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						new DialogBuilder(a)
								.setText("Are you sure you want to cancel this? You will only be refunded half the cost of the purchase.")
								.setPositiveButton(DialogBuilder.SURE,
										new View.OnClickListener() {
											long lastClicked;

											@Override
											public void onClick(View v) {
												if (lastClicked + 500 > GameTime.getTime())
													return;
												lastClicked = GameTime.getTime();

												Queueable q = selB.getBuildQueue().currentlyBuilding;
												if (q == null)
													return;

												Cost c = new Cost(q.getCosts());
												c.divideBy(2);
												if (selB.getBuildQueue().cancelCurrentlyBuilding()) {
													team.getPR().refund(c);
													refresh(selB);
												}
											}
										})
								.setNegativeButton(DialogBuilder.NO_THANKS, null)
								.show();
					}
				}) );*/
			}
			else {
				//Log.d(TAG, selB + " is not leveling up");
//				LevelUpButton lub = new LevelUpButton(a,mm,selB,selB,null,team);
//				if(level instanceof HeroesLevel && ((HeroesLevel)level).highestLevelTowersAllowed() <= selB.lq.getLevel()){
//					lub.setEnabled(false);
//					lub.addLockOverlay();
//				}
//				nOSButtons.add(lub);
			}

			nOSButtons.add( DestroyBuildingButton.getInstance(a, selB , mm, team) );

			//Not used in tower defense
			//			if( level instanceof YourBaseLevel ){
			//				YourBaseLevel ybLevel = (YourBaseLevel) level;
			//				if( selB instanceof TownCenter )//Buildings.isInstanceOf(selB.getBuildingsName(), Buildings.TownCenter)  )
			//					nOSButtons.add(new ViewArmyButton( kc , ui , ybLevel ));
			//			}


			//Check to see if the new scroller is exactly the same as the old one
			boolean rebuildScroller = false;

			synchronized( OSButtons ){
				if( OSButtons.size() == nOSButtons.size() ){
					for( int i = 0 ; i < OSButtons.size() ; ++i )
						if( !OSButtons.get(i).equals( nOSButtons.get(i)) ){
							//Log.e( TAG , "!"+OSButtons.get(i).getClass().getSimpleName()+".equals("+ nOSButtons.get(i).getClass().getSimpleName()+"), rebuilding scroller" );
							rebuildScroller = true;
						}
				}
				else
					rebuildScroller = true;

				if( rebuildScroller ){
					OSButtons.clear();
					OSButtons.addAll( nOSButtons );
					return true;
				}
			}
		}
		return false;
	}



	public boolean determineIfAndWhatToDisplay( @Nullable List<? extends GameElement> selBs )
	{
		Activity a = Rpg.getGame().getActivity();

		if( selBs == null || selBs.isEmpty() ){
			//Log.e( TAG , "determineIfAndWhatToDisplay(null)");
			return false;
		}

		ArrayList<SButton> nOSButtons = new ArrayList<>();


		//Team team = mm.getTeam(selBs.get(0).getTeamName());



		//Check to see if the new scroller is exactly the same as the old one
		boolean rebuildScroller = false;

		synchronized( OSButtons ){
			if( OSButtons.size() == nOSButtons.size() ){
				for( int i = 0 ; i < OSButtons.size() ; ++i )
					if( !OSButtons.get(i).equals( nOSButtons.get(i)) ){
						Log.e( TAG , "!"+OSButtons.get(i).getClass().getSimpleName()+".equals("+ nOSButtons.get(i).getClass().getSimpleName()+"), rebuilding scroller" );
						rebuildScroller = true;
					}
			}
			else
				rebuildScroller = true;

			if( rebuildScroller ){
				OSButtons.clear();
				OSButtons.addAll( nOSButtons );
				return true;
			}
		}

		return false;
	}



	private static boolean oneOfTheseCanLevelUp(@Nullable ArrayList<? extends GameElement> selBs) {
		if( selBs == null )
			return false;

		for( GameElement ge : selBs )
			if( ge instanceof LivingThing )
				if( ((LivingThing)ge).canLevelUp() )
					return true;

		return false;
	}


	public void showBuildingOptionsScroller(@NonNull Building selB){
		if( determineIfAndWhatToDisplay( selB ) )
			selUI.displayTheseInRightScroller( OSButtons , BS );
	}

	public void showBuildingOptionsScroller(List<? extends GameElement> selBs){
		if( determineIfAndWhatToDisplay( selBs ) )
			selUI.displayTheseInRightScroller( OSButtons , BS );
	}





	public void refresh(@NonNull Building b) {
		showBuildingOptionsScroller(b);
	}

	public void refresh(List<? extends GameElement> ges) {
		showBuildingOptionsScroller( ges );
	}



	@NonNull
    public ArrayList<SButton> getPSButtons() {
		return PSButtons;
	}





	public void clearButtons() {
		synchronized( OSButtons ){
			OSButtons.clear();
		}
	}


































}
