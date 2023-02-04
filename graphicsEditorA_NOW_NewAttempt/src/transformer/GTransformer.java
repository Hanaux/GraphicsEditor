package transformer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

import shapeTools.GShapeTool;

public abstract class GTransformer {

	protected GShapeTool selectedShape;
//	protected Shape transShape;
	protected int px, py;
//	protected Point2D previous, center;
	public GTransformer(GShapeTool selectedShape) {
//		this.previous = new Point2D.Double();
//		this.center = new Point2D.Double();
		this.selectedShape = selectedShape;
	}
	
	public GShapeTool getShape() {return this.selectedShape;}
	
	abstract public void initTransforming(Graphics2D graphics2d, int x, int y); 
//	{
//		this.selectedShape.draw(graphics2d);
//		this.center.setLocation(this.selectedShape.getCenterX(), this.selectedShape.getCenterY());
//		this.previous.setLocation(x, y);
//		this.px = x;
//		this.py = y;
//	}

	abstract public void keepTransforming(Graphics2D graphics2d, int x, int y); 
//	{
//		this.selectedShape.draw(graphics2d);
//		this.transform(graphics2d, x, y);
//		this.px = x;
//		this.py = y;
//		this.selectedShape.draw(graphics2d);
//
//	}
	
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
//		this.selectedShape.draw(graphics2d);
		
	}
	public void continueTransforming(Graphics2D graphics2d, int x, int y) {
		
	}
	
//	abstract public void transform(Graphics2D graphics2d, int x, int y);
}