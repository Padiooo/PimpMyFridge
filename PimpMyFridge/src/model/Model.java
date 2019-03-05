package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Observable;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class Model extends Observable implements SerialPortEventListener {

	/** Serial port */
	private SerialPort serialPort;
	/** Streams */
	private InputStream serialIn;
	private OutputStream serialOut;
	private BufferedReader serialReader;

	private Obj tempChoisi;
	private Obj tempFrigo;
	private Obj tempPeltier;
	private Obj tempExt;
	private Obj humidite;
	private Obj alertes;
	private GraphObj graphObj;
	private String oldString;

	// private truc graph;

	// Open port
	public Model() throws UnsupportedCommOperationException, TooManyListenersException, NoSuchPortException,
			PortInUseException, IOException {
		CommPortIdentifier port = CommPortIdentifier.getPortIdentifier("COM10");
		CommPort commPort = port.open(this.getClass().getName(), 2000);
		serialPort = (SerialPort) commPort;
		serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		serialIn = serialPort.getInputStream();
		serialOut = serialPort.getOutputStream();
		serialReader = new BufferedReader(new InputStreamReader(serialIn));
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
		this.humidite = new Obj(10);
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {

		try {
			String line = serialReader.readLine();
			if (!(line.equals(oldString))) {
				oldString = line;
				// System.out.println("READ from serial: " + line);
				splitAndRefresh();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void splitAndRefresh() {

		// Parse the information
		String[] values = oldString.split(":");

		graphObj.addTemp(Float.parseFloat(values[1]));
		tempFrigo.setFlt(Float.parseFloat(values[1]));
		humidite.setFlt(Float.parseFloat(values[7]));
		tempPeltier.setFlt(Float.parseFloat(values[3]));
		tempExt.setFlt(Float.parseFloat(values[5]));

		setChanged();
		notifyObservers();
	}

	public void serialWrite(int i) {

		try {
			serialOut.write(i);
		} catch (IOException e) {
			System.out.println("Error serialWrite()");
			System.exit(0);
		}
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public void setSerialIn(InputStream serialIn) {
		this.serialIn = serialIn;
	}

	public void setSerialOut(OutputStream serialOut) {
		this.serialOut = serialOut;
	}

	public void setSerialReader(BufferedReader serialReader) {
		this.serialReader = serialReader;
	}

	public void setTempChoisi(Obj tempChoisi) {
		this.tempChoisi = tempChoisi;
	}

	public void setTempFrigo(Obj tempFrigo) {
		this.tempFrigo = tempFrigo;
	}

	public void setTempPeltier(Obj tempPeltier) {
		this.tempPeltier = tempPeltier;
	}

	public void setTempExt(Obj tempExt) {
		this.tempExt = tempExt;
	}

	public void setAlertes(Obj alertes) {
		this.alertes = alertes;
	}

	public void setOldString(String oldString) {
		this.oldString = oldString;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public InputStream getSerialIn() {
		return serialIn;
	}

	public OutputStream getSerialOut() {
		return serialOut;
	}

	public BufferedReader getSerialReader() {
		return serialReader;
	}

	public Obj getTempChoisi() {
		return tempChoisi;
	}

	public Obj getTempFrigo() {
		return tempFrigo;
	}

	public Obj getTempPeltier() {
		return tempPeltier;
	}

	public Obj getTempExt() {
		return tempExt;
	}

	public Obj getAlertes() {
		return alertes;
	}

	public String getOldString() {
		return oldString;
	}

	public void setGraphObj(GraphObj graphObj) {
		this.graphObj = graphObj;
	}

	public GraphObj getGraphObj() {
		return this.graphObj;
	}

	public Obj getHumidite() {
		return humidite;
	}

	public void setHumidite(Obj humidite) {
		this.humidite = humidite;
	}

}
