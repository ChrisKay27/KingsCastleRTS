package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.ProjectileAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

public class UndeadBarracks extends AttackingBuilding
{

	private static final String TAG = "UndeadBarracks";
	private static final String NAME = "Barracks";

	public static final Buildings name = Buildings.UndeadBarracks;


	private static Image deadImage;
	private static ImageFormatInfo imageFormatInfo;

	private static RectF staticPerceivedArea; // this is only the offset from the mapLocation.

	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES;
	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;

	private static final Cost cost = new Cost( 500 , 0 , 500 , 0 );



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

		//int ironAgeID = R.drawable.evil_barracks_iron_age;
		//int bronzeAgeID = R.drawable.evil_barracks_bronze_age;
		int stoneAgeID = R.drawable.evil_barracks_stone_age;


		float dp = Rpg.getDp();

		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(  22500 * dp * dp );
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared( 27000 * dp * dp );
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 20 );
		STATIC_ATTACKER_ATTRIBUTES.setROF( 800 );

		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setFullHealth( 300 );
		STATIC_ATTRIBUTES.setHealth( 300 );
		STATIC_ATTRIBUTES.setFullMana( 0 );
		STATIC_ATTRIBUTES.setMana( 0 );
		STATIC_ATTRIBUTES.setHpRegenAmount( 1 );
		STATIC_ATTRIBUTES.setRegenRate( 4000 );

		staticPerceivedArea = Rpg.fourByFourArea;
	}

	private RectF perceivedArea;



	{
		setAQ(new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES, getLQ().getBonuses()));

	}


	public UndeadBarracks()
	{
		super( name , null );
		setBuildingsName(name);
		loadImages();
		loadPerceivedArea();
		setAttributes();
	}

	public UndeadBarracks( @NonNull vector v, Teams t )
	{
		super(name, t);
		setTeam(t);
		setLoc(v);
	}


	@Override
	public boolean create(@NonNull MM mm) {
		getAQ().setCurrentAttack(new ProjectileAttack(mm, this, new Arrow())) ;
		return super.create(mm);
	}
	@Override
	protected void setupAttack() {

	}
	@Override
	public void setSelected ( boolean b )
	{
		super.setSelected( b );

	}






	@Override
	public Image getDeadImage()
	{
		loadImages();
		return deadImage;
	}

	@Override
	public void loadImages()
	{
		deadImage = Assets.genericDestroyedBuilding;
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
	public Cost getCosts() {
		return cost;
	}










	@Override
	public ImageFormatInfo getImageFormatInfo(){
		return imageFormatInfo;
	}

	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2){
		imageFormatInfo=imageFormatInfo2;
	}







	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{

		//////Log.i ( TAG , "getting perc area for a barracks, perc area = " + perceivedArea );
		if( perceivedArea == null )
		{
			perceivedArea = getStaticPerceivedArea();
			//
			//			Image image = getImage();
			//			if( image == null )
			//			{
			//				throw new IllegalStateException("image == null when trying to load perc area.");
			//			}
			//
			//			int widthDiv2 = image.getWidthDiv2();
			//			int heightDiv2 = image.getHeightDiv2();
			//
			//			perceivedArea = new RectF( - widthDiv2 , - heightDiv2 + 5 * dp  , widthDiv2 , heightDiv2 - 5 * dp );

		}
		//////Log.i ( TAG , "perc area is now: " + perceivedArea );
		return perceivedArea;
	}


	public void setPerceivedSpriteArea(RectF perceivedSpriteArea2)
	{
		staticPerceivedArea = perceivedSpriteArea2;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			staticPerceivedArea = new Barracks().getPerceivedArea();
		}
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea)
	{
		staticPerceivedArea = staticPercArea;
	}






	@NonNull
    @Override
	public Attributes getNewAttributes()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}





















}

