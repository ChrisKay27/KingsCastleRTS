package com.kingscastle.ui;

import android.graphics.Color;
import android.graphics.Rect;

import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Input;
import com.kingscastle.gameUtils.vector;

/**
 * Created by Chris on 9/1/2015 for Heros
 */
public class ThumbStick implements TouchEventAnalyzer {

    private static final String TAG = "ThumbStick";

    private final Rect bounds;
    private final ThumbStickListener tsl;
    private final vector position = new vector(), center = new vector();
    private int pointerID;


    public ThumbStick(Rect bounds, ThumbStickListener tsl ) {
        this.bounds = bounds;
        this.tsl = tsl;
        center.set(bounds.centerX(), bounds.centerY());
    }

    @Override
    public boolean analyzeTouchEvent(Input.TouchEvent e) {
        if( !bounds.contains(e.x, e.y) ) {
            if( e.pointer == pointerID ) {
                tsl.thumbLeftThumbStick();
                pointerID = -1;
                return true;
            }
            return false;
        }
        if( e.pointer != pointerID && pointerID != -1 )
            return false;

        if( e.type == Input.TouchEvent.TOUCH_UP ){
            tsl.thumbLeftThumbStick();
            pointerID = -1;
            return true;
        }

        pointerID = e.pointer;

        //Log.d(TAG, "analyzeTouchEvent " + e );
        position.x = e.x - bounds.centerX();
        position.y = e.y - bounds.centerY();

        tsl.thumbStickPositionChanged(position);

        return true;
    }


    public void paint(Graphics g) {
        g.drawRectBorder(bounds, Color.GREEN, 1);

        g.drawCircle(center, 30);
        g.drawCircle(center.x+position.x, center.y+position.y, 30);
    }

    interface ThumbStickListener {
        void thumbStickPositionChanged(vector position);

        void thumbLeftThumbStick();
    }
}
