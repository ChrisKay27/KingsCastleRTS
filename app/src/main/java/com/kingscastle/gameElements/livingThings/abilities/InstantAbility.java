package com.kingscastle.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public abstract class InstantAbility implements Ability
{
	private LivingThing caster;
	private LivingThing target;

	private Anim anim;

	private boolean casted;

    private MM mm;

	public InstantAbility(@NotNull LivingThing caster, @NotNull LivingThing target){
		this.target = target;
		this.caster = caster;
	}

	@Override
	public boolean cast( MM mm )
	{
        this.mm = mm;
		doAbility();
		return true;
	}

	@Override
	public boolean cast( @NotNull @NonNull MM mm , @NotNull LivingThing target)
	{
        this.mm = mm;
        this.target = target;
		doAbility();
		return true;
	}


	@Override
	public boolean act() {
		return true;
	}


    public boolean canCastOn(LivingThing potentialTarget){
        return true;
    }


	@Override
	public LivingThing getCaster() {
		return caster;
	}
	@Override
	public void setCaster(@NonNull LivingThing caster) {
		this.caster = caster;
	}


	@Override
	public LivingThing getTarget() {
		return target;
	}
	@Override
	public void setTarget(@NonNull LivingThing target) {
		this.target = target;
	}


	@Override
	public Anim getAnim() {
		return anim;
	}
	public void setAnim(Anim anim) {
		this.anim = anim;
	}


	public boolean isCasted() {
		return casted;
	}
	public void setCasted(boolean casted) {
		this.casted = casted;
	}

    protected MM getMM() {
        return mm;
    }

    @Override
	public void die() {
	}

    @Override
    public boolean isOver() {
        return true;
    }

    @Override
	public boolean analyseTouchEvent(@NotNull Input.TouchEvent event) {
		return false;
	}

    @Override
    public void undoAbility() {
    }
}
