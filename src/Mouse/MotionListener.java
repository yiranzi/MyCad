package Mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import CADMain.CAD;
import Model.Model;

public class MotionListener implements MouseMotionListener {
	private Control control;
	public MotionListener()
	{
		control = CAD.control;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==1)
		{
			control.SendData(3,e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
