package com.kingscastle.gameUtils;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class BlockingQueue<E>
{
	private final int MAX_SIZE;
	private final LinkedList<E> queue = new LinkedList<E>();


	public BlockingQueue( int maxSize )
	{
		MAX_SIZE = maxSize;
	}




	public boolean addLast( E elm ) {
		while( true )	{
			synchronized( queue ) {
				if( !isFull() )	{
					queue.addLast( elm );
					return true;
				}
			}
			try	{
				Thread.sleep( (long) (Math.random()*100) );
			}
			catch (InterruptedException e)	{
				e.printStackTrace();
				return false;
			}
		}

	}




	@NotNull
    public E removeFirst() {
		while( true ) {
			synchronized( queue ){
				if( !queue.isEmpty() )
					return queue.removeFirst();
			}
			try	{
				Thread.sleep( (long) (Math.random()*100) );
			}
			catch ( InterruptedException e ){
				throw new RuntimeException(e);
			}
		}
	}




	public int size()	{
		synchronized( queue ){
			return queue.size();
		}
	}



	public boolean isEmpty()	{
		synchronized( queue ){
			return queue.isEmpty();
		}
	}



	private boolean isFull()	{
        synchronized( queue ){
			if( queue.size() == MAX_SIZE )
				return true;
			else
				return false;
		}
	}



	public void clear() {
		synchronized( queue )	{
			queue.clear();
		}
	}






}
