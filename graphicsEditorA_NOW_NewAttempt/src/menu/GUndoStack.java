package menu;

import java.util.Vector;

import shapeTools.GShapeTool;

public class GUndoStack { // 수정 필요함 자잘한거. 도형 하나 그리고 undo했을 때 문제라던지, if문 조건 문제라던지
	
	private static final int maxIndex = 10;
	private int bottomIndex;
	private int topIndex;
	private int size;
	
	private Vector<Vector<GShapeTool>> elements;
	
	
	public GUndoStack() {
		this.bottomIndex = 0; // 궅이 하자면 -1 
		this.topIndex = 0;
		this.size = 0;
		this.elements = new Vector<Vector<GShapeTool>>();
		
		for(int i = 0; i<maxIndex ; i++ ) {
			this.elements.add(new Vector<GShapeTool>());
		}
	}

	public void push(Vector<GShapeTool> shapes) {

		this.elements.set(this.topIndex%maxIndex, shapes);
		this.topIndex++;
		
		if((this.topIndex-this.bottomIndex)==maxIndex) {
			this.bottomIndex++;
		} else {
			this.size++;
		}
	}
	
	public Vector<GShapeTool> undo() {
		Vector<GShapeTool> undoElement = null;
		if(this.topIndex >= this.bottomIndex) {
			if(this.topIndex>1) {
				this.topIndex--;
				undoElement =  this.elements.get((this.topIndex%maxIndex)-1); 
			}
		} 
		
		return undoElement;
	}
	public void deleteUndo() {
		this.topIndex--;
	}
	public void upperRedo() {
		this.topIndex++;
	}
	public Vector<GShapeTool> redo() {
		Vector<GShapeTool> redoElement = null;
		
		if(this.topIndex - this.bottomIndex < this.size) { // -1 넣어봄

			redoElement = this.elements.get(this.topIndex%maxIndex);
			this.topIndex++;
	
		} else {
			redoElement = this.elements.get(this.topIndex%maxIndex-1);
		}
		
		return redoElement;
	}




}
