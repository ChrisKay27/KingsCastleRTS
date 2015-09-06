package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MeleeSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;


public class AntlerHead extends MeleeSoldier {

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] images = Assets.loadImages( R.drawable.antler_head , 0 , 0 , 1 , 1 );


	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES; @NonNull
                                                                    @Override
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES; @NonNull
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 4000 , 4000 , 0 , 2 );

	static
	{
		float dp = Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo( 0  , 0 ,
				0 , 0 , 1 , 1);

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared(0);
		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(15000*dp*dp);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 100 );  STATIC_ATTACKER_ATTRIBUTES.setdDamageAge( 0 ); STATIC_ATTACKER_ATTRIBUTES.setdDamageLvl( 5 );
		STATIC_ATTACKER_ATTRIBUTES.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresBLvl(10); STATIC_ATTRIBUTES.setRequiresAge(Age.STEEL); STATIC_ATTRIBUTES.setRequiresTcLvl(16);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 20000 );
		STATIC_ATTRIBUTES.setHealth( 20000 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 30 );
		STATIC_ATTRIBUTES.setFullMana(0);
		STATIC_ATTRIBUTES.setMana(0);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 10 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 2 );
		STATIC_ATTRIBUTES.setSpeed(.5f * dp);
	}

	{
		setAQ(new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES,getLQ().getBonuses()));
		setCostsLives(10);
		setGoldDropped(300);
	}

	public AntlerHead(@NonNull vector loc, Teams team){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public AntlerHead(){
	}




	@Override
	public boolean create(@NonNull @NotNull MM mm) {
		boolean superCreate =  super.create(mm);
		getAnim().setScale(1.5f);
		return superCreate;
	}


	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
			teamName = Teams.BLUE;

		return images;
	}

	@Override
	public void loadImages()
	{
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
		AntlerHead.imageFormatInfo = imageFormatInfo;
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




	@Override
	public RectF getStaticPerceivedArea() {
		return Rpg.getNormalPerceivedArea();
	}
	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}
	@NonNull
    @Override
	public Attributes getNewAttributes() {
		return new Attributes(STATIC_ATTRIBUTES);
	}



	@Override
	public Cost getCosts() {
		return cost;
	}
	public static void setCost(Cost cost) {
		AntlerHead.cost = cost;
	}



	private static final String TAG = AntlerHead.class.getSimpleName();

	@NonNull
    @Override
	public String toString() {
		return TAG;
	}
	@NonNull
    @Override
	public String getName() {
		return TAG;
	}


}
