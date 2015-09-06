package com.kingscastle.gameElements.spells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.CycloneLargeAnim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class EarthQuake extends InstantSpell
{

	private static Image iconImage;


	@NonNull
    private static final ArrayList<vector> offsets1;

	@NonNull
    private static final ArrayList<vector> offsets2;

	static{
		//float dp = Rpg.getDp();
		vector temp;
		offsets1 = new ArrayList<vector>();
		offsets2 = new ArrayList<vector>();

		temp = new vector(0,0);
		offsets1.add(temp);
		offsets2.add(temp);

		//		temp = copyAndRotate(72,temp);
		//		offsets1.add(temp);
		//
		//		temp = copyAndRotate(72,temp);
		//		offsets1.add(temp);
		//
		//		temp = copyAndRotate(72,temp);
		//		offsets1.add(temp);
		//
		//		temp = copyAndRotate(72,temp);
		//		offsets1.add(temp);
		//
		//		temp = copyAndRotate(36,temp);
		//		temp.set(temp.getX()*1.5f,temp.getY()*1.5f);
		//		offsets2.add(temp);
		//
		//		temp = copyAndRotate(72,temp);
		//		offsets2.add(temp);
		//
		//		temp = copyAndRotate(72,temp);
		//		offsets2.add(temp);
		//
		//		temp = copyAndRotate(72,temp);
		//		offsets2.add(temp);
		//
		//		temp = copyAndRotate(72,temp);
		//		offsets2.add(temp);
	}


	{
		setRefreshEvery(200);
	}

	public EarthQuake() {
	}
	public EarthQuake(@NonNull EarthQuake earthQuake_) {
		setDamage(earthQuake_.getDamage());
	}




	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.EARTHQUAKE ; 			}


	@Override
	public void refresh()
	{
		//createQuakes(offsets2);
		die();
	}


	@Override
	public boolean cast( @NonNull MM mm )
	{
		super.cast(mm);

		setLoc(getCaster().loc);
		setStartTime( GameTime.getTime() );
		CycloneLargeAnim cla = new CycloneLargeAnim(loc);
		cla.addAnimationListener(new AnimatorListenerAdapter(){
			@Override
			public void onAnimationEnd(Animator animation_) {
				createQuakes(offsets1);
			}
		});
		mm.getEm().add( cla );


		return true;
	}






	@NonNull
    @Override
	public String getName()
	{
		return "EarthQuake";
	}


	@Override
	public boolean hitsOnlyOneThing(){
		return false;
	}


	@NonNull
    @Override
	public Spell newInstance()
	{
		return new EarthQuake(this);
	}



	@NonNull
    @Override
	public String toString()
	{
		return "Earth Quake";
	}








	private void createQuakes( @NonNull ArrayList<vector> offsets)
	{

		ArrayList<SpellCreationParams> params = getQuakeCreationParam(this,offsets);
		Quake q;

		for(SpellCreationParams param : params)
		{
			q = (Quake) SpellInstanceCreator.getSpellInstance(param);
			getMM().add((GameElement) q);
		}
	}






	@Override
	public int calculateManaCost( @NotNull @NonNull LivingThing aWizard)
	{
		return 10 + aWizard.getLQ().getLevel() * 5 ;
	}



	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.quake_icon);
		}
		return iconImage;
	}








	@NonNull
    private static vector copyAndRotate(int deg, vector temp2)
	{
		vector temp = new vector(temp2);
		temp.rotate(deg);
		return temp;
	}



	@NonNull
    private static ArrayList<SpellCreationParams> getQuakeCreationParam( @NonNull EarthQuake MQ ,  @NonNull ArrayList<vector> offsets)
	{
		ArrayList<SpellCreationParams> params = new ArrayList<SpellCreationParams>();

		Quake q = new Quake();
		q.setDamage(MQ.getDamage());

		for(vector offs : offsets)
		{
			SpellCreationParams param = new SpellCreationParams();
			vector v = new vector(MQ.loc).add(offs);
			param.setSpellToBeCopied(q);
			param.setLocation(v);
			param.setShooter(MQ.getCaster());
			param.setTeam(MQ.getTeamName());
			param.setDamage(MQ.getDamage());
			params.add(param);
		}

		return params;

	}


	@Override
	public void loadAnimation() {
	}


	@Override
	public int calculateDamage() {
		return getDamage();
	}


}
