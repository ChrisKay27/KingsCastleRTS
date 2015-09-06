package com.kingscastle.teams;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Image;

public class TeamColors
{
	@Nullable
    private static final Image redTeamFlag = null;//Assets.loadImage( R.drawable.flag_red );
	@Nullable
    private static final Image blueTeamFlag = null;//Assets.loadImage(R.drawable.flag_blue);
	@Nullable
    private static final Image yellowTeamFlag = null;//Assets.loadImage( R.drawable.flag_yellow );
	@Nullable
    private static final Image orangeTeamFlag = null;//Assets.loadImage( R.drawable.flag_orange );
	//private static final Image pinkTeamFlag = Assets.loadImage( R.drawable.flag_pink );
	@Nullable
    private static final Image greenTeamFlag = null;//Assets.loadImage( R.drawable.flag_green );
	@Nullable
    private static final Image whiteTeamFlag = null;//Assets.loadImage( R.drawable.flag_white );



	@Nullable
    public static Image getFlagForTeam ( @NonNull Teams teamName )
	{
		switch ( teamName )
		{


		case BLUE:
			return blueTeamFlag;


		case RED:
			return redTeamFlag;


		case GREEN:
			return greenTeamFlag;


		case WHITE:
			return whiteTeamFlag;


		case ORANGE:
			return orangeTeamFlag;


		case GRAY:
			return greenTeamFlag;


		case PURPLE:
			return blueTeamFlag;
		case BLACK:
			break;





		case YELLOW:
			return yellowTeamFlag;


		default:
			break;

		}

		return redTeamFlag;

	}




}



