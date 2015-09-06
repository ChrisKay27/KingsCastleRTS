package com.kingscastle.gameElements;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameUtils.Queueable;
import com.kingscastle.teams.Teams;

import java.io.BufferedWriter;
import java.io.IOException;



public class Technology implements Queueable
{
	protected Building buildFrom;

	@Nullable
    public Image getIconImage() {
		return null;
	}

	@Override
	public int getBuildTime()
	{
		return 10000;
	}

	@Override
	public void queueableComplete() {

	}

	private static final String name = "";
	@Override

	public String getName() {
		return name;
	}
	@NonNull
    protected static String START_TAG = "<Tech";
	@NonNull
    protected static String ENDTAG = "</Tech>";
	public void saveYourself( BufferedWriter b ) throws IOException
	{
	}

	/**
	 * @return the team
	 */
	@Nullable
    public Teams getTeam() {
		return null;
	}



	public static Technology getFromString( String className )
	{
		try
		{
			Class<?> aTech = Class.forName("com.kaebe.kingscastle27.Technologies." + className );

			Technology tech = (Technology) aTech.newInstance();

			return tech;
		}
		catch(Exception e)
		{
			//////Log.v( "Technology" , "Did not find the Class in the Technologies. folder " );
			return null;
		}

	}

	@Override
	public void setBuildTime(int buildTime) {
	}

	public void setLivingThing(LivingThing lt) {
	}

	public void setLTClassName(String ltClass) {
	}

	public void setTeamName( Teams team) {
	}

	@Nullable
    public String getLTClassName() {
		return null;
	}



	public void setBuilding(Building b) {
		buildFrom = b;
	}


	public Building getBuiltFrom() {
		return buildFrom;
	}

	@Nullable
    @Override
	public Cost getCosts() {
		return null;
	}



}
