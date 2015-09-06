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
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedRangedSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.Bow;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class SkullFucker extends AdvancedRangedSoldier
{

	private static Image[] staticImages;
	private static ImageFormatInfo imageFormatInfo;

	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES; @NonNull
                                                                    @Override
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES; @NonNull
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	@Nullable
    private static Cost cost = null;

	static
	{
		setImageFormatInfo(new ImageFormatInfo(R.drawable.skull_fucque_red , 0, 0 ,0 , 1 , 1 ));

		float dp = Rpg.getDp();

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared(10000 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(5000*dp*dp);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared(22500 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 10 );  STATIC_ATTACKER_ATTRIBUTES.setdDamageAge( 3 ); STATIC_ATTACKER_ATTRIBUTES.setdDamageLvl( 1 ); //20);
		STATIC_ATTACKER_ATTRIBUTES.setROF(600);

		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresBLvl(1); STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setLevel( 1 ); //20);
		STATIC_ATTRIBUTES.setFullHealth(400);
		STATIC_ATTRIBUTES.setHealth( 100 ); STATIC_ATTRIBUTES.setdHealthAge( 50 ); STATIC_ATTRIBUTES.setdHealthLvl( 10 ); //400);
		STATIC_ATTRIBUTES.setFullMana(200);
		STATIC_ATTRIBUTES.setMana(200);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(500);
		STATIC_ATTRIBUTES.setSpeed(3f * dp);

	}

	{
		setAQ(new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES,getLQ().getBonuses()));
	}


	public SkullFucker(@NonNull vector loc, Teams team)
	{
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public SkullFucker()
	{

	}



	@Override
	public void finalInit( MM mm )
	{
		super.finalInit( mm );
		getAQ().setCurrentAttack( new Bow( mm , this , new Arrow() ) );
		aliveAnim.add(getAQ().getCurrentAttack().getAnimator(),true);
	}








	/**
	 * DO NOT LOAD THE IMAGES, USE GETIMAGES() to make sure they are not null. This method is used to check if the static images are null so they can be loaded.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages()
	{
		return staticImages;
	}


	/**
	 * @param staticImages2 the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages2) {
		staticImages = staticImages2;
	}







	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return imageFormatInfo;
	}

	public static void setImageFormatInfo(ImageFormatInfo imageFormatInfo) {
		SkullFucker.imageFormatInfo = imageFormatInfo;
	}







	@NonNull
    @Override
	public Attributes getNewAttributes()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}




	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}


	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}

	@Nullable
    @Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost) {
		SkullFucker.cost = cost;
	}
	private static final String TAG = "Ancient Mecha Winged";
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
