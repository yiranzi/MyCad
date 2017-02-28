//数据类需要接受数据,并储存这些数据,并且在合适的时间,要求重新绘制.
//他需要一些基本的功能和变量.
//比如,起始点,终点,粗细,颜色,位置坐标(和起始点类似其实)
package Model;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import CADMain.CAD;//为了获得frame
import Mouse.MouseEventData;
import Shape.ChooseBox;
import Shape.MyShape;
import Shape.ShapeBox;
import Shape.ShapeInside;
import View.View;


public class Model {
	// 保存绘制数据的容器
	private HashMap<ShapeInside, ShapeBox> draws = new HashMap<ShapeInside, ShapeBox>();
	// 临时选中的元素
	private HashMap<ShapeInside, ShapeBox> choosenShapeInside = new HashMap<ShapeInside, ShapeBox>();

	private MyShape current;// 当前选中的对象
	private View view;
	private HashMap<String, MyShape> hashShape = new HashMap<String, MyShape>();

	// 用来保存在这里写好,统一设置的listener.
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

	// private DrawMe drawing;
	private String commandType = "todraw";// 0为选择1为画
	private String nowDrawWhat = "drawline";// 用户当前点击的是什么画图功能.

	private BufferData bufferData;
	// 因为选区需要进行碰撞检测,我们还是使用box类型
	// 那么就需要...为box添加一个visible的属性.进行设置
	private ArrayList<ChooseBox> chooseRanges = new ArrayList<ChooseBox>();
	private Frame frame;//为了添加上消息弹窗

	// 缓存内部类.
	class BufferData {
		private int startX;
		private int startY;
		private int endX;
		private int endY;
		private String txt = "txt";
		private float bold = 1;
		private Color color = Color.black;

		// 设置缓存属性
		private void SetColor(Color color) {
			if (color != null) {
				this.color = color;
			}
		}
		
		private void BufferAttrToShape()
		{
			ShapeInside s = (ShapeInside) current;
			s.SetBold(bufferData.bold);
			s.SetColor(bufferData.color);
			s.SetString(bufferData.txt);
		}

		// 提交缓存属性
		private void BufferAttrToChoosen() {
			
			for (ShapeInside shape : choosenShapeInside.keySet()) {
				shape.SetBold(bufferData.bold);
				shape.SetColor(bufferData.color);
			}
		}

		// new的时候保存
		private void SaveBufferStart() {
			bufferData.startX = bufferData.endX;
			bufferData.startY = bufferData.endY;
			// SetEndPos(endX,endY);
		}

		// 开始任何流程前保存重点坐标
		private void SaveBufferEnd(int endX, int endY) {
			bufferData.endX = endX;
			bufferData.endY = endY;
			// SetEndPos(endX,endY);
		}

		// // 设置终点坐标(每次都调用,坐标设置)
		// private void BufferToShapeWhenChoosing() {
		// current.SetEnd(bufferData.endX, bufferData.endY);
		// }
		
		// 更新shape起点坐标.在new的时候调用一次
		private void BufferToShapeWhenNew() {
			// 设置起点坐标
			current.SetStart(bufferData.endX, bufferData.endY);
			
		}

		// 绘制或者框选的更新
		private void BufferToShapeWhenDrug() {
			current.SetEnd(bufferData.endX, bufferData.endY);
			// draws.get(current).SetEnd(bufferData.endX, bufferData.endY);
		}
		
		// 更新shape起点坐标.在new的时候调用一次
		private void BufferToShapeStartDiff(int width,int height) {
			// 设置起点坐标
			ShapeInside shapeInside= (ShapeInside)current;
			shapeInside.SetStartDiff(bufferData.endX, bufferData.endY,width,height);
			
		}

		// 绘制或者框选的更新
		private void BufferToShapeEndDiff(int width,int height) {
			ShapeInside shapeInside= (ShapeInside)current;
			shapeInside.SetEndDiff(bufferData.endX, bufferData.endY,width,height);
			// draws.get(current).SetEnd(bufferData.endX, bufferData.endY);
		}

		// // 设置终点坐标(每次都调用,坐标设置)
		// private void BufferToShapeWhenChoosing() {
		// current.SetEnd(bufferData.endX, bufferData.endY);
		// }

		//移动的更新
		private void BufferToShapeWhenMove() {

			int dertaX = bufferData.endX - bufferData.startX;
			int dertaY = bufferData.endY - bufferData.startY;
			// 遍历并更新.
			for (ShapeInside shape : choosenShapeInside.keySet()) {
				shape.move(dertaX, dertaY);
			}
		}

	}

	public Model() {
		bufferData = new BufferData();
		SetDrawFunc();// 重写不同shape的绘制方法
		SettingButtonLietener();// 设置按钮的监听器方法写进hash
		frame = CAD.GetFrame();

	}

	// 设置view
	public void SetView(View view) {
		this.view = view;
	}

	// 给按钮添加事件
	public void Init(ArrayList<JButton> settingButtons, ArrayList<JButton> drawButtons) {
		DrawButtonLietener(drawButtons);// 添加绘制按钮
		AddSettingButton(settingButtons);// 添加设置按钮
	}

	// 设置绘制方法
	private void SetDrawFunc() {
		hashShape = AddDrawFunc.SetDrawFunc(hashShape);
	}

	// 给绘图的窗体添加上Listener
	private void DrawButtonLietener(ArrayList<JButton> shapeChoose) {

		for (int i = 0; i < shapeChoose.size(); i++) {
			JButton button = shapeChoose.get(i);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					nowDrawWhat = button.getName();
					// nowDrawWhat = (hashShape.get(buttonName)).clone();
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
				JOptionPane.showMessageDialog(frame,
						"1点击设置属性." + "2点击并拖动绘制图形." + "3选择drawbox选择多个形状." + "4选中状态下点击拖动物体进行移动,选中后右键双击删除物体", "帮助",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		listeners.add(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bufferData.bold++;
				bufferData.BufferAttrToChoosen();
				UpdataView();
			}
		});

		listeners.add(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bufferData.bold > 2) {
					bufferData.bold--;
				}
				bufferData.BufferAttrToChoosen();
				UpdataView();
			}
		});

		listeners.add(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("123123");
				Color chooseColor = JColorChooser.showDialog(frame, "Choose background color", Color.white);
				if (chooseColor != null) {
					bufferData.SetColor(chooseColor);
				}
				bufferData.BufferAttrToChoosen();
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
					// 可以统一添加吗?那么执行顺序是什么呢
					UpdataView();
				}
			});
		}
	}

	// 按钮引起绘制切换
	private void SetCommandType(String buttonName) {
		// 如果选中了绘图,点击选择
		if (commandType == "drawclick") {
			commandType = "todraw";
			current = null;
		}
		if (buttonName == "drawbox" && (commandType == "todraw")) {
			commandType = "tochoose";
			current = null;
		} else if (buttonName != "drawbox")// 非框选按钮(选中了绘图按钮)
		{

			// 如果当前为要绘制
			if (commandType == "tochoose") {
				commandType = "todraw";
				current = null;
			}
			// 如果已经选中了东西
			else if (commandType == "choosed") {
				commandType = "todraw";
				CancelChoose();
				current = null;
				StopChoosing();
			}
		}
	}

	
	private void CreateNewByClick() {
		// 应该单独做一个不可拖动的绘制类型.由点击事件进行触发.
		// 并且只会让点击事件进行捕捉.忽视其他的
		// 并且它的数据里面,应该只有一个起点.它是通过起点绘制的
		// 如果想让的边框绘制具有通用性,那么就需要计算出来合适的finalend数值
		ShapeInside b = (ShapeInside) FindDrawWhat(1);// 将将要绘制的图案给过去.
		if (!b.GetIsDrug()) {
			// SetStartPosBuffer
			if (nowDrawWhat == "drawstring") {
				String inputValue = JOptionPane.showInputDialog("Please input a value");
				if (inputValue != null) {
					bufferData.txt = inputValue;
					bufferData.SaveBufferStart();
					current = b;// 变为可操作状态
					ShapeBox s = (ShapeBox) FindDrawWhat(0);// 添加box
					b.SetBox(s);
					SaveToShapes(b, s); // 储存
					// BufferToShape
					int width = bufferData.txt.length() * 8;
					int height = 10;
					bufferData.BufferToShapeStartDiff(width,height);// 更新坐标
					bufferData.BufferAttrToShape();
					bufferData.BufferToShapeEndDiff(width,height);// 更新end坐标数据
					commandType = "drawclick";
				}
			}
		}
	}

	// 生成一个绘制的过程.
	private void CreateNewByDrug() {
		ShapeInside b = (ShapeInside) FindDrawWhat(1);// 将将要绘制的图案给过去.
		if (b.GetIsDrug()) {
			// SetStartPosBuffer
			bufferData.SaveBufferStart();
			current = b;// 变为可操作状态
			ShapeBox s = (ShapeBox)FindDrawWhat(0);// 添加box
			b.SetBox(s);
			SaveToShapes(b, s); // 储存
			bufferData.BufferToShapeWhenNew();// 更新坐标
			bufferData.BufferAttrToShape();
			bufferData.BufferToShapeWhenDrug();// 更新end坐标数据
			commandType = "drawing";
		}
	}

	// 这里处理来自control的事件信息.根据当前状态来相应这些事件.
	// 设置属性
	// 左按下 1
	// 左按+移动.2
	// 移动 3
	// 抬起 4
	// 左单击 5
	// 右双击 6
	private void ChangeTypeByMouse(int mouseType) {
		// 完成了选择
		if (commandType.equals("choosed"))
		{
			// 左键按下并发生移动
			if (mouseType == 2) {
				// 判断击中点位置.
				if (ClickOnChoosed()) {
					// 准备移动的操作
					commandType = "moving";
					StopChoosing();
					bufferData.SaveBufferStart();
					for (ShapeInside shape : choosenShapeInside.keySet()) {
						// 初始化监测位移的变量.
						shape.setMoveOrig();
					}
				} else {
					CancelChoose();// 如果没有shift,如果有shift去做排除这个元素的操作.
					//commandType = "tochoose";
					NewChooseRange();
				}

			}
			// 点击
			else if (mouseType == 5) {
				System.out.println("随机更换一个选择");
			}
			// 删除
			else if (mouseType == 6) {
				System.out.println("删除这个元素");
				RemoveAndDraw();
			}
		}
		// 正要绘制
		else if (commandType.equals("todraw")) {
			// 左键按下并发生移动
			if (mouseType == 2) {
				CreateNewByDrug();
			}
			// 点击
			else if (mouseType == 5) {
				CreateNewByClick();
			}
		}
		// 正要选择
		else if (commandType.equals("tochoose")) {
			// 左键按下并发生移动
			if (mouseType == 2) {		
				NewChooseRange();
			}
		}
		// 正在选择中
		else if (commandType.equals("choosing")) {
			// 移动
			if (mouseType == 3) {
				bufferData.BufferToShapeWhenDrug();
			} // 抬起
			else if (mouseType == 4) {
				current = null;
				// 1判定交集
				for (int i = 0; i < chooseRanges.size(); i++) {
					CancelChoose();// 如果没有按下shift就清空,可以在前面就判定好
					commandType = "tochoose";
					ChooseBox chooseRange = (ChooseBox) chooseRanges.get(i);
					for (ShapeInside shape : draws.keySet()) {
						ShapeBox testBox = (ShapeBox) draws.get(shape);
						if (chooseRange.IfCollision(testBox)) {
							// 2添加到elements
							choosenShapeInside.put(shape, draws.get(shape));
							commandType = "choosed";
						}
					}
				}

				// 3将这些box所在的父级的visible打开?(如果为单机操作,那么..遍历的时候有就返回)
				SetBoxVisible(true);
				// 4移除掉选框为null?(如果没有shift的话,有的话也应该移除)
				StopChoosing();
				// current = null;
			}
		} else if (commandType.equals("drawing")) {
			// 移动
			if (mouseType == 3) {
				bufferData.BufferToShapeWhenDrug();
			} // 抬起
			else if (mouseType == 4) {
				current = null;
				commandType = "todraw";
			}
		} else if (commandType.equals("drawclick")) {
			commandType = "todraw";
			current = null;
		} else if (commandType.equals("moving")) {
			// 移动
			if (mouseType == 3) {
				bufferData.BufferToShapeWhenMove();
			} // 抬起
			else if (mouseType == 4) {
				commandType = "choosed";
				current = null;
			}
		}
	}

	//将状态机出来的函数保存下来,这样可以实现切换.
	//这个函数用来生成新的选区
	private void NewChooseRange()
	{
		bufferData.SaveBufferStart();
		ChooseBox b = (ChooseBox) FindDrawWhat(2);// 将将要绘制的图案给过去.
		current = b;// 变为可操作状态
		chooseRanges.add(b);// 储存
		bufferData.BufferToShapeWhenNew();
		bufferData.BufferToShapeWhenDrug();// 更新end坐标数据
		commandType = "choosing";
	}

	// 遍历所有的选框.看测试点是否落在上面
	private Boolean ClickOnChoosed() {
		// ShapeBox testPoint = (ShapeBox)current;
		ChooseBox testPoint = new ChooseBox(bufferData.endX, bufferData.endY);
		for (ShapeInside choosen : choosenShapeInside.keySet()) {
			ShapeBox choosenBox = (ShapeBox) choosenShapeInside.get(choosen);
			if (testPoint.IfCollision(choosenBox)) {
				return true;
			}
		}
		return false;
	}

	// // 设置终点坐标(每次都调用,坐标设置)
	// private void BufferToShapeWhenChoosing() {
	// current.SetEnd(bufferData.endX, bufferData.endY);
	// }

	// 一个临时的内部接口,保存到数据中
	private void SaveToShapes(ShapeInside picture, ShapeBox box) {
		draws.put(picture, box);
	}

	// 接收鼠标事件的接口
	public void SendMouseEvent(MouseEventData mouseData) {
		bufferData.SaveBufferEnd(mouseData.Get(1), mouseData.Get(2));
		// 判断对点击事件是否感兴趣
		ChangeTypeByMouse(mouseData.Get(0));
		UpdataView();

	}

	// 判断击中点位置是否在选区里面
	private void RemoveAndDraw() {
		// 1当前点击的物体删除掉.
		ChooseBox testPoint = new ChooseBox(bufferData.endX, bufferData.endY);
		for (ShapeInside choosen : choosenShapeInside.keySet()) {
			ShapeBox choosenBox = (ShapeBox) choosenShapeInside.get(choosen);
			if (testPoint.IfCollision(choosenBox)) {
				System.out.println(choosenShapeInside.remove(choosen));
				System.out.println(draws.remove(choosen));
				break;
			}
		}
	}

	// create的时候,获得已经预设好的对象的浅拷贝
	// 图形用这里面拿到的.选框使用rect.外边框使用
	// 图形.选框.外边框
	private MyShape FindDrawWhat(int type) {
		if (type == 1) {
			ShapeInside s = (ShapeInside) hashShape.get(nowDrawWhat);
			return s.clone();
		} else if(type == 2) {
			ChooseBox s = (ChooseBox) hashShape.get("choosebox");
			return s.clone();
		}else if(type == 0)
		{
			ShapeBox s = (ShapeBox) hashShape.get("drawbox");
			return s.clone();
		}
		else
		{
			return null;
		}
	}

	// 取消掉当前选择的所有元素
	private void CancelChoose() {
		SetBoxVisible(false);
		choosenShapeInside.clear();
	}

	// 停止框选过程,清空范围框
	private void StopChoosing() {
		chooseRanges.clear();
	}

	// 控制box显隐
	private void SetBoxVisible(boolean visible) {
		for (ShapeInside shape : choosenShapeInside.keySet()) {
			ShapeBox box = (ShapeBox) draws.get(shape);
			box.SetBoxVisible(visible);
		}
	}

	// 请求view更新
	private void UpdataView() {
		view.UpadteAndDraw();
	}

	// view调用让我们绘制
	public void DrawWithData(Graphics g) {
		// TODO Auto-generated method stub
		for (ShapeInside shape : draws.keySet()) {
			shape.Draw(g);
			//draws.get(shape).Draw(g);
		}
		for (ShapeBox shapeBox : draws.values()) {
			shapeBox.Draw(g);
		}
		// 绘制选择框
		if (chooseRanges.size() != 0) {
			for (int i = 0; i < chooseRanges.size(); i++) {
				chooseRanges.get(i).Draw(g);
			}
		}
		
	}
}