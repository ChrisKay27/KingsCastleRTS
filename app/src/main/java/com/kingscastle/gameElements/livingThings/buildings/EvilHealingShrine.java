package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.TargetingParams;
import com.kingscastle.gameElements.livingThings.abilities.AbilityManager;
import com.kingscastle.gameElements.livingThings.abilities.HealingSpell;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.HealingAttack;
import com.kingscastle.gameElements.targeting.TargetFinder;
import com.kingscastle.gameElements.targeting.TargetFinder.CondRespon;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class EvilHealingShrine extends Shrine
{

	private static final String TAG = "Evil Healing Shrine";

	public static final Buildings name = Buildings.EvilHealingShrine;

	private static final Image image = Assets.loadImage(R.drawable.satanic_podium);
	private static final Image deadImage = Assets.loadImage( R.drawable.small_rubble );
	@Nullable
    private static final Image iconImage = null;//Assets.loadImage( R.drawable.satanic_podium_icon );

	private static RectF staticPerceivedArea = Rpg.oneByOneArea; // this is only the offset from the mapLocation.

	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;

	private static final Cost costs = new Cost( 300 , 0 , 300 , 300 , 0 );
	@NonNull
    @Override
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }
	@NonNull
    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }

	static
	{

		float dpSquared = Rpg.getDpSquared();
		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared     ( 30000 * dpSquared );
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared    ( 30000 * dpSquared );
		STATIC_ATTACKER_ATTRIBUTES.setROF( 1000 );

		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 ); // 1 );
		STATIC_ATTRIBUTES.setFullHealth( 500 );
		STATIC_ATTRIBUTES.setHealth( 500 );
		STATIC_ATTRIBUTES.setFullMana( 100 );
		STATIC_ATTRIBUTES.setMana( 100 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 2 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setSpeed( 0 );

		STATIC_ATTACKER_ATTRIBUTES.setdDamageAge(4);
		STATIC_ATTACKER_ATTRIBUTES.setdDamageLvl(1);
		STATIC_ATTACKER_ATTRIBUTES.setdROFAge(-100);
		STATIC_ATTACKER_ATTRIBUTES.setdROFLvl(-20);
		STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredAge(-3000 * dpSquared);
		STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredLvl(-100 * dpSquared);
		STATIC_ATTRIBUTES.setAge( Age.STONE );
		STATIC_ATTRIBUTES.setdHealthAge(300);
		STATIC_ATTRIBUTES.setdHealthLvl(50);
		STATIC_ATTRIBUTES.setdRegenRateAge( -100 );
		STATIC_ATTRIBUTES.setdRegenRateLvl(-20);

	}
	@Nullable
    private final HealingSpell hs;
	{
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, getLQ().getBonuses() ) );
		hs = new HealingSpell(this,null);
		hs.setCaster( this );
		hs.setShowEvilAnimation( true );
		getAQ().setCurrentAttack( new HealingAttack(getMM(), this , hs ) );
	}

	public EvilHealingShrine()
	{
		super(name);
	}

	public EvilHealingShrine( @NonNull vector v, Teams t )
	{
		this();
		setTeam(t);
		setLoc(v);
	}
	@Override
	protected void setupAttack() {

	}

	private TargetingParams params;

	private void createTargetingParams()
	{
		if( params == null )
		{
			final AbilityManager abm = getMM().getTM().getTeam( getTeamName() ).getAbm();
			params = new TargetingParams()
			{
				@NonNull
                @Override
				public CondRespon postRangeCheckCondition( @NonNull LivingThing target )
				{
					if( target.lq.getHealth() == target.lq.getFullHealth() )
						return CondRespon.FALSE;
					return CondRespon.TRUE;
				}
			};
			params.setLookAtBuildings( false );
			params.setTeamOfInterest( getTeamName() );
			params.setOnThisTeam( true );
			params.setWithinRangeSquared( getAQ().getAttackRangeSquared() );
			params.setFromThisLoc ( loc );
		}
	}



	@Override
	public void findATarget()
	{
		if( startTargetingAgainAt < GameTime.getTime() )
		{
			createTargetingParams();
			TargetFinder tf = this.targetFinder;
			if( tf != null )
				setTarget(tf.findTarget( params ));

			startTargetingAgainAt = GameTime.getTime() + 2000;

			////Log.d( TAG ,"Looking for target, found a " + target );
		}
	}



	@Override
	public boolean isOutOfRangeOrDead( LivingThing thing1 , LivingThing thingA )
	{
		boolean outOfRange = isOutOfRangeOrDeadORFullHealth( thing1 , thingA );
		////Log.d( TAG ,"Target is out of range or dead or full health" );
		return outOfRange;


		//return isOutOfRangeOrDeadORFullHealth( thing1 , thingA );
	}

	private boolean isOutOfRangeOrDeadORFullHealth( @Nullable LivingThing healer ,
			@Nullable LivingThing healingTarget2 )
	{

		if( healer == null || healingTarget2 == null )
			return true;

		if( healer == healingTarget2 )
			return healingTarget2.lq.getHealth() == healingTarget2.lq.getFullHealth();

		if ( super.isOutOfRangeOrDead ( healer, healingTarget2 ))
			return true;
		else
		{
			if( healingTarget2.lq.getHealth() == healingTarget2.lq.getFullHealth())
				return true;
		}

		return false;
	}



	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em)
	{
		em.add( a , true);
		backing.setSize(Backing.TINY);
		em.add( backing, EffectsManager.Position.Behind );
	}

	@Override
	public Image getImage() {
		return image;
	}
	@Override
	public Image getDamagedImage() {
		return image;
	}
	@Override
	public Image getDeadImage() {
		return deadImage;
	}
	@Nullable
    @Override
	public Image getIconImage() {
		return iconImage;
	}

	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		return staticPerceivedArea;
	}


	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2)
	{
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}

	//	@Override
	//	public void addHealthBarToAnim( Races race )
	//	{
	//		super.addHealthBarToAnim( race );
	//		if( healthBar != null )
	//			healthBar.setShowBar( false );
	//	}


	@NonNull
    @Override
	public Cost getCosts(){
		return costs;
	}

	@NonNull
    @Override
	public String getName() {
		return TAG;
	}



	@NonNull
    @Override
	public Attributes getNewAttributes() {
		return new Attributes(STATIC_ATTRIBUTES);
	}



}
