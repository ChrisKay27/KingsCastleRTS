package com.kingscastle.framework;


import com.kingscastle.Game;

public abstract class Screen
{

	protected Game game;


	protected boolean firstTimeCreated=false;

	protected Screen(Game game)
	{
		this.game = game;
	}

	protected void setGame(Game game)
	{
		this.game = game;
	}

	public abstract void update();

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();

	public abstract void backButton();

	public void paint(Graphics g)
	{
	}

	public void updateUI() {
	}
}

