package shapeTools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import main.GConstants;
import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;
import shapeTools.GShapeTool.EAnchors;

abstract public class GShapeTool implements Serializable, Cloneable {
	// attributes
	private static final long serialVersionUID = 1L;
	Cursor cursor;
	public enum EAnchors {
		x0y0,
		x0y1,
		x0y2,
		x1y0,
		x1y2,
		x2y0,
		x2y1,
		x2y2,
		RR
	}
	
	private EDrawingStyle eDrawingStyle;
	protected Shape shape;
	private Ellipse2D[] anchors;
	private boolean isSelected;
	private EAnchors selectedAnchor;
	private EAction eAction;
	private AffineTransform affineTranform;
	private Color lineColor, fillColor;
	Point2D  x0y0, x0y1,x0y2, x1y0, x1y2, x2y0, x2y1,x2y2;
	// working variables
	
	// constructors
	public GShapeTool(EDrawingStyle eDrawingState) {
		this.anchors = new Ellipse2D.Double[EAnchors.values().length];
		for (EAnchors eAnchor: EAnchors.values()) {
			this.anchors[eAnchor.ordinal()] = new Ellipse2D.Double();
		}
		this.isSelected = false;
		this.eDrawingStyle = eDrawingState;
		this.selectedAnchor = null;

		this.affineTranform = new AffineTransform();
		this.affineTranform.setToIdentity();
		getTheme();
	}	
	public void autoThemeSet() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat now = new SimpleDateFormat("HH");
		int tm = Integer.parseInt(now.format(date));
		if(tm>=18 || tm<07) {
			this.fillColor = Color.DARK_GRAY;
			this.lineColor = Color.WHITE;
		} else {
			this.fillColor = Color.WHITE;
			this.lineColor = Color.BLACK;
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
				this.fillColor = Color.white;
				this.lineColor = Color.black;
				break;
			case "DARK" :
				this.fillColor = Color.DARK_GRAY;
				this.lineColor = Color.WHITE;
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
	
	public Object clone() {
		GShapeTool cloned = null;
		try {
			cloned = (GShapeTool)super.clone();
			for (EAnchors eAnchor: EAnchors.values()) {
				cloned.anchors[eAnchor.ordinal()] = (Ellipse2D) this.anchors[eAnchor.ordinal()].clone();
			}
			cloned.affineTranform = (AffineTransform) this.affineTranform.clone();
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return cloned;
	}
	// getters & setters
	public EDrawingStyle getDrawingStyle() {
		return this.eDrawingStyle;
	}
	public void setLineColor(Color color) {
		this.lineColor = color;
	}
	public void setFillColor(Color color) {
		this.fillColor = color;
	}
	public EAction getAction() {
		return this.eAction;
	}
	// methods
	public EAction containes(int x, int y) {
		this.eAction = null;
		if (this.isSelected) {
			for (int i=0; i<this.anchors.length-1; i++) {
				if (this.affineTranform.createTransformedShape(this.anchors[i]).contains(x, y)) {
					this.selectedAnchor = EAnchors.values()[i];
					this.eAction = EAction.eResize;
				}
			}
			
	//		this.affineTranform.createTransformedShape(this.anchors[i]).contains(x, y)
			
			if (this.affineTranform.createTransformedShape(this.anchors[EAnchors.RR.ordinal()]).contains(x, y)) { // 얘도 AffineTransform으로 바꿔줘야하겟지? **********
				this.selectedAnchor = EAnchors.RR;
				this.eAction = EAction.eRotate;
			}
		} 
		
		if (this.affineTranform.createTransformedShape(this.shape).contains(x, y)) { // 여기를 바꿈. 물어볼 때는 Transform한 거로 물어봐야함. **********
			this.selectedAnchor = null;
			this.eAction = EAction.eMove;
		}
		return this.eAction;
	}
	public EAnchors getSelectedAnchor() {
		return this.selectedAnchor;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public void initTransform(Graphics2D graphics2d, int x, int y) {
		
	}

	public void move(Graphics2D graphics2d, double dx, double dy) {
		this.draw(graphics2d);
		this.affineTranform.translate(dx, dy);
		this.draw(graphics2d);
	}
	public void resize(Graphics2D graphics2d, Point pStart, Point pEnd) {
		graphics2d.setXORMode(fillColor);
		this.draw(graphics2d);
		Point2D resizeOrigin = getResizeAnchor();
		move(graphics2d, resizeOrigin.getX(), resizeOrigin.getY());
		Point2D resizeFactor = computeResizeFactor(pStart, pEnd);
		this.affineTranform.scale(resizeFactor.getX(), resizeFactor.getY()); // 수정 필. scale한 정도를 넣어야함. 
		move(graphics2d, -resizeOrigin.getX(), -resizeOrigin.getY());
		this.draw(graphics2d);
	}
	private Point getResizeAnchor() {
		Point resizeAnchor = new Point();
		resizeAnchor.setLocation(getAnchorList(getSelectedAnchor()));
		return resizeAnchor;
	}
	private Point2D computeResizeFactor(Point2D previousP, Point2D currentP) {
		double px = previousP.getX();
		double py = previousP.getY();
		double cx = currentP.getX();
		double cy = currentP.getY();
		double deltaW = 0;
		double deltaH = 0;
		switch(getSelectedAnchor()) {
			case x2y1 :
				deltaW = cx-px;
				deltaH = 0;
				break;
			case x0y1 :
				deltaW = -(cx - px);
				deltaH = 0;
				break;
			case x1y2 :
				deltaW = 0;
				deltaH = cy - py;
				break;
			case x1y0 :
				deltaW = 0;
				deltaH = -(cy - py);
				break;
			case x2y2 :
				deltaW = cx - px;
				deltaH = cy - py;
				break;
			case x2y0 :
				deltaW = cx - px;
				deltaH = -(cy - py);
				break;
			case x0y2 :
				deltaW = -(cx - px);
				deltaH = cy - py;
				break;
			case x0y0 :
				deltaW = -(cx - px);
				deltaH = -(cy - py);
				break;
			default:
				break;
		}
		
		double currentW = getWidth();
		double currentH = getHeight();
		double xFactor = 0.0;
		if (currentW > 1.0)
			xFactor = (1.0 + deltaW / currentW);
		double yFactor = 0.0;
		if (currentH > 1.0)
			yFactor = (1.0 + deltaH / currentH);
		return new Point.Double(xFactor, yFactor);
	}
	
	
	public void rotate(Graphics2D graphics2d, Point pStart, Point pEnd) {//rotate하고 move시켜야함,2개의 matrix가 필요할수도있음
		this.draw(graphics2d);
		double centerX = this.shape.getBounds().getCenterX();
		double centerY = this.shape.getBounds().getCenterY();
		
		double startAngle = Math.toDegrees(
				Math.atan2(centerX-pStart.x, centerY-pStart.y));
		double endAngle = Math.toDegrees(
				Math.atan2(centerX-pEnd.x, centerY-pEnd.y));
		double rotationangle = startAngle-endAngle;
		if(rotationangle<0) {
			rotationangle += 360;
			}
		
		this.affineTranform.rotate(Math.toRadians(rotationangle), centerX, centerY);
		this.draw(graphics2d);
	}

	
	public void drawAnchors(Graphics2D graphics) {
		int wAnchor = GConstants.wAnchor;
		int hAnchor = GConstants.hAnchor;
		
		Rectangle rectangle = this.shape.getBounds();
		int x0 = rectangle.x - wAnchor/2;
		int x1 = rectangle.x - wAnchor/2 + (rectangle.width)/2;
		int x2 = rectangle.x - wAnchor/2 + rectangle.width;
		int y0 = rectangle.y - hAnchor/2;
		int y1 = rectangle.y - hAnchor/2 + (rectangle.height)/2;
		int y2 = rectangle.y - hAnchor/2 + rectangle.height;
		
		this.x0y0 = new Point();
		this.x0y1 = new Point();
		this.x0y2 = new Point();
		this.x1y0 = new Point();
		this.x1y2 = new Point();
		this.x2y0 = new Point();
		this.x2y1 = new Point();
		this.x2y2 = new Point();
		
		setAnchorList(x0,x1,x2,y0,y1,y2);
		
		this.anchors[EAnchors.x0y0.ordinal()].setFrame(x0, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x0y1.ordinal()].setFrame(x0, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.x0y2.ordinal()].setFrame(x0, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.x1y0.ordinal()].setFrame(x1, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x1y2.ordinal()].setFrame(x1, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y0.ordinal()].setFrame(x2, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y1.ordinal()].setFrame(x2, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y2.ordinal()].setFrame(x2, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.RR.ordinal()].setFrame(x1, y0-40, wAnchor, hAnchor);
		
		for (EAnchors eAnchor: EAnchors.values()) {
//			Color color = graphics.getColor();
			graphics.setColor(Color.WHITE);
			graphics.fill(this.affineTranform.createTransformedShape(this.anchors[eAnchor.ordinal()]));
			graphics.setColor(Color.black);
			graphics.draw(this.affineTranform.createTransformedShape(this.anchors[eAnchor.ordinal()]));

		
		}
	}
	public void setAnchorList(int x0,int x1,int x2,int y0,int y1,int y2) {

		this.x0y0.setLocation(x0,y0);
		this.x0y1.setLocation(x0,y1);
		this.x0y2.setLocation(x0,y2);
		this.x1y0.setLocation(x1,y0);
		this.x1y2.setLocation(x1,y2);
		this.x2y0.setLocation(x2,y0);
		this.x2y1.setLocation(x2,y1);
		this.x2y2.setLocation(x2,y2);		
		
	}
	public void draw(Graphics2D graphics) {

		if(this.fillColor!=null) {
			graphics.setColor(fillColor);
			graphics.fill(this.affineTranform.createTransformedShape(this.shape));
		}
		graphics.setColor(this.lineColor);
		graphics.draw(this.affineTranform.createTransformedShape(this.shape));
		if (isSelected) {
			this.drawAnchors(graphics);
		}	
	}

	public Shape getShape() {
		return this.affineTranform.createTransformedShape(this.shape);
	}

	public void animate(Graphics2D graphics2d, int x, int y) {
		this.draw(graphics2d);
		this.movePoint(x, y);
		if(eDrawingStyle!=EDrawingStyle.e1PointDrawing) {

			this.draw(graphics2d);
		} 
	}
	public double getWidth() {
		return this.shape.getBounds2D().getWidth();
	}
	public double getHeight() {
		return this.shape.getBounds2D().getHeight();
	}
//	public double getCenterX() {
//		return this.shape.getBounds2D().getCenterX();
//	}
//	public double getCenterY() {
//		return this.shape.getBounds2D().getCenterY();
//	}
	public Point2D getAnchorList(EAnchors point) {
		Point2D p = null;
		
		if(point == EAnchors.x0y0) {
			p = x2y2;
		} else if (point == EAnchors.x0y1) {
			p = x2y1;
		} else if (point == EAnchors.x0y2) {
			p = x2y0;
		} else if (point == EAnchors.x1y0) {
			p = x1y2;
		} else if (point == EAnchors.x1y2) {
			p = x1y0;
		} else if (point == EAnchors.x2y0) {
			p = x0y2;
		} else if (point == EAnchors.x2y1) {
			p = x0y1;
		} else if (point == EAnchors.x2y2) {
			p = x0y0;
		}
		
		return p;
	}
	// interface
	public abstract void setInitPoint(int x, int y);
	public void setIntermediatePoint(int x, int y) {}
	public abstract void setFinalPoint(int x, int y);	
	public abstract void movePoint(int x, int y);

}
