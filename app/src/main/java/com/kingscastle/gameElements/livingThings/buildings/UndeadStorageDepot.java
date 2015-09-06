package com.kingscastle.gameElements.livingThings.buildings;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;

public class UndeadStorageDepot extends StorageDepot
{
	private static final String TAG = "UndeadStorageDepot";
	private static final String NAME = "Storage Depot";

	public static final Buildings name = Buildings.UndeadStorageDepot;

	private static final Image image = Assets.loadImage( R.drawable.undead_storage_depot);

	@Nullable
    private final Image iconImage = null;//Assets.loadImage(R.drawable.undead_storage_depot_icon);



	public UndeadStorageDepot()
	{
		super( name );
	}



	@Override
	public Image getImage(){
		return image;
	}


	@Override
	public Image getDamagedImage(){
		return getImage();
	}


	@Nullable
    @Override
	public Image getIconImage(){
		return iconImage;
	}



	@NonNull
    @Override
	public String toString() {
		return TAG;
	}


	@NonNull
    @Override
	public String getName() {
		return NAME;
	}

}
