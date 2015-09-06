package com.kingscastle.level.Heroes;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 9/5/2015 for KingsCastle-Heroes
 */
public abstract class Pickup {

    private static final double pickupRangeSquared = Rpg.sixTeenDpSquared;
    private final vector loc;
    private long overAt = GameTime.getTime() + 10000;

    public Pickup(vector loc) {
        this.loc = loc;
    }

    public boolean isWithinRange(@NotNull vector playersLoc){
        return loc.distanceSquared(playersLoc) < pickupRangeSquared;
    }


    public boolean isOver(){
        return overAt < GameTime.getTime();
    }
    public void setOverAt(long overAt) {
        this.overAt = overAt;
    }


    public void onOver(){
    }


    public abstract void pickedUp(@NotNull MM mm ,@NotNull Unit byThisPlayer);

    public abstract Anim getAnim();

    public abstract boolean canPickup(@NotNull Unit byThisPlayer);
}
