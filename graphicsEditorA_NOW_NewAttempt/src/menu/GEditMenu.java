package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.EEditMenuItem;
import shapeTools.GShapeTool;

public class GEditMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private GPanel panel;

//	private Vector<GShapeTool> undoShapes = new Vector<>();
	
	public GEditMenu(String text, Color color) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EEditMenuItem eMenuItem: EEditMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getText());
			KeyStroke ks = KeyStroke.getKeyStroke(eMenuItem.getKey(), InputEvent.CTRL_DOWN_MASK);
			menuItem.setActionCommand(eMenuItem.name());
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
	
	@SuppressWarnings("unchecked")
	public void undo() {
		this.panel.undo();
	}
	
	public void redo() {
		this.panel.redo();
	}
	public void cut() {
		this.panel.cut();
	}
	public void paste() {
		this.panel.paste();
	}
	public void delete() {
		this.panel.delete();
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EEditMenuItem eMenuItem = EEditMenuItem.valueOf(e.getActionCommand());
			switch(eMenuItem) {
			case eUndo:
				undo();
				break;
			case eRedo:
				redo();
				break;
			case eGroup:
				break;
			case eUnGroup:
				break;
			case eCut:
				cut();
				break;
			case ePaste:
				paste();
				break;
			case eDelete:
				delete();
				break;
			default :
				break;
			}

		}




	
	}
	
}
