package frame;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JMenuBar;

import main.GConstants.EMenu;
import menu.GColorMenu;
import menu.GEditMenu;
import menu.GFileMenu;
import menu.GHelpMenu;
import menu.GThemeMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GColorMenu colorMenu;
	private GThemeMenu themeMenu;
	private GHelpMenu helpMenu;
	private Color color;
	
	public GMenuBar() {
		getTheme();
		this.setBackground(this.color);
		this.fileMenu = new GFileMenu(EMenu.eFile.getText(), this.color);
		this.add(this.fileMenu);
		this.fileMenu.setMnemonic(KeyEvent.VK_F);
		
		this.editMenu = new GEditMenu(EMenu.eEdit.getText(), this.color);
		this.add(this.editMenu);
		this.editMenu.setMnemonic(KeyEvent.VK_E);
		
		this.colorMenu = new GColorMenu(EMenu.eColor.getText(), this.color);
		this.add(this.colorMenu);
		this.colorMenu.setMnemonic(KeyEvent.VK_C);
		
		this.themeMenu = new GThemeMenu(EMenu.eTheme.getText(), this.color);
		this.add(this.themeMenu);
		this.themeMenu.setMnemonic(KeyEvent.VK_T);
		
		this.helpMenu = new GHelpMenu(EMenu.eHelp.getText(), this.color);
		this.add(this.helpMenu);
		this.helpMenu.setMnemonic(KeyEvent.VK_H);
		
	}

	public void setAssociation(GPanel panel) {
		this.fileMenu.setAssociation(panel);		
		this.editMenu.setAssociation(panel);
		this.colorMenu.setAssociation(panel);
		this.themeMenu.setAssociation(panel);
		this.helpMenu.setAssociation(panel);
		
	}
	public void autoThemeSet() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat now = new SimpleDateFormat("HH");
		int tm = Integer.parseInt(now.format(date));
		if(tm>=18 || tm<07) {
			this.color = new Color(40,40,40);
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
				this.color = new Color(40,40,40);
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
	
	public void windowClosing() {
		fileMenu.exitProgram();
	}
	

}
