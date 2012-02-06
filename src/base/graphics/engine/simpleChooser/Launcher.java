package base.graphics.engine.simpleChooser;

import base.ActionManager;
import base.graphics.engine.main.GraphicEngine;
import base.physic.NewBodyAction;
import base.physic.engine.PhysicsManager;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.CountDownLatch;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Launcher implements WindowListener {

	private static ModeChooser frame;
	private static DisplayMode mode;

	private JComboBox<DisplayMode> comboBox;
	private Boolean fullScreen;
	private JCheckBox fullScreenCheckBox;
	private CountDownLatch loginSignal;
	private Boolean vSync;
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
		
		PhysicsManager pManager = new PhysicsManager(aManager);
		createRandomActions(aManager);
		while(true){
			try {
				pManager.update();
				Thread.sleep(10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void createRandomActions(ActionManager aManager) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BodyDef bd = new BodyDef();
		FixtureDef fd = new FixtureDef();
		fd.density = 5.0f;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(2.0f, 2.0f);
		fd.shape = shape;
		fd.restitution = 0.5f;
		bd.type = BodyType.DYNAMIC;
		bd.position = new Vec2(0.0f, 0.0f);
		bd.angle = MathUtils.HALF_PI/4;
		aManager.addPhysicAction(new NewBodyAction(bd,fd));		
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
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("Window closed.");
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
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
