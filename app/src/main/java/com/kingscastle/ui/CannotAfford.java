package com.kingscastle.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import com.kaebe.kingscastle.R;
import com.kingscastle.gameElements.Cost;
import com.kingscastle.teams.RT;
import com.kingscastle.teams.Team;


public class CannotAfford {


	public static void showCannotAffordMessage( @NonNull final Activity a , @Nullable final OnClickListener posListener , @Nullable final OnClickListener negListener , @NonNull final Team team , @NonNull final Cost cost , final int magicDustCost ){

		DialogBuilder db = new DialogBuilder(a).setText(a.getString(R.string.cannot_afford) + "\n" + a.getString(R.string.would_you_like_to_buy_plural, cost.toResString() , magicDustCost+ " Magic Dusts" ));


		db.setPositiveButton(DialogBuilder.SURE, new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( team.getPR().canAfford( RT.MAGIC_DUST , magicDustCost )){
					if( team.getPR().spend( RT.MAGIC_DUST , magicDustCost ) ){
						team.getPR().refund(cost);
						if( posListener != null )
							posListener.onClick(v);
					}
				}
				else
					showCannotAffordMdMessage( a , magicDustCost );
			}
		});

		db.setNegativeButton(DialogBuilder.NO_THANKS, new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( negListener != null )
					negListener.onClick(v);
			}
		});
		db.show();
	}




	public static void showCannotAffordMdMessage( @NonNull final Activity a , final int magicDustCost ){

		DialogBuilder db = new DialogBuilder(a).setText(a.getString(R.string.you_do_not_have) + a.getString(R.string.would_you_like_to_get_some ));


		db.setPositiveButton(DialogBuilder.SURE, new OnClickListener() {
			@Override
			public void onClick(View v) {
				//PurchaseScreen.showPurchaseScreen();
			}
		})
		.setNegativeButton(DialogBuilder.NO_THANKS, null).show();
	}












}//end Class
