package com.mmm.findtherythm2;

import java.util.HashMap;

import com.mmm.findtherythm2.IndexActivity;
import com.mmm.findtherythm2.R;
import com.mmm.findtherythm2.utils.ScoreUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class IndexActivity extends Activity {
	private static final String TAG = "IndexActivit";
	GameActivity gameActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		Log.i(TAG, "init index activity");
		
		// Component declaration
		Button buttonJouer = (Button) findViewById(R.id.buttonJouer);
		Button buttonScore = (Button) findViewById(R.id.buttonScore);
		Button buttonQuit = (Button) findViewById(R.id.buttonQuit);
		
		// Handler declaration
		buttonJouer.setOnClickListener(buttonJouerHandler);
		buttonScore.setOnClickListener(buttonScoreHandler);
		buttonQuit.setOnClickListener(buttonQuitHandler);
	}

	OnClickListener buttonJouerHandler = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Factory.getInstance();
			Intent intent = new Intent(IndexActivity.this, GameActivity.class);
			startActivityForResult(intent,0);
		}	
	};
	
	OnClickListener buttonScoreHandler = new OnClickListener() {
		@Override
		public void onClick(View v) {
			setContentView(R.layout.score);
			ImageView back = (ImageView) findViewById(R.id.backFromScore);
			back.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//setContentView(R.layout.activity_index);
					startActivity(new Intent(IndexActivity.this , IndexActivity.class));
				}
			});
			HashMap<String, Integer> map = ScoreUtil.getListScore();
			int i = 0;
			for (String mapKey : map.keySet()) {
				i++;
				if(i == 1) {
					TextView name1 = (TextView) findViewById(R.id.name1);
					name1.setText(mapKey);
					TextView score1 = (TextView) findViewById(R.id.score1);
					score1.setText(map.get(mapKey).toString());
				}
				else if(i == 2) {
					TextView name2 = (TextView) findViewById(R.id.name2);
					name2.setText(mapKey);
					TextView score2 = (TextView) findViewById(R.id.score2);
					score2.setText(map.get(mapKey).toString());
				}
				else if(i == 3) {
					TextView name3 = (TextView) findViewById(R.id.name3);
					name3.setText(mapKey);
					TextView score3 = (TextView) findViewById(R.id.score3);
					score3.setText(map.get(mapKey).toString());
				}	
			}
		}
	};
	
	OnClickListener buttonQuitHandler = new OnClickListener() {
		@Override
		public void onClick(View v) {
			IndexActivity.this.finish();
		}	
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

}
