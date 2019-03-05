package model;

import java.util.Observable;

public class Obj extends Observable {

	private String str;
	private float flt;

	public Obj(String str) {
		this.str = str;
		this.flt = 0;
	}

	public Obj(float flt) {
		this.flt = flt;
		this.str = null;
	}

	public String getStr() {
		return str;
	}

	public float getFlt() {
		return flt;
	}

	public void setStr(String str) {
		this.str = str;
		setChanged();
		notifyObservers();
	}

	public void setFlt(float flt) {
		this.flt = flt;
		setChanged();
		notifyObservers();
	}
	
	

}
