package com.kingscastle.level;

import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.LivingThing.OnDeathListener;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.army.HumanWizard;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;
import com.kingscastle.util.ManagerListener;

/**
 * Created by Chris on 8/31/2015 for Heroes
 */
public class HeroesForestLevel extends HeroesLevel {


    @Override
    protected void subOnCreate() {
        super.subOnCreate();

        hero = new HumanWizard(new vector(getLevelWidthInPx()/2,getLevelHeightInPx()/2), Teams.BLUE);
        mm.add(hero);

        getBackground().setCenteredOn(hero.loc);


        final OnDeathListener odl = new OnDeathListener() {
            @Override
            public void onDeath(final LivingThing lt) {
                hero.doOnYourThread(new Runnable() {
                    @Override
                    public void run() {
                        hero.addExp(lt.lq.getExpGiven());
                    }
                });
            }
        };
        mm.getTeam(Teams.RED).getAm().addListener(new ManagerListener<Humanoid>() {
            @Override
            public boolean onAdded(Humanoid h) {
                h.addDL(odl);
                return false;
            }

            @Override
            public boolean onRemoved(Humanoid humanoid) {
                return false;
            }
        });

    }

    @Override
    protected void subOnStart() {
        super.subOnStart();
        ui.setSelected(hero);
    }

    @Override
    protected void subAct() {
        super.subAct();
        getBackground().setCenteredOn(hero.loc);
    }
}
