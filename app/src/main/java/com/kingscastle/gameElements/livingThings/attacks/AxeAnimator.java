package com.kingscastle.gameElements.livingThings.attacks;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.Sound;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameUtils.vector;

import java.util.ArrayList;
import java.util.List;

public class AxeAnimator extends AttackAnimator
{


	private static List<Image> staticAxeImagesEast;
    private static List<Image> staticAxeImagesWest;
    private static List<Image> staticAxeImagesSouth;
    private static List<Image> staticAxeImagesNorth;

	private static List<Image> staticAxeImages = Assets.loadAnimationImages(R.drawable.axe_with_reverse, 6, 2);

    static{
        staticAxeImagesSouth = new ArrayList<>();

        staticAxeImagesSouth.add(staticAxeImages.get( 1 ));
        staticAxeImagesSouth.add(staticAxeImages.get( 2 ));
        staticAxeImagesSouth.add(staticAxeImages.get( 3 ));
        staticAxeImagesSouth.add(staticAxeImages.get( 4 ));
        staticAxeImagesSouth.add(staticAxeImages.get( 5 ));
        staticAxeImagesWest = staticAxeImagesSouth;

        staticAxeImagesEast = new ArrayList<>();
        staticAxeImagesEast.add( staticAxeImages.get( 10 ));
        staticAxeImagesEast.add( staticAxeImages.get( 9 ));
        staticAxeImagesEast.add( staticAxeImages.get( 8 ));
        staticAxeImagesEast.add( staticAxeImages.get( 7 ));
        staticAxeImagesEast.add( staticAxeImages.get( 6 ));
        staticAxeImagesNorth = staticAxeImagesEast;
    }

	private static List<Sound> axeSounds;

	@NonNull
    private static final vector EOffset;
    @NonNull
    private static final vector WOffset;
    @NonNull
    private static final vector SOffset;
    @NonNull
    private static final vector NOffset ;



	private int nextSound = 0;

	//private final Attack ownersAttack;



	static
	{
		float dp = Rpg.getDp();

		EOffset = new vector( 0 , 3 * dp );
		WOffset = new vector( -dp , 3 * dp );
		NOffset = new vector( -3 * dp , 3 * dp );
		SOffset = new vector( 3 * dp , 3 * dp );

	}

	{
		setEOffset( EOffset );
		setWOffset( WOffset );
		setNOffset( NOffset );
		setSOffset( SOffset );
	}


	public AxeAnimator( Humanoid owner , MeleeAttack ownersAttack )
	{
		super(owner,3);
		//this.ownersAttack=ownersAttack;
		loadImages();
	}


	private void loadImages()	{

		standingStillSouth = staticAxeImages.get(0);
		standingStillWest = standingStillSouth;
		standingStillEast = staticAxeImages.get(11);
		standingStillNorth = standingStillEast;

		imagesEast = staticAxeImagesEast;
		imagesNorth = staticAxeImagesNorth;
		imagesWest = staticAxeImagesSouth;
		imagesSouth = staticAxeImagesSouth;

	}


	//
	//	@Override
	//	public void doAttack()
	//	{
	//		ownersAttack.checkHit( attackingInDirectionUnitVector );
	//	}



	@Override
	public void playSound()
	{
		if( axeSounds == null )
			return;

		if( wasDrawnThisFrame() )
		{
			Sound sound = axeSounds.get( nextSound );

			if( sound == null )
				return;

			sound.play( 0.1f );

			++nextSound ;

			if( nextSound > axeSounds.size() - 1 )
				nextSound = 0;
        }

	}



	@Override
	public void loadSounds()
	{
		if( axeSounds == null )
		{
			axeSounds = new ArrayList<>();
			String s;

			for( int i = 1 ; i < 12 ; ++i )	{
				if( i < 10 )
					s = "0";
				else
					s = "";

				axeSounds.add( Rpg.getGame().getAudio().createSound("workerSounds/axe_chopping_-" + s + i + ".wav") );
			}
		}
	}




}
