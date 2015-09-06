package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumMeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;


public class Saruman extends MediumMeleeSoldier
{

	private static final String TAG = "Saruman";
	private static final String NAME = "";

	//private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;
	//private static ImageFormatInfo imageFormatInfo;


	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES; @NonNull
                                                                    @Override
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES; @NonNull
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 0 , 0 , 0 , 1 );

	static
	{
		float dp = Rpg.getDp();

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared( 0 );

		STATIC_ATTRIBUTES = new Attributes();   STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight(150*dp);
		STATIC_ATTRIBUTES.setHealth(100000);
		STATIC_ATTRIBUTES.setFullHealth(100000);
	}

	{
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES,getLQ().getBonuses()) );
	}

	public Saruman()
	{
	}


	public Saruman( @NonNull vector loc , Teams team )
	{
		super(team);
		setLoc( loc );

		this.team = team;
	}


	@Override
	public void finalInit( MM mm )
	{

	}

	@Override
	public void loadAnimation( @NotNull @NonNull MM mm )
	{
	}

	@Nullable
    @Override
	public Image[] getImages()
	{
		return null;
	}

	@Override
	public void loadImages()
	{
	}



	@Override
	public boolean act(){
		return false;
	}

	@Override
	public void die(){

	}

	@Nullable
    @Override
	public ImageFormatInfo getImageFormatInfo()
	{
		return null;
	}



	public void setImageFormatInfo( ImageFormatInfo imageFormatInfo2 )
	{

	}




	@Override
	public RectF getStaticPerceivedArea()
	{
		return Rpg.getNormalPerceivedArea();
	}




	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

	}




	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Nullable
    @Override
	public Image[] getStaticImages() {
		return null;
	}

	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {
	}







	@NonNull
    @Override
	public Attributes getNewAttributes()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		Saruman.cost = cost;
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
