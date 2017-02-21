package Mouse;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import CADMain.CAD;
import Model.Model;
import View.View;


//新建面板.
//给面板添加上我们的控制器
public class Control {
	private JPanel drawPanel;
	private Model model;
	private int catchNext = 0;
	//private Model model;
	public Control()
	{
		model = CAD.model;
		
	}

	//初始化按钮面板
	public void Init()
	{
		//model.Init();
	}
	//解析鼠标事件.
	
//	左按+移动.2
//	移动 3
//	抬起 4
//	左单击 5
//	右双击 6
	public void SendData(int type,MouseEvent e)
	{
		switch(type)
		{
			case 1://左键点击
				catchNext = 1;
				break;
			case 2://左拖移动
	
				break;
			case 3:
				if(catchNext == 0)
				{
					SendMouseEvent(new MouseEventData(2,e.getX(),e.getY()));
					catchNext = -1;
				}
				else
				{
					SendMouseEvent(new MouseEventData(3,e.getX(),e.getY()));	
				}
				break;
			case 4://左抬起
				SendMouseEvent(new MouseEventData(4,e.getX(),e.getY()));
				break;
			case 5:
				SendMouseEvent(new MouseEventData(5,e.getX(),e.getY()));
				break;
			case 6://右双击
				SendMouseEvent(new MouseEventData(6,e.getX(),e.getY()));			
				break;
			default:
				break;	
		}
		if(catchNext!=-1)
		{
			catchNext--;
		}
						
	}

	public void SendMouseEvent(MouseEventData sendData) {
		// TODO Auto-generated method stub
		model.SendMouseEvent(sendData);
	}
}
