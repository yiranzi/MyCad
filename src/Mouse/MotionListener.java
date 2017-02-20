package Mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import CADMain.CAD;
import Model.Model;

public class MotionListener implements MouseMotionListener {
	private MouseEventData sendData;
	private Control control;
	public MotionListener()
	{
		control = CAD.control;
	}
	//左键点击1
	//左键抬起2
	//左键按下移动 3
	private void SendData(MouseEvent e,int type)
	{
		//左键移动
		if(e.getButton() == 1 && type == 1)
		{
			sendData = new MouseEventData(3,e.getX(),e.getY());
		}
		control.SendMouseEvent(sendData);
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		SendData(e,1);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
