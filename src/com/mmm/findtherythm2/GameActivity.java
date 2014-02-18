package com.mmm.findtherythm2;

import java.util.ArrayList;
import java.util.Timer;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmm.findtherythm2.controller.Controller;
import com.mmm.findtherythm2.model.ButtonRythm;
import com.mmm.findtherythm2.model.Model;
import com.mmm.findtherythm2.model.Observer;

public class GameActivity extends Activity implements Observer{
	
	private static final String TAG = "GameActivity";
	
	private ImageView background;
	private MediaPlayer mMediaPlayer = new MediaPlayer();
	private MediaPlayer mMediaPlayer2 = new MediaPlayer();
	private MediaPlayer mMediaPlayer3 = new MediaPlayer();
	private AnimationDrawable danceAnimation;	
	private ArrayList<ImageView> push;
	private ImageView back;
	private TextView scoreView;
	private int score;
	//private Controller controlleur;
	private ButtonRythm enable_button;
	private Handler myHandler;
	private Runnable myRunnable = new Runnable() {
		@Override
		public void run() {
			// Code à éxécuter de façon périodique
			Factory.getInstance().getController().clickFailAction();
			myHandler.postDelayed(this,2000);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "start GameActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);	
		configSound();
		mMediaPlayer2.start();
		
		background = (ImageView) findViewById(R.id.background);
		background.setBackgroundResource(R.drawable.dance_animation);
		danceAnimation = (AnimationDrawable) background.getBackground();
		danceAnimation.setCallback(background);
		danceAnimation.setVisible(true, true);
		
		//création des boutons
		Log.i(TAG, "création des boutons");
		push= new ArrayList<ImageView>();
		push.add((ImageView) findViewById(R.id.push1));
		push.add((ImageView) findViewById(R.id.push2));
		push.add((ImageView) findViewById(R.id.push3));
		push.add((ImageView) findViewById(R.id.push4));
		push.add((ImageView) findViewById(R.id.push5));
		
		// Initialisation du boutton retour
		back = (ImageView) findViewById(R.id.buttonBack);
		back.setOnClickListener(backHandler);
		
		//Intialisation du controlleur
		Log.i(TAG, "ajout de l'observateur");
		Model m = Factory.getInstance().getModel();
		m.addObserver(this);

		//affectation des listeners
		for(int i=0; i<push.size(); i++)
			push.get(i).setOnClickListener(pushBouton);
		
		//Initialisation du score 
		scoreView = (TextView) findViewById(R.id.scoreView);
		
		Factory.getInstance().getController().startGameAction();
		
		myHandler = new Handler();
		myHandler.postDelayed(myRunnable,2000); 
	}
		
	public void oppaDance() {
		final RelativeLayout rl = (RelativeLayout) findViewById(R.id.layoutGame);
        rl.postDelayed(new Runnable() {
            int i = 0;
            public void run() {
            	rl.setBackgroundResource(
                    i++ % 2 == 0 ?
                    		R.drawable.op2 :
                    		R.drawable.op1);
            	rl.postDelayed(this, 500);
            }
        }, 500);
	}
	
	public void configSound() {
		mMediaPlayer = MediaPlayer.create(this, R.raw.error);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setLooping(false);
		mMediaPlayer.setVolume(100, 100);
		mMediaPlayer3 = MediaPlayer.create(this, R.raw.valid);
		mMediaPlayer3.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer3.setLooping(false);
		mMediaPlayer3.setVolume(100, 100);
		mMediaPlayer2 = MediaPlayer.create(this, R.raw.sound1);
		mMediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer2.setLooping(true);
		mMediaPlayer.setVolume(20, 20);
	}

		
	OnClickListener backHandler = new OnClickListener() {
		@Override
		public void onClick(View buttonBack) {
			Log.i(TAG, "onClick button back");
			Factory.getInstance().destroy();
			finActivity();
			GameActivity.this.finish();
		}
	};
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus) {
			danceAnimation.start();
		}
	}

	OnClickListener pushBouton = new OnClickListener() {
	
		@Override
		public void onClick(View boutonPushed) {
			Log.i(TAG, "onClick button");
			myHandler.removeCallbacks(myRunnable);
			myHandler.postDelayed(myRunnable, 2000);
			for(int i=0; i< push.size(); i++){
				Log.i(TAG, "for : "+boutonPushed.getId()+" | "+push.get(i).getId());
				//Je cherche la correpondance du bouton cliqué dans la liste des views
				if(boutonPushed.getId() == push.get(i).getId()) {
					Log.i(TAG, "enable : "+enable_button.getId());
					//si on clique sur le bon bouton
					if(i == enable_button.getId()){
						Factory.getInstance().getController().clickSuccessAction();
					}
					else {
						Factory.getInstance().getController().clickFailAction();
					}
				}	
			}	
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public void update(Model model) {
		Log.i(TAG, "update");
		scoreView.setText("Score : "+model.getScore());
		updatePush(model);
		updateSound(model);
		Log.i(TAG, "fin update");
	}

	private void updateSound(Model model) {
		// TODO Auto-generated method stub
		if (model.getMove().equals("right"))
			mMediaPlayer3.start();
		else if (model.getMove().equals("wrong"))
			mMediaPlayer.start();
	}

	private void updatePush(Model model) {
		ArrayList<ButtonRythm> listBouton= model.getButtonRythm();
		for(int i=0; i<listBouton.size(); i++){
			Log.i(TAG, "push("+i+") = "+listBouton.get(i).getState());
			if(listBouton.get(i).getState() == true) {
				push.get(i).setImageResource(R.drawable.button_green);
				enable_button = listBouton.get(i);
			}
			else {
				push.get(i).setImageResource(R.drawable.button_red);
			}
		}
		score = model.getScore();
	}
	
	protected void finActivity() {
		mMediaPlayer2.stop();
		pushBouton = null;
		myHandler.removeCallbacks(myRunnable);
	}

}
