package base.graphics.engine.simpleChooser;

import base.ActionManager;
import base.graphics.CreateGameRenderable;
import base.graphics.CreateGameRenderable_dummy;
import base.graphics.engine.main.GraphicEngine;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.CountDownLatch;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.jbox2d.common.Mat22;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Launcher implements WindowListener {

	private static DisplayMode mode;
	private static ModeChooser frame;

	private JComboBox<DisplayMode> comboBox;
	private CountDownLatch loginSignal;
	private Boolean fullScreen;
	private Boolean vSync;
	private JCheckBox fullScreenCheckBox;
	private JCheckBox vSyncCheckBox;
	
	public Launcher(){
		mode = null;
		DisplayMode[] dModes = null;
		
		try {
			dModes = Display.getAvailableDisplayModes();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		simpleModeSelectorUI(dModes);
		
		//TODO add reference to ActionManager
		
		ActionManager aManager = new ActionManager();
		
		GraphicEngine test = new GraphicEngine(mode, fullScreen, vSync, aManager);		
		Thread graphics = new Thread(test);
		graphics.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Transform transform = new Transform();
		transform.set(new Vec2(0.0f,0.0f), (float)(Math.PI/2));
		aManager.addGraphicsAction(new CreateGameRenderable_dummy(0, 0, transform));
			
	}

	private void simpleModeSelectorUI(DisplayMode[] dModes) {
		loginSignal = new CountDownLatch(1);
		frame = new ModeChooser("Choose mode");
		frame.setLayout(new FlowLayout());
		comboBox = new JComboBox<>(dModes);
		frame.add(comboBox);
		DisplayMode current = Display.getDesktopDisplayMode();
		comboBox.setSelectedItem(current);
		JButton button = new JButton("OK");
		frame.add(button);
		button.addActionListener(frame);
		fullScreenCheckBox = new JCheckBox("Fullscreen");
		vSyncCheckBox = new JCheckBox("vSync");
		frame.add(fullScreenCheckBox);
		frame.add(vSyncCheckBox);
		frame.addWindowListener(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		try {
			loginSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("Window closing.");
		mode = (DisplayMode)comboBox.getSelectedItem();
		fullScreen = fullScreenCheckBox.isSelected();
		vSync = vSyncCheckBox.isSelected();
		loginSignal.countDown();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("Window closed.");
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
