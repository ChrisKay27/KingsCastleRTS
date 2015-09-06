package com.kingscastle.gameElements.livingThings.attacks;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.Sound;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameUtils.vector;
import com.kaebe.kingscastle.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MeleeAnimator extends AttackAnimator
{

	public enum MeleeTypes
	{
		LongSword , Sabre , Mace , Hands ,  PickAxe , Hammer , Axe, Hoe, RESOURCES
	}

	private static final List<Image> staticLongSwordImagesEast = new ArrayList<>(5);
	private static final List<Image> staticLongSwordImagesWest = new ArrayList<>(5);
	private static final List<Image> staticLongSwordImagesSouth = new ArrayList<>(5);
	private static final List<Image> staticLongSwordImagesNorth = new ArrayList<>(5);
	private static final List<Image> staticSabreImagesEast = new ArrayList<>(5);
	private static final List<Image> staticSabreImagesWest = new ArrayList<>(5);
	private static final List<Image> staticSabreImagesSouth = new ArrayList<>(5);
	private static final List<Image> staticSabreImagesNorth = new ArrayList<>(5);
	private static final List<Image> staticMaceImagesEast = new ArrayList<>(5);
	private static final List<Image> staticMaceImagesWest = new ArrayList<>(5);
	private static final List<Image> staticMaceImagesSouth = new ArrayList<>(5);
	private static final List<Image> staticMaceImagesNorth = new ArrayList<>(5);
	private static final List<Image> staticHandImagesEast = new ArrayList<>(5);
	private static final List<Image> staticHandImagesWest = new ArrayList<>(5);
	private static final List<Image> staticHandImagesSouth = new ArrayList<>(5);
	private static final List<Image> staticHandImagesNorth = new ArrayList<>(5);

	private static List<Image> staticLongSwordImages;
	private static List<Image> staticSabreImages;
	private static List<Image> staticMaceImages;
	private static List<Image> staticHandImages;



	protected static ArrayList<Sound> longSwordSounds,sabreSounds,maceSounds;
	//private final int nextSound = 0;
	//private final Attack ownersAttack;
	@NonNull
    private final MeleeTypes meleeType;
	@NonNull
    private static final vector EOffset;
	@NonNull
    private static final vector WOffset;
	@NonNull
    private static final vector SOffset;
	@NonNull
    private static final vector NOffset ;



	static
	{
		EOffset = new vector( 0 , 3 * Rpg.getDp() );
		WOffset = EOffset;
		NOffset = new vector();// -3 * Rpg.getDp() , 3 * Rpg.getDp() );
		SOffset = new vector( 5 * Rpg.getDp() , 6 * Rpg.getDp() );


        //Long sword images
        staticLongSwordImages = Assets.loadAnimationImages(R.drawable.longsword_full,6,4);

        for( int i = 1 ; i < 6 ; ++i )
            staticLongSwordImagesNorth.add( staticLongSwordImages.get(i) );
        for( int i = 7 ; i < 12 ; ++i )
            staticLongSwordImagesWest.add( staticLongSwordImages.get(i) );
        for( int i = 16 ; i > 11 ; --i )
            staticLongSwordImagesEast.add( staticLongSwordImages.get(i) );
        for( int i = 19 ; i < 24 ; ++i )
            staticLongSwordImagesSouth.add( staticLongSwordImages.get(i) );


        //Hand images... for punching, don't think they are used
        staticHandImages= Assets.loadAnimationImages(R.drawable.hand_with_reverse, 6, 2);

        staticHandImagesSouth.add(staticHandImages.get(1));
        staticHandImagesSouth.add(staticHandImages.get(2));
        staticHandImagesSouth.add(staticHandImages.get(3));
        staticHandImagesSouth.add(staticHandImages.get(4));
        staticHandImagesSouth.add(staticHandImages.get(5));
        staticHandImagesWest.addAll(staticHandImagesSouth);

        staticHandImagesEast.add(staticHandImages.get(10));
        staticHandImagesEast.add(staticHandImages.get(9));
        staticHandImagesEast.add(staticHandImages.get(8));
        staticHandImagesEast.add(staticHandImages.get(7));
        staticHandImagesEast.add(staticHandImages.get(6));
        staticHandImagesNorth.addAll(staticHandImagesEast);




        // Load Sabre Images... obviously
        staticSabreImages = Assets.loadAnimationImages(R.drawable.sabre_with_reverse,6,2);
        staticSabreImagesSouth.add(staticSabreImages.get(1));
        staticSabreImagesSouth.add(staticSabreImages.get(2));
        staticSabreImagesSouth.add(staticSabreImages.get(3));
        staticSabreImagesSouth.add(staticSabreImages.get(4));
        staticSabreImagesSouth.add(staticSabreImages.get(5));
        staticSabreImagesWest.addAll(staticSabreImagesSouth);
        staticSabreImagesEast.add(staticSabreImages.get(10));
        staticSabreImagesEast.add(staticSabreImages.get(9));
        staticSabreImagesEast.add(staticSabreImages.get(8));
        staticSabreImagesEast.add(staticSabreImages.get(7));
        staticSabreImagesEast.add(staticSabreImages.get(6));
        staticSabreImagesNorth.addAll(staticSabreImagesEast);



        staticMaceImages = Assets.loadAnimationImages( R.drawable.mace_with_reverse , 6 , 2 );

        staticMaceImagesSouth.add(staticMaceImages.get( 1 ));
        staticMaceImagesSouth.add(staticMaceImages.get(2));
        staticMaceImagesSouth.add(staticMaceImages.get(3));
        staticMaceImagesSouth.add(staticMaceImages.get(4));
        staticMaceImagesSouth.add(staticMaceImages.get(5));
        staticMaceImagesWest.addAll(staticMaceImagesSouth);

        staticMaceImagesEast.add(staticMaceImages.get(10));
        staticMaceImagesEast.add(staticMaceImages.get(9));
        staticMaceImagesEast.add(staticMaceImages.get(8));
        staticMaceImagesEast.add(staticMaceImages.get(7));
        staticMaceImagesEast.add(staticMaceImages.get(6));
        staticMaceImagesNorth.addAll(staticMaceImagesEast);


	}


	{
		setEOffset( EOffset );
		setWOffset( WOffset );
		setNOffset( NOffset );
		setSOffset( SOffset );
	}


	public MeleeAnimator( Humanoid owner , @NotNull MeleeTypes weaponType , MeleeAttack ownersAttack )
	{
		super( owner , 4 );
		//this.ownersAttack=ownersAttack;
		meleeType = weaponType;
		loadImages(weaponType);
	}



	public MeleeAnimator( Humanoid owner , MeleeAttack ownersAttack ) {
		this(owner,MeleeTypes.LongSword,ownersAttack);
	}



	private void loadImages( @NotNull MeleeTypes meleeType ) {
		float dp = Rpg.getDp();

		switch(meleeType) {
		case Hands:
			standingStillSouth = staticHandImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast = staticHandImages.get(11);
			standingStillNorth = standingStillEast;
			imagesEast  = staticHandImagesEast  ;
			imagesNorth = staticHandImagesNorth ;
			imagesWest  = staticHandImagesSouth ;
			imagesSouth = staticHandImagesSouth ;

			break;

		default:
		case LongSword:
			standingStillNorth = staticLongSwordImages.get(0);
			standingStillWest  = staticLongSwordImages.get(6);
			standingStillEast  = staticLongSwordImages.get(17);
			standingStillSouth = staticLongSwordImages.get(18);

			imagesEast =staticLongSwordImagesEast;
			imagesNorth=staticLongSwordImagesNorth;
			imagesWest =staticLongSwordImagesWest;
			imagesSouth=staticLongSwordImagesSouth;

			setSOffset( new vector(getSOffset()) ); getSOffset().add( -dp , 2*dp );
			setNOffset( new vector(getNOffset()) ); getNOffset().add( 2*dp , -dp );
			break;

		case Sabre:

			standingStillSouth = staticSabreImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast =staticSabreImages.get(11);
			standingStillNorth = standingStillEast;
			imagesEast =staticSabreImagesEast;
			imagesNorth=staticSabreImagesNorth;
			imagesWest =staticSabreImagesWest;
			imagesSouth=staticSabreImagesSouth;

			setSOffset( new vector(getSOffset()) ); getSOffset().add( -2*dp , -2*dp );

			break;

		case Mace:

			standingStillSouth = staticMaceImages.get(0);
			standingStillWest = standingStillSouth;
			standingStillEast = standingStillNorth = staticMaceImages.get(11);

			imagesEast = staticMaceImagesEast;
			imagesNorth = staticMaceImagesNorth;
			imagesWest = staticMaceImagesWest;
			imagesSouth = staticMaceImagesSouth;

			break;
		}

	}


	//	@Override
	//	public void doAttack()
	//	{
	//		ownersAttack.checkHit( attackingInDirectionUnitVector );
	//	}

	@Override
	public void playSound()
	{
//				if( wasDrawnThisFrame() )
//				{
//
//					longSwordSounds.get( nextSound ).play( 0.5f );
//
//					++nextSound;
//
//					if( nextSound > 2 )
//					{
//						nextSound = 0;
//					}
//
//				}

	}



	@Override
	public void loadSounds()
	{
		//		if( longSwordSounds == null )
		//		{
		//			longSwordSounds=new ArrayList<Sound>();
		//
		//			longSwordSounds.add( Rpg.getGame().getAudio().createSound( "attackSounds/melee_sound.wav" ) );
		//			longSwordSounds.add( Rpg.getGame().getAudio().createSound( "attackSounds/melee_sound2.wav" ) );
		//			longSwordSounds.add( Rpg.getGame().getAudio().createSound( "attackSounds/sword_sound.wav" ) );
		//
		//			sabreSounds = maceSounds = longSwordSounds;
		//		}
	}



	protected boolean thereAreNoImagesYet()
	{
		if( meleeType == MeleeTypes.Hands )
			return true;
		else
			return false;
	}






}
