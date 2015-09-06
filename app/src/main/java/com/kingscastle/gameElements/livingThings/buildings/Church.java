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
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

public class Church extends Building
{

	private static final String TAG = "Church";

	public static final Buildings name = Buildings.Church;

	private static  RectF staticPerceivedArea; // this includes the offset from the mapLocation


	private static Image deadImage;

	private static Cost cost = new Cost( 10000 , 0 , 10000 , 0 );

	@NonNull
    private static final Attributes STATIC_ATTRIBUTES;



	@NonNull
    @Override
	protected Attributes getStaticLQ() { return STATIC_ATTRIBUTES;   }

	private RectF perceivedArea;


	private BuildableUnits buildableUnits ;



	static
	{
		int bronzeAgeID = R.drawable.church_bronze_age;
		int stoneAgeID = R.drawable.church_stone_age;


		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresAge(Age.STONE); STATIC_ATTRIBUTES.setRequiresTcLvl(4);
		STATIC_ATTRIBUTES.setRangeOfSight( 250 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth(250);
		STATIC_ATTRIBUTES.setHealth(250); STATIC_ATTRIBUTES.setdHealthLvl(30);
		STATIC_ATTRIBUTES.setHpRegenAmount(2);
		STATIC_ATTRIBUTES.setRegenRate( 4000 );

		staticPerceivedArea = Rpg.fourByFourArea;
	}

	{
		deadImage = Assets.genericDestroyedBuilding;
	}

	public Church(){
		super( name );
	}


	public Church( vector v , Teams t )
	{
		super( name , t );

		setLoc( v );
		setTeam( t );

		setBuildingsName( name );
		loadImages();

		loadPerceivedArea();
		setAttributes();
	}




	@Override
	protected void addAnimationToEm(@NonNull Anim a, boolean sorted, @NonNull EffectsManager em)
	{
		em.add( a , true);
		backing.setSize(Backing.LARGE);
		em.add( backing, EffectsManager.Position.Behind );
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
	public void loadImages(){
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
		Church.cost = cost;
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
		return TAG;
	}





}
