package com.mmm.findtherythm;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class IndexActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		
		// Component declaration
		Button buttonJouer = (Button) findViewById(R.id.buttonJouer);
		Button buttonScore = (Button) findViewById(R.id.buttonScore);
		
		// Handler declaration
		buttonJouer.setOnClickListener(buttonJouerHandler);
		buttonScore.setOnClickListener(buttonScoreHandler);
		
	}
	
	OnClickListener buttonJouerHandler = new OnClickListener() {

		@Override
		public void onClick(View v) {
			setContentView(R.layout.activity_game);	
		}
		
	};
	
	OnClickListener buttonScoreHandler = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Redirect to Score Layout
			
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

}
