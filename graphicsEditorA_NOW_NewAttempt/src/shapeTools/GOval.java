package shapeTools;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import main.GConstants.EDrawingStyle;

public class GOval extends GShapeTool {
	// attributes
	private static final long serialVersionUID = 1L;

	public GOval() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Ellipse2D.Float();
	}
	@Override
	public Object clone() {
		GShapeTool cloned = null;
		cloned = (GShapeTool)super.clone();
		cloned.shape = (Shape) ((Ellipse2D.Float) this.shape).clone();
		return cloned;
	}
	// methods
	@Override
	public void setInitPoint(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
	}
	@Override
	public void setFinalPoint(int x, int y) {
	}	
	
	@Override
	public void movePoint(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(
				ellipse.getX(), ellipse.getY(), x-ellipse.getX(), y-ellipse.getY());
	}

}
