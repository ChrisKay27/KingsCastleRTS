package com.kingscastle.gameElements.livingThings.army;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.FreezeAnim;
import com.kingscastle.effects.animations.LightEffect2;
import com.kingscastle.effects.animations.TapAnim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.AdvancedMageSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.managment.ListPkg;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.spells.LightningBolts;
import com.kingscastle.gameElements.spells.SummoningPortal;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.level.Level;
import com.kingscastle.teams.Teams;


public class Elder extends AdvancedMageSoldier
{

	private static final String TAG = Elder.class.getSimpleName();
	private static final String NAME = TAG;

	private static ImageFormatInfo imageFormatInfo;
	private static Image[] redImages , blueImages , greenImages , orangeImages , whiteImages ;

	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;

	private static Cost cost = new Cost( 2500 , 2500 , 2000 , 3 );

	static
	{
		int dp = (int) Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( 0  , 0 ,
				0 , 0 , 1 , 1);
		imageFormatInfo.setID( R.drawable.elder );

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared(0);
		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(15000*dp*dp);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared(22500 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 100 );
		STATIC_ATTACKER_ATTRIBUTES.setROF(300);

		STATIC_ATTRIBUTES = new Attributes();
		STATIC_ATTRIBUTES.setRequiresBLvl(1);
		STATIC_ATTRIBUTES.setRequiresAge(Age.STONE);
		STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 80000 );
		STATIC_ATTRIBUTES.setHealth( 80000 );
		STATIC_ATTRIBUTES.setFullMana(0);
		STATIC_ATTRIBUTES.setMana(0);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 15 );
		STATIC_ATTRIBUTES.setSpeed(0.4f * dp);
	}

	private long summonPortalAt;

	{
		setAQ(new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES,getLQ().getBonuses()));
		setGoldDropped(300);
		setCostsLives(10);
	}

	public Elder(@NonNull vector loc, Teams team){
		super(team);
		setLoc(loc);
	}

	public Elder(){
	}

	private long freezeBuildingAt = 0;
	private Anim tapThis,glow;

	@Override
	public boolean act() {

		if( freezeBuildingAt < GameTime.getTime() ){

			ListPkg<Building> goodBuildings = getMM().getBuildingsOnTeam(Teams.BLUE);
			if( goodBuildings.size != 0 ) {

				int bIndex = (int) (Math.random() * goodBuildings.size);
				final Building b = goodBuildings.list[bIndex];

				if( !b.isStunned() ){
					freezeBuildingAt = GameTime.getTime() + 2500 + (int)(Math.random()*2500);
					b.setStunnedUntil(GameTime.getTime() + 60000);

					final Anim glow = new LightEffect2(b.loc);
					final TapAnim tapThis = new TapAnim(b.loc);
					final FreezeAnim freeze = new FreezeAnim(b.loc, 60000);
					freeze.setRequiredPosition(EffectsManager.Position.InFront);
					freeze.setScale(2);
					tapThis.setAliveTime(4000);

					getMM().getUI().addTappable(b, new Runnable() {
						@Override
						public void run() {
							b.setStunnedUntil(0);
							freeze.setOver(true);
							tapThis.setOver(true);
							glow.setOver(true);
						}
					});
					this.tapThis = tapThis;
					this.glow = glow;
					getMM().getEm().add(glow);
					getMM().getEm().add(tapThis);
					getMM().getEm().add(freeze);
				}
			}
		}


		if( summonPortalAt < GameTime.getTime() ) {
			summonPortalAt = GameTime.getTime() + 10000;
			Level lvl = getMM().getLevel();

			CD cd = getMM().getUI().getCD();

			vector loc = new vector();
			int attempts=0;
			do{
				loc.set(Math.random()*lvl.getLevelWidthInPx(), Math.random()*lvl.getLevelHeightInPx());
				if( cd.checkPlaceable(loc) )
					break;
				attempts++;
			}while( attempts < 20 );
			if( attempts < 20 ){
				if( Math.random() < 0.5 ) {
					final GameElement sPortal = new SummoningPortal(loc, this, 1000, 10000, null, KratosHeavyArmor.class);
					getMM().add(sPortal);
					getMM().getUI().addTappable(sPortal, new Runnable() {
						@Override
						public void run() {
							sPortal.die();
						}
					});
				}
				else{
					final GameElement sPortal = new SummoningPortal(loc, this, 300, 10000, null, Spider.class, SpiderLarge.class);
					getMM().add(sPortal);
					getMM().getUI().addTappable(sPortal, new Runnable() {
						@Override
						public void run() {
							sPortal.die();
						}
					});
				}

			}
		}


		return super.act();
	}

	@Override
	public boolean create(@NonNull MM mm) {
		boolean superCreate =  super.create(mm);
		getAnim().setScale(1.5f);
		final LightEffect2 le = new LightEffect2(loc);
		le.setScale(2);
		mm.getEm().add(le);
		getAnim().addAnimationListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				le.setOver(true);
			}
		});
		return superCreate;
	}



	@Override
	protected void setupSpells(){
		LightningBolts bolt = new LightningBolts();
		bolt.setDamage(getAQ().getDamage());
		bolt.setCaster(this);
		SpellAttack lBoltAttack = new SpellAttack(getMM(), this , bolt  );
		getAQ().addAttack(lBoltAttack) ;
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
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		if( orangeImages == null )
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
		if( blueImages == null )
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		if( greenImages == null )
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
		if( whiteImages == null )
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );
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
		Elder.imageFormatInfo = imageFormatInfo;
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

	@NonNull
    @Override
	public Attributes getNewAttributes()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {

	}


	@Override
	public Cost getCosts() {
		return cost;
	}


	public static void setCost(Cost cost) {
		Elder.cost = cost;
	}

	public static Image[] getRedImages()
	{
		if ( redImages == null )
		{
			redImages = Assets.loadImages( imageFormatInfo.getRedId()  , 3 , 4, 0 , 0 , 1 , 1 );
		}
		return redImages;
	}

	public static void setRedImages(Image[] redImages) {
		Elder.redImages = redImages;
	}

	public static Image[] getBlueImages()
	{
		if ( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 3 , 4, 0 , 0 , 1 , 1 );
		}
		return blueImages;
	}

	public static void setBlueImages(Image[] blueImages) {
		Elder.blueImages = blueImages;
	}

	public static Image[] getGreenImages()
	{
		if ( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return greenImages;
	}

	public static void setGreenImages(Image[] greenImages) {
		Elder.greenImages = greenImages;
	}

	public static Image[] getOrangeImages()
	{
		if ( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return orangeImages;
	}

	public static void setOrangeImages(Image[] orangeImages) {
		Elder.orangeImages = orangeImages;
	}

	public static Image[] getWhiteImages()
	{
		if ( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 3 , 4 , 0 , 0 , 1 , 1 );
		}
		return whiteImages;
	}

	public static void setWhiteImages(Image[] whiteImages) {
		Elder.whiteImages = whiteImages;
	}

	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
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
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }


	@NonNull
    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }
}
