package com.kingscastle.gameUtils;

public interface CoordConverter {

	int getMapWidth();
	int getMapHeight();

	vector getCoordsScreenToMap(float x, float y, vector intoThis);

	vector getCoordsScreenToMap(vector v, vector intoThis);


	vector getCoordsMapToScreen(float x, float y, vector intoThis);


	vector getCoordsMapToScreen(vector v, vector intoThis);


}
