package com.qlj.toolbox.util;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtil
{
	public static Typeface getRegularTypeface(Context context)
	{
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/RobotoCondensed-Regular.ttf");
		return typeFace;
	}

}
