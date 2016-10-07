package com.domain.rd.researchdirect;

import android.os.Bundle;

/**
 * @author dipenp
 *
 */
public class ActivityHome extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 *  We will not use setContentView in this activty
		 *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/

		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.activity_home, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);

		//kc ((ImageView)findViewById(R.id.image_view)).setBackgroundResource(R.drawable.image1);
	}

}
