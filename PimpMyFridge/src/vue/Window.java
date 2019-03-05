package vue;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class Window extends JFrame implements Observer {

	public Window() {

		this.setTitle("PimpMyFridge");
		this.setSize(625, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setVisible(true);

	}

	@Override
	public void update(Observable o, Object arg) {

		repaint();
		this.getContentPane().repaint();
		
	}
}
