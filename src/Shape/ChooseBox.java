package Shape;

import java.awt.Color;
import java.awt.Graphics;

public class ChooseBox extends MyShape implements Cloneable{
	
	public ChooseBox(int x,int y)
	{
		this();
		startX = endX = x;
		startY = endY = y;
	}

	public ChooseBox() {
		// TODO Auto-generated constructor stub
		color = Color.BLACK;
		bold = 1;
	}

	public boolean IfCollision(MyShape box) {
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

	public boolean Inside(int x, int y, MyShape box) {
		boolean isInside = false;
		if(x>=box.startFinalX && x<=box.endFinalX && y>=box.startFinalY && y<=box.endFinalY)
		{
			isInside = true; 
		}
		return isInside;
	}

	@Override
	protected void DrawShape(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(startFinalX, startFinalY, endFinalX-startFinalX, endFinalY-startFinalY);
	}

	
	@Override
	public MyShape clone(){
		ChooseBox shapebox = null;  
        try {  
        	shapebox = (ChooseBox) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }          
        return shapebox;
	}
	
}
