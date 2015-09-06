package com.kingscastle.gameElements.livingThings;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.Settings;
import com.kingscastle.gameUtils.vector;


public class HealthBar extends Anim
{
	@Nullable
    private LivingThing owner;
	@Nullable
    private Rect healthBar;
	private int fullWidth;

	public HealthBar( LivingThing lt )
	{
		owner = lt;
	}


	@Override
	public void paint( @NonNull Graphics g , vector v )
	{
		if( Settings.showHealthBar == false )
		{
			return;
		}
		if ( owner == null || owner.getLQ() == null )
		{
			setOver( true );
		}
		if( owner.getLQ().getHealth() < 0 )
		{
			return;
		}
		else
		{
			g.drawRect( getHealthBar() , v , Color.RED );
		}
	}



	@Nullable
    Rect getHealthBar()
	{
		if ( healthBar == null )
		{
			loadFullHealthBar();
		}

		int w = (int)  ( fullWidth * owner.getLQ().getHealthPercent() );

		healthBar.set( healthBar.left , healthBar.top , healthBar.left + w , healthBar.bottom );

		return healthBar;
	}


	void loadFullHealthBar()
	{
		float dp = Rpg.getDp();
		fullWidth = (int) (dp * 16);

		int left = (int) (-dp * 8);
		int top = (int) (-dp * 15);
		healthBar = new Rect(left , top , left + fullWidth , (int) (top + dp * 1) );
	}


	@Override
	public void nullify()
	{
		owner = null;
		healthBar = null;
		super.nullify();
	}
}