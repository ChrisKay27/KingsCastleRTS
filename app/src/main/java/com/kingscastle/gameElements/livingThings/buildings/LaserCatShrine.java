package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kaebe.kingscastle.R;
import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.Backing;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.SpellAttack;
import com.kingscastle.gameElements.spells.Laser;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class LaserCatShrine extends Shrine
{

	private static final String TAG = "Laser Cat Shrine";

	public static final Buildings name = Buildings.LaserCatShrine;

	private static final Image image     = Assets.loadImage(R.drawable.cat_statue);
	private static final Image deadImage = Assets.loadImage( R.drawable.small_rubble );
	@Nullable
    private static final Image iconImage = null;//Assets.loadImage( R.drawable.cat_statue_icon );

	private static RectF staticPerceivedArea = Rpg.oneByOneArea; // this is only the offset from the mapLocation.

	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;

	private static final Cost costs = new Cost( 500 , 0 , 500 , 0 );
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
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 14 );

		STATIC_ATTRIBUTES = new Attributes();
        STATIC_ATTRIBUTES.setRequiresAge(Age.IRON); STATIC_ATTRIBUTES.setRequiresTcLvl(14);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 ); // 2 );
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
		STATIC_ATTRIBUTES.setdHealthAge(200);
		STATIC_ATTRIBUTES.setdHealthLvl(40);
		STATIC_ATTRIBUTES.setdRegenRateAge( -100 );
		STATIC_ATTRIBUTES.setdRegenRateLvl(-20);

	}

	{
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, getLQ().getBonuses() ) );

		Laser laser = new Laser();
		laser.setCaster( this );
		laser.addOffs( new vector( -Rpg.twoDp , -Rpg.eightDp ));
		laser.addOffs( new vector( Rpg.twoDp , -Rpg.eightDp ));
		getAQ().setCurrentAttack( new SpellAttack(getMM(),this , laser ) ) ;


	}

	public LaserCatShrine()
	{
		super(name);
	}

	public LaserCatShrine( @NonNull vector v , Teams t )
	{
		this();
		setTeam(t);
		setLoc(v);
	}

	@Override
	public boolean act()
	{
		////Log.d( TAG ,"pre act(), target == " + target );
		super.act();
		////Log.d( TAG ,"post act(), target == " + target );
		return isDead();
	}

	@Override
	protected void setupAttack() {

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
