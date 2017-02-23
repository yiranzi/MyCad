package Shape;

import java.awt.Graphics;
public class ShapeBox extends MyShape implements Cloneable{
	private boolean visible = false;
	public ShapeBox()
	{
		
	}
	
	public ShapeBox(int x,int y)
	{
		startX = endX = x;
		startY = endY = y;
	}
//	
	public ShapeBox(boolean visible)
	{
		this.visible = visible;
	}
//	//用于根据绘制点调整框体大小的
//	public void SetPosition()
//	{
//		
//	}
	

	//碰撞体检测
	public boolean IfCollision(ShapeBox box)
	{	
		if( Inside(startX,startY,box))return true;//左上
		if( Inside(this.endX ,this.startY,box))return true;//右上
		if( Inside(this.startX ,this.endY,box))return true;//左下
		if( Inside(this.endX ,this.endY,box))return true;//右下
		if( Inside(box.startX,box.startY,this))return true;//左上
		if( Inside(box.endX ,box.startY,this))return true;//右上
		if( Inside(box.startX ,box.endY,this))return true;//左下
		if( Inside(box.endX ,box.endY,this))return true;//右下
		return false;	
	}
	
	public boolean Inside(int x,int y,ShapeBox box)
	{
		boolean isInside = false;
		if(x>=box.startX && x<=box.endX && y>=box.startY && y<=box.endY)
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

//	//父类给他设置的地方
//	public void SetPoSAndSize(int x, int y, int width, int height) {
//		// TODO Auto-generated method stub
//		if(visible)
//		{
//			this.startX = x;
//			this.startY = y;
//			this.endX = 		
//		}
//	}

	@Override
	protected void fixPosition() {
		// TODO Auto-generated method stub
		super.fixPosition();
		startFinalX = Math.abs(startFinalX);
		startFinalY = Math.abs(startFinalY);
		endFinalX = Math.abs(endFinalX);
		endFinalY = Math.abs(endFinalY);
	}
 
	@Override
	protected void DrawShape(Graphics g) {
		// TODO Auto-generated method stub
		if(visible)
		{
			g.drawRect(startFinalX, startFinalY, endFinalX-startFinalX, endFinalY-startFinalY);
		}
	}

	@Override
	public ShapeBox clone(){
		ShapeBox shapebox = null;  
        try {  
        	shapebox = (ShapeBox) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }          
        return shapebox;
	}


}
