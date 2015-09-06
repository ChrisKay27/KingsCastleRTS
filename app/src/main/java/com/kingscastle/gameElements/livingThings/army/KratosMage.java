package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.gameElements.livingThings.TargetingParams;
import com.kingscastle.gameElements.livingThings.abilities.AbilityManager;
import com.kingscastle.gameElements.livingThings.abilities.HotBuff;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.BuffAttack;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.spells.PoisonSpell;
import com.kingscastle.gameElements.targeting.TargetFinder;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class KratosMage extends MageSoldier
{

	private static final String TAG = KratosMage.class.getSimpleName();
	private static final String NAME = "Kratos Mage";

	private static Image[] staticImages , redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;


	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES; @NonNull
                                                                    @Override
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES; @NonNull
                                                                @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES; }

	private static Cost cost = new Cost( 30 , 0 , 0 , 1 );


	static
	{
		float dp = Rpg.getDp();

		imageFormatInfo = new ImageFormatInfo( 0 , 0 , 2 , 0 , 4 , 2 );
		imageFormatInfo.setID(R.drawable.kratos_7);

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared( 0 );
		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(5000*dp*dp);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared( Rpg.getMeleeAttackRangeSquared() );
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 30 );  STATIC_ATTACKER_ATTRIBUTES.setdDamageAge( 0 ); STATIC_ATTACKER_ATTRIBUTES.setdDamageLvl( 1 );
		STATIC_ATTACKER_ATTRIBUTES.setROF( 1000 );

		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresBLvl(1); STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight( 300 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 300 );
		STATIC_ATTRIBUTES.setHealth( 300 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 10 ); //
		STATIC_ATTRIBUTES.setFullMana( 0 );
		STATIC_ATTRIBUTES.setMana( 0 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 1 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setArmor( 10 );  STATIC_ATTRIBUTES.setdArmorAge( 3 ); STATIC_ATTRIBUTES.setdArmorLvl( 1 );
		STATIC_ATTRIBUTES.setSpeed( 1f * dp );
	}

	{
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES,getLQ().getBonuses()) );
		setGoldDropped(10);
	}


	public KratosMage(@NonNull vector loc, Teams team)
	{
		super(team);
		setLoc( loc );
		super.team = team;
	}

	public KratosMage()
	{
	}

	@Override
	public boolean act() {

		boolean superAct = super.act();
		if( getFriendlyTarget() != null )
			setFriendlyTarget(null);
		return superAct;
	}

	@Override
	protected void setupSpells() {
		HotBuff buffSpell = new HotBuff(this,null);
		buffSpell.setHealAmount(5);
		this.buffSpell = buffSpell;
		getAQ().setFriendlyAttack( new BuffAttack(getMM(),this , buffSpell ) ) ;


		PoisonSpell pSpell = new PoisonSpell();
		pSpell.setDamage(getAQ().getDamage());
		pSpell.setCaster(this);

		SpellAttack poisonAttack = new SpellAttack(getMM(), this , pSpell  );
		getAQ().setCurrentAttack( poisonAttack ) ;
	}




	@Override
	protected void createFriendlyTargetingParams()
	{
		if( friendlyParams == null )
		{
			final AbilityManager abm = getMM().getTM().getTeam( getTeamName() ).getAbm();

			friendlyParams = new TargetingParams()
			{
				@NonNull
                @Override
				public TargetFinder.CondRespon postRangeCheckCondition( @NonNull LivingThing target )
				{
					if( !buffSpell.canCastOn(target) )
						return TargetFinder.CondRespon.FALSE;

					if( target == KratosMage.this) {
						if (KratosMage.this.lq.getHealthPercent() < 0.8f )
							return TargetFinder.CondRespon.TRUE;
						else
							return TargetFinder.CondRespon.FALSE;
					}

					if( loc.distanceSquared(target.loc) < aq.getAttackRangeSquared() )
						return TargetFinder.CondRespon.RETURN_THIS_NOW;
					return TargetFinder.CondRespon.FALSE;
				}
			};

			friendlyParams.setTeamOfInterest( getTeamName() );
			friendlyParams.setOnThisTeam( true );
			friendlyParams.setWithinRangeSquared(getAQ().getFocusRangeSquared());
			friendlyParams.setFromThisLoc ( loc );
			friendlyParams.setLookAtBuildings( false );
			friendlyParams.setLookAtSoldiers( true );
		}
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

	@Override
	public Anim getDyingAnimation(){
		return Assets.deadSkeletonAnim;
	}



	@Override
	public ImageFormatInfo getImageFormatInfo(){
		return imageFormatInfo;
	}

	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2){
		imageFormatInfo=imageFormatInfo2;
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
	@Override
	public Image[] getStaticImages() {
		return staticImages;
	}

	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages) {
		KratosMage.staticImages = staticImages;
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
		KratosMage.cost = cost;
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
