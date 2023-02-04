package menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import frame.GPanel;
import main.GConstants.EFileMenuItem;
import shapeTools.GShapeTool;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components

	// associations
	private GPanel panel;
	private File file;
	private File dir;
	private String filename;
	
	public GFileMenu(String text, Color color) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenuItem eFileMenuItem: EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eFileMenuItem.getText());
			KeyStroke ks = KeyStroke.getKeyStroke(eFileMenuItem.getKey(), InputEvent.CTRL_DOWN_MASK); 
			menuItem.setActionCommand(eFileMenuItem.name());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(ks);
			this.add(menuItem);
		}
		this.file = null;
		this.dir = null;
		if(color==Color.WHITE) {
			this.setForeground(Color.BLACK);
		} else {
			this.setForeground(Color.white);
		}
		
	}
	public void setAssociation(GPanel panel) {
		this.panel = panel;		
	}
	
	private void openFile() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new BufferedInputStream(	
							new FileInputStream(this.file)));
			Vector<GShapeTool> shapes = 
					(Vector<GShapeTool>) objectInputStream.readObject();
			this.panel.setShapes(shapes);
			objectInputStream.close();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void saveFile() {
		try {
			if(!this.filename.endsWith(".dgd")&&!this.filename.endsWith(".DGD")) {
				this.filename+=".dgd";
			}
			File saveFile = new File(this.filename);

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(saveFile)));
			objectOutputStream.writeObject(this.panel.getShapes());
			objectOutputStream.close();
			this.panel.setModified(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean checkSaveOrNot() {
		boolean bCancel = true;
		if(this.panel.isModified()) {
			//save
			int reply
			= JOptionPane.showConfirmDialog(this.panel, "Save This?"); //modal dialog = �� ȭ���� Ŭ������ ����
			if(reply == JOptionPane.OK_OPTION) {
				this.save();
				bCancel = false;
				
			} else if(reply == JOptionPane.NO_OPTION) {
				this.panel.setModified(false);
				bCancel = false;
			} 
		} else {
			bCancel = false;
		}
		return bCancel;
	}
	private void nnew() {
		if(!checkSaveOrNot()) {
		this.panel.clearScreen();
		this.file = null;
		}
	}	
	private void open() {
		if(!checkSaveOrNot()) { // if not cancel		
			
			JFileChooser chooser = new JFileChooser(this.dir);
			chooser.setSelectedFile(this.file);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Drawing Graphic data", "dgd");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this.panel);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = chooser.getSelectedFile();
				this.filename = chooser.getSelectedFile().toString();
				this.openFile();
			} 
		} //else { cancel }

	}
	private void save() {
		if(this.panel.isModified()) {
			if(this.file == null) {
				this.saveAs();
			} else {
				this.saveFile();
			}
		}

	}
	private void saveAs() {
		//save
		JFileChooser chooser = new JFileChooser(this.dir);
		chooser.setSelectedFile(this.file);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Drawing Graphic data", "dgd");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this.panel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.dir = chooser.getCurrentDirectory();
			this.file = chooser.getSelectedFile();
			this.filename = chooser.getSelectedFile().toString();

			this.saveFile();
		}
	}

	public void exitProgram() {
		if(!checkSaveOrNot()) {
			System.exit(0);
		}
	}
	
	public void print(){
		Component component;
		component = this.panel;
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setJobName("Print Component");
		pj.setPrintable(new Printable() {
			public int print(Graphics pg, PageFormat pf, int pageNum) {
				if(pageNum > 0) {
					return Printable.NO_SUCH_PAGE;
				}
				Graphics2D g2 = (Graphics2D) pg;
				g2.translate(pf.getImageableX(), pf.getImageableY());
				component.paint(g2);
				return Printable.PAGE_EXISTS;
			}
		});
		if(pj.printDialog()==false) {
			return;
		}
		try {
			pj.print();
		} catch (PrinterException e) {
			
		}
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenuItem eMenuItem = EFileMenuItem.valueOf(e.getActionCommand());
			switch(eMenuItem) {
			case eNew:
				nnew();
				break;
			case eOpen:
				open();	
				break;
			case eSave:
				save();
				break;
			case eSaveAs:
				saveAs();
				break;
			case ePrint:
				print();
				break;
			case eExit:
				exitProgram();
				break;
			default :
				break;
			}

		}

	
	}



}
