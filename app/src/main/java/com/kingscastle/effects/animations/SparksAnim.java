package com.kingscastle.effects.animations;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;


public class SparksAnim extends Anim {

    private static final List<Image> blueImages1 = Assets.loadAnimationImages(R.drawable.sparks, 4, 6, 5, 4),
            blueImages2 = Assets.loadAnimationImages(R.drawable.sparks, 4, 6, 4, 4),
            redImages1 = Assets.loadAnimationImages(R.drawable.sparks, 4, 6, 0, 4),
            redImages2 = Assets.loadAnimationImages(R.drawable.sparks, 4, 6, 1, 4),
            yellowImages1 = Assets.loadAnimationImages(R.drawable.sparks, 4, 6, 2, 4),
            yellowImages2 = Assets.loadAnimationImages(R.drawable.sparks, 4, 6, 3, 4);

    private final int staticTfb = 50;
	private final int color;

	public SparksAnim( @NonNull vector loc , int color )
	{
		super(loc);
		setTbf( staticTfb );
		this.color = color;
		loadImages( color );
		onlyShowIfOnScreen = true;
	}

	public SparksAnim( int color )
	{
		setTbf( staticTfb );
		this.color = color;
		loadImages( color );
	}


	private void loadImages(int color) {
		switch(color){

		case Color.BLUE:
			if(Math.random()<0.5)
				setImages( blueImages1 );
			 else
				setImages( blueImages2 );
			break;

		case Color.RED:
			if(Math.random()<0.5){
				setImages( redImages1 );
			} else{
				setImages( redImages2 );
			}
			break;
		case Color.YELLOW:
			if(Math.random()<0.5){
				setImages( yellowImages1 );
			} else{
				setImages( yellowImages2 );
			}
			break;

		}

		//		p=new Paint();
		//		p.setAntiAlias(true);
		//		p.setAlpha(255);
		//		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		//		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
		//			p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
		//		} else{
		//			p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
		//		}
	}
	//	public void paint(Graphics g, Vector v) {
	//		if(getImage()!=null){
	//			g.drawImage(getImage(), v.getIntX()-getImage().getWidthDiv2(), v.getIntY()-getImage().getHeightDiv2());
	//		}
	//
	//	}


	public int getColor()
	{
		return color;
	}



}
