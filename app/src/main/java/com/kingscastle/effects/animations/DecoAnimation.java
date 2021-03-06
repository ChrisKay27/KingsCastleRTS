package com.kingscastle.effects.animations;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 7/19/2015 for Tower Defence
 */
public class DecoAnimation extends Anim {
    @NonNull
    private static Map<Integer,Image> images = new HashMap<>();


    public DecoAnimation(vector loc, @DrawableRes int id){
        super( loc );
        if( !images.containsKey(id) )
            images.put(id, Assets.loadImage(id));
        setImage(images.get(id));
        setLooping(true);
        setAliveTime(Integer.MAX_VALUE);
    }



}
