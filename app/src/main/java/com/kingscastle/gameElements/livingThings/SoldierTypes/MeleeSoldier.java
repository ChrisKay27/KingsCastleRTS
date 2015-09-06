package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.gameElements.livingThings.attacks.MeleeAnimator;
import com.kingscastle.gameElements.livingThings.attacks.MeleeAttack;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.movement.pathing.Path;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

public abstract class MeleeSoldier extends Unit {

	public MeleeSoldier() {
	}

	public MeleeSoldier(Teams team) {
		super(team);
	}


	@Override
	protected boolean armsAct()
	{
		boolean armsActed = super.armsAct();
		if( armsActed ){
			Path path = getPathToFollow();
			if( path != null && !path.isHumanOrdered() ){
				setPathToFollow(null);
			}
		}

		return armsActed;
	}

	@Override
	public void loadAnimation(@NotNull MM mm) {
		super.loadAnimation(mm);
        MeleeAttack ma = new MeleeAttack(mm, this, MeleeAnimator.MeleeTypes.LongSword, mm.getCD());
		getAQ().setCurrentAttack(ma);
		aliveAnim.add(ma.getAnimator(), true);
	}

}
