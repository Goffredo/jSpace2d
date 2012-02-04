package base.graphics.engine.simpleChooser;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class ModeChooser extends JFrame implements ActionListener {

	public ModeChooser(String title) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(title);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		assert (this.getWindows().length==1);
		Window w;
		w = this.getWindows()[0];
		w.getToolkit().getSystemEventQueue().postEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));

	}

}
