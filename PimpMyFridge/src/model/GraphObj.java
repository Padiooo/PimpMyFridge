package model;

import java.util.ArrayList;
import java.util.Observable;

public class GraphObj extends Observable {

	private ArrayList<Float> temp = null;
	private int i = 0;

	public GraphObj() {

		this.temp = new ArrayList<Float>();

	}

	public void addTemp(float f) {

		if (temp.size() <= 15) {
			temp.add(f);
		} else {
			temp.add(i, f);
			i++;
			if (i == 15) {
				i = 0;
			}
		}

		setChanged();
		notifyObservers();

	}

	public ArrayList getTemp() {
		return temp;
	}

}
