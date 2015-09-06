package com.kingscastle.level.background;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.implementation.AndroidImage;

import java.util.ArrayList;

public class GidTileHolder
{
	private final ArrayList<Image> gidImages = new ArrayList<Image>();

	{
		gidImages.add(new AndroidImage());
	}

	void addImageToGidImages(@Nullable Image image, int tileWidth, int tileHeight)
	{
		if ( image == null || tileWidth <= 0 || tileHeight <= 0){
			return;
		}

		for( int j = 0 ; j*tileHeight < image.getHeight() ; j ++)
		{
			for( int i = 0 ; i*tileWidth < image.getWidth() ; i ++ )
			{

				Bitmap newBitmap = Bitmap.createBitmap(image.getBitmap(), i*tileWidth, j*tileHeight, tileWidth, tileHeight);
				gidImages.add(new AndroidImage(newBitmap, Graphics.ImageFormat.RGB565));

			}
		}
		//Rpg.print("TileGidHolder.addImageToGidImages() : So far there are " + gidImages.size() + " gid images.") ;
	}

	public void loadImageIntoGids(@Nullable TilesetParams readTileset)
	{
		if( readTileset == null )
		{
			return;
		}

		addImageToGidImages( getTilesetImageFromString(readTileset.getFileName()) ,
				readTileset.getTileWidth() , readTileset.getTileHeight() );

	}


	@Nullable
    private Image getTilesetImageFromString(@Nullable String fileName)
	{
		//Rpg.print("getting the tileset for filename of " + fileName);

		if( fileName == null ) {
			return null;
		}

		//		if( fileName.equals("mountain_landscape_23"))
		//		{
		//			return Assets.loadImage(R.drawable.mountain_landscape_23);
		//		}
		//		else if( fileName.equals("water_tileset"))
		//		{
		//			return Assets.loadImage(R.drawable.water_tileset);
		//		}
		//		else if( fileName.equals("forest_ground"))
		//		{
		//			return Assets.loadImage(R.drawable.forest_ground);
		//		}

		return null;
	}

	@Nullable
    public Image getImage(short s)
	{
		//System.out.println("Trying to get Image with gid of " + s);
		if( s >= gidImages.size() )
		{
			//System.out.println("gid out of range");
			return null;
		}
		else
		{
			return gidImages.get(s);
		}
	}

}
