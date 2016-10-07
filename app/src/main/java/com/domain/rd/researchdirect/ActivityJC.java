package com.domain.rd.researchdirect;

import android.os.Bundle;
import android.widget.TextView;

/**
 * @author dipenp
 *
 */
public class ActivityJC extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.activity_jc, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);

		//kc ((ImageView)findViewById(R.id.image_view)).setBackgroundResource(R.drawable.image5);
	} @Override
	public void search(String query) {
		TextView textView = (TextView) findViewById(R.id.textView5);
		textView.setText("JC:" + query);

		// reset loader, swap cursor, etc.
	}
}
