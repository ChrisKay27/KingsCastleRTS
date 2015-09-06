package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kaebe.kingscastle.R;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumMage;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.spells.Icicle;
import com.kingscastle.gameElements.spells.LightningBolts;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class UndeadDullahan extends MediumMage
{

	private static final String TAG = "UndeadDullahan";
	private static final String NAME = "Dullahan";

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

	private static Cost cost = new Cost( 1000 , 1000 , 1000 , 3 );

	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0 , 0 ,
				0 , 0 , 1 , 1 );
		imageFormatInfo.setOrangeId( R.drawable.bonedullahan_a_red );
		imageFormatInfo.setRedId( R.drawable.bonedullahan_a_red );

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared(12000 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(5000*dp*dp);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared(5000 * dp * dp);
        STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredAge(1500 * dp * dp); STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredLvl(500 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setROF(1000);
		STATIC_ATTACKER_ATTRIBUTES.setDamage(25); STATIC_ATTACKER_ATTRIBUTES.setdDamageLvl(5);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresCLvl( 1 );  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight( 275 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 200 );
		STATIC_ATTRIBUTES.setHealth( 200 ); STATIC_ATTRIBUTES.setdHealthLvl( 10 );
		STATIC_ATTRIBUTES.setFullMana( 100 );
		STATIC_ATTRIBUTES.setMana( 100 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 2 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setSpeed( 1f * dp );
	}



	{
		setAQ(new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, lq.getBonuses() ));


	}


	public UndeadDullahan(@NonNull vector loc,Teams team)
	{
		super(team);
		setLoc(loc);
		super.team = team;
	}

	public UndeadDullahan() {

	}


	@Override
	protected void upgrade(){
		super.upgrade();

	}

	protected void setupSpells(){
		LightningBolts bolt = new LightningBolts();
		bolt.setDamage(getAQ().getDamage());
		bolt.setCaster(this);
		SpellAttack lBoltAttack = new SpellAttack(getMM(), this , bolt  );
		getAQ().addAttack( lBoltAttack );


		Icicle icicle = new Icicle(aq.getDamage(), 0.5f);
		icicle.setDamage(getAQ().getDamage());
		icicle.setCaster(this);
		SpellAttack icicleAttack = new SpellAttack(getMM(), this , icicle  );
		getAQ().addAttack(icicleAttack);
	}




	@Override
	public void finalInit( MM mm )
	{
		loadAnimation(mm);

		hasFinalInited = true;
	}




	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
		{
			teamName = Teams.BLUE;
		}

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
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 3, 4, 0, 0, 1, 1);
		}
		if( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		if( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId() , 3 , 4 , 0 , 0 , 1 , 1 );
		}
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
		UndeadDullahan.imageFormatInfo = imageFormatInfo;
	}
	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
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
	public Image[] getStaticImages()
	{
		return null;
	}


	/**
	 * @param staticImages2 the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages2) {

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
		UndeadDullahan.cost = cost;
	}




	public static Image[] getRedImages()
	{
		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		UndeadDullahan.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		UndeadDullahan.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		UndeadDullahan.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		UndeadDullahan.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		UndeadDullahan.whiteImages = whiteImages;
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

	@Override
	public String getAbilityMessage() {
		return buffMessage;
	}

}
