package shapeTools;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;

import main.GConstants.EDrawingStyle;

public class GLine extends GShapeTool {

	private static final long serialVersionUID = 1L;
	//component
	//Shape¿ª implements «‘.
	
	//constructors
	public GLine() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Line2D.Double();
	}
	@Override
	public Object clone() {
		GShapeTool cloned = null;
		cloned = (GShapeTool)super.clone();
		cloned.shape = (Shape)((Line2D.Double) this.shape).clone();
		return cloned;
	}
	
	//getter & setters

	
	//method


	@Override
	public void setInitPoint(int x, int y) {
		// TODO Auto-generated method stub
		Line2D line = (Line2D) this.shape;
		line.setLine(x, y, x, y);

	}
	@Override
	public void setFinalPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movePoint(int x, int y) {
		// TODO Auto-generated method stub
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}




}
