package com.kingscastle.effects.animations;

import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris_000 on 7/9/2015.
 */
public class DestroyedBuildingAnim extends Anim {

    private static final List<Image> staticImages = new ArrayList<>();
    static{
        staticImages.add(Assets.loadImage(R.drawable.rubble));
    }


    public DestroyedBuildingAnim(@NonNull vector loc) {
        super( loc );
        onlyShowIfOnScreen = true;
        setAliveTime(60);

        setImage(staticImages.get((int) (Math.random() * staticImages.size())));

        add(new RapidImpact(loc),true);
    }

    @Override
    @NonNull
    public Anim newInstance(@NonNull vector loc){
        return new DestroyedBuildingAnim(loc);
    }


    @NonNull
    @Override
    public String toString() {
        return "DestroyedBuildingAnim";
    }
}
