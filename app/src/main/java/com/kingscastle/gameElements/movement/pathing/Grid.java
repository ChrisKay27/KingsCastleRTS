package com.kingscastle.gameElements.movement.pathing;


import android.support.annotation.NonNull;

import com.kingscastle.gameUtils.vector;

public class Grid
{

	private final float gridTileSize;
	@NonNull
    private final boolean[][] gridTiles;


	public Grid( int numHorzTiles , int numVertTiles , float gridTileSize )
	{
		gridTiles = new boolean[numHorzTiles][numVertTiles];
		this.gridTileSize = gridTileSize;

		for( boolean[] column : gridTiles )
			for( int j = 0 ; j < column.length ; ++j )
				column[j] = true;


		////Log.d( "Grid" , "numHorzTiles=" + numHorzTiles + " numVertTiles" + numVertTiles + " gridTileSize" + gridTileSize);
		////Log.d( "Grid" , "gridTiles.length=" + gridTiles.length + " gridTiles[0].length" + gridTiles[0].length );
	}


	@NonNull
    public boolean[][] getGridTiles(){
		return gridTiles;
	}

	public float getGridSize(){
		return gridTileSize;
	}



	public synchronized int width(){
		return gridTiles.length;
	}
	public synchronized int height(){
		if( gridTiles.length > 0 )
			return gridTiles[0].length;
		return 0;
	}

	public boolean isWalkable(@NonNull vector v) {
		int i = (int) (v.x/gridTileSize);
		int j = (int) (v.y/gridTileSize);

		if( i < 0 || i > width() || j < 0 || j > height() )
			return false;
		synchronized( this ){
			return gridTiles[i][j];
		}
	}




}
