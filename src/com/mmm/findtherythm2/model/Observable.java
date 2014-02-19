package com.mmm.findtherythm2.model;

public interface Observable {
	public void addObserver(Observer obs);
	public void removeObserver();
	public void notifyObserver();
}