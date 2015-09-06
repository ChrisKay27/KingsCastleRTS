package com.kingscastle.gameElements.resources;


import com.kingscastle.gameElements.GameElement;

public class ResourceGetter
{
	private static final String TAG = "ResourceGetter";


	public static GameElement getResourceFromString( String name , int remainingResources  , int x , int y )
	{
		String className = name;

		if( className.equals("S") )
			className = "SpruceTree";
		else if( className.equals("L") )
			className = "LargeTree";
		else if( className.equals("B") )
			className = "SmallTree";

		try
		{
			Class<?> aResource = Class.forName("com.kaebe.kingscastle27.gameElements.resources." + className );

			GameElement resource = (GameElement) aResource.newInstance();

			if( resource instanceof Mine )
				((Mine) resource).setRemainingResources( remainingResources );
			else if( resource instanceof Tree)
				((Tree) resource).setRemainingResources( remainingResources );

			resource.setLoc( x , y );

			return resource;
		}
		catch(Exception e)
		{
			////Log.v( TAG , "Did not find the Class in the livingThing.army. folder " );
			return null;
		}

	}

}
