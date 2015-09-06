package com.kingscastle.gameElements.livingThings.army;

import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.Attributes;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.Age;
import com.kingscastle.teams.Team;
import com.kingscastle.teams.Teams;
import com.kingscastle.teams.races.Races;

import java.util.Vector;

public class UndeadWorker extends Worker
{

	private static final Attributes staticAttributes; @Override
	protected Attributes getStaticLQ() { return staticAttributes; }

	public UndeadWorker(){}
	public UndeadWorker( Vector loc, Teams team){
		super(loc, team );
	}


	static
	{

		float dp = Rpg.getDp();


		staticAttributes = new Attributes();  staticAttributes.setRequiresBLvl(1); staticAttributes.setRequiresAge(Age.STONE); staticAttributes.setRequiresTcLvl(1);
		staticAttributes.setLevel( 1 ); // 1 );
		staticAttributes.setFullHealth( 125 );
		staticAttributes.setHealth( 125 ); staticAttributes.setdHealthAge( 30 ); staticAttributes.setdHealthLvl( 10 ); //125 );
		staticAttributes.setFullMana( 0 );
		staticAttributes.setMana( 0 );
		staticAttributes.setHpRegenAmount( 1 );
		staticAttributes.setRegenRate( 2100 );
		staticAttributes.setSpeed( 1.9f * dp );


	}

	{
		maxHeldResources = 24;
		actEvery = 1000;
	}


	@Override
	public void finalInit( MM mm )
	{
		loadAnimation( mm );

		workerAnims = new WorkerAnimations( this , mm );
		workerAnims.setHoeAttack( new ZombieEating( this  , mm.getCD() ) );


		workerAnims.setAnimation( WorkerAnims.AXE );

		findNewJob();
	}


	@Override
	public Image[] getImages()
	{
		return WorkerImagesUndead.getImages( this, lq.getAge() );
	}

	@Override
	public void loadAnimation( MM mm )
	{
		if ( aliveAnim == null )
		{
			Team t = mm.getTeam( team );
			mm.getEm().add( aliveAnim = new Animator( this , getImages() ) , true );

			Races race = Races.HUMAN;
			if( t != null )
				race = t.getRace().getRace();

			addHealthBarToAnim( race );
		}
	}


}
