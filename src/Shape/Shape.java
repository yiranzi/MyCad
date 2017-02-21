package Shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import Model.Data;

//图形类有一个共同特征,他们都实现了draw,move,changeFont接口.

//这是一个abstract函数
public class Shape implements DrawMe,Cloneable{
	//private Model data = new Model();不需要知道model的存在.被动接受就可以了
	//再点击画布后,实例化一个特定的shape
	//首先构造好.并且保存这些数据到model中
	
	//这些设置函数,被接受到操作的view层数据所更新使用
	//设置起点坐标
	protected int startX;
	protected int startY;
	protected int endX;
	protected int endY;
	protected int startFinalX;
	protected int startFinalY;
	protected int endFinalX;
	protected int endFinalY;
	//protected int origiX;//移动原始点
	//protected int origiY;
	protected int moveX;//移动的距离
	protected int moveY;
	protected String txt;
	
	//粗细
	protected float bold;
	protected Color color;
	//修正数值的
	protected void fixPosition()
	{
		startFinalX = startX;
		startFinalY = startY;
		endFinalX = endX;
		endFinalY = endY;
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
	public Shape() {
		// TODO Auto-generated constructor stub
		System.out.println("shape new");
	}
	public void setMoveOrig()
	{
		this.moveX = 0;
		this.moveY = 0;
	}
	//移动的函数
	public void move(int dX,int dY)
	{
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
	}
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
		
	//执行draw的接口
	@Override
	public void Draw(Graphics g) {
		//这里面天上颜色,设置上粗细等通用方法.
		fixPosition();
		g.setColor(color);
		((Graphics2D)g).setStroke(new BasicStroke(bold));
	}
	
	@Override
	public Shape clone() {  
		Shape shape = null;  
        try {  
        	shape = (Shape) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }          
        return shape;  
    }  	
}

//不需要生成其实.直接去设置就好了
//public Shape(float x,float y,int type)
//{
//	//data.SetStartPoint(x,y);
//}
//这个和end更新是一样的,暂时不需要
//public void move()
//{
//	
//}
