package com.kingscastle.gameElements.spells;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.EruptionAnim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;


public class Eruption extends InstantSpell
{

	private static Image iconImage;


	@NonNull
    private static RectF staticPerceivedArea = new RectF(-Rpg.getDp()*30,-Rpg.getDp()*30,Rpg.getDp()*30,Rpg.getDp()*30);




	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.ERUPTION ; 			}

	public Eruption()
	{
	}

	private Eruption(int damage)
	{
		setDamage(damage);
	}


	@Override
	public void refresh()
	{
		doDamage(cd.checkMultiHit(getTeamName(), getArea()));
	}




	@Override
	public int calculateManaCost( @NotNull @NonNull LivingThing aWizard)
	{
		if( aWizard != null )
			return 25 + aWizard.getLQ().getLevel() * 7;
		return 1;
	}




	@Override
	public boolean cast( @NonNull MM mm )
	{
		super.cast(mm);
		if( mm.getSdac().stillDraw( loc ) ){
			loadAnimation();
			Anim anim = getAnim();
			setAliveTime( anim.getTbf()*anim.getImages().size() );
			setRefreshEvery(500);
			mm.getEm().add( anim , true );
		}
		setStartTime(GameTime.getTime());
		setLastRefreshed(GameTime.getTime());

		return true;
	}


	@Override
	public void setLoc(@NonNull vector loc){
		super.setLoc(loc);
		super.loc.translate(0,1);
	}


	@Override
	public void loadAnimation( MM mm )
	{
		loadAnimation();
	}
	@Override
	public void loadAnimation()
	{
		setAnim( new EruptionAnim(loc) );
	}



	@NonNull
    @Override
	public RectF getPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			int sizeDiv2 = (int) (30 * Rpg.getDp()) ;
			staticPerceivedArea = new RectF( -sizeDiv2 , -sizeDiv2 , sizeDiv2 , sizeDiv2 );
		}
		return staticPerceivedArea;
	}



	@Override
	public boolean hitsOnlyOneThing() {
		return false;
	}




	@NonNull
    @Override
	public String getName() {
		return "Eruption";
	}



	@NonNull
    @Override
	public Spell newInstance()
	{
		return new Eruption( getDamage() );
	}


	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.explosion_icon);
		}
		return iconImage;
	}




}
