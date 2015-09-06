package com.kingscastle.gameElements.livingThings.attacks;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public abstract class Attack
{
	@NonNull
    protected final MM mm;
	@NonNull
    protected final LivingThing owner;


	public abstract void act();
	public abstract boolean attack(LivingThing target);

	Attack(@NotNull MM mm,@NotNull LivingThing owner2){
		owner = owner2;
		this.mm = mm;
		if( mm == null )
			throw new NullPointerException(owner + " has created a "+this+" and passed it a null mm!");
	}


	/**
	 * @return the owner
	 */
    @NonNull
    LivingThing getOwner()
	{
		return owner;
	}



	public void removeAnim()	{
	}

	public void attack(vector inDirection)
	{

	}

	@Nullable
    public Anim getAnimator() {
		return null;
	}

	public void checkHit(vector attackingInDirection) {
	}

	public void attackFromUnitVector(vector unitVector) {
	}




}
