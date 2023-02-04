package frame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import main.GConstants.CFrame;

public class GFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private GPanel panel;
	private GToolBar toolBar;
	private GMenuBar menuBar;
	private WindowHandler windowHandler;
	
	public GFrame() {
		// initialize attributes
		this.setLocation(CFrame.point);
		this.setSize(CFrame.dimesion);
		this.setTitle(CFrame.title);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		this.windowHandler = new WindowHandler();
		this.addWindowListener(windowHandler);
		
		// initialize components
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);
		
		BorderLayout layoutManager = new BorderLayout();
		this.getContentPane().setLayout(layoutManager);
		
		this.toolBar = new GToolBar();
		this.toolBar.setBorderPainted(false);
		this.getContentPane().add(this.toolBar, BorderLayout.NORTH);
		
		this.panel = new GPanel();
		this.getContentPane().add(this.panel, BorderLayout.CENTER);
		
		// set associations
		this.menuBar.setAssociation(this.panel);
		this.toolBar.setAssociation(this.panel);
	}

	public void initialize() {
		this.toolBar.initialize();
		this.panel.initialize();		
	}
	private class WindowHandler implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			menuBar.windowClosing();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
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
}
