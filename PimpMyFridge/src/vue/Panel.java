package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Obj;

public class Panel extends JPanel implements Observer {

	private int x;
	private int y;
	private Color color;
	private String text;
	private int size = 30;
	private Obj obj;

	public Panel(Color color, int x, int y, Obj obj) {
		setBackground(color);
		setPreferredSize(new Dimension(x, y));
		this.color = color;
		this.x = x;
		this.y = y;
		this.obj = obj;
		if (obj.getFlt() != 0) {
			text = Float.toString(obj.getFlt()) + "°C";
		}
		if (obj.getStr() != null) {
			text = obj.getStr();
		}
		obj.addObserver(this);
	}

	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.drawRect(0, 0, x - 1, y - 1);
		if (this.text != null) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, size));
			g.drawString(text, x / 2 - size / 4 * text.length(), y / 4 + size);
		}
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void paintString(String s) {
		this.text = s;
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (obj.getFlt() != 0) {
			text = Float.toString(obj.getFlt()) + "°C";
		}
		if (obj.getStr() != null) {
			text = obj.getStr();
		}
		repaint();

	}
}
