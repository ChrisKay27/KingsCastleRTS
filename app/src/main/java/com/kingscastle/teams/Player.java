package com.kingscastle.teams;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.level.PR;
import com.kingscastle.teams.races.Race;

import java.io.BufferedWriter;
import java.io.IOException;

public class Player
{
	private static final String TAG = "Player";

	private String savedGameName;



	private Teams teamName;

	@Nullable
    private Team team;


	public boolean spendCosts( @Nullable Cost costs )
	{
		if( costs == null )
			throw new IllegalArgumentException("Costs cannot be null");
		else
			return team.getPR().spend( costs );
		//totalZoneResources.spend(costs);

	}



	public void refundCosts(@Nullable Cost costs)
	{
		if( costs == null )
			return;
		else
			team.getPR().refund(costs );
		//	totalZoneResources.refund(costs);

	}


	public boolean canAfford( @Nullable Cost cost )
	{
		if( cost == null )
			return true;
		////Log.e( TAG , " Warning cost was null! ");

		return team.canAfford( cost );
	}




	@NonNull
    public PR getPR()
	{
		return team.getPR();
	}






	public void saveYourSelf( @Nullable BufferedWriter b ) throws IOException
	{
		if( b == null )
			return;

		String s = "<Player>";

		b.write( s , 0, s.length() );
		b.newLine();

		team.getPR().saveYourSelf( b );


		if( getAbs() != null )
			getAbs().saveYourSelf(b);

		s = "</Player>";
		b.write(s,0,s.length());
		b.newLine();
	}






	public String getSavedGameName() {
		return savedGameName;
	}


	public void nullify() {
	}

	public AllowedBuildings getAbs(){
		return team.abs;
	}

	public void setAbs(AllowedBuildings abs) {
		team.abs = abs;
	}


	public void setSavedGameName(String savedGameName) {
		this.savedGameName = savedGameName;
	}




	public void finalInit( MM mm ) {
		// TODO Auto-generated method stub

	}




	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}




	public Teams getTeamName()
	{
		return teamName;
	}
	public void setTeamName(Teams teamName) {
		this.teamName = teamName;
	}

	public void setTeam(@Nullable Team team) {
		this.team = team;
		if( team != null )
			teamName = team.getTeamName();
	}
	@Nullable
    public Team getTeam()
	{
		return team;
	}








	@NonNull
    public Race getRace(){
		return team.race;
	}



	public void act() {
	}

	public void pauseThread() {
	}



	public void startThread() {
	}




//	public AvailableSpells getAs() {
//		return team.as;
//	}
//
//
//	public void setAs(AvailableSpells as) {
//		team.as = as;
//	}















}
