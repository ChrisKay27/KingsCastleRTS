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
import com.kingscastle.gameElements.livingThings.abilities.HealingSpell;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.HealingAttack;
import com.kingscastle.gameElements.targeting.TargetFinder;
import com.kingscastle.gameElements.targeting.TargetFinder.CondRespon;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class BasicHealingShrine extends Shrine
{

	private static final String TAG = "Basic Healing Shrine";
	private static final String NAME = "Healing Shrine";

	public static final Buildings name = Buildings.BasicHealingShrine;

	private static final Image image = Assets.loadImage(R.drawable.stone_circle);
	private static final Image deadImage = Assets.loadImage( R.drawable.small_rubble );
	private static final Image iconImage = Assets.loadImage( R.drawable.stone_circle_icon );

	private static RectF staticPerceivedArea = Rpg.oneByOneArea; // this is only the offset from the mapLocation.

	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;

	private static final Cost costs = new Cost( 1000 , 0 , 0 , 0 );

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
		STATIC_ATTACKER_ATTRIBUTES.setdROFAge(-100);
		STATIC_ATTACKER_ATTRIBUTES.setdROFLvl(-20);
		STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredAge(3000 * dpSquared);
		STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredLvl(100 * dpSquared);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(3);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 200 );
		STATIC_ATTRIBUTES.setHealth( 200 );
		STATIC_ATTRIBUTES.setFullMana( 100 );
		STATIC_ATTRIBUTES.setMana( 100 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 2 );
		STATIC_ATTRIBUTES.setRegenRate( 1000 );
		STATIC_ATTRIBUTES.setSpeed( 0 );

		STATIC_ATTRIBUTES.setHealAmount(15); STATIC_ATTRIBUTES.setdHealLvl(10);

		STATIC_ATTRIBUTES.setdHealthAge(0);
		STATIC_ATTRIBUTES.setdHealthLvl(35);
		STATIC_ATTRIBUTES.setdRegenRateAge(0);
		STATIC_ATTRIBUTES.setdRegenRateLvl(0);

	}


	@Nullable
    private final HealingSpell hs;

	{
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, getLQ().getBonuses() ) );
		hs = new HealingSpell(this,null);
		hs.setCaster( this );
		getAQ().setCurrentAttack( new HealingAttack(getMM(), this , hs ) );
	}

	public BasicHealingShrine()
	{
		super(name);
	}

	@Override
	protected void setupAttack() {

	}

	public BasicHealingShrine( @NonNull vector v, Teams t )
	{
		this();
		setTeam(t);
		setLoc(v);
	}


	private TargetingParams params;

	private void createTargetingParams()
	{
		if( params == null )
		{
			params = new TargetingParams()
			{
				@NonNull
                @Override
				public CondRespon postRangeCheckCondition( @NonNull LivingThing target )
				{
					if( target.lq.getHealth() == target.lq.getFullHealth() )
						return CondRespon.FALSE;

					if( hs.canCastOn(target) )
						return CondRespon.TRUE;

					return CondRespon.FALSE;
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
		{
			return healingTarget2.lq.getHealth() == healingTarget2.lq.getFullHealth();
		}
		if ( super.isOutOfRangeOrDead ( healer, healingTarget2 ))
		{
			return true;
		}
		else
		{
			if( healingTarget2.lq.getHealth() == healingTarget2.lq.getFullHealth())
			{
				return true;
			}
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
		return NAME;
	}


	@NonNull
    @Override
	public Attributes getNewAttributes() {
		return new Attributes(STATIC_ATTRIBUTES);
	}



}
