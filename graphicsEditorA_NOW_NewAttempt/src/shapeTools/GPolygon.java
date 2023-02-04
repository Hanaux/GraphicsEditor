package shapeTools;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;

import main.GConstants.EDrawingStyle;

public class GPolygon extends GShapeTool {
	private static final long serialVersionUID = 1L;

	public GPolygon() {
		super(EDrawingStyle.eNPointDrawing);
		this.shape = new Polygon();
	}
	@Override
	public Object clone() {
		GShapeTool cloned = null;
		cloned = (GShapeTool)super.clone();
		Polygon polygon = new Polygon();
		//for 루핑 돌리면서 좌표를 일일히 옮겨줘야함. this.shape => polygon value copy.  array로 구성됨
			for(int i=0;i<polygon.npoints;i++) {
				((Polygon)this.shape).addPoint(polygon.xpoints[i], polygon.ypoints[i]);
			} 

		cloned.shape = (Shape)(this.shape);
		return cloned;
	}
	@Override
	public void setInitPoint(int x, int y) {
		this.shape = new Polygon();
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
		polygon.addPoint(x, y);
	}
	public void setIntermediatePoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
	}
	@Override
	public void setFinalPoint(int x, int y) {
	}
	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}

}
