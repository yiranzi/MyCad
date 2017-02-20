package Mouse;

import javax.swing.JPanel;

import CADMain.CAD;
import Model.Model;
import View.View;


//新建面板.
//给面板添加上我们的控制器
public class Control {
	private JPanel drawPanel;
	private Model model;
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

	public void SendMouseEvent(MouseEventData sendData) {
		// TODO Auto-generated method stub
		model.SendMouseEvent(sendData);
	}
}
