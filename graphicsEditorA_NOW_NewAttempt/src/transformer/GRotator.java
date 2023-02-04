package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import shapeTools.GShapeTool;

public class GRotator extends GTransformer {

	public GRotator(GShapeTool selectedShape) {
		super(selectedShape);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public void transform(Graphics2D graphics2d, int x, int y) {
//		this.getShape().draw(graphics2d);
//		double rotationAngle = RotationAngle(this.center, this.previous, new Point2D.Double(x, y));
//		this.selectedShape.rotate(graphics2d,Math.toRadians(rotationAngle) ,center.getX(), center.getY());
//		this.getShape().draw(graphics2d);
//		this.previous.setLocation(x, y);
//
//	}
//	
//	private double RotationAngle(Point2D center, Point2D previous, Double current) {
//		double startAngle = Math.toDegrees(Math.atan2(center.getX()-previous.getX(), center.getY()-previous.getY()));
//		double endAngle = Math.toDegrees(Math.atan2(center.getX()-current.getX(), center.getY()-current.getY()));
//		double angle = startAngle-endAngle;
//		if(angle<0) {angle += 360;}
//		return angle;
//	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		this.selectedShape.initTransform(graphics2d, x, y);
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		this.selectedShape.rotate(graphics2d, new Point(px,py), new Point(x,y));
		this.px = x;
		this.py = y;
	}

}
