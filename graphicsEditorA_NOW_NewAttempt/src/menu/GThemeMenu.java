package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import frame.GPanel;
import main.GConstants;
import main.GConstants.EThemeMenuItem;

public class GThemeMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private GPanel panel;
	private JRadioButtonMenuItem[] radio;


	public GThemeMenu(String text, Color color) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
//		for (EThemeMenuItem eThemeItem: EThemeMenuItem.values()) {
//			JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(eThemeItem.getText());
//			menuItem.setActionCommand(eThemeItem.name());
//			menuItem.addActionListener(actionHandler);
//			this.add(menuItem);
//		}
		
		JMenuItem autoTheme = new JMenuItem(GConstants.EThemeMenuItem.eAuto.getText());
		autoTheme.setActionCommand(GConstants.EThemeMenuItem.eAuto.name());
		autoTheme.addActionListener(actionHandler);
//		autoTheme.setSelected(true);
		this.add(autoTheme);
		
		JMenuItem lightTheme = new JMenuItem(GConstants.EThemeMenuItem.eLightMode.getText());
		lightTheme.setActionCommand(GConstants.EThemeMenuItem.eLightMode.name());
		lightTheme.addActionListener(actionHandler);
		this.add(lightTheme);
		
		JMenuItem darkTheme = new JMenuItem(GConstants.EThemeMenuItem.eDarkMode.getText());
		darkTheme.setActionCommand(GConstants.EThemeMenuItem.eDarkMode.name());
		darkTheme.addActionListener(actionHandler);
		this.add(darkTheme);
		
		if(color==Color.WHITE) {
			this.setForeground(Color.BLACK);
		} else {
			this.setForeground(Color.white);
		}

	}
	public void setAssociation(GPanel panel) {
		this.panel = panel;		
	}
	public void autoSet() {
		int reply = JOptionPane.showConfirmDialog(this.panel, "테마 변경 시 재실행 하셔야 합니다. 저장하지 않은 내용은 사라집니다.", "주의!",JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.OK_OPTION) {
		this.panel.autoTheme();
		System.exit(0);
		}
	}
	public void lightSet() {
		int reply = JOptionPane.showConfirmDialog(this.panel, "테마 변경 시 재실행 하셔야 합니다. 저장하지 않은 내용은 사라집니다.", "주의!",JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.OK_OPTION) {
		this.panel.lightTheme();
		System.exit(0);
		}
		}
	public void darkSet() {
		int reply = JOptionPane.showConfirmDialog(this.panel, "테마 변경 시 재실행 하셔야 합니다. 저장하지 않은 내용은 사라집니다.", "주의!",JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.OK_OPTION) {
		this.panel.darkTheme();
		System.exit(0);
		}
	}

	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EThemeMenuItem eThemeItem = EThemeMenuItem.valueOf(e.getActionCommand());
			switch(eThemeItem) {
			case eAuto:
				autoSet();
				break;
			case eLightMode:
				lightSet();
				break;
			case eDarkMode:
				darkSet();
				break;
			default :
				break;
			}

		}

	}
}
