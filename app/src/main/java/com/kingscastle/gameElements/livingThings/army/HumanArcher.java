package com.kingscastle.gameElements.livingThings.army;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MediumRangedSoldier;
import com.kingscastle.gameElements.livingThings.attacks.AttackerAttributes;
import com.kingscastle.gameElements.livingThings.attacks.Bow;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.projectiles.Arrow;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;


public class HumanArcher extends MediumRangedSoldier
{
	private static final String TAG = "Archer";

	private static Image[] staticImages , redImages , blueImages , greenImages , orangeImages , whiteImages ;
	private static ImageFormatInfo imageFormatInfo;

	@NonNull
    private static final Attributes STATIC_ATTRIBUTES; @NonNull
                                                                @Override
	protected Attributes getStaticLQ()   { return STATIC_ATTRIBUTES; }
	@NonNull
    private static final AttackerAttributes STATIC_ATTACKER_ATTRIBUTES; @NonNull
                                                                    @Override
	protected AttackerAttributes getStaticAQ() { return STATIC_ATTACKER_ATTRIBUTES; }

	private static Cost cost = new Cost( 400 , 400 , 400 , 2 );

	static{
		int dp = (int) Rpg.getDp();
		imageFormatInfo = new ImageFormatInfo(0 , 0,
				0 , 0 , 4 , 2);
		imageFormatInfo.setRedId( R.drawable.archer_red );


		STATIC_ATTACKER_ATTRIBUTES = new AttackerAttributes();

		STATIC_ATTACKER_ATTRIBUTES.setStaysAtDistanceSquared( 17000 * dp * dp );
		STATIC_ATTACKER_ATTRIBUTES.setFocusRangeSquared(5000*dp*dp);
		STATIC_ATTACKER_ATTRIBUTES.setAttackRangeSquared(22500 * dp * dp); STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredAge(0); STATIC_ATTACKER_ATTRIBUTES.setdRangeSquaredLvl(500 * dp * dp);
		STATIC_ATTACKER_ATTRIBUTES.setDamage( 20 );  STATIC_ATTACKER_ATTRIBUTES.setdDamageAge( 0 ); STATIC_ATTACKER_ATTRIBUTES.setdDamageLvl( 3 );
		STATIC_ATTACKER_ATTRIBUTES.setROF(1000);

		STATIC_ATTRIBUTES = new Attributes(); STATIC_ATTRIBUTES.setRequiresBLvl(6); STATIC_ATTRIBUTES.setRequiresAge(Age.BRONZE); STATIC_ATTRIBUTES.setRequiresTcLvl(6);
		STATIC_ATTRIBUTES.setRangeOfSight( 350 );
		STATIC_ATTRIBUTES.setLevel( 1 );
		STATIC_ATTRIBUTES.setFullHealth( 200 );
		STATIC_ATTRIBUTES.setHealth( 200 ); STATIC_ATTRIBUTES.setdHealthAge( 0 ); STATIC_ATTRIBUTES.setdHealthLvl( 13 );
		STATIC_ATTRIBUTES.setFullMana(0);
		STATIC_ATTRIBUTES.setMana(0);
		STATIC_ATTRIBUTES.setHpRegenAmount(1);
		STATIC_ATTRIBUTES.setRegenRate(1000);
		STATIC_ATTRIBUTES.setArmor( 6 );  STATIC_ATTRIBUTES.setdArmorAge( 0 ); STATIC_ATTRIBUTES.setdArmorLvl( 1.4f );
		STATIC_ATTRIBUTES.setSpeed(2.1f * dp);
	}

	{
		setAQ( new AttackerAttributes(STATIC_ATTACKER_ATTRIBUTES,getLQ().getBonuses()) );
	}

	public HumanArcher(@NonNull vector loc, Teams team){
		super(team);
		setLoc(loc);
		setTeam(team);
	}

	public HumanArcher() {

	}


	@Override
	public void finalInit( MM mm )
	{
		super.finalInit( mm );

		if( getAQ().getCurrentAttack() != null )
			return;

		else if( getAQ().getCurrentAttack() == null )
		{
			getAQ().setCurrentAttack( new Bow( mm, this , new Arrow() ) );
			aliveAnim.add( getAQ().getCurrentAttack().getAnimator() , true );
		}
	}





	@Override
	public Image[] getImages()
	{
		loadImages();

		Teams teamName = getTeamName();
		if( teamName == null )
		{
			teamName = Teams.BLUE;
		}

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
		{
			redImages = Assets.loadImages(imageFormatInfo.getRedId(), 0, 0, 1, 1);
		}
		if( orangeImages == null )
		{
			orangeImages = Assets.loadImages( imageFormatInfo.getOrangeId()  , 0 , 0 , 1 , 1 );
		}
		if( blueImages == null )
		{
			blueImages = Assets.loadImages( imageFormatInfo.getBlueId()  , 0 , 0 , 1 , 1 );
		}
		if( greenImages == null )
		{
			greenImages = Assets.loadImages( imageFormatInfo.getGreenId()  , 0 , 0 , 1 , 1 );
		}
		if( whiteImages == null )
		{
			whiteImages = Assets.loadImages( imageFormatInfo.getWhiteId()  , 0 , 0 , 1 , 1 );
		}
	}




	/**
	 * DO NOT LOAD THE IMAGES HERE, USE GETIMAGES() to make sure they are not null.
	 * @return the staticImages
	 */
	@Override
	public Image[] getStaticImages()
	{
		return staticImages;
	}



	/**
	 * @param staticImages the staticImages to set
	 */
	@Override
	public void setStaticImages(Image[] staticImages)
	{
		HumanArcher.staticImages = staticImages;
	}




	@Override
	public ImageFormatInfo getImageFormatInfo()
	{
		return imageFormatInfo;
	}


	public void setImageFormatInfo(ImageFormatInfo imageFormatInfo2)
	{
		imageFormatInfo=imageFormatInfo2;
	}



	@Override
	public RectF getStaticPerceivedArea()
	{
		return Rpg.getNormalPerceivedArea();
	}



	@Override
	public void setStaticPerceivedArea(RectF staticPercArea2) {

	}




	@NonNull
    @Override
	public Attributes getNewAttributes()
	{
		return new Attributes(STATIC_ATTRIBUTES);
	}


	@Override
	public Cost getCosts()
	{
		return cost;
	}

	public static void setCost(Cost cost)
	{
		HumanArcher.cost = cost;
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
