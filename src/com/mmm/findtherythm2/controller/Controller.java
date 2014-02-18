package com.mmm.findtherythm2.controller;

import android.util.Log;

import com.mmm.findtherythm2.model.Model;

public class Controller {

	Model model;
	private static final String TAG = "Controlleur";
	public Controller(Model model) {
		this.model = model;
	}
	
	public void clickSuccessAction() {
		Log.i(TAG, "clickSuccessAction");
		model.nextMove(true);
	}
	
	public void clickFailAction() {
		Log.i(TAG, "clickFailAction");
		model.nextMove(false);
	}
	
	public void terminerPartieAction() {
		model.quitPartie();
	}

	public void startGameAction() {
		model.startPartie();
	}
		
}
