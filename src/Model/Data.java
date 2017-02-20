package Model;

public class Data implements Cloneable{
	public int posX;
	public int posY;
	public String txt;
	//public DrawMe type;
	
	@Override
	protected Data clone() {  
		Data data = null;  
        try {  
        	data = (Data) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
          
        return data;  
    }  
}
