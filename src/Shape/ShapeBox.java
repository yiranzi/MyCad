package Shape;

import java.awt.Graphics;

import Model.Data;

public class ShapeBox extends Shape {
	private int min;
	private int max;
	private boolean visible = false;
	public ShapeBox()
	{
		
	}
	
	public ShapeBox(boolean visible)
	{
		this.visible = visible;
	}
	//用于根据绘制点调整框体大小的
	public void SetPosition()
	{
		
	}
	
	public boolean IfCollision(ShapeBox box)
	{
		boolean isInside = false;
		this.fixPosition();
		box.fixPosition();
		if( Inside(this.startFinalX,this.startFinalY,box))return true;//左上
		if( Inside(this.endFinalX ,this.startFinalY,box))return true;//右上
		if( Inside(this.startFinalX ,this.endFinalY,box))return true;//左下
		if( Inside(this.endFinalX ,this.endFinalY,box))return true;//右下
		if( Inside(box.startFinalX,box.startFinalY,this))return true;//左上
		if( Inside(box.endFinalX ,box.startFinalY,this))return true;//右上
		if( Inside(box.startFinalX ,box.endFinalY,this))return true;//左下
		if( Inside(box.endFinalX ,box.endFinalY,this))return true;//右下
		return false;	
	}
	
	public boolean Inside(int x,int y,ShapeBox box)
	{
		boolean isInside = false;
		if(x>=box.startFinalX && x<=box.endFinalX && y>=box.startFinalY && y<=box.endFinalY)
		{
			isInside = true; 
		}
		return isInside;
	}
	
	public void SetBoxVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	@Override
	public void Draw(Graphics g) {
		// TODO Auto-generated method stub
		if(visible)
		{
			super.Draw(g);			
			g.drawRect(startFinalX, startFinalY, endFinalX - startFinalX, endFinalY - startFinalY);
		}
		

//		SetBoxStart(startX,startY);
//		SetBoxEnd(endX,endY);
//		if(boxVisible)
//		{
//			outBox.Draw(g, d);
//		}
	}
}
