package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class Button extends JButton {

	private String name;

	public Button(String str) {
		super(str);
		this.name = str;
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
		g2d.drawString(name, this.getWidth() / 3, (this.getHeight() / 4 * 3));

	}

}
