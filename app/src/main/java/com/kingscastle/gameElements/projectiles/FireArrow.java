package com.kingscastle.gameElements.projectiles;

import android.support.annotation.NonNull;

import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 8/5/2015 for Tower Defence
 */
public class FireArrow extends Arrow {

    public FireArrow()	{}
    public FireArrow(LivingThing shooter, vector unitVectorInDirection) {
        super( shooter , unitVectorInDirection);
    }
    public FireArrow( @NonNull LivingThing caster , LivingThing target) {
        super( caster , target );
    }
    private FireArrow(LivingThing shooter, vector to, LivingThing target) {
    }


    @NonNull
    @Override
    public Projectile newInstance(@NotNull LivingThing shooter, @NotNull vector unitVectorInDirection) {
        return new FireArrow(shooter,unitVectorInDirection);
    }



}
