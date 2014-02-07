package com.mmm.findtherythm.model;

public class ButtonRythm {
	int id;
	boolean state;
	String image;
	public ButtonRythm(int id, boolean state, String image) {
		super();
		this.id = id;
		this.state = state;
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	

}
