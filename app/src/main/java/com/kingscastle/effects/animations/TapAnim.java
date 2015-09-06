package com.kingscastle.effects.animations;

import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

/**
 * Created by Chris on 8/2/2015 for Tower Defence
 */
public class TapAnim extends Anim {

    private static final String TAG = TapAnim.class.getSimpleName();
    private static final Image tapImage = Assets.loadImage(R.drawable.tap);

    public TapAnim(@NonNull vector loc) {
        super( loc );
        setAliveTime(10000);
        setLooping(true);
        setRequiredPosition(EffectsManager.Position.InFront);
    }

    private final static String tapHere = "Tap!";



    @Override
    public void paint( @NonNull Graphics g , @NonNull vector v ) {
        g.drawImage(tapImage, v.x - tapImage.getWidthDiv2(), v.y-tapImage.getHeightDiv2(), getPaint());
        //g.drawString(tapHere,v.x-Rpg.tenDp,v.y,paint);
    }

}
