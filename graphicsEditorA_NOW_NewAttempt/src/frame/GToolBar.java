package frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import main.GConstants.EShapeTool;

public class GToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = 1L;	

	// associations
	private GPanel panel;  
	private Color color;
	
	public GToolBar() {
		// initialize components
		getTheme();
		ActionHandler actionHandler = new ActionHandler();
		this.setBackground(this.color);
		for (EShapeTool eButton: EShapeTool.values()) {
			ImageIcon original = new ImageIcon(eButton.getOriginal());
			ImageIcon pressed = new ImageIcon(eButton.getPressed());
			JButton button = new JButton();
			button.setBorderPainted(false);

			if(this.color==Color.white) {
				button.setBackground(Color.WHITE);	
				button.setIcon(original);
				button.setPressedIcon(pressed);
			} else {
				button.setBackground(Color.DARK_GRAY);
				button.setIcon(pressed);
				button.setPressedIcon(original);
			}
		
			button.setActionCommand(eButton.toString());
			button.addActionListener(actionHandler);
			this.add(button);
		}
	}
	public void autoThemeSet() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat now = new SimpleDateFormat("HH");
		int tm = Integer.parseInt(now.format(date));
		if(tm>=18 || tm<07) {
			this.color = Color.DARK_GRAY;
		} else {
			this.color = Color.WHITE;
		}
	}
	public void getTheme() {
		try {
			File file = new File(".\\ theme.txt");
			String theme = "";
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				theme = scan.nextLine();
			}
			switch(theme) {
			case "WHITE" :
				this.color = Color.white;
				break;
			case "DARK" :
				this.color = Color.DARK_GRAY;
				break;
			case "AUTO" :
				autoThemeSet();
				break;
			default :
				break;
			}
		}catch (FileNotFoundException e) {
			
		}
	}
	public void initialize() {
		((JButton)(this.getComponent(EShapeTool.eRectangle.ordinal()))).doClick();
	}
	
	public void setAssociation(GPanel panel) {
		this.panel = panel;		
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			EShapeTool eShapeTool = EShapeTool.valueOf(event.getActionCommand());

			panel.setSelection(eShapeTool.getShapeTool());
		}		
	}




}
