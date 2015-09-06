package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UndeadGuardTower extends AttackingBuilding
{
	private static final String TAG = "UndeadGuardTower";
	private static final String NAME = "Guard Tower";
	public static final Buildings name = Buildings.UndeadGuardTower;

	private static RectF staticPerceivedArea;


	private static Image image , damagedImage ,
	deadImage , iconImage;

	private static final Cost cost = new Cost( 1000 , 0 , 1000 , 0 );


	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;

	private static ArrayList<vector> staticDamageOffsets;

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

		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(35000 * dpSquared);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared(35000 * dpSquared);
		STATIC_ATTACKER_ATTRIBUTES.setDamage(25);
		STATIC_ATTACKER_ATTRIBUTES.setROF(700);

		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight(300);
		STATIC_ATTRIBUTES.setLevel( 1 ); //10);
		STATIC_ATTRIBUTES.setFullHealth(500);
		STATIC_ATTRIBUTES.setHealth(500);
		STATIC_ATTRIBUTES.setFullMana(125);
		STATIC_ATTRIBUTES.setMana(125);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);

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



		staticPerceivedArea = Rpg.guardTowerArea;
	}


	{
		if( staticPerceivedArea == null )
		{
			staticPerceivedArea = new GuardTower().getStaticPerceivedArea();
		}
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES,getLQ().getBonuses()) );
	}




	public UndeadGuardTower()
	{
		super( name , null );


	}

	public UndeadGuardTower( @NonNull vector v , Teams t )
	{
		super( name , t );
		setTeam( t );
		setLoc( v );
		loadPerceivedArea();
		loadImages();
	}

	@Override
	protected void setupAttack() {

	}


	@Override
	public void loadAnimation( @NotNull MM mm )
	{
		boolean justCreatedAnim = buildingAnim == null;

		super.loadAnimation( mm );

		if( buildingAnim != null && justCreatedAnim )
			buildingAnim.setOffs( 0 , -Rpg.sixTeenDp );
	}



	void loadDamageOffsets()
	{
		float dp = Rpg.getDp();

		staticDamageOffsets = new ArrayList<vector>();
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*-5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
		staticDamageOffsets.add( new vector( Math.random()*5*dp , -15*dp + Math.random()*30*dp ) );
	}

	@Override
	public ArrayList<vector> getDamageOffsets()
	{
		if( staticDamageOffsets == null )
		{
			loadDamageOffsets();
		}
		return staticDamageOffsets;
	}









	@Override
	public Image getImage() {
		loadImages();
		return image;
	}
	@Override
	public Image getDamagedImage() {
		loadImages();
		return damagedImage;
	}

	@Override
	public Image getDeadImage() {
		loadImages();
		return deadImage;
	}
	@Override
	public void loadImages()
	{
		if (image == null)
		{
			int aliveId = 0;//R.drawable.evil_round_tower;
			image = Assets.loadImage(aliveId);
			damagedImage = image;
			deadImage = Assets.smallDestroyedBuilding;
		}
	}







	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();


		return staticPerceivedArea;
	}



	public void setPerceivedSpriteArea( RectF perceivedSpriteArea2 )
	{

		boolean areaWasNull = staticPerceivedArea == null;


		staticPerceivedArea = perceivedSpriteArea2;


		if( areaWasNull && staticPerceivedArea != null )
		{

			staticPerceivedArea.top = staticPerceivedArea.top + staticPerceivedArea.height()/1.5f;
		}

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
	public Cost getCosts() {
		return cost;
	}



	@NonNull
    @Override
	public Attributes getNewAttributes()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}

	@Override
	public Image getIconImage() {
		return iconImage;
	}

	private static void setIconImage(Image iconImage) {
		UndeadGuardTower.iconImage = iconImage;
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