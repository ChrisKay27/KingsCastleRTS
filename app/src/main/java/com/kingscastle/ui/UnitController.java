package com.kingscastle.ui;

import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameUtils.vector;

/**
 * Created by Chris on 9/4/2015 for KingsCastle-Heroes
 */
public class UnitController implements TouchEventAnalyzer {


    private final UI ui;

    public UnitController(UI ui) {
        this.ui = ui;
    }

    private final vector temp = new vector();
    @Override
    public boolean analyzeTouchEvent(Input.TouchEvent e) {
        Unit u = ui.getSelectedUnit();

        if( u != null ){
            ui.getCoordsMapToScreen(u.loc, temp);
            temp.x = e.x - temp.x;
            temp.y = e.y - temp.y;
            u.attackOnceInDirection(temp);


            return true;
        }
        else
            return false;
    }


}
