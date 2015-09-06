package com.kingscastle.ui;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class SelectedUnits {

    private static final String TAG = SelectedUnits.class.getSimpleName();

    @Nullable
    private Building selectedBuilding;
	@Nullable
    private Unit selectedUnit;
	@Nullable
    private GameElement selectedThing;
    @Nullable
    private List<GameElement> selectedGameElements;
	@Nullable
    private List<Unit> selectedUnits;



	public void setSelected(@NotNull Unit u) {
        Log.d(TAG, "setSelected Unit:" + u);
        if (selectedUnit != null)
            selectedUnit.setSelected(false);

        selectedUnit = u;
        selectedUnit.setSelected(true);
	}

    public void setSelected(@NotNull Building b) {
        setSelectedBuilding(b);
    }


	public void setSelected(@NotNull GameElement ge) {
		if (selectedThing != null)
			selectedThing.setSelected(false);

		selectedThing = ge;
		selectedThing.setSelected(true);
	}


	public void setSelectedGameElements(@NotNull List<? extends GameElement> ges) {
		clearSelected();

        for( GameElement ge : ges) {
            ge.setSelected(true);
            if( ge instanceof LivingThing )
                ((LivingThing)ge).setSelectedColor(Color.YELLOW);
        }
        selectedGameElements = new ArrayList<>(ges);
	}

    public void setSelectedUnits(@NotNull List<Unit> ges) {
        clearSelected();

        for( GameElement ge : ges) {
            ge.setSelected(true);
            if( ge instanceof LivingThing )
                ((LivingThing)ge).setSelectedColor(Color.YELLOW);
        }
        selectedUnits = ges;
    }

/*
	public void setSelectedUnit(LivingThing u) {
		clearSelected();

		selectedUnit = u;
		selectedThing = u;
		if (selectedUnit != null) {

			selectedUnit.setSelected(true);
			selectedUnit.setSelectedColor(Color.YELLOW);
		}
	}*/


	public void setSelectedBuilding(@NotNull Building b) {
		clearSelected();

		selectedBuilding = b;
		selectedThing = b;

        selectedBuilding.setSelected(true);
        selectedBuilding.setSelectedColor(Color.YELLOW);
    }


	public void clearSelected() {
        Log.d(TAG, "clearSelected");
		if (selectedThing != null) {
			selectedThing.setSelected(false);
			// selectedBuilding.hideHealthPercentage();
			selectedThing = null;
		}


		clearSelectedBuilding();

		clearSelectedUnit();

		clearSelectedThings();
	}

	public void clearSelectedBuilding() {
		LivingThing selectedBuilding = this.selectedBuilding;
		if (selectedBuilding == null)
			return;

		selectedBuilding.setSelected(false);
		this.selectedBuilding = null;
	}

	public void clearSelectedUnit() {
		LivingThing selectedUnit = this.selectedUnit;
		if (selectedUnit == null)
			return;

		selectedUnit.setSelected(false);
		this.selectedUnit = null;
	}

	public void clearSelectedThings() {
		List<GameElement> selectedThings = this.selectedGameElements;
		if (selectedThings == null)
			return;

		for (GameElement ge : selectedThings)
			ge.setSelected(false);

		this.selectedGameElements = null;
	}





	public synchronized void setUnSelected(@NotNull GameElement ge) {

		if (selectedUnit == ge) {
			selectedUnit = null;
			ge.setSelected(false);
			return;
		} else if (selectedBuilding == ge) {
			selectedBuilding = null;
			ge.setSelected(false);
		} else if (selectedThing == ge) {
			selectedThing = null;
			ge.setSelected(false);
		}


		if (selectedGameElements != null) {
            if (selectedGameElements.remove(ge))
                ge.setSelected(false);

            if (selectedGameElements.isEmpty())
                selectedGameElements = null;
        }
	}



    public synchronized void setUnSelected(@Nullable Unit u) {
        if( u != null ) {
            if (selectedUnit == u) {
                selectedUnit = null;
                u.setSelected(false);
            }

            if (selectedUnits != null) {
                if (selectedUnits.remove(u))
                    u.setSelected(false);

                if (selectedUnits.isEmpty())
                    selectedUnits = null;
            }
        }
    }

    public synchronized void setUnSelected(@Nullable Building b) {
        if (selectedBuilding == b && b != null) {
            selectedBuilding = null;
            b.setSelected(false);
        }
    }



	public void moveSelected(vector inDirection) {
        Unit u = getSelectedUnit();
		if (u != null) {
            Log.d(TAG , "moveSelected");
			u.walkToAndStayHereAlreadyCheckedPlaceable(null);
            u.getLegs().act(inDirection, true);
		}
	}

	//	public boolean moveSelectedUnits(Vector dest) {
	//		if( getSelectedUnits() != null ) {
	//			return SquareFormation.staticMoveTroops(getSelectedUnits(), dest);
	//
	//		} else if (getSelectedSquad() != null) {
	//			return getSelectedSquad().setGroupHere(dest);
	//		}
	//
	//		return false;
	//	}



	public boolean somethingIsSelected() {
		if (getSelectedThing() != null || getSelectedUnits() != null
				|| getSelectedUnit() != null || getSelectedBuilding() != null)
			return true;
		else
			return false;
	}

	public boolean multipleThingsAreSelected() {
		if (getSelectedUnits() != null)
			return true;
		else
			return false;
	}



	@Nullable
    public Building getSelectedBuilding() {
		return selectedBuilding;
	}

	@Nullable
    public GameElement getSelectedThing() {
		return selectedThing;
	}

    @Nullable
    public Unit getSelectedUnit() {
        if (selectedUnit != null)
            selectedUnit.setSelected(true);

        return selectedUnit;
    }

    @Nullable
    public List<Unit> getSelectedUnits() {
		return selectedUnits;
	}

}
