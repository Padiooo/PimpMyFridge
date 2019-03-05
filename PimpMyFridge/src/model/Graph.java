package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class Graph extends JPanel implements Observer {

	private int x;
	private int y;
	private Color color;
	private String text;
	private int size = 30;
	private GraphObj graphObj;

	public Graph(Color color, int x, int y, GraphObj graphObj) {
		setBackground(Color.white);
		setPreferredSize(new Dimension(x, y));
		this.color = color;
		this.x = x;
		this.y = y;
		this.graphObj = graphObj;

		graphObj.addObserver(this);
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(0, 0, x - 1, y - 1);
		if (this.text != null) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, size));
			g.drawString(text, x / 2 - size / 4 * text.length(), y / 4 + size);
		}

		drawGraph(g, graphObj.getTemp(), 10, 10);

		/*
		 * g.setColor(Color.white);
		 * 
		 * Date current = new Date();
		 * 
		 * ArrayList<Date> date = graphObj.getDate(); ArrayList<Float> temp =
		 * graphObj.getTemp();
		 * 
		 * g.drawLine(25, 275, 575, 275); float a = 0f; for (int i = 0; i < date.size();
		 * i++) { a = (current.getTime() - date.get(i).getTime()) / 1000; int x =
		 * Math.round(550 - (a * 50)); int y = Math.round(temp.get(i) * 15);
		 * g.drawOval(x - 5, y - 5, 10, 10); System.out.println("x:" + x + " y:" + y); }
		 */
	}

	public void drawGraph(Graphics g, ArrayList<Float> values, int initial_x, int initial_y) {
		g.setColor(Color.WHITE);
		g.fillRect(initial_x - 10, initial_y - 10, 600, 200);

		g.setColor(Color.BLACK);
		g.drawLine(initial_x, initial_y + 200, initial_x + 600, initial_y + 200); // x axe
		g.drawLine(initial_x, initial_y, initial_x, initial_y + 200); // y axe

		// Axes labels
		g.drawString("T (°C)", initial_x - 5, initial_y);
		g.drawString("25", initial_x - 10, initial_y + 15);
		g.drawString("0", initial_x - 10, initial_y + 185);
		g.drawString("t (min)", initial_x + 550, initial_y + 180);

		for (int i = 0; i < 25; i++) {
			int y = (int) (initial_y + i * 8.8);

			g.drawLine(initial_x, y, initial_x + 5, y);
		}

		for (int i = 0; i < 10; i++) {
			int x = (int) (initial_x + i * 41.1);

			g.drawLine(x, initial_y + 215, x, initial_y + 200);
		}

		int last_x = 0;
		int last_y = 0;

		for (int i = 0; i < values.size(); i++) {
			int v_y = (int) (initial_y + (25 - values.get(i)) * 8.8);
			int v_x = (int) (initial_x + i * 41.1);

			g.setColor(Color.RED);
			g.fillOval(v_x - 3, v_y - 3, 6, 6);

			if (last_x != 0 && last_y != 0) {
				g.drawLine(last_x, last_y, v_x, v_y);
			}

			last_x = v_x;
			last_y = v_y;
		}

	}

	@Override
	public void update(Observable o, Object arg) {

		repaint();

	}

}
