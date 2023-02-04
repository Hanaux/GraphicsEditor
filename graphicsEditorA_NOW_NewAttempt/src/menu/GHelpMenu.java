package menu;


import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frame.GPanel;
import main.GConstants.EHelpMenuItem;


public class GHelpMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private GPanel panel;

	public GHelpMenu(String text, Color color) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EHelpMenuItem eColorItem: EHelpMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eColorItem.getText());
			menuItem.setActionCommand(eColorItem.name());
			menuItem.addActionListener(actionHandler);
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
	public void help() {
		File file = new File(".\\guidebook.pdf");
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		} 
		
	}
	public void version() {
		JOptionPane.showMessageDialog(this.panel,"버전정보 : \t GraphicsEditor_1.1v \n 제작자 : \t응용소프트웨어학과 18학번 한승헌 \n "
				+ "최근업데이트 : \t 2021.06.06" 
				, "Version_Informaion", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EHelpMenuItem eHelpItem = EHelpMenuItem.valueOf(e.getActionCommand());
			switch(eHelpItem) {
			case eHelp:
				help();
				break;
			case eVersion:
				version();
				break;
			default :
				break;
			}

		}

	}

}
