package View;

import java.awt.Frame;

//这是展示图像的地方.
//也是做用户操作的地方
public class View{
	private Frame frame;
	
	public void SetFrame(Frame frame)
	{
		this.frame = frame;
	}
	
	//现在的设计是..直接把
	public void UpadteAndDraw() {
		// TODO Auto-generated method stub	
		//frame.setVisible(true);
		frame.repaint();
	}	
}
