package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import shapeTools.GShapeTool;

public class GResizer extends GTransformer {

	public GResizer(GShapeTool selectedShape) {
		super(selectedShape);
		// TODO Auto-generated constructor stub
	}

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
		this.selectedShape.resize(graphics2d, new Point(px, py), new Point(x,y));
		this.px = x;
		this.py = y;
		
	}
	
	

}
