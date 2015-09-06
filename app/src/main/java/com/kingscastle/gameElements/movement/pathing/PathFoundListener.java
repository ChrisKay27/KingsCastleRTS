package com.kingscastle.gameElements.movement.pathing;

public interface PathFoundListener
{
	public void onPathFound(Path path);

	public void cannotPathToThatLocation(String reason);
}
