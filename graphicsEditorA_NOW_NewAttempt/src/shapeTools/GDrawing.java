package shapeTools;

import java.awt.Shape;
import java.awt.geom.Line2D;

import main.GConstants.EDrawingStyle;

public class GDrawing extends GShapeTool {
	private static final long serialVersionUID = 1L;
	
	public GDrawing() {
		super(EDrawingStyle.e1PointDrawing);
		this.shape = new Line2D.Double();

	}
	@Override
	public Object clone() {
		GShapeTool cloned = null;
		cloned = (GShapeTool)super.clone();
		cloned.shape = (Shape)((Line2D.Double) this.shape).clone();
		return cloned;

	}
	// methods
	@Override
	public void setInitPoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x,y, x, y);
		
	}
	
	@Override
	public void setFinalPoint(int x, int y) {

	}	
	
	@Override
	public void movePoint(int x, int y) {
		Line2D line = (Line2D)this.shape;
		line.setLine(line.getX2(), line.getY2(), x, y);

	}

}
