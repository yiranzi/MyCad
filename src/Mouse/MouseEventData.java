package Mouse;

//这个是控制器传递给数据层的数据包
public class MouseEventData {
	private int controlType;
	private int posX;
	private int posY;
	
	public MouseEventData(int controlType, int posX, int posY) {
		this.controlType = controlType;
		this.posX = posX;
		this.posY = posY;
	}

	public int Get(int type)
	{
		int answer = -1;
		switch(type)
		{
			case 0:
				answer = this.controlType;
				break;
			case 1:
				answer = this.posX;
				break;
			case 2:
				answer = this.posY;
				break;
			default:
				break;
		}
		if(answer == -1)
		{
			System.out.println("type not found");
		}
		return answer;
		
	}
}
