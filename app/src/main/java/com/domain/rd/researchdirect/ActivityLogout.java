package com.domain.rd.researchdirect;

import android.os.Bundle;
import android.widget.TextView;

/**
 * @author dipenp
 *
 */
public class ActivityLogout extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.activity_logout, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);

		//kc ((ImageView)findViewById(R.id.image_view)).setBackgroundResource(R.drawable.image4);
	}
	@Override
	public void search(String query) {
		TextView textView = (TextView) findViewById(R.id.textView4);
		textView.setText("Logout:" + query);

		// reset loader, swap cursor, etc.
	}
}
