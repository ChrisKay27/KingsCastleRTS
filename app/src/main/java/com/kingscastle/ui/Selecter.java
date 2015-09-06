package com.kingscastle.ui;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Selecter implements TouchEventAnalyzer
{
	private static final String TAG = "Selecter";

	private final UI ui;
    private final vector temp = new vector();


    Selecter( UI ui_ ){
		ui = ui_;
	}



    @Override
    public boolean analyzeTouchEvent(Input.TouchEvent e) {
        if( e.type != Input.TouchEvent.TOUCH_UP )
            return false;

        temp.set(e.x, e.y);
        ui.getCc().getCoordsScreenToMap(temp, temp);

        GameElement ge = ui.getCD().checkPlaceableOrTarget(temp);
        //Log.d(TAG, "Found "+ge+" to select at " + temp);

        if (ge != null && ge.getTeamName() == Teams.BLUE ){//&& !(ge.isStunned()) {
            setSelected(ge);
            return true;
            //setSelectedBuilding((Building) ge);
        }

        return false;
    }




    public boolean setSelected( @NotNull GameElement ge )	{
		Log.v(TAG, ge + " selected");


		synchronized( sls ){
			for( OnSelectedListener sl : sls )
				sl.onSelected(ge);
		}

		return false;
	}






	public boolean setSelected( @NotNull List<? extends GameElement> ges )
	{
		List<Unit> units = new ArrayList<>();

		for( GameElement ge : ges )
			if( ge instanceof Unit )
                units.add( (Unit) ge );

		////Log.d( TAG , "lts.size() = " + lts.size() );
		if( units.size() == 1 )
			return setSelected( units.get(0) );


		else if( !units.isEmpty() )
		{
			clearSelection();

			ui.setSelectedUnits(units);


			//UI ui = UI.getInstance();


			//ui.setTopRightButton( UnselectButton.getInstance() );
			//ui.setBottomRightButton( TroopSelectorButton.getInstance() );

			return true;
		}


		return false;
	}

    /**
     * Must NOT be called from UI class.
     */
	public boolean clearSelection()
	{
		Log.d( TAG , "clearSelection()" );

		ui.clearSelected();

//		UIView uiView = ui.uiView;
//		if( uiView != null )
//			uiView.showTroopSelectorButton();



		ui.selUI.clearScrollerButtons();
		ui.selUI.clearSelections();
		ui.bo.clearButtons();
		//ui.uo.clearButtons();
		ui.bb.cancel();

			////Log.d( TAG , "end of clearSelection()" );
		return true;
	}

	public void unselect( @NonNull GameElement ge )
	{
		ge.setSelected( false );
		ui.setUnSelected( ge );
	}


	private final List<OnSelectedListener> sls = new ArrayList<OnSelectedListener>();

	public void addSl(OnSelectedListener usl) {
		synchronized( sls ){
			sls.add( usl );
		}
	}
	public boolean Slcontains(Object object) {
		synchronized( sls ){
			return sls.contains(object);
		}
	}
	public boolean Slremove(Object object) {
		synchronized( sls ){
			return sls.remove(object);
		}
	}


    public static interface OnSelectedListener{
		void onSelected(GameElement ge);
	}




}
