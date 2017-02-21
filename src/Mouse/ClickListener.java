package Mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



import CADMain.CAD;


public class ClickListener implements MouseListener {

	private Control control;

	public ClickListener()
	{
		control = CAD.control;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==3 && e.getClickCount() ==2)
		{
			control.SendData(6,e);
		}
		if(e.getButton()==1)
		{
			control.SendData(5,e);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==1)
		{
			control.SendData(1,e);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==1)
		{
			control.SendData(4,e);
		}
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
