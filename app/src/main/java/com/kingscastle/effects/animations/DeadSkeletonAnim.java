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
public class DeadSkeletonAnim extends Anim {

    private static final List<Image> staticImages = new ArrayList<>();

    static{
        List<Image> images = new ArrayList<>();
        images.add(Assets.loadImage(R.drawable.skeleton));
        images.addAll(Assets.loadAnimationImages(R.drawable.skeleton_corpse, 2, 1));
        images.addAll(Assets.loadAnimationImages(R.drawable.dead_skeletons, 4, 2));
        DeadSkeletonAnim.staticImages.addAll( images );
    }


    public DeadSkeletonAnim(vector loc) {
        super( loc );
        onlyShowIfOnScreen = true;
        setAliveTime(20000);
        setImage(staticImages.get((int) (Math.random() * staticImages.size())));
    }

    @Override
    @NonNull
    public Anim newInstance(vector loc){
        return new DeadSkeletonAnim(loc);
    }


    @NonNull
    @Override
    public String toString() {
        return "DeadSkeletonAnim";
    }
}
