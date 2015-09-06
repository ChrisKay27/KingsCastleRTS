package com.kingscastle.gameElements.livingThings.buildings;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

public class UndeadChurch extends Building
{

	private static final String TAG = "UndeadChurch";
	private static final String NAME = "Church";

	public static final Buildings name = Buildings.UndeadChurch;

	private static  RectF staticPerceivedArea; // this includes the offset from the mapLocation


	private static Image deadImage;


	private static Cost cost = new Cost( 700 , 0 , 700 , 0 );

	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;




	@NonNull
    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }

	static
	{
		//int ironAgeID = R.drawable.evil_church_iron_age;
		//int bronzeAgeID = R.drawable.evil_church_bronze_age;
		//int stoneAgeID = R.drawable.evil_church_stone_age;



		STATIC_ATTRIBUTES = new Attributes();  STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(1);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 ); //5);
		STATIC_ATTRIBUTES.setFullHealth(200);
		STATIC_ATTRIBUTES.setHealth(200);
		STATIC_ATTRIBUTES.setHpRegenAmount(2);
		STATIC_ATTRIBUTES.setRegenRate( 4000 );

		staticPerceivedArea = Rpg.fourByFourArea;
	}


	private RectF perceivedArea;

	private BuildableUnits buildableUnits ;



	public UndeadChurch( @NonNull vector v , Teams t )
	{
		this();
		setLoc( v );
		setTeam( t );

	}


	private UndeadChurch()
	{
		super( name );
		setBuildingsName( name );
		loadImages();

		loadPerceivedArea();
		setAttributes();
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




	/**
	 * returns a rectangle to be placed with its center on the mapLocation of the tower
	 */
	@Override
	public RectF getPerceivedArea()
	{
		loadPerceivedArea();

		if( perceivedArea == null )
		{
			perceivedArea = staticPerceivedArea;
		}
		return perceivedArea;

	}



	public void setPerceivedSpriteArea( RectF perceivedSpriteArea2 )
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





	@Override
	public Cost getCosts() {
		return cost;
	}

	public static void setCost(Cost cost) {
		UndeadChurch.cost = cost;
	}




	@NonNull
    @Override
	public Attributes getNewAttributes()
	{
		return new Attributes(STATIC_ATTRIBUTES);
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
