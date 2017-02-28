package Shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

//使用的时候
//1默认必须调用父类的draw方法.
//2需要重写drawshape方法
//3drawatt 和 fix 如果不使用可以覆盖掉
//4默认所有shape都需要有drawbox,如果不需要外边框的绘制,可以覆盖掉
//5现在把这个放在了必须重写的drawshape中.因为也有一定相关性
//5drawbox需要根据情况重写

public class ShapeInside extends MyShape implements Cloneable{
	private ShapeBox outsideBox; 
	
	protected int moveX;//移动的距离
	protected int moveY;
	
	private boolean isDrug = true;
	
	protected String txt;
	
	public ShapeInside()
	{
		
	}
	//构造的时候,告知这种形状是否通过拖拽绘制
	public ShapeInside(boolean isDrug)
	{
		this.isDrug = isDrug;
	}
	
	//初始化的时候,将outsideBox也初始化
	@Override
	public void SetStart(int x, int y) {
		// TODO Auto-generated method stub
		super.SetStart(x, y);
		outsideBox.SetStart(x, y);
	}
	
	@Override
	public void SetEnd(int x, int y) {
		// TODO Auto-generated method stub
		super.SetEnd(x, y);
		outsideBox.SetEnd(x, y);
	}
	
	//重写这两个方法,完成外边框设置
	public void SetStartDiff(int x, int y,int width,int height) {
		// TODO Auto-generated method stub
		super.SetStart(x, y);
		outsideBox.SetStart(x, y - height);
	}
	
	public void SetEndDiff(int x, int y,int width,int height) {
		// TODO Auto-generated method stub
		super.SetEnd(x, y);
		outsideBox.SetEnd(x+width, y);
	}
	

	

	//当移动这个物体的时候.重置他的坐标
	public final void setMoveOrig()
	{
		this.moveX = 0;
		this.moveY = 0;
	}
	
	//移动的函数
	public final void move(int dX,int dY)
	{
		int x = moveX;
		int y = moveY;
		while(moveX !=dX)
		{
			if(moveX>dX)
			{
				startX--;
				endX--;
				moveX--;
			}
			else 
			{
				startX++;
				endX++;
				moveX++;
			}
		}
		while( moveY !=dY)
		{
			if(moveY>dY)
			{
				startY--;
				endY--;
				moveY--;
			}
			else 
			{
				startY++;
				endY++;
				moveY++;
			}
		}
		x = moveX - x;
		y = moveY - y;
		outsideBox.SetStart(outsideBox.startX + x, outsideBox.startY + y);
		outsideBox.SetEnd(outsideBox.endX + x, outsideBox.endY + y);
	}
	
	//设置颜色
	public void SetColor(Color color)
	{
		this.color = color;
	}
	
	//设置粗细
	public void SetBold(float bold)
	{
		this.bold = bold;
	}
	
	//设置String
	public void SetString(String txt)
	{
		this.txt = txt;
	}
	
	public boolean GetIsDrug() {
		// TODO Auto-generated method stub
		return isDrug;
	}
	@Override
	public ShapeInside clone(){
		ShapeInside shapeinside = null; 
//		shapeinside = (ShapeInside) super.clone(); 
        try {  
        	shapeinside = (ShapeInside) super.clone();  
        } 
        catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }          
        return shapeinside;
	}
	
	@Override
	protected void DrawShape(Graphics g) {
		// TODO Auto-generated method stub
		
	}  	
	
	//如果能实现深拷贝,这个就不需要了,就可以自动拷贝,但是把地址get给外面进行保存.
	public void SetBox(ShapeBox s)
	{
		this.outsideBox = s;
	}	
}
