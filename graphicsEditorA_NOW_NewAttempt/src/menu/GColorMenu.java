package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.Vector;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.EColorMenuItem;
import main.GConstants.EEditMenuItem;
import shapeTools.GShapeTool;

public class GColorMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private GPanel panel;
	
	public GColorMenu(String text, Color color) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EColorMenuItem eColorItem: EColorMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eColorItem.getText());
			KeyStroke ks = KeyStroke.getKeyStroke(eColorItem.getKey(), InputEvent.CTRL_DOWN_MASK); 
			menuItem.setActionCommand(eColorItem.name());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(ks);
			this.add(menuItem);
		}
		if(color==Color.WHITE) {
			this.setForeground(Color.BLACK);
		} else {
			this.setForeground(Color.white);
		}
	}
	public void setAssociation(GPanel panel) {
		this.panel = panel;		
	}
	public void line() {
		Color color = JColorChooser.showDialog(this.panel, "Line Color", this.panel.getBackground());
		this.panel.setLineColor(color);
	}
	public void fill() {
		Color color = JColorChooser.showDialog(this.panel, "Fill Color", this.panel.getForeground());
		this.panel.setFillColor(color);
	}
	
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EColorMenuItem eColorItem = EColorMenuItem.valueOf(e.getActionCommand());
			switch(eColorItem) {
			case eLine:
				line();
				break;
			case eFill:
				fill();
				break;
			default :
				break;
			}

		}

	}
}
