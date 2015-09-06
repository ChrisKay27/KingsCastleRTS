package com.kingscastle.gameElements.livingThings.attacks;

import android.support.annotation.NonNull;

import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameElements.spells.Spell;
import com.kingscastle.gameElements.spells.SpellCreationParams;
import com.kingscastle.gameElements.spells.SpellInstanceCreator;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Chris on 9/6/2015 for KingsCastle-Heroes
 */
public class TripleShotSpellAttack extends SpellAttack {

    private final SpellAttack atkToTriple;

    /**
     * @param mm     The one MM instance for this level.
     * @param atkToTriple      The attack to copy shooting it also at -15 and 15 degrees
     */
    public TripleShotSpellAttack(@NotNull MM mm, @NotNull LivingThing caster, @NotNull SpellAttack atkToTriple) {
        super(mm, caster, atkToTriple.getSpell());
        this.atkToTriple = atkToTriple;
    }

    private final vector tempUnitVector = new vector();
    @Override
    public void attackFromUnitVector(@NonNull vector unitVector) {
        SpellCreationParams params = getSpellCreationParams(atkToTriple);
        tempUnitVector.set(unitVector);
        params.setUnitVectorInDirection(tempUnitVector);
        Spell spell = SpellInstanceCreator.getSpellInstance(params);

        mm.add((GameElement) spell);

        if( owner instanceof Humanoid)
            ((Humanoid)owner).setLookDirectionFromUnit( params.getUnitVectorInDirection() );


        tempUnitVector.rotate(-15);
        mm.add((GameElement) SpellInstanceCreator.getSpellInstance(params));


        tempUnitVector.rotate(15);
        mm.add((GameElement) SpellInstanceCreator.getSpellInstance(params));
    }


}
