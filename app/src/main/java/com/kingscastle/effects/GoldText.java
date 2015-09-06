
package com.kingscastle.effects;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;


public class GoldText extends RisingText{

	public final static int txtColor = Color.YELLOW;
	private final static Paint yellowCenter = Palette.getPaint( Color.YELLOW , Rpg.getSmallestTextSize() );

	public GoldText(String t,LivingThing on){
		super(t, on);

	}
	public GoldText(int m,LivingThing on){
		super(m+"", on);

	}
	@NonNull
    @Override
	public Paint getPaint(){
		return yellowCenter;
	}
}