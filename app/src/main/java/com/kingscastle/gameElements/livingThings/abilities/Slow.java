package com.kingscastle.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.FiveRingsAnim;
import com.kingscastle.effects.animations.FreezeAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.Bonuses;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class Slow extends Buff
{
	private static final String name = "Slow";
	private final float speedBonus;

	private static Image iconImage;

	{
		setAliveTime( 2000 );
	}

	private final boolean earthAnim;

	public Slow(@NotNull LivingThing caster, @NotNull LivingThing target , float speedReductionPerc , boolean earthAnim_) {
		super(caster, target);
		earthAnim = earthAnim_;
		speedBonus = speedReductionPerc;
	}

	public Slow(@NotNull LivingThing caster, @NotNull LivingThing target){
		super(caster, target);
		speedBonus = 0.5f;
		earthAnim = false;
	}

	long start;
	private boolean buffApplied = false;

	@Override
	public void doAbility()
	{
		buffApplied = true;
		//start = GameTime.getTime();


		//Log.d(name, "Slow.doAbility(): at " + start);

		Attributes lq = getTarget().getLQ();
		Bonuses b = lq.getBonuses();
		b.subtractFromSpeedBonusMultiplier(speedBonus);
	}


	@Override
	public void undoAbility()
	{
		if( buffApplied ) {
			//long end = GameTime.getTime();
			//Log.d(name, "Slow.undoAbility(): at " + end + ", " + (end - start) + " ms later.");
			Bonuses b = getTarget().getLQ().getBonuses();
            b.addToSpeedBonusMultiplier(speedBonus);
		}
	}




	@Override
	protected void addAnimationToManager( @NonNull MM mm , @NonNull Anim anim2)
	{
		////Log.d( name , "Adding anim2 to Em:" + hashCode() );
		mm.getEm().add( anim2 , EffectsManager.Position.InFront );
		//	boolean added = Rpg.getMM().getEm().add( anim2 , EffectsManager.Position.InFront );
		////Log.d( name , "added = "  + added );
	}

    @Override
    public boolean isOver() {
        return isDead();
    }


    @NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.SLOW ; 			}



	@Override
	public int calculateManaCost(@NotNull @NonNull LivingThing aWizard){
		return 0;
	}

	@NonNull
    @Override
	public Slow newInstance(@NotNull LivingThing target) {
		return new Slow(getCaster(),target,speedBonus,earthAnim);
	}


	@Override
	public void loadAnimation( @NonNull MM mm )
	{

		if( getAnim() == null )
		{
			if( earthAnim ){
				//Log.d( name , "Creating earthAnim for slow");
				FiveRingsAnim fra = new FiveRingsAnim( getTarget().loc );
				fra.setAliveTime( 10000 );
				fra.setLooping( true );
				setAnim( fra );
			}
			else
				setAnim( new FreezeAnim( getTarget().loc , 10000 ));
			//getAnim().setOffs( new Vector( 0 , getTarget().area.height()/2 ) );
			//getAnim().setLooping(true);
		}
	}




	@NonNull
    @Override
	public String toString() {
		return name;
	}
	@NonNull
    public String getName() {
		return name;
	}





	@Nullable
    @Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.multishot_icon);
		}
		return null;
	}


	public float getSpeedBonus()
	{
		return speedBonus;
	}







}
