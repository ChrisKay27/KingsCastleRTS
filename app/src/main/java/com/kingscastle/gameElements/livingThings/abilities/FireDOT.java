package com.kingscastle.gameElements.livingThings.abilities;

import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.TorchLightAnim;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 8/3/2015 for Tower Defence
 */
public class FireDOT extends DOTBuff {

    private static final String TAG = FireDOT.class.getSimpleName();

    public FireDOT(@NotNull LivingThing caster, @NotNull LivingThing target, int damage) {
        super(caster, target, damage);
    }

    public FireDOT(@NotNull LivingThing caster, int damage) {
        super(caster, null, damage);
    }


    @Override
    public void refresh(@NonNull EffectsManager em) {
        super.refresh(em);
        em.add(new TorchLightAnim(getTarget().loc));
    }

    @Override
    public void loadAnimation(@NotNull MM mm )    {
        Anim a = new TorchLightAnim(getTarget().loc);
        setAnim(a);
    }

    @Override
    protected void addAnimationToManager(@NotNull MM mm,@NotNull Anim anim2) {
       // Log.d(TAG, "Adding " + anim2 + " to manager");
        mm.getEm().add(anim2);
    }

    @NonNull
    @Override
    public Ability newInstance(@NotNull LivingThing target)    {
        return new FireDOT(getCaster(),target,getDamage());
    }


    @NonNull
    @Override
    public String toString() {
        return TAG;
    }

}
