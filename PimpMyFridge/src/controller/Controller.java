package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import model.Graph;
import model.GraphObj;
import model.Model;
import model.Obj;
import vue.Panel;
import vue.Window;

public class Controller implements ActionListener {

	private Window window;
	private Model model;

	private Button bouttonMoins1;
	private Button bouttonPlus1;

	private JPanel content;

	public Controller(Window window, Model model) {
		this.window = window;
		this.model = model;
		model.addObserver(window);
	}

	public void setup() {

		model.setTempChoisi(new Obj((float) 17.00));
		model.setTempFrigo(new Obj((float) 20.00));
		model.setTempPeltier(new Obj((float) 17.00));
		model.setTempExt(new Obj((float) 21.00));
		model.setAlertes(new Obj(""));
		model.setGraphObj(new GraphObj());

		Panel tempChoisi = new Panel(Color.YELLOW, 300, 100, model.getTempChoisi());

		Panel tempFrigo = new Panel(Color.red, 100, 100, model.getTempFrigo());
		Panel tempPeltier = new Panel(Color.green, 100, 100, model.getTempPeltier());
		Panel tempExt = new Panel(Color.black, 100, 100, model.getTempExt());

		Panel bouttonMoins = new Panel(Color.cyan, 150, 100, model.getTempChoisi());
		bouttonMoins1 = new Button("-");
		bouttonMoins.setLayout(new BorderLayout());
		bouttonMoins.add(bouttonMoins1, BorderLayout.CENTER);
		bouttonMoins1.addActionListener(this);

		Panel bouttonPlus = new Panel(Color.BLUE, 150, 100, model.getTempChoisi());
		bouttonPlus1 = new Button("+");
		bouttonPlus.setLayout(new BorderLayout());
		bouttonPlus.add(bouttonPlus1, BorderLayout.CENTER);
		bouttonPlus1.addActionListener(this);

		Panel alertes = new Panel(Color.orange, 300, 100, model.getAlertes());
		Graph graph = new Graph(Color.DARK_GRAY, 600, 200, model.getGraphObj());

		// Le conteneur principal
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(600, 400));
		content.setBackground(Color.DARK_GRAY);
		// On définit le layout manager
		content.setLayout(new GridBagLayout());
		// L'objet servant à positionner les composants
		GridBagConstraints gbc = new GridBagConstraints();

		// ---------------------------------------------
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 6;
		gbc.gridheight = 2;
		content.add(tempChoisi, gbc);
		// ---------------------------------------------
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		content.add(tempFrigo, gbc);
		// ---------------------------------------------
		gbc.gridx = 7;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		content.add(tempPeltier, gbc);
		// ---------------------------------------------
		gbc.gridx = 8;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		content.add(tempExt, gbc);
		// ---------------------------------------------
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;
		content.add(bouttonMoins, gbc);
		// ---------------------------------------------
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;
		content.add(bouttonPlus, gbc);
		// ---------------------------------------------
		gbc.gridx = 6;
		gbc.gridy = 2;
		gbc.gridwidth = 6;
		gbc.gridheight = 2;
		content.add(alertes, gbc);
		// ---------------------------------------------
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 12;
		gbc.gridheight = 4;
		content.add(graph, gbc);
		// ---------------------------------------------

		// on ajoute le conteneur à la fenêtre
		window.setContentPane(content);
		window.repaint();

	}

	public void running() {
		checkCondensation();
		checkDoor();
		mathVoltage();
	}

	public void checkCondensation() {
		float Ta = model.getTempFrigo().getFlt();
		float hum = model.getHumidite().getFlt();
		float k = (float) ((17.27 * Ta) / (237.7 + Ta) + Math.log(hum));

		if (k > model.getTempFrigo().getFlt()) {
			model.getAlertes().setStr("  /!\\ Condensation /!\\");
		}
		else {
			model.getAlertes().setStr("");
		}
	}

	public void checkDoor() {
		float pel = model.getTempPeltier().getFlt();
		float fri = model.getTempFrigo().getFlt();
		if (fri - pel > 4) {
			model.getAlertes().setStr("/!\\ Porte Ouverte /!\\");
		}
	}

	public void mathVoltage() {
		float fri = model.getTempFrigo().getFlt();
		float cho = model.getTempChoisi().getFlt();
		
		if(fri > cho) {
			model.serialWrite(1);
		}
		else {
			model.serialWrite(0);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == bouttonPlus1) {
			model.getTempChoisi().setFlt((float) ((float) model.getTempChoisi().getFlt() + 0.5));
		}
		if (e.getSource() == bouttonMoins1) {
			model.getTempChoisi().setFlt((float) ((float) model.getTempChoisi().getFlt() - 0.5));
		}

		window.repaint();
	}

}
