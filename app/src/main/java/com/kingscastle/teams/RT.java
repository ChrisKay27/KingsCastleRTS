package com.kingscastle.teams;

import android.support.annotation.NonNull;

/**
 * Created by chris_000 on 7/4/2015.
 */
public enum RT
{
    GOLD,MAGIC_DUST;

    private String name;

    public static RT getFromString( @NonNull String t )
    {
        if( t.equals( GOLD.toString() ) )
            return GOLD;
        else
            return null;
    }

    @Override
    public String toString(){
        if( name != null )
            return name;
        else{
            String n = name().toLowerCase();//Locale.CANADA);
            name = n.substring(0,1).toUpperCase() + n.substring(1);
            return name;
        }
    }
}