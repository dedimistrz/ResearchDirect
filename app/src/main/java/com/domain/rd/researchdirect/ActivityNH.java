package com.domain.rd.researchdirect;

import android.os.Bundle;
import android.widget.TextView;

/**
 * @author dipenp
 *
 */
public class ActivityNH extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.activity_nh, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);

		//kc ((ImageView)findViewById(R.id.image_view)).setBackgroundResource(R.drawable.image4);
	}
	@Override
	public void search(String query) {
		TextView textView = (TextView) findViewById(R.id.textView2);
		textView.setText("NH:" + query);

		// reset loader, swap cursor, etc.
	}
}
