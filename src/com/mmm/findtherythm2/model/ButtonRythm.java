package com.mmm.findtherythm2.model;

public class ButtonRythm {
	int id;
	boolean state; 
	public ButtonRythm(int id) {
		super();
		this.id = id;
		this.state = false;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean getState() {
		return state;
	}

	public void enable() {
		state = true;
	}
	
	public void disable() {
		state = false;
	}
}
