//数据类需要接受数据,并储存这些数据,并且在合适的时间,要求重新绘制.
//他需要一些基本的功能和变量.
//比如,起始点,终点,粗细,颜色,位置坐标(和起始点类似其实)
package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import CADMain.CAD;
import Mouse.MouseEventData;
import Shape.DrawMe;
import Shape.Shape;
import Shape.ShapeBox;
import Shape.ShapeInside;
import View.View;

import javax.swing.JButton;

public class Model {
	// 保存绘制数据的容器
	private HashMap<Shape, Shape> draws = new HashMap<Shape, Shape>();
	//临时选中的元素
	private HashMap<Shape, Shape> choose = new HashMap<Shape, Shape>();
	
	private Shape current;// 当前选中的对象
	private Data saveData;// 临时保存的数据信息.作为中转.new的时候再去传过去.
	private View view;
	private HashMap<String, Shape> hashShape = new HashMap<String, Shape>();
	
	// 用来保存在这里写好,统一设置的listener.
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

	//private DrawMe drawing;
	private String commandType="todraw";// 0为选择1为画
	private String nowDrawWhat = "drawline";// 用户当前点击的是什么画图功能.
	
	private BufferData bufferData;
	private ArrayList<Shape> chooseBoxs = new ArrayList<Shape>();
	private int mayMove = 0;//检测移动的变量
	private Frame frame;
	
	// 之前做的内部类..现在直接拿到外面去了.因为没觉得内部类描述这个有啥用.
	// 还是拿回来,做缓存的内部类使用应该不错.用来保存所有可能的设置属性.
	class BufferData {
		private int startX;
		private int startY;
		private int endX;
		private int endY;
		private String txt ="qqq";
		private float bold = 1;
		private Color color = Color.black;
	}

	//设置view
	public void SetView(View view) {
		this.view = view;
	}

	public Model() {
		//saveData = new Data();// 初始化savedata缓存
		bufferData = new BufferData();
		SetDrawFunc();// 重写不同shape的绘制方法	
		SettingButtonLietener();// 设置按钮的监听器方法写进hash
		frame = CAD.GetFrame();
		
	}
	
	//给按钮添加事件;
	public void Init(ArrayList<JButton> settingButtons, ArrayList<JButton> drawButtons)
	{
		DrawButtonLietener(drawButtons);//添加绘制按钮
		AddSettingButton(settingButtons);//添加设置按钮
	}

	// 设置绘制方法的地方
	//!!!这个命名和cad中的要一致!!!
	private void SetDrawFunc() {
		// 这个内部类也可以直接从类里面拿数据.
		// 我在这里做了一个shape的子匿名类.他会在开始的时候运行一次,返回一个shape.并重写他的方法.并且保存在hash中
		hashShape.put("drawrect", new ShapeInside() {

			@Override
			public void Draw(Graphics g) {
				// 弃用了之前直接用缓存设局设置的方式.改成了调用shape传参数的方法
				// 但是我现在就是在函数内部啊,其实是说的通的
				// g.drawString(d.txt, d.posX, d.posY);
				super.Draw(g);
				//fixPosition();
				g.drawRect(startFinalX, startFinalY, endFinalX - startFinalX, endFinalY - startFinalY);
				}

		});
		
		hashShape.put("drawrectbox", new ShapeBox() {		
			@Override
			public void Draw(Graphics g) {
				//这个图形不需要绘制外边框box
			}
		});
		
		hashShape.put("drawstring", new ShapeInside() {
			@Override
			protected void fixPosition() {
				// TODO Auto-generated method stub
				startFinalX = startX;
				startFinalY = startY;
				endFinalX = endX;
				endFinalY = endY;
			}
			@Override
			public void Draw(Graphics g) {
				// 弃用了之前直接用缓存设局设置的方式.改成了调用shape传参数的方法
				// 但是我现在就是在函数内部啊,其实是说的通的
				// g.drawString(d.txt, d.posX, d.posY);
				super.Draw(g);
				g.drawString(txt, endFinalX, endFinalY);
			}

		});
		
		hashShape.put("drawstringbox", new ShapeBox() {
			@Override
			protected void fixPosition() {
				// TODO Auto-generated method stub
				startFinalX = startX;
				startFinalY = startY;
				endFinalX = endX;
				endFinalY = endY;
			}
			
			@Override
			public void Draw(Graphics g) {
				
				if(GetBoxVisible())
				{
					super.Draw(g);			
					g.drawRect(endFinalX, endFinalY, 100, 10);
				}
			}
		});

		hashShape.put("drawline", new ShapeInside() {
			@Override
			protected void fixPosition() {
				// TODO Auto-generated method stub
				startFinalX = startX;
				startFinalY = startY;
				endFinalX = endX;
				endFinalY = endY;
				if(endX<startX)
				{
					startFinalX = endX;
					endFinalX = startX;
					startFinalY = endY;
					endFinalY = startY;
				}
				if(endY<startY)
				{
					
				}
			}
			@Override
			public void Draw(Graphics g) {
				// TODO Auto-generated method stub
				super.Draw(g);
				g.drawLine(startFinalX, startFinalY, endFinalX, endFinalY);
			}

		});
		
		hashShape.put("drawlinebox", new ShapeBox() {
			@Override
			public void Draw(Graphics g) {
				super.Draw(g);
			}
		});
		
		hashShape.put("drawcircle", new ShapeInside() {
	
			
			@Override
			public void Draw(Graphics g) {
				super.Draw(g);
				//fixPosition();
				g.drawOval(startFinalX, startFinalY, endFinalX - startFinalX, endFinalY - startFinalY);
			}
			
		});
		
		hashShape.put("drawcirclebox", new ShapeBox() {
			@Override
			public void Draw(Graphics g) {
				super.Draw(g);
			}
		});
		
		hashShape.put("drawbox", new ShapeBox(true) {
			
			
			@Override
			public void Draw(Graphics g) {
				// TODO Auto-generated method stub
				super.Draw(g);
				//g.drawRect(startFinalX, startFinalY, endFinalX - startFinalX, endFinalY - startFinalY);
			}

		});
	}
	//绘图说明
	//drawshape的父类中有设置属性.
	//使用终点坐标前的调节位置fixPosition
	//box中在visible方法中有super.draw和一个常规的矩形框绘制.
	
	
	// 给绘图的窗体添加上Listener
	private void DrawButtonLietener(ArrayList<JButton> shapeChoose) {
	
		for (int i = 0; i < shapeChoose.size(); i++) {
			JButton button = shapeChoose.get(i);
			button.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					nowDrawWhat = button.getName();
					//nowDrawWhat = (hashShape.get(buttonName)).clone();
					SetCommandType(nowDrawWhat);
					UpdataView();
				}
			});
		}
	}
	
	

	// 这个顺序,和按钮顺序相同

	// 设置属性的listener编辑
	private void SettingButtonLietener() {
		listeners.add(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bufferData.txt = "1111";
				UpdataView();
			}
		});
	
		listeners.add(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bufferData.txt = "2222222222222222";
				UpdataView();
			}
		});
	
		listeners.add(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bufferData.bold++;
				SetAttribute();
				UpdataView();
				//1更新缓存的粗细数值
				//2提交缓存数据的修改
				//3绘制
			}
		});
		
		listeners.add(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bufferData.bold>2)
				{
					bufferData.bold--;
				}			
				SetAttribute();
				UpdataView();
				//1更新缓存的粗细数值
				//2提交缓存数据的修改
				//3绘制
			}
		});
		
		listeners.add(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("123123");
				 Color chooseColor = JColorChooser.showDialog(frame,
		                    "Choose background color", Color.white);
		                 if(chooseColor != null)
		                 {
		                	 	bufferData.color = chooseColor;
		                 }
		                 SetAttribute();
		                 UpdataView();
		                 
			}
		});
		

		
	
	}
	
	// 给设置属性的按钮添加上listener
	private void AddSettingButton(ArrayList<JButton> shapeChoose) {
		for (int i = 0; i < shapeChoose.size(); i++) {
			JButton button = shapeChoose.get(i);
			button.addActionListener(listeners.get(i));
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					UpdataView();
				}
			});
		}
	}

	//按钮引起绘制切换
	private void SetCommandType(String buttonName)
	{
		//如果选中了绘图,点击选择
		if(buttonName ==  "drawbox" && commandType =="todraw")
		{
			commandType ="tochoose";
		}		
		else if(buttonName !=  "drawbox")//非框选按钮(选中了绘图按钮)
		{
			
			//如果当前为要绘制
			if(commandType =="tochoose")
			{	
				commandType ="todraw";
			}
			//如果已经选中了东西
			else if(commandType =="choosed")
			{
				commandType ="todraw";
				CancelChoose();
				current = null;
				mayMove = 0;
				StopChoosing();
				
				
				//小画笔
			}			
		}
		System.out.println("之后要绘制的是"+commandType);
		
		
//		if(commandType == "choosed")
//		{
//			if(buttonName == )
//			//清空有所的选择
//		}
//		if(buttonName == "drawbox")
//		{
//			commandType = "todraw";
//			//小框子
//		}
//		else if(buttonName == "move")
//		{
//			commandType = 2;
//			StopChoosing();
//			//小手
//		}
//		else//绘制的图标
//		{
//			commandType = 1;
//			CancelChoose();
//			current = null;
//			mayMove = 0;
//			StopChoosing();
//			UpdataView();
//			
//			//小画笔
//		}
	}
	
	//设置属性
//	左按下 1
//	左按+移动.2
//	移动 3
//	移动+抬起 4
//	左单击 5
//	右双击 6
	private void ChangeTypeByMouse(int mouseType)
	{
		System.out.println(mouseType);
		System.out.println(commandType);
		//完成了选择
		if(commandType.equals("choosed"))
		{
			//左键按下并发生移动
			if(mouseType == 2)
			{
				//判断击中点位置.
				if(ClickOnChoosed())
				{
					//准备移动的操作
					commandType = "moving";
					bufferData.startX = bufferData.endX;//设置起始坐标.
					bufferData.startY = bufferData.endY;//设置起始坐标.				
					System.out.println("重置");
					StopChoosing();
					for(Shape shape:choose.keySet())
					{
						//初始化监测位移的变量.
						shape.setMoveOrig();
						choose.get(shape).setMoveOrig();						
					}		
				}
				else
				{
					CancelChoose();//如果没有shift,如果有shift去做排除这个元素的操作.				
					commandType = "tochoose";
				}
				
			}
			//点击
			else if(mouseType == 5)
			{
				System.out.println("随机更换一个选择");
			}
			//删除
			else if(mouseType == 6)
			{
				System.out.println("删除这个元素");
				RemoveAndDraw();
			}
		}
		//正要绘制
		if(commandType.equals("todraw"))
		{
			//左键按下并发生移动
			if(mouseType == 2)
			{
				bufferData.startX = bufferData.endX;//设置起始坐标.
				bufferData.startY = bufferData.endY;//设置起始坐标.
				Shape b = FindDrawWhat(1);//将将要绘制的图案给过去.
				if(nowDrawWhat == "drawstring")
				{
					String inputValue = JOptionPane.showInputDialog("Please input a value");
					if(inputValue!=null)
					{
						bufferData.txt = inputValue;
					}
					
				}
				current = b;//变为可操作状态			
				Shape s = FindDrawWhat(0);//添加box
				SaveToShapes(b, s);	//储存
				BufferToShapeWhenNew();//更新坐标
				BufferToShapeWhenDraw();//更新end坐标数据
				commandType = "drawing";
			}
		}
		//正要选择
		if(commandType.equals("tochoose"))
		{
			//左键按下并发生移动
			if(mouseType == 2)
			{
				bufferData.startX = bufferData.endX;//设置起始坐标.
				bufferData.startY = bufferData.endY;//设置起始坐标.
				Shape b = FindDrawWhat(1);//将将要绘制的图案给过去.
				current = b;//变为可操作状态	
				chooseBoxs.add(b);//储存
				current.SetStart(bufferData.startX, bufferData.startY);//设置初始坐标
				BufferToShapeWhenChoosing();//更新end坐标数据
				commandType = "choosing";
			}
		}	
		//正在选择中
		if(commandType.equals("choosing"))
		{
			//移动
			if(mouseType == 3)
			{
				BufferToShapeWhenChoosing();
			}//抬起
			else if(mouseType == 4)
			{
				current = null;
				//1判定交集
				for(int i =0;i<chooseBoxs.size();i++)
				{
					CancelChoose();//如果没有按下shift就清空,可以在前面就判定好
					commandType = "tochoose";
					ShapeBox choosebox = (ShapeBox)chooseBoxs.get(i);
					for(Shape shape:draws.keySet())
					{
						ShapeBox testBox = (ShapeBox)draws.get(shape);
						if(choosebox.IfCollision(testBox))
						{
							//2添加到elements
							choose.put(shape, draws.get(shape));	
							commandType = "choosed";
						}
					}				
				}		
						
				//3将这些box所在的父级的visible打开?(如果为单机操作,那么..遍历的时候有就返回)
				SetBoxVisible(true);
				//4移除掉选框为null?(如果没有shift的话,有的话也应该移除)
				StopChoosing();
				//current = null;
			}
		}		
		if(commandType.equals("drawing"))
		{
			//移动
			if(mouseType == 3)
			{
				BufferToShapeWhenDraw();
			}//抬起
			else if(mouseType == 4)
			{
				current = null;
				commandType = "todraw";
			}
		}	
		if(commandType.equals("moving"))
		{
			//移动
			if(mouseType == 3)
			{
				BufferToShapeWhenMove();
			}//抬起
			else if(mouseType == 4)
			{
				commandType = "choosed";
				current = null;
			}
		}		
	}
	
	//遍历所有的选框.看是否落在上面
	private Boolean ClickOnChoosed()
	{	
		//ShapeBox testPoint = (ShapeBox)current;
		ShapeBox testPoint = new ShapeBox(bufferData.endX,bufferData.endY);
		ShapeBox drawshape;
		for(Shape choosen:choose.keySet())
		{
			ShapeBox choosenBox = (ShapeBox)choose.get(choosen);
			if(testPoint.IfCollision(choosenBox))
			{
				
				return true;
			}
		}
		return false;

	}

	//获得当前状态
	private int GetCommandType()
	{
		return 0;
	}
	
	//提交缓存改动
	private void WrittingBuffer()
	{
		
	}
	
	//	public void ColliderTest(Shape a,Shape b)
	//	{
	//		
	//	}
		
		//接受到有效鼠标点,更新数据.
		private void BufferStartPos(int endX,int endY)
		{
			bufferData.endX = endX;
			bufferData.endY = endY;
			//SetEndPos(endX,endY);
		}



	// 设置终点坐标(每次都调用,坐标设置)
	private void BufferToShapeWhenDraw() {
		current.SetEnd(bufferData.endX, bufferData.endY);
		draws.get(current).SetEnd(bufferData.endX, bufferData.endY);	
	}
	
	// 设置终点坐标(每次都调用,坐标设置)
	private void BufferToShapeWhenChoosing() {
		current.SetEnd(bufferData.endX, bufferData.endY);
	}

	//更新所有选中单位的坐标,用坐标变化绝对值来做.
	//绝对值的计算方式是用现在的坐标减去原始坐标.
	private void BufferToShapeWhenMove() {
		
		int dertaX = bufferData.endX - bufferData.startX;
		int dertaY = bufferData.endY - bufferData.startY;
		//遍历并更新.
		for(Shape shape:choose.keySet())
		{
			shape.move(dertaX, dertaY);
			choose.get(shape).move(dertaX, dertaY);;
		}
	}

	//buffer写入shape坐标.在new的时候调用一次
	private void BufferToShapeWhenNew() {
		//设置起点坐标
		current.SetStart(bufferData.startX, bufferData.startY);
		//设置好边框的起点坐标
		draws.get(current).SetStart(bufferData.startX, bufferData.startY);
		//
		current.SetBold(bufferData.bold);
		current.SetColor(bufferData.color);
		current.SetString(bufferData.txt);
	}

	private void SetAttribute()
	{
		for(Shape shape:choose.keySet())
		{
			shape.SetBold(bufferData.bold);
			shape.SetColor(bufferData.color);
		}
	}

	// 一个临时的内部接口,保存到数据中
	private void SaveToShapes(Shape picture, Shape box) {
		draws.put(picture, box);
	}
	
	//

	//接收鼠标事件的接口
	public void SendMouseEvent(MouseEventData mouseData)
	{
		//解析鼠标事件.
//		左按+移动.2
//		移动 3
//		抬起 4
//		左单击 5
//		右双击 6
		
		//排除掉无意义的操作
		//这个筛选的价值是什么呢?
//		if(current == null && mouseData.Get(0)!=1 && mouseData.Get(0)!=4)
//		{
//			//return;
//		}
		//首先更新鼠标,无论如何缓存总会被有效的数据覆盖.
		BufferStartPos(mouseData.Get(1),mouseData.Get(2));
		//判断对点击事件是否感兴趣
		ChangeTypeByMouse(mouseData.Get(0));
		//UpDataLocal(mouseData.Get(1),mouseData.Get(2));//首先鼠标读入缓存作为end	
		UpdataView();
		
//		//不是点击操作
//		//if(current == null)//&& mouseData.Get(0)!=4
//		
//		//
////		if(current == null && mouseData.Get(0) == 1 && mouseData.Get(0)!=4)
////		{
////			//左键按下
////			if(mouseData.Get(0) == 1)
////			{	
////				if(mayMove == 1)//如果可能会移动.//先判定落点是不是在已选中的图形上
////				{
////					CurrentPointIfChoose();
////				}
////				//绘图he绘制选择框
////				CreateShape();
////				BufferToShape();//更新end坐标数据
////			}
////		}
//		else
//		{	
//			//左键抬起
//			if(mouseData.Get(0) == 2)
//			{
//				//1更新end坐标
//				//2current值为空
//				BufferToShape();//更新end坐标数据
//				LeftRelease();
//			}
//			//左键按下并且移动
//			if(mouseData.Get(0) == 3)
//			{
//				if(mayMove == 2)
//				{
//					SetCommandType("move");//确定是在移动.
//					mayMove = 0;
//				}
//				BufferToShape();
//			}
//			
//			//左键单击
//			if(mouseData.Get(0) == 4)
//			{
//				System.out.println("click");
//				//BufferToShape();//更新end坐标数据
//				//点击都是无意义操作.
//				//1创建的时候点击.清除创建
//				//draws.
//				//2框选的时候点击,选择其中的一个.
//				//if()
//			}	
//			
//			//右键双击
//			if(mouseData.Get(0) == 5)
//			{
//				System.out.println("remove");
//				RemoveAndDraw();
//			}		
//		}	
//		//update图像
		
	}
	
	
//	private void RemoveAndDraw()
//	{
//		//1当前点击的物体删除掉.
//		ShapeBox testPoint = new ShapeBox(bufferData.endX,bufferData.endY);
//		ShapeBox drawshape;
//		for(Shape shape:choose.keySet())
//		{
//			drawshape = (ShapeBox)choose.get(shape);
//			if(testPoint.IfCollision(drawshape))
//			{
//				choose.remove(drawshape);
//				draws.remove(drawshape);
//				
//			}
//		}
//		//2获得点击点和碰撞的关系.
//		//3如果点击点在choose中.1删除.2.删除这个元素.如果不在.不改变任何状态
//		
//	}
	
	//判断击中点位置是否在选区里面
	private void RemoveAndDraw()
	{
		//1当前点击的物体删除掉.
		ShapeBox testPoint = new ShapeBox(bufferData.endX,bufferData.endY);
		for(Shape choosen:choose.keySet())
		{
			ShapeBox choosenBox = (ShapeBox)choose.get(choosen);
			if(testPoint.IfCollision(choosenBox))
			{
				System.out.println(choose.remove(choosen));
				System.out.println(draws.remove(choosen));	
				break;
			}
		}
		//2获得点击点和碰撞的关系.
		//3如果点击点在choose中.1删除.2.删除这个元素.如果不在.不改变任何状态	
	}
	
	//创建一个新的物体
	private void CreateShape()
	{

	}

	//create的时候,获得已经预设好的对象的浅拷贝
	private Shape FindDrawWhat(int type)
	{
		if(type == 1)
		{
			return(hashShape.get(nowDrawWhat)).clone();
		}
		else
		{
			return(hashShape.get(nowDrawWhat+"box")).clone();
		}
		
	}
	
	//取消掉当前选择的所有元素
	private void CancelChoose()
	{
		SetBoxVisible(false);
		choose.clear();
	}

	//停止框选过程,清空范围框
	private void StopChoosing()
	{
		chooseBoxs.clear();
	}

	//控制box显隐
	private void SetBoxVisible(boolean visible)
	{
		for(Shape shape:choose.keySet())
		{
			ShapeBox box = (ShapeBox)draws.get(shape);
			box.SetBoxVisible(visible);
		}		
	}
	
	//请求view更新
	private void UpdataView() {
		view.UpadteAndDraw();
	}

	//view调用让我们绘制
	public void DrawWithData(Graphics g) {
		// TODO Auto-generated method stub
		for(Shape shape:draws.keySet())
		{
			shape.Draw(g);
			draws.get(shape).Draw(g);
		}
		//绘制选择框
		if(chooseBoxs.size()!=0)
		{
			for(int i =0;i<chooseBoxs.size();i++)
			{
				chooseBoxs.get(i).Draw(g);
			}
		}
	}
	
}
//private HashMap<String,JButton> componentData = new
	// HashMap<String,JButton>();
	// private String[] buttonName = {"hello","world","shape"};
	// private HashMap<String,>


// 初始化界面.
// CAD调用.传入集合
// 考虑下updata的机制
// 这里面一定要知道jB 吗?不能脱离这层关系吗?

// switch(i)
// {
// case 0://draw 1
// button.addActionListener(listeners.get(i));
// break;
//
// case 1://draw 2
// button.addActionListener(new ActionListener() {
//
// @Override
// public void actionPerformed(ActionEvent e) {
// // TODO Auto-generated method stub
// Data d = GetCurrentData();
// d.txt = "22222222222222";
// d.posX = 50;
// d.posY = 100;
// //Updata();
// }
// });
// break;
//
// case 2://drawstring
// button.addActionListener(new ActionListener() {
//
// @Override
// public void actionPerformed(ActionEvent e) {
// // TODO Auto-generated method stub
// //创建的工作不是这边处理的.这边只负责接受创建后的数据.
// //这边数据有变化的时候,进行update就行了
//
// //这边是
//
// System.out.println("现在要绘制字符串");
// //System.out.println(button.getName());
// //nowDrawWhat = hashShape.get(button.getName());
// nowDrawWhat = hashShape.get(button.getName()).clone();
// //nowDrawWhat = new Shape();
// System.out.println(nowDrawWhat);
// //d.type = //这里可以改成用字符串或者别的来代替.直接在这里new怪怪的.
// //Updata();
// }
// });
// break;
// case 3://drawline
// button.addActionListener(new ActionListener() {
//
// @Override
// public void actionPerformed(ActionEvent e) {
// System.out.println("现在要绘制直线");
// nowDrawWhat = (hashShape.get(button.getName())).clone();
//
// }
// });
// break;
// case 4://drawstring
// button.addActionListener(new ActionListener() {
//
// @Override
// public void actionPerformed(ActionEvent e) {
// DrawMe b =nowDrawWhat;
// //Shape b = new Shape();
//
// //current = b;//设置后自动选中这个元素
// if(saveData.txt == null)
// {
// saveData.txt = "hello";
// }
// if(saveData.txt.equals("1"))
// {
//
// }
// else
// {
// //saveData.posX = 50;
// //saveData.posY = 100;
// }
// System.out.println("原先的"+saveData);
//
// Data s = saveData.clone();
// System.out.println("houlai1的"+saveData);
//
// SaveToShapes(b,s);
// System.out.println("保存的"+s);
// System.out.println("save"+saveData);
// //saveData = new Data();
//
// Updata();
// }
// });
// break;
// default:
// break;


//private int GetDoingWhat() {
//	// 如果当前要创建
//	if (CommandType == 0) {
//		// 如果
//		// if(current == null)
//		// 如果没有创建呢
//		if (drawing == null) {
//
//			return 0;
//		} else {
//			return 1;
//			// 设置重点坐标
//		}
//	}
//	// 如果要点击修改
//	else {
//		if (current == null) {
//			// find找到某个点
//		} else {
//			if (drawing == null) {
//				// find
//			}
//
//		}
//		if (drawing == null) {
//
//			// 不需要做处理
//
//			return 0;
//		} else {
//
//			// 设置重点坐标
//		}
//	}
//	// if(current == null)
//	return 0;
//}

// 当按下发生的时候
//public void Press(int x, int y) {
//	
//	if (GetDoingWhat() == 0) {
//		// new方法
//		
//
//		// 设置drawing
//
//		// 设置起始坐标
//	} else {
//		System.out.println("press");
//	}
//	
//
//}
//	// 让外部调用
// 这里面绘制是外面的事情,但是这样给是不是太偷懒了呢
// 把数据打给view
//public DrawMe[] GetData() {
//	DrawMe[] d = new DrawMe[draws.size()];
//	return draws.keySet().toArray(d);
//}

//// 当拖动的时候.无论左右键.
//public void Move(int x, int y) {
//	// 更新保存这个数据.并且计算差值.然后修改起始和坐标值.current得movefunction
//	if (GetDoingWhat() == 0) {
//		// new方法
//
//		// 设置drawing
//
//		// 设置起始坐标
//	} else {
//		System.out.println("press");
//	}
//}
