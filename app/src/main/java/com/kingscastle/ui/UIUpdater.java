package com.kingscastle.ui;


import android.support.annotation.Nullable;

import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;

public class UIUpdater
{
	private static final String TAG = "UIUpdater";


	private static UIUpdater updater;

	private final SelectedUI selUI;
	private final Selecter selecter;

	UIUpdater(SelectedUI selUI_ , Selecter selecter_ ){
		selUI = selUI_;
		selecter = selecter_;
		updater = this;
	}



	public static void onUnitRemovedFromMap(@Nullable Unit u) {
		UIUpdater updater = UIUpdater.updater;
		if( updater == null ) return;

		if( u == null ){
			//Log.e( TAG , "u == null" );
			return;
		}

		if( u.isSelected() ){
			updater.selecter.unselect(u);
			//	SelectedUI.hide();
		}

	}












}
