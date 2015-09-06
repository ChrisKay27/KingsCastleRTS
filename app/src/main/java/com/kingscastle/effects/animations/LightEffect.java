package com.kingscastle.effects.animations;

import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;

/**
 * Created by chris_000 on 7/9/2015.
 */
public class LightEffect extends Anim {

    public enum LightEffectColor{LIGHT_BLUE,DARK_BLUE,LIGHT_ORANGE,DARK_ORANGE};

    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.light_effects,3,4);


    public LightEffect(vector loc, @NonNull LightEffectColor color) {
        super(loc);

        switch(color){
            case DARK_BLUE: setImage( staticImages.get(4)); break;
            case LIGHT_BLUE: setImage( staticImages.get(5)); break;
            case LIGHT_ORANGE: setImage( staticImages.get(0)); break;
            case DARK_ORANGE: setImage( staticImages.get(2)); break;
        }

        onlyShowIfOnScreen = true;
        setPaint(Rpg.getXferAddPaint());
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
