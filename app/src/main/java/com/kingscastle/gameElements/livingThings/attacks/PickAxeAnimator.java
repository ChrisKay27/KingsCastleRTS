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

public class PickAxeAnimator extends AttackAnimator
{
	private static List<Image> staticPickAxeImagesEast;
    private static List<Image> staticPickAxeImagesWest;
    private static List<Image> staticPickAxeImagesSouth;
    private static List<Image> staticPickAxeImagesNorth;

	private static List<Image> staticPickAxeImages;


	private static List<Sound> pickAxeSounds;

	@NonNull
    private static final vector EOffset;
    @NonNull
    private static final vector WOffset;
    @NonNull
    private static final vector SOffset;
    @NonNull
    private static final vector NOffset;


	private int nextSound = 0;

	//private final Attack ownersAttack;



	static	{
		EOffset = new vector(  4* Rpg.getDp() , 3*Rpg.getDp()  );
		WOffset = new vector( -3*Rpg.getDp() , 4*Rpg.getDp()  );
		NOffset = new vector( -3*Rpg.getDp() , 3*Rpg.getDp()  );
		SOffset = new vector(  0 , 5*Rpg.getDp()  );

	}

	{
		setEOffset(EOffset);
		setWOffset(WOffset);
		setNOffset(NOffset);
		setSOffset(SOffset);
	}


	public PickAxeAnimator(@NonNull Humanoid owner, MeleeAttack ownersAttack)	{
		super(owner,3);
		//this.ownersAttack=ownersAttack;
		loadImages();
		loadSounds();
	}


	private void loadImages()	{

		if(staticPickAxeImages ==null)
		{
			staticPickAxeImages= Assets.loadAnimationImages(R.drawable.pickaxe_with_reverse, 8, 2);

			staticPickAxeImagesSouth = new ArrayList<>();
			staticPickAxeImagesSouth.add(staticPickAxeImages.get(1));
			staticPickAxeImagesSouth.add(staticPickAxeImages.get(2));
			staticPickAxeImagesSouth.add(staticPickAxeImages.get(3));
			staticPickAxeImagesSouth.add(staticPickAxeImages.get(4));
			staticPickAxeImagesSouth.add(staticPickAxeImages.get(5));
			staticPickAxeImagesSouth.add(staticPickAxeImages.get(6));
			staticPickAxeImagesSouth.add(staticPickAxeImages.get(7));

			staticPickAxeImagesWest = staticPickAxeImagesSouth;

			staticPickAxeImagesEast = new ArrayList<>();
			staticPickAxeImagesEast.add(staticPickAxeImages.get(15));
			staticPickAxeImagesEast.add(staticPickAxeImages.get(14));
			staticPickAxeImagesEast.add(staticPickAxeImages.get(13));
			staticPickAxeImagesEast.add(staticPickAxeImages.get(12));
			staticPickAxeImagesEast.add(staticPickAxeImages.get(11));
			staticPickAxeImagesEast.add(staticPickAxeImages.get(10));
			staticPickAxeImagesEast.add(staticPickAxeImages.get(9));
			staticPickAxeImagesNorth=staticPickAxeImagesEast;

		}

		standingStillSouth = staticPickAxeImages.get(0);
		standingStillWest = standingStillSouth;
		standingStillEast = staticPickAxeImages.get(8);
		standingStillNorth = standingStillEast;

		imagesEast=staticPickAxeImagesEast;
		imagesNorth=staticPickAxeImagesNorth;
		imagesWest=staticPickAxeImagesSouth;
		imagesSouth=staticPickAxeImagesSouth;

	}



	//	@Override
	//	public void doAttack()
	//	{
	//		ownersAttack.checkHit( attackingInDirectionUnitVector );
	//	}



	@Override
	public void playSound()
	{
		loadSounds();

		if( pickAxeSounds == null )
		{
			return;
		}

		if( wasDrawnThisFrame() )
		{
			Sound sound = pickAxeSounds.get( nextSound );

			if( sound == null )
			{
				return;
			}

			sound.play( 0.5f );

			++nextSound ;

			if( nextSound > 10 )
			{
				nextSound = 0;
			}
		}

	}



	@Override
	public void loadSounds()
	{
		if( pickAxeSounds == null )
		{
			pickAxeSounds = new ArrayList<Sound>();

			for( int i = 0 ; i < 11 ; ++i )
			{
				pickAxeSounds.add(Rpg.getGame().getAudio().createSound("pickaxe/pickaxe_"+ i + ".wav"));
			}
		}
	}




}
