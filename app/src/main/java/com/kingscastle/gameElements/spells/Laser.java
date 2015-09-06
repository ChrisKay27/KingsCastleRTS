package com.kingscastle.gameElements.spells;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.LaserAnim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class Laser extends InstantSpell
{


	private static Image iconImage;

	private static final RectF staticPerceivedArea = new RectF( -Rpg.getDp()*2 , -Rpg.getDp()*2 , Rpg.getDp()*2 , Rpg.getDp()*2 ) ;

	private ArrayList<vector> offsets = new ArrayList<vector>();

	{

		setAliveTime     ( 1000 );
		setRefreshEvery  ( 200 );
		setLastRefreshed ( GameTime.getTime() );
	}




	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.LASER ; 			}


	@Override
	protected void refresh()
	{
		////Log.d( "Laser" , "Laser refreshing, damage = " + getDamage() );
		doDamage( cd.checkMultiHit( getTeamName() , getArea() ) );
	}


	@Override
	public int calculateDamage()
	{
		if( getCaster() != null )
		{
			return getCaster().lq.getLevel()*7;
		}
		return 7;
	}


	@Override
	public int calculateManaCost(@NotNull @NonNull LivingThing aWizard)
	{
		return 0;
	}



	@Override
	public boolean cast( @NonNull MM mm )
	{
		super.cast(mm);
		setAliveTime     ( 1000 );
		setRefreshEvery  ( 200 );
		setLastRefreshed ( GameTime.getTime() );
		setStartTime( GameTime.getTime() );

		loadAnimation();
		mm.getEm().add( getAnim() , true );
		area.set( staticPerceivedArea );
		area.offsetTo( getTarget().area.left , getTarget().area.top );
		return true;
	}


	@Override
	public void setLoc(@NonNull vector loc){
		super.setLoc(loc);
	}



	@Override
	public void loadAnimation()
	{
		if( offsets.isEmpty() )
			setAnim( new LaserAnim( loc , getTarget().loc ) );
		else
		{
			LaserAnim anim = new LaserAnim( loc , getTarget().loc );
			anim.setOffs( offsets.get( 0 ));
			setAnim( anim );

			if( offsets.size() > 1 )
			{
				boolean first = true;
				for( vector offs : offsets )
				{
					if( first ){
						first = false;
						continue;
					}
					anim = new LaserAnim( loc , getTarget().loc );
					anim.setOffs( offs );
					getAnim().add( anim , true );
				}
			}
		}
	}




	@NonNull
    @Override
	public RectF getPerceivedArea()
	{
		return staticPerceivedArea;
	}



	@Override
	public boolean hitsOnlyOneThing() {
		return true;
	}




	@NonNull
    @Override
	public String getName() {
		return "Laser";
	}



	@NonNull
    @Override
	public Spell newInstance() {
		Laser laser = new Laser();
		laser.offsets = this.offsets;
		return laser;
	}




	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.explosion_icon);
		}
		return iconImage;
	}


	public void addOffs(vector offs)
	{
		offsets.add( offs );
	}

}
