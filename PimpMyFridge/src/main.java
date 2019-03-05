
import java.io.IOException;
import java.util.TooManyListenersException;

import controller.Controller;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import model.Model;
import vue.Window;

public class main {

	public static void main(String[] args) {

		Window frame = new Window();
		Model model = null;
		try {
			model = new Model();
		} catch (UnsupportedCommOperationException | TooManyListenersException | NoSuchPortException
				| PortInUseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Controller con = new Controller(frame, model);
		con.setup();
		while (true) {
			con.running();
			frame.repaint();
		}

	}

}
