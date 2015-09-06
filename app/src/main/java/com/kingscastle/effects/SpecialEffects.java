package com.kingscastle.effects;

import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.animations.GroundSmasherLargeAnim;
import com.kingscastle.effects.animations.PCloudAnim;
import com.kingscastle.effects.animations.SparksAnim;
import com.kingscastle.framework.Pool;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.Settings;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.level.Level.GameState;

import java.util.ArrayList;

public class SpecialEffects
{


	private static MM mm;

    public enum SpellType{
		ICE, LIGHTNING, FIRE, HEAL, NONE
	}

	@Nullable
    private static RectF stillDraw;


	public static boolean onCreatureDamaged( float x , float y , int dam )
	{
		if( stillDraw.contains(x, y) )
		{
			if( Settings.showDamageText )
				SpecialTextEffects.onCreatureDamaged( x, y, dam );
			createBloodSplatter( x , y );
			return true;
		}

		return false;
	}



	public static boolean onCreatureHealed( float x , float y , int heal )
	{
		return SpecialTextEffects.onCreatureHealed( x, y, heal );
	}


    public static void onExperienceGained(int x, int y, int exp) {
        SpecialTextEffects.onExperienceGained( x, y, exp );
    }


	private static void createBloodSplatter( float x, float y )
	{
		SparksAnim bloodSplatter = bloodSplatterPool.newObject();
		bloodSplatter.loc.set( x , y );
		bloodSplatter.reset();
		mm.getEm().add(bloodSplatter);
	}


	public static void createGroundPounder( @NonNull EffectsManager em , float x, float y )
	{
		GroundSmasherLargeAnim gs = new GroundSmasherLargeAnim(new vector(x,y));
		em.add(gs);
	}




	public static void setup(@Nullable RectF stillDrawArea, MM mm) {
		if( stillDrawArea == null )
			throw new IllegalStateException( "rect == null" );

		SpecialEffects.stillDraw = stillDrawArea;
		SpecialTextEffects.stillDraw = stillDrawArea;
		SpecialSoundEffects.stillDraw = stillDrawArea;
		SpecialEffects.mm = mm;
		SpecialTextEffects.mm = mm;
	}


	static void free( SparksAnim t )
	{
		bloodSplatterPool.free( t );

	}



	static void freeAll(ArrayList<RisingText> list)
	{
		SpecialTextEffects.freeAll( list );
	}



	@NonNull
    private static final Pool<SparksAnim> bloodSplatterPool;

	static
	{
		Pool.PoolObjectFactory<SparksAnim> factory = new Pool.PoolObjectFactory<SparksAnim>() {
			@NonNull
            @Override
			public SparksAnim createObject() {
				return new SparksAnim( Color.RED );
			}
		};
		bloodSplatterPool = new Pool<SparksAnim>(factory, 100);
	}




	public static void playHitSound(float x, float y)
	{
		if( !Settings.muteSounds ) {
			SpecialSoundEffects.playHitSound( x , y );
		}
	}
	public static void playMissSound(float x, float y){
		if( !Settings.muteSounds )
			SpecialSoundEffects.playMissSound( x , y );
	}
	public static void playBowSound(float x, float y) {
		if( !Settings.muteSounds )
			SpecialSoundEffects.playBowSound( x , y );
	}

	public static boolean playSpellCastSound( SpellType st , float x , float y )
	{
		if( !Settings.muteSounds )
			return SpecialSoundEffects.playSpellCastSound(st, x, y);
		else
			return false;
	}


	public static void playHammerSound(float x, float y)
	{
		if( !Settings.muteSounds )
			SpecialSoundEffects.playHammerSound( x , y );
	}

	public static void playAxeSound(float x, float y)
	{
		if( !Settings.muteSounds ) {
			SpecialSoundEffects.playAxeSound( x , y );
		}
	}

	public static void playPickaxeSound(float x, float y)
	{
		if( !Settings.muteSounds ) {
			SpecialSoundEffects.playPickaxeSound( x , y );
		}
	}



	public static void onCreatureLvledUp(float x, float y) {
		if( Rpg.getGame().getState() != GameState.InGamePlay )
			return;
		showSparkleAnim( x, y);
		//		if( SpecialTextEffects.onCreatureLvledUp( x,  y) )
		//			showSparkleAnim( x, y);
	}



	public static void showSparkleAnim(float x, float y) {
		if( Rpg.getGame().getState() != GameState.InGamePlay )
			return;

		PCloudAnim aaa = new PCloudAnim(new vector(x,y));

		EffectsManager em = mm.getEm();
		if( em != null )
			em.add( aaa );

	}











}
