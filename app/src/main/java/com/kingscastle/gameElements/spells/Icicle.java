package com.kingscastle.gameElements.spells;

import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.animations.IcicleAnim;
import com.kingscastle.effects.animations.SparksAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.abilities.Slow;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;


public class Icicle extends ProjectileSpell {

	private static final String TAG = Icicle.class.getSimpleName();

	private static RectF staticPerceivedArea;

	private static Image iconImage;

	public static final float staticSpeed = 10* Rpg.getDp();
	private static final float staticRangeSquared = 40000*Rpg.getDp()*Rpg.getDp(); //40000=200*200


	private int damagePerLvl = 2;
	private int baseDamage = 5;
    private float speedCurse;

    public Icicle(int damage, float speedCurse) {
        this.speedCurse = speedCurse;
        setDamage(damage);
	}
	public Icicle(int damage) {
		setDamage(damage);
	}

    public Icicle(@NonNull Icicle ice) {
		setDamage(ice.getDamage());
        speedCurse = ice.speedCurse;
	}


	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.ICICLE ; 			}



	@Override
	public void setLoc(vector loc){
		super.setLoc(new vector(loc));
	}



	@Override
	public int calculateDamage()
	{
		if( getCaster() != null )
		{
			Attributes lq = getCaster().getLQ();

			return (int) ( ( baseDamage + (lq.getLevel() * damagePerLvl ) + (int) ( Math.random()*10 ) ) * lq.getBonuses().getDamageBonus() );
		}

		return 30;
	}

	@Override
	public int calculateManaCost(@NotNull @NonNull LivingThing aWizard)
	{
		return 0;
		//return 3 + aWizard.getLQ().getLevel()*2;
	}




	@Override
	public void doDamage(  @Nullable LivingThing lt )
	{
		if( lt != null )
		{
			die();

			if(getCaster() != null)
			{
				////Log.d( TAG , "doDamage("+lt+")");

				lt.takeDamage(getDamage(), getCaster());

                Slow slowBuff = new Slow(caster,lt,speedCurse,false);
                getMM().add(slowBuff);
			}
			else
				lt.takeDamage( getDamage(), null );

		}
	}


	@Override
	public void die()
	{
		getMM().getEm().add(new SparksAnim(loc,Color.BLUE),true);
		super.die();
	}


	@Nullable
    public Image getImage() {
		return null;
	}


	@Override
	public RectF getStaticPerceivedArea()
	{
		loadStaticPerceivedArea();
		return staticPerceivedArea;
	}


	void loadStaticPerceivedArea()
	{
		if (staticPerceivedArea == null)
		{
			int sizeDiv2 = (int) (2 * Rpg.getDp());
			staticPerceivedArea = new RectF(-sizeDiv2, -sizeDiv2, sizeDiv2 , sizeDiv2);
		}
	}



	@Override
	public void loadAnimation(@NonNull vector unit)
	{
		if( getAnim() == null )
			setAnim( new IcicleAnim(loc, vector.getDirection8(unit).getDir()));
	}




	@Override
	public void loadAnimation()
	{

	}


	@Override
	public int getManaCost()
	{
		return 0;
	}






	@NonNull
    @Override
	public String getName()
	{
		return "Icicle";
	}



	@Override
	public void uncast()
	{
	}

	@NonNull
    @Override
	public Spell newInstance() {
		return new Icicle(this);
	}


	@Override
	public boolean cast( @NonNull MM mm )
	{
		super.cast(mm);

		if( getDamage() == 0 )
			setDamage(calculateDamage());

		getAnim().restart();
		mm.getEm().add(getAnim(),true);
		return true;
	}




	@Override
	public float getStaticSpeed()
	{
		return staticSpeed;
	}


	@Override
	public float getStaticRangeSquared()
	{
		return staticRangeSquared;
	}


	@Override
	public Image getIconImage()
	{
		return iconImage;
	}



	public int getDamagePerLvl() {
		return damagePerLvl;
	}
	public void setDamagePerLvl(int damagePerLvl) {
		this.damagePerLvl = damagePerLvl;
	}


}
