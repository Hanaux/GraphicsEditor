package frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JPanel;

import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;
import menu.GUndoStack;
import shapeTools.GShapeTool;
import shapeTools.GShapeTool.EAnchors;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GTransformer;

public class GPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private Vector<GShapeTool> shapes;
	private GMouseHandler mouseHandler;
	private GUndoStack undoStack;
	private Color color;
	Cursor cursor;
	
	// associations

	// working objects
	private GShapeTool shapeTool;
	private GShapeTool selectedShape;
	private GShapeTool temp;
	private GShapeTool tempCut;
	private GTransformer transformer;
	private boolean bModified;

	///////////////////////////////////////////////////////
	// getters and setters
	public Vector<GShapeTool> getShapes() {
		return this.shapes;
	}
	public void setShapes(Vector<GShapeTool> shapes) {
		this.shapes = shapes;
		this.repaint();
	}
	public void setSelection(GShapeTool shapeTool) {
		this.shapeTool = shapeTool;
	}
	public boolean isModified() {
		return this.bModified;
	}
	public void setModified(boolean bModified) {
		this.bModified = bModified;
	}
	public void setLineColor(Color color) {
		if(this.selectedShape!=null) {
			Graphics2D graphics = (Graphics2D)this.getGraphics();
			this.selectedShape.setLineColor(color);
			this.selectedShape.draw(graphics);
			this.shapes.add(selectedShape);
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}
	public void setFillColor(Color color) {
		Graphics2D graphics = (Graphics2D)this.getGraphics();
		if(this.selectedShape!=null) {
			this.selectedShape.setFillColor(color);
			this.selectedShape.draw(graphics);
			this.shapes.add(selectedShape);
			this.undoStack.push(this.deepCopy(this.shapes));

		}
	}
	public void cut() {
		if(this.selectedShape!=null) {
			this.tempCut = this.selectedShape;
			this.shapes.remove(this.selectedShape);
			this.undoStack.push(this.deepCopy(this.shapes));
			repaint();
		}
	}
	public void paste() {
		if(this.selectedShape!=null && this.tempCut!=null) {			
			this.shapes.add(this.tempCut);
			this.undoStack.push(this.deepCopy(this.shapes));
			setSelected(this.tempCut); // 추가한거!
			repaint();

		}
	}

	public void delete() {
		if(this.selectedShape!=null) {
			this.shapes.remove(this.selectedShape);
			this.undoStack.push(this.deepCopy(this.shapes));
			repaint();
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
	public void autoTheme() {
		setTheme("AUTO");
	}
	public void lightTheme() {
		setTheme("WHITE");
	}
	
	public void darkTheme() {
		setTheme("DARK");
	}
	public void setTheme(String theme) {
		try {
			File file = new File(".\\ theme.txt");
			FileWriter fw = new FileWriter(file, false);
			fw.write(theme);
			fw.flush();
			fw.close();
		} catch(Exception e) {
			e.printStackTrace();
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
	// constructors
	public GPanel() {
		getTheme();
		this.shapes = new Vector<GShapeTool>();
		this.setBackground(this.color);
		this.mouseHandler = new GMouseHandler();		
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.addMouseWheelListener(this.mouseHandler);
		this.bModified = false;
		
		this.undoStack = new GUndoStack();
	}
	public void initialize() {
		this.setBackground(this.color);
	}
	public void clearScreen() {
		this.shapes.clear();
		this.repaint();
	}
	// methods	
	public Vector<GShapeTool> deepCopy(Vector<GShapeTool> original) {
		@SuppressWarnings("unchecked")
		Vector<GShapeTool> clonedShapes = (Vector<GShapeTool>)this.shapes.clone();
		for(int i=0;i<this.shapes.size();i++) {
			clonedShapes.set(i, (GShapeTool) this.shapes.get(i).clone()); //(i).clone()
		}
		return clonedShapes;
	}
	
	public void undo() {
		if (this.shapes.size() >1 ) {
		this.shapes = this.undoStack.undo();
		this.repaint();		
		} 
		else if(this.shapes.size()==1){
			this.temp = shapes.lastElement();
			Vector<GShapeTool> tempVector = null;
			tempVector = this.shapes;
			tempVector.remove(temp);
			setShapes(tempVector);
			this.undoStack.deleteUndo();
			
		}
	}
	public void redo() {
		if(this.shapes.size()>0) {
		this.shapes =this.undoStack.redo(); // this.deepCopy(
		this.repaint();
		} else if (this.shapes.size()==0) {
			this.shapes.add(temp);
			this.undoStack.upperRedo();
			repaint();
		}
	}
	
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		for (GShapeTool shape: this.shapes) {
			shape.draw((Graphics2D)graphics);
		}
	}
	private void setSelected(GShapeTool selectedShape) {
		for (GShapeTool shape: this.shapes) {
			shape.setSelected(false);
		}
		if(selectedShape != null) {
		this.selectedShape = selectedShape;
		this.selectedShape.setSelected(true);
		}
		Graphics graphics = this.getGraphics();
		paint(graphics);

	}
	
	private GShapeTool onShape(int x, int y) {
		for (GShapeTool shape: this.shapes) {
			EAction eAction = shape.containes(x, y);
			if (eAction != null) {
				return shape;
			}
		}
		return null;
	}
	private void initDrawing(int x, int y) {
		this.selectedShape = (GShapeTool) this.shapeTool.clone();
		this.selectedShape.setInitPoint(x, y);
	}
	private void setIntermediatePoint(int x, int y) {
		this.selectedShape.setIntermediatePoint(x, y);
	}	
	private void keepDrawing(int x, int y) {
		// exclusive or mode
		Graphics2D graphics2D = (Graphics2D) getGraphics();
		if(this.selectedShape.getDrawingStyle()!=EDrawingStyle.e1PointDrawing) {
			graphics2D.setXORMode(getBackground());
		}
		// erase
		selectedShape.animate(graphics2D, x, y);
	}	
	private void finishDrawing(int x, int y) {
		this.selectedShape.setFinalPoint(x, y);
		this.shapes.add(this.selectedShape);
		this.bModified = true;
		
		this.undoStack.push(this.deepCopy(this.shapes));
	}
	
	private void initTransforming(GShapeTool selectedShape, int x, int y) {

		this.selectedShape = selectedShape;
		EAction eAction = this.selectedShape.getAction();
		switch(eAction) {
		case eMove :
			this.transformer = new GMover(this.selectedShape);
			break;
		case eResize :
			this.transformer = new GResizer(this.selectedShape);
			break;
		case eRotate :
			this.transformer = new GRotator(this.selectedShape);
			break;
		default :
			break;
		}
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.initTransforming(graphics2d, x, y);
	}
	private void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.keepTransforming(graphics2d, x, y);
		this.repaint();
	}
	private void finishTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.finishTransforming(graphics2d, x, y);
		this.bModified = true;
		this.setSelected(this.selectedShape);
//		shapes.add(selectedShape); //오류시 삭제
		this.undoStack.push(this.deepCopy(this.shapes));
		
	}
	///////////////////////////////////////////////////////	
	// inner classes
	private class GMouseHandler 
		implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

		private boolean isDrawing;
		private boolean isTransforming;
		public GMouseHandler() {
			this.isDrawing = false;
			this.isTransforming = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing||shapeTool.getDrawingStyle() == EDrawingStyle.e1PointDrawing) {
						setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
				} else {
					initTransforming(selectedShape, e.getX(), e.getY());
					this.isTransforming = true;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing || shapeTool.getDrawingStyle() == EDrawingStyle.e1PointDrawing) {
					keepDrawing(e.getX(), e.getY());
				} 
			} else if (this.isTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing || shapeTool.getDrawingStyle() == EDrawingStyle.e1PointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				}
			} else if (this.isTransforming) {
				finishTransforming(e.getX(), e.getY());
				this.isTransforming = false;
			}
		}
		
		public void ChangeCursor(int x, int y) {
			GShapeTool selectedShape = onShape(x,y);
			if(selectedShape==null) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));				
			} else {
				EAnchors eAnchors = selectedShape.getSelectedAnchor();
				if(eAnchors==null) {
					setCursor(new Cursor(Cursor.MOVE_CURSOR));
				} else {
					switch(eAnchors) {
					case x0y0 :
						setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
						break;
					case x0y1 :
						setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
						break;
					case x0y2 :
						setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
						break;
					case x1y0 :
						setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
						break;
					case x1y2 :
						setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
						break;
					case x2y0 :
						setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
						break;
					case x2y1 : 
						setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
						break;
					case x2y2 :
						setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
						break;
					case RR : 
						setCursor(new Cursor(Cursor.HAND_CURSOR));
						break;
					default :
						break;
					}
					
				}
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					keepDrawing(e.getX(), e.getY());
				} 
			} else {
				ChangeCursor(e.getX(), e.getY());
			}
		}
		private void mouseLButton1Clicked(MouseEvent e) {		
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
					setSelected(selectedShape);
				} else {
					setSelected(selectedShape);
				}
			} else {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					setIntermediatePoint(e.getX(), e.getY());
				}
			}			
		}
		
		private void mouseLButton2Clicked(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;		
				}
			}
		}		

		private void mouseRButton1Clicked(MouseEvent e) {
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.mouseLButton1Clicked(e);
				} else if (e.getClickCount() == 2) {
					this.mouseLButton2Clicked(e);
				}
			} else if (e.getButton() == MouseEvent.BUTTON2) {
				if (e.getClickCount() == 1) {
					this.mouseRButton1Clicked(e); //
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}		
	}






}
