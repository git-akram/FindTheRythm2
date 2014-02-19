package com.mmm.findtherythm2.model;

import java.util.ArrayList;

import android.util.Log;

public class Model implements Observable{
	private int score;
	private ArrayList<ButtonRythm> buttonRythmList;
	private String move;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	private static final String TAG = "Model";
	
	public void startPartie() {
		this.buttonRythmList = new ArrayList<ButtonRythm>();
		ButtonRythm buttonRythm1 = new ButtonRythm(0);
		ButtonRythm buttonRythm2 = new ButtonRythm(1);
		ButtonRythm buttonRythm3 = new ButtonRythm(2);
		ButtonRythm buttonRythm4 = new ButtonRythm(3);
		ButtonRythm buttonRythm5 = new ButtonRythm(4);
		ButtonRythm buttonRythm6 = new ButtonRythm(5);
		ButtonRythm buttonRythm7 = new ButtonRythm(6);
		ButtonRythm buttonRythm8 = new ButtonRythm(7);
		/*ButtonRythm buttonRythm9 = new ButtonRythm(8);*/
		this.buttonRythmList.add(buttonRythm1);
		this.buttonRythmList.add(buttonRythm2);
		this.buttonRythmList.add(buttonRythm3);
		this.buttonRythmList.add(buttonRythm4);
		this.buttonRythmList.add(buttonRythm5);
		this.buttonRythmList.add(buttonRythm6);
		this.buttonRythmList.add(buttonRythm7);
		this.buttonRythmList.add(buttonRythm8);
		//this.buttonRythmList.add(buttonRythm9);
		activateButtonRandomly(0);
		this.move = "start";
		this.score = 0;
		notifyObserver();
	}
	
	public void quitPartie() {
		this.score = 0;
		buttonRythmList.clear();
	}

	public int getScore() {
		return score;
	}

	public ArrayList<ButtonRythm> getButtonRythm() {
		return buttonRythmList;
	}

	public String getMove() {
		return move;
	}
	
	private void setFalseMove() {
		move = "wrong";
	}
	
	private void setRightMove() {
		move = "right";
	}
	
	private void nextButton() {
		Log.i(TAG, "nextButton");
		Log.i(TAG, "nextButton");
		
		int bouttonActiveId = 0;
		Log.i(TAG, "nextButton");
		
		for(int i=0; i<buttonRythmList.size(); i++) {
			Log.i(TAG, "traitement nextButton"+i);
			if(buttonRythmList.get(i).getState())
				bouttonActiveId = buttonRythmList.get(i).getId();
		}
		Log.i(TAG, "Pass traitement nextButton");
		activateButtonRandomly(bouttonActiveId);	
		Log.i(TAG, "End of nextButton");
	}
	
	private void activateButtonRandomly(int id) {
		int rand = 0;
		int rand2 = 0;
		int rand3 = 0;
		do { 
			rand = (int) (Math.random() * 8);
			rand2 = (int) (Math.random() * 8);
			rand3 = (int) (Math.random() * 8);
		}while(rand == id);
		
		for(ButtonRythm buttonRythm : buttonRythmList) {
			if(buttonRythm.getId() == rand || buttonRythm.getId() == rand2 || buttonRythm.getId() == rand3 )
				buttonRythm.enable();
			else
				buttonRythm.disable();
		}
	}
	
	private void addScore() {
		score = score + 100;
	}
	
	private void deductScore() {
		if(score >= 100) {
			score = score - 100;	}
	}
	
	public void nextMove(boolean move) {
		Log.i(TAG, "nextMove");
		if(move) {
			addScore();
			setRightMove();
		}
		else {
			deductScore();
			setFalseMove();
		}
		nextButton();
		
		notifyObserver();
	}

	@Override
	public void addObserver(Observer obs) {
		this.listObserver.add(obs);	
	}

	@Override
	public void removeObserver() {
		listObserver = new ArrayList<Observer>();	
	}

	@Override
	public void notifyObserver() {
		Log.i(TAG, "Start of listObserver notify");
		for(int i=0; i<listObserver.size(); i++){
			Log.i(TAG, "notify observer"+i);
			listObserver.get(i).update(this);
		}      
		Log.i(TAG, "End of listObserver notify");
	}
}
