package com.mmm.findtherythm2;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmm.findtherythm2.Factory;
import com.mmm.findtherythm2.GameActivity;
import com.mmm.findtherythm2.utils.ScoreUtil;
import com.mmm.findtherythm2.R;
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
	private EditText name;
	private Button save;
	private TextView scoreView;
	private int score;
	ArrayList<Integer> enableBoutons = new ArrayList<Integer>();
	private Handler myHandler;
	private Handler myHandler2;
	private int nbv=0;
	CountDownTimer timer = new CountDownTimer(30000, 30000) {

	    @Override
	    public void onTick(long millisUntilFinished) {
	       // Nothing to do
	    }

	    @Override
	    public void onFinish() {
	    	finActivity();
			setContentView(R.layout.save);
			// Initialisation Sauvegarde de partie
			name = (EditText) findViewById(R.id.nameEditText);
			save = (Button) findViewById(R.id.saveButton);
			save.setOnClickListener(saveHandler);
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
		push.add((ImageView) findViewById(R.id.push6));
		push.add((ImageView) findViewById(R.id.push7));
		push.add((ImageView) findViewById(R.id.push8));
		//push.add((ImageView) findViewById(R.id.push9));
		
		hide();
		
		// Initialisation du boutton retour
		back = (ImageView) findViewById(R.id.buttonBack);
		back.setOnClickListener(backHandler);
		
		//Intialisation du controlleur
		Log.i(TAG, "ajout de l'observateur");
		Model m = Factory.getInstance().getModel();
		m.addObserver(this);

		//affectation des listeners
		for(int i=0; i<push.size(); i++)
			push.get(i).setOnClickListener(pushBouton2);
		
		//Initialisation du score 
		scoreView = (TextView) findViewById(R.id.scoreView);
		
		Factory.getInstance().getController().startGameAction();
		
		myHandler = new Handler();
		myHandler2 = new Handler();
		myHandler.post(myRunnable); 
		timer.start();
	}
	
	public void hide(){
		for (ImageView im : push)
        	im.setVisibility(View.INVISIBLE);
	}
	
	public void appearsAndDisapers() throws InterruptedException
	{

		Random g = new Random();

	    int move = g.nextInt(8);
	    int move2 = g.nextInt (8);
	    int move3 = g.nextInt (8);
	    int move4 = g.nextInt(8);
	    hide();
	    for (int j=0;j<enableBoutons.size();j++)
	    	push.get(enableBoutons.get(j)).setVisibility(View.VISIBLE);
	    push.get(move).setVisibility(View.VISIBLE);
	    push.get(move2).setVisibility(View.VISIBLE);
	    push.get(move3).setVisibility(View.VISIBLE);
	    push.get(move4).setVisibility(View.VISIBLE);
	    
  	

	}
	
	OnClickListener saveHandler = new OnClickListener() {
		@Override
		public void onClick(View buttonSave) {
			Log.i(TAG, "onClick button save");
			ScoreUtil.savePartie(name.getText().toString() , Factory.getInstance().getModel().getScore());
			Factory.getInstance().destroy();
			GameActivity.this.finish();
		}
	};
		
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
	
	


	
	OnClickListener pushBouton2 = new OnClickListener() {
		
		@Override
		public void onClick(View boutonPushed) {
			
		
			for(int i=0; i< push.size(); i++){
				
				if(boutonPushed.getId() == push.get(i).getId()) {
					if(enableBoutons.contains(i)){
						nbv++;
						if (nbv == enableBoutons.size()){
							Factory.getInstance().getController().clickSuccessAction();
							nbv=0;
							myHandler.removeCallbacks(myRunnable);
							myHandler2.removeCallbacks(myRunnable2);
							myHandler.post(myRunnable);
						}
							
						else
							mMediaPlayer3.start();
					}
					else {
						nbv=0;
						Factory.getInstance().getController().clickFailAction();
						myHandler.removeCallbacks(myRunnable);
						myHandler2.removeCallbacks(myRunnable2);
						myHandler.post(myRunnable);
					}

				}	
			}	
		}
	};
	
	private Runnable myRunnable = new Runnable() {
		@Override
		public void run() {
			// Code à éxécuter de façon périodique
			try {
				appearsAndDisapers();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//myHandler2.removeCallbacks(myRunnable2);
			myHandler2.postDelayed(myRunnable2,3000);
			myHandler.postDelayed(this,3000);
			
		}
	};
	
	private Runnable myRunnable2 = new Runnable() {
		@Override
		public void run() {
			if (nbv == enableBoutons.size()){
				Factory.getInstance().getController().clickSuccessAction();
			}
			else {
				Factory.getInstance().getController().clickFailAction();
			}
			nbv=0;
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
		enableBoutons.clear();
		for(int i=0; i<listBouton.size(); i++){
			Log.i(TAG, "push("+i+") = "+listBouton.get(i).getState());
			if(listBouton.get(i).getState() == true  ) {
				push.get(i).setImageResource(R.drawable.button_green);
				enableBoutons.add(listBouton.get(i).getId());
				Log.i(TAG, "enable("+i+") = "+listBouton.get(i).getId());
			}
			else {
				push.get(i).setImageResource(R.drawable.button_red);
			}
		}
		score = model.getScore();
	}
	
	protected void finActivity() {
		mMediaPlayer2.stop();
		pushBouton2 = null;
		myHandler.removeCallbacks(myRunnable);
		myHandler2.removeCallbacks(myRunnable2);
	}

}
