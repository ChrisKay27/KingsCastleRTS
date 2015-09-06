package com.kingscastle.gameElements.projectiles;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.gameUtils.vector;

class ProjectileCreator {


	@Nullable
    public static Projectile getSpellInstance(@NonNull ProjectileParams params)
	{
		Projectile p;

		//Getting an instance of the projectile.
		if( params.getProjToBeCopied() != null )
		{
			p = params.getProjToBeCopied().newInstance();
		}
		else
		{
			p = getProjFromString(params.getProjName());
		}


		//Finding its shooter.
		if(params.getShooter()!=null)
		{
			p.setShooter(params.getShooter());
		}



		//Finding its location.
		if( params.getLocation() != null )
		{
			p.setLoc(params.getLocation());
		}
		else if( params.getShooter() != null )
		{
			p.setLoc(new vector(params.getShooter().loc));
		}

		p.setStartLoc(p.loc);




		if( params.getUnitVectorInDirection() == null ) //determine its units vector
		{

			if ( params.getVelocity() != null)
			{
				vector unit = params.getVelocity().getUnitVector();
				params.setUnitVectorInDirection( unit );
				p.setVelocity( params.getVelocity() );
				p.setUnit( unit );

			}
			else if( params.getDestination() != null)
			{
				vector unit = new vector(p.loc,params.getDestination());
				unit.turnIntoUnitVector();
				params.setUnitVectorInDirection( unit ); // Used to load the animation on some spells
				p.setUnit( unit );
			}

			if( params.getUnitVectorInDirection() != null )
			{
				p.setUnit(params.getUnitVectorInDirection());
				if( p.getVelocity() == null )
				{
					float speed = params.getSpeed();
					if( speed == 0 )
					{
						speed = p.getStaticSpeed();
					}
					vector velocity = new vector(params.getUnitVectorInDirection()).times( speed );
					p.setVelocity(velocity);
				}
			}
		}

		if( params.getRangeSquared() != 0 )
		{
			p.setRangeSquared( params.getRangeSquared() );
		}
		else
		{
			p.setRangeSquared ( p.getStaticRangeSquared() );
		}



		if( p.loc != null )
		{
			p.setStartLoc(p.loc);
		}


		if( params.getTeam() != null )
		{
			p.setTeam(params.getTeam());
		}

		p.updateArea();

		return p;
	}





	@Nullable
    private static Projectile getProjFromString(String spellName)
	{
		return null;
	}

}
