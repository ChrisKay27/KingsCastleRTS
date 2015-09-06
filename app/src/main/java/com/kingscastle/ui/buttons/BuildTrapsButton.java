package com.kingscastle.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.implementation.ImageDrawable;
import com.kingscastle.ui.BuildingBuilder;


public class BuildTrapsButton extends SButton
{
	private static final String TAG = "BuildBuildingsButton";

	private static Image buttonIcon = Assets.loadImage(R.drawable.build_buildings_button);

	private final BuildingBuilder bb;

	private BuildTrapsButton( Activity a , final BuildingBuilder bb_ )
	{
		super( a );
		bb = bb_;

		ImageDrawable id = new ImageDrawable( buttonIcon.getBitmap() , 0 , 0 , new Paint());
		setForeground(id);


//		setOnClickListener( new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				bb.showScroller( Rpg.getGame().getPlayer().getAbs() );
//			}
//		});

	}




	@NonNull
    public static BuildTrapsButton getInstance( Activity a , final BuildingBuilder bb )
	{

		return new BuildTrapsButton( a , bb );
	}


	@NonNull
    @Override
	public BuildTrapsButton clone(){
		BuildTrapsButton bbb = new BuildTrapsButton( a , bb );
		return bbb;
	}



















}
