package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Animator;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedMageSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.spells.EarthQuake;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;


public class ButterFlyFairy extends AdvancedMageSoldier
{

	private static final String TAG = "ButterFlyFairy";
	private static final String NAME = "ButterFly Fairy";


	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;



	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES; @NonNull
                                                                    @Override
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES; @NonNull
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 10000 , 10000 , 10000 , 4 );

	static{

		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				0 , 0 , 1 , 1);
		//imageFormatInfo.setID(R.drawable.)
		//		imageFormatInfo.setOrangeId( R.drawable.butter_deflower_orange );
		//		imageFormatInfo.setRedId( R.drawable.butter_deflower_red );
		//		imageFormatInfo.setBlueId( R.drawable.butter_deflower_blue );
		//		imageFormatInfo.setGreenId( R.drawable.butter_deflower_green );
		//		imageFormatInfo.setWhiteId( R.drawable.butter_deflower_white );

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared( 10000 * dp * dp );
		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(5000*dp*dp);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared(22500 * dp * dp); STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredAge(1500 * dp * dp); STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredLvl(500 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 100 );  STATIC_ATTACKER_ATTRIBUTES.setdDamageAge( 0 ); STATIC_ATTACKER_ATTRIBUTES.setdDamageLvl( 20 );
		STATIC_ATTACKER_ATTRIBUTES.setROF( 1000 );

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresCLvl( 1 );  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 2000 );
		STATIC_ATTRIBUTES.setHealth( 2000 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 30 ); //350 );
		STATIC_ATTRIBUTES.setFullMana( 200 );
		STATIC_ATTRIBUTES.setMana( 200 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 1 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setSpeed( 1.8f * dp );
	}


	{
		setAQ(new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, lq.getBonuses() ));

	}


	public ButterFlyFairy(@NonNull vector loc, Teams team){
		super(team);
		setLoc(loc);

		setTeam(team);
	}

	public ButterFlyFairy() {
	}

	@Override
	protected void upgrade(){
		super.upgrade();

	}

	protected void setupSpells(){

		EarthQuake eq = new EarthQuake();
		eq.setDamage(getAQ().getDamage());
		eq.setCaster(this);
		SpellAttack fireBallAttack = new SpellAttack(getMM(), this , eq  );
		getAQ().addAttack( fireBallAttack ) ;


		//		Eruption erup = new Eruption();
		//		erup.setDamage(getAQ().getDamage());
		//		erup.setCaster(this);
		//		SpellAttack erupAttack = new SpellAttack( this , erup );
		//		getAQ().addAttack( erupAttack ) ;


		//		Laser lazer = new Laser();
		//		lazer.setDamage(getAQ().getDamage()/5);
		//		lazer.setCaster(this);
		//		SpellAttack lazerAttack = new SpellAttack( this , lazer );
		//		getAQ().addAttack( lazerAttack ) ;
	}



	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
			teamName = Teams.BLUE;


		switch( teamName )
		{
		default:
		case RED:
			return redImages;

		case GREEN:
			return greenImages;

		case BLUE:
			return blueImages;

		case ORANGE:
			return orangeImages;

		case WHITE:
			return whiteImages;

		}

	}

	@Override
	public void loadImages()
	{
		if( redImages == null )
		{
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		}
		if( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		}
		if( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
		}
		if( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );
		}
	}


	@Override
	public void loadAnimation( @NotNull MM mm )
	{
		super.loadAnimation(mm);
		Animator anim = getAnim();
		if( anim != null )
			anim.setScale(2f);
	}



	/**
	 * @return the imageFormatInfo
	 */
	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return imageFormatInfo;
	}

	/**
	 * @param imageFormatInfo the imageFormatInfo to set
	 */
	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
		ButterFlyFairy.imageFormatInfo = imageFormatInfo;
	}






	@Override
	public RectF getStaticPerceivedArea(){
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

	public static Image[] getRedImages()
	{
		if ( redImages == null )
			redImages = Assets.loadImages( imageFormatInfo.getRedId()  , 3 , 4, 0 , 0 , 1 , 1 );

		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		ButterFlyFairy.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		if ( blueImages == null )
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 3 , 4, 0 , 0 , 1 , 1 );

		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		ButterFlyFairy.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		if ( greenImages == null )
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 3 , 4 , 0 , 0 , 1 , 1 );

		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		ButterFlyFairy.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		if ( orangeImages == null )
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 3 , 4 , 0 , 0 , 1 , 1 );

		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		ButterFlyFairy.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		if ( whiteImages == null )
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 3 , 4 , 0 , 0 , 1 , 1 );

		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		ButterFlyFairy.whiteImages = whiteImages;
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
		ButterFlyFairy.cost = cost;
	}


	@Override
	public String getAbilityMessage()
	{
		return buffMessage;
	}




















}
