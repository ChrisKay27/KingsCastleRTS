package com.kingscastle.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;

import com.kingscastle.framework.implementation.ImageDrawable;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.livingThings.orders.Order;
import com.kingscastle.ui.UnitOrders;

import java.util.List;

public class OrderButton extends SButton
{

	private final Order order;
	private final UnitOrders uOrders;

	private OrderButton( Activity a, Order o , final UnitOrders uOrders_ )
	{
		super(a);
		uOrders = uOrders_;

		if( o == null )
			throw new IllegalArgumentException("Trying to set order of an orderButton and order was null.");

		order = o;

		if( o.getIconImage() != null ){
			ImageDrawable id = new ImageDrawable( o.getIconImage().getBitmap() , 0 , 0 , new Paint());
			setForeground(id);
		}

		setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				//				if ( order == null || !Rpg.getGame().getLevel().somethingIsSelected() )
				//					return;

				uOrders.setPendingOrder( order );
			}
		});
	}




	public static OrderButton getInstance( Activity c, Order order , Unit orderable , List<? extends Unit> orderables , final UnitOrders uOrders )
	{
		OrderButton ob = new OrderButton(c, order , uOrders );

		if( orderable != null )
			order.setUnitToBeOrdered( orderable );
		else if( orderables != null )
			order.setUnitsToBeOrdered( orderables );


		return ob;
	}




	Order getOrder() {
		return order;
	}


	@Override
	public OrderButton clone(){
		return new OrderButton( a , order , uOrders );
	}

}
