package com.kingscastle.gameElements.livingThings.orders;

import android.support.annotation.NonNull;

public enum Stance
{

	FREE  , HOLD_POSITION , GUARD_LOCATION , PLAYING_THE_OBJECTIVE;

	@NonNull
    public Stance getNext()
	{
		return getNext( this );
	}

	@NonNull
    private static Stance getNext(@NonNull Stance current)
	{
		switch( current )
		{
		default:
		case FREE: return GUARD_LOCATION;
		//	case HOLD_POSITION: return GUARD_LOCATION;
		case GUARD_LOCATION: return FREE;
		}
	}
}
