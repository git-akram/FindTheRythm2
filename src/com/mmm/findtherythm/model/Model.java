package com.mmm.findtherythm.model;

import java.util.ArrayList;

import com.mmm.findtherythm.services.Graphic;
import com.mmm.findtherythm.services.Sound;

public class Model {
	private int score;
	private ArrayList<ButtonRythm> buttonRythm;
	private Graphic graphic;
	private Sound sound;
	
	public Model(int score, ArrayList<ButtonRythm> buttonRythm,
			Graphic graphic, Sound sound) {
		super();
		this.score = score;
		this.buttonRythm = buttonRythm;
		this.graphic = graphic;
		this.sound = sound;
	}

	public int getScore() {
		return score;
	}

	public ArrayList<ButtonRythm> getButtonRythm() {
		return buttonRythm;
	}

	public Graphic getGraphic() {
		return graphic;
	}

	public Sound getSound() {
		return sound;
	}
	
	private void newImage() {
		
	}
	
	private void nextButton() {
		
	}
	
	private void addScore() {
		
	}
	
	public void nextMove() {
		
	}
}
