package Shape;

import java.awt.Graphics;

//我们在从子类的共同需求构造组合父类
public abstract class MyShape implements DrawMe {
	protected int startFinalX;
	protected int startFinalY;
	protected int endFinalX;
	protected int endFinalY;
	protected int startX;
	protected int startY;
	protected int endX;
	protected int endY;
	
	public void SetStart(int x,int y)
	{
		startX = x;
		startY = y;
	}
	
	
	//设置终点坐标
	public void SetEnd(int x,int y)
	{
		endX = x;
		endY = y;
	}
	
	@Override
	public void Draw(Graphics g) {
		fixPosition();//修正坐标
		DrawShape(g);//绘制	
	}
	
	protected void fixPosition()
	{
		startFinalX = startX;
		startFinalY = startY;
		endFinalX = endX;
		endFinalY = endY;	
	}
	
	//子类要重写
	protected abstract void DrawShape(Graphics g);
	
//	@Override
//	public ShapeInside clone(){  
//		ShapeInside shapeinside = null;  
//        try {  
//        	shapeinside = (ShapeInside) super.clone();  
//        } catch (CloneNotSupportedException e) {  
//            e.printStackTrace();  
//        }          
//        return shapeinside;  
//    }

}
