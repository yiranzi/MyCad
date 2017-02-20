package Mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



import CADMain.CAD;


public class ClickListener implements MouseListener {
	private MouseEventData sendData;
	private Control control;
	//左键点击1
	//左键抬起2
	//移动 3
	//左键单击4
	public ClickListener()
	{
		control = CAD.control;
	}
	private void SendData(MouseEvent e,int type)
	{
		//左键点击
		if(e.getButton() == 1 && type == 1)
		{
			sendData = new MouseEventData(type,e.getX(),e.getY());
			
		}
		
		//左键抬起
		if(e.getButton() == 1 && type == 2)
		{
			sendData = new MouseEventData(type,e.getX(),e.getY());
		}
		
		//左键click
		if(e.getButton() == 1 && type == 4)
		{
			sendData = new MouseEventData(type,e.getX(),e.getY());
		}
		control.SendMouseEvent(sendData);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		SendData(e,4);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		SendData(e,1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		SendData(e,2);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}
