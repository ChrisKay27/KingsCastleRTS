package com.kingscastle.gameElements.movement.pathing;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import java.util.ArrayList;

class GridBuilder
{



	public static boolean buildGrid( @NonNull ArrayList<RectF> rects , @NonNull Grid grid )
	{

		boolean[][] gridTiles = grid.getGridTiles();

		float gridSize = grid.getGridSize();
		int horzTiles = gridTiles.length;
		int vertTiles = gridTiles[0].length;


		float xOffs = gridSize/2;
		float yOffs = gridSize/2;



		for( int i = 0 ; i < horzTiles ; ++i )
		{
			for( int j = 0 ; j < vertTiles ; ++j )
			{

				boolean walkable = true;

				for( RectF rect : rects )
				{
					if( rect.contains( xOffs , yOffs ) )
					{
						walkable = false;
						break;
					}
				}

				gridTiles[i][j] = walkable;

				yOffs += gridSize;
			}

			xOffs += gridSize;
		}


		return true;
	}









}
