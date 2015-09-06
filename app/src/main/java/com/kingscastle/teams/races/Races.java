package com.kingscastle.teams.races;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public enum Races
{

	HUMAN("Human") , DWARF("Dwarf") , ORC("Orc") , UNDEAD("Undead") , HOLY("Holy") ;


	@NonNull
    private static final ArrayList<Races> races;

	static
	{
		races = new ArrayList<Races>();
		races.add( HUMAN );
		//races.add( DWARF );
		races.add( UNDEAD );
		//races.add( HOLY );
		//races.add( ORC );
	}

	private final String name;

	private Races( String n ){
		name = n;
	}

	public Race getRace()
	{

		switch( this )
		{
		case DWARF:
			return null;//DwarfRace.getInstance();

		default:
		case HUMAN:
			return HumanRace.getInstance();

		case ORC:
			return OrcRace.getInstance();

		case UNDEAD:
			return UndeadRace.getInstance();

		case HOLY:
			return null;//HolyRace.getInstance();
		}

	}

	@Override
	public String toString()
	{
		return name;
	}

	//	public Races getNextOwnedRace()
	//	{
	//		KingsCastle game = Rpg.getGame();
	//
	//		boolean ownsDwarf = game.doesPlayerOwnDwarfRace();
	//		boolean ownsOrc = game.doesPlayerOwnOrcRace();
	//
	//
	//		switch( this )
	//		{
	//		case HUMAN :
	//
	//			if( ownsDwarf )
	//			{
	//				return DWARF;
	//			}
	//			else if( ownsOrc )
	//			{
	//				return ORC;
	//			}
	//
	//		case DWARF:
	//
	//			if( ownsOrc )
	//			{
	//				return ORC;
	//			}
	//			break;
	//
	//		case ORC:
	//
	//			return HUMAN;
	//
	//		}
	//
	//		return this;
	//	}
	//
	//
	//	public Races getPrevOwnedRace()
	//	{
	//		KingsCastle game = Rpg.getGame();
	//
	//		boolean ownsDwarf = game.doesPlayerOwnDwarfRace();
	//		boolean ownsOrc = game.doesPlayerOwnOrcRace();
	//		switch( this )
	//		{
	//		case HUMAN :
	//
	//			if( ownsOrc )
	//			{
	//				return ORC;
	//			}
	//			else if( ownsDwarf )
	//			{
	//				return DWARF;
	//			}
	//
	//		case DWARF:
	//
	//			return HUMAN;
	//
	//
	//
	//		case ORC:
	//
	//			if( ownsDwarf )
	//			{
	//				return DWARF;
	//			}
	//			break;
	//
	//
	//		}
	//
	//		return this;
	//
	//	}



	@NonNull
    public static Races getFromString( @Nullable String raceString )
	{
		if( raceString == null )
		{
			return HUMAN;
		}
		else if( raceString.equals( HUMAN.name ) )
		{
			return HUMAN;
		}
		else if( raceString.equals( ORC.name ) )
		{
			return ORC;
		}
		else if( raceString.equals( DWARF.name ) )
		{
			return DWARF;
		}
		else if( raceString.equals( UNDEAD.name ) )
		{
			return UNDEAD;
		}
		else if( raceString.equals( HOLY.name ) )
		{
			return HOLY;
		}
		return HUMAN;
	}


	@NonNull
    public static ArrayList<Races> getRaces()
	{
		return races;
	}


	public Race newInstance() {
		switch( this ){
		case DWARF: return null;//new DwarfRace();

		case HOLY:return null;//new HolyRace();
		default:
		case HUMAN:return new HumanRace();

		case ORC:return new HumanRace();

		case UNDEAD:return new UndeadRace();

		}
	}
























}
