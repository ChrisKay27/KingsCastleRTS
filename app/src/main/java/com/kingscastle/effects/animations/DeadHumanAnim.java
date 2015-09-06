package com.kingscastle.effects.animations;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris_000 on 7/9/2015.
 */
public class DeadHumanAnim extends Anim {

    private static final List<Image> staticImages = new ArrayList<>();
    static{
        List<Image> images = Assets.loadAnimationImages(R.drawable.dead_people,12,5);
        DeadHumanAnim.staticImages.addAll( images.subList(0,images.size()-10));
    }


    public DeadHumanAnim(vector loc) {
        super(loc);
        onlyShowIfOnScreen = true;
        setAliveTime(20000);
        setImage(staticImages.get((int) (Math.random()*staticImages.size())));
    }

    @Override
    @NotNull
    public Anim newInstance(vector loc){
        return new DeadHumanAnim(loc);
    }



    @NotNull
    @Override
    public String toString() {
        return "DeadHumanAnim";
    }
}
