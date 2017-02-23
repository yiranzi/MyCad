package View;

import java.awt.Frame;

public class View{
	private Frame frame;
	
	public void SetFrame(Frame frame)
	{
		this.frame = frame;
	}
	
	public void UpadteAndDraw() {
		frame.repaint();
	}	
}
