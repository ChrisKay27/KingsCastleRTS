package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

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
import com.kingscastle.gameElements.livingThings.attacks.ProjectileAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoneTower extends AttackingBuilding
{
	private static final String TAG = "Stone Tower";

	public static final Buildings name = Buildings.StoneTower;

	private static RectF staticPerceivedArea = new RectF();

	private static final int aliveId = R.drawable.stone_tower;


	private static Image image,damagedImage,deadImage , iconImage;

	private static final Cost cost = new Cost ( 1000 , 0 , 500 , 0 );


	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;

	private static ArrayList<vector> staticDamageOffsets;

	@NonNull
    @Override
	protected AttackerAttributes getStaticAQ() {
		return STATIC_ATTACKER_ATTRIBUTES;
	}

	@NonNull
    @Override
	protected Attributes getStaticLQ() {
		return STATIC_ATTRIBUTES;
	}

	static
	{
		float dp = Rpg.getDp();

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(36000 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared(36000 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setDamage(50);
		STATIC_ATTACKER_ATTRIBUTES.setROF(600);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresAge(Age.STEEL); STATIC_ATTRIBUTES.setRequiresTcLvl(17);
		STATIC_ATTRIBUTES.setRangeOfSight(350);
		STATIC_ATTRIBUTES.setLevel( 1 ); //15);
		STATIC_ATTRIBUTES.setFullHealth(1000);
		STATIC_ATTRIBUTES.setHealth(1000);
		STATIC_ATTRIBUTES.setFullMana(125);
		STATIC_ATTRIBUTES.setMana(125);
		STATIC_ATTRIBUTES.setHpRegenAmount(2);
		STATIC_ATTRIBUTES.setRegenRate(2000);


		image = Assets.loadImage(aliveId);

		staticPerceivedArea = Rpg.guardTowerArea;

		//		int widthDiv2 = image.getWidthDiv2();
		//		int heightDiv2 = image.getHeightDiv2();
		//
		//		staticPerceivedArea.set( - widthDiv2 , -  15 * dp  , widthDiv2 , heightDiv2 - 5 * dp );

	}

	{
		setAQ(new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, getLQ().getBonuses()));

	}



	public StoneTower()
	{
		super( name , null );
	}

	public StoneTower( @NonNull vector v , Teams t )
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
	public boolean create(@NonNull MM mm) {
		getAQ().setCurrentAttack( new ProjectileAttack( mm , this , new Arrow() ) ) ;
		return super.create(mm);
	}

	@Override
	public void loadAnimation( @NotNull MM mm )
	{
		boolean justCreatedAnim = buildingAnim == null;

		super.loadAnimation( mm );

		if( buildingAnim != null && justCreatedAnim )
			buildingAnim.setOffs( 0 , -Rpg.sixTeenDp );
	}

	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em)
	{
		em.add( a , true);
		backing.setSize(Backing.SMALL);
		em.add( backing, EffectsManager.Position.Behind );
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
		if ( image == null || damagedImage == null || deadImage == null )
		{
			image = Assets.loadImage( aliveId );
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
	public Image getIconImage()
	{

		return iconImage;
	}

	private static void setIconImage(Image iconImage)
	{
		StoneTower.iconImage = iconImage;
	}

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