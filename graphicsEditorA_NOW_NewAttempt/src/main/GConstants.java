package main;

import java.awt.Dimension;
import java.awt.Point;

import shapeTools.GOval;
import shapeTools.GRectangle;
import shapeTools.GPolygon;
import shapeTools.GLine;
import shapeTools.GDrawing;
import shapeTools.GShapeTool;

public class GConstants {
	public static class CFrame {
		public final static Point point = new Point(200, 300);
		public final static Dimension dimesion = new Dimension(400, 600);
		public final static String title = "Drawing_Tool";
	}
	
	public enum EDrawingStyle {
		e2PointDrawing,
		eNPointDrawing,
		e1PointDrawing
	};
	
	public static final int wAnchor = 10;	
	public static final int hAnchor = 10;
	
	public enum EAction {
		eDraw,
		eMove,
		eResize,
		eRotate,
		eShear
	}
	
	public enum EShapeTool {
		
		eRectangle(new GRectangle(),"images/rect/original.png","images/rect/pressed.png"), // "eRectangle"
		eOval(new GOval(), "images/oval/original.png","images/oval/pressed.png"),
		eLine(new GLine(), "images/line/original.png","images/line/pressed.png"),
		ePolygon(new GPolygon(), "images/poly/original.png","images/poly/pressed.png"),
		eDrawing(new GDrawing(), "images/draw/original.png","images/draw/pressed.png");
		
		private GShapeTool shapeTool;
		private String original;
		private String pressed;

		private EShapeTool(GShapeTool shapeTool, String original, String pressed) {
			this.shapeTool = shapeTool;
			this.original = original;
			this.pressed = pressed;
		}
		public GShapeTool getShapeTool() {
			return this.shapeTool;
		}
		public String getOriginal() {
			return this.original;
		}
		public String getPressed() {
			return this.pressed;
		}
	}
	
	public enum EMenu {
		eFile("File (F)"),
		eEdit("Edit (E)"),
		eColor("Color (C)"),
		eTheme("Theme (T)"),
		eHelp("Help (H)");		
		private String text;
		private EMenu(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EFileMenuItem {
		eNew("New", 'N'),
		eOpen("Open", 'O'),
		eSave("Save", 'S'),
		eSaveAs("SaveAs", 'D'),
		ePrint("Print", 'P'),
		eExit("Exit", 'Q');
		
		private String text;
		private char hotkey;
		private EFileMenuItem(String text, char hotkey) {
			this.text = text;
			this.hotkey = hotkey;
		}
		public String getText() {
			return this.text;
		}
		public char getKey() {
			return this.hotkey;
		}
	}
	
	public enum EEditMenuItem {
		eUndo("Undo", 'Z'),
		eRedo("Redo", 'R'),
		eGroup("Group", 'G'),
		eUnGroup("Ungroup", 'U'),
		eCut("Cut", 'X'),
		ePaste("Paste", 'V'),
		eDelete("Delete", 'E');
		
		private String text;
		private char hotkey;
		private EEditMenuItem(String text, char hotkey) {
			this.text = text;
			this.hotkey = hotkey;
		}
		public String getText() {
			return this.text;
		}
		public char getKey() {
			return this.hotkey;
		}
	}
	
	public enum EColorMenuItem {
		eLine("Line", 'L'),
		eFill("Fill", 'F'),;
		
		private String text;
		private char hotkey;
		private EColorMenuItem(String text, char hotkey) {
			this.text = text;
			this.hotkey = hotkey;
		}
		public String getText() {
			return this.text;
		}
		public char getKey() {
			return this.hotkey;
		}
	}
	
	public enum EThemeMenuItem {
		eAuto("Auto"),
		eLightMode("LightMode"),
		eDarkMode("DarkMode");
		
		private String text;
		private EThemeMenuItem(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EHelpMenuItem {
		eHelp("Help"),
		eVersion("Info");
		
		private String text;
		private EHelpMenuItem(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
}
