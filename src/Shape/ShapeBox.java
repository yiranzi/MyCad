package Shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class ShapeBox extends MyShape implements Cloneable{
	private boolean visible = false;
	public ShapeBox()
	{
		color = Color.BLACK;
		bold = 1;
	}
	
//	public ShapeBox(int x,int y)
//	{
//		startX = endX = x;
//		startY = endY = y;
//	}
//	

//	//用于根据绘制点调整框体大小的
//	public void SetPosition()
//	{
//		
//	}
	

	//碰撞体检测
	public boolean IfCollision(ShapeBox box)
	{	
		fixPosition();//修正坐标
		box.fixPosition();
		if( Inside(startFinalX,startFinalY,box))return true;//左上
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
	
	public boolean GetBoxVisible()
	{
		return visible;
	}

	@Override
	protected void fixPosition() {
		// TODO Auto-generated method stub
		System.out.println("shapebox的fixposition");
		super.fixPosition();
		//4象限
		if(endX<startX)
		{
			startFinalX = endX;
			endFinalX = startX;
		}
		if(endY<startY)
		{
			startFinalY = endY;
			endFinalY = startY;
		}
	}
 
	@Override
	protected void DrawShape(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(startFinalX, startFinalY, endFinalX-startFinalX, endFinalY-startFinalY);
	}
	
	

	@Override
	public void Draw(Graphics g) {
		if(visible)
		{
			super.Draw(g);
		}	
	}

	@Override
	public ShapeBox clone(){
		ShapeBox shapebox = null; 
//		shapebox = (ShapeBox) super.clone();
        try {  
        	shapebox = (ShapeBox) super.clone();  
        } 
        catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }          
        return shapebox;
	}




}
