package CADMain;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Model;
import Mouse.Control;
import View.MyFrame;
import View.View;


//这是程序的入口
//界面按钮jpanel保存在这里
public class CAD {
	public static Model model;
	public static View view;
	public static Control control;
	
	private JPanel buttonpanel;
	private MyFrame frame;
	//JFrame frame;
	
	//这个hash完全没有用.别乱用.这个数据根本不需要获得.用数字更方便
	//private HashMap<String,JButton> commandButtons = new HashMap<String,JButton>();
	private ArrayList<JButton> settingButtons = new ArrayList<JButton>();
	private ArrayList<JButton> drawButtons = new ArrayList<JButton>();
	//private String[] buttonName = {"hello","world","shape","create"};
	private String[] settingButtonName = {"draw 1","draw 222","useless"};
	private String[] drawButtonName = {"drawrect","drawstring","drawline","drawbox"};
	
	//初始化原始的操作按钮
	
	public CAD(int width,int height)
	{
		model = new Model();	
		view = new View();
		control = new Control();
		
		frame = new MyFrame(width,height);
		buttonpanel = frame.GetPanel("buttonpanel");
		this.Init();
		model.Init(settingButtons, drawButtons);
		model.SetView(view);
		view.SetFrame(frame);//绑定上frame
	
		
		view.UpadteAndDraw();
		frame.setVisible(true);

		
	}
	
	//获得面板
	public JFrame GetFrame()
	{
		return frame;
	}
	
	private void Init()
	{
		SetButton();	
	}
	
	private void SetButton()
	{
		JButton button;
		String name;
		//CommandButton();
		for(int i = 0;i< settingButtonName.length;i++)
		{
			name = settingButtonName[i];
			button = new JButton(name);
			button.setName(name);
			settingButtons.add(button);
			buttonpanel.add(button);	
		}	
		
		//DrawButton();
		for(int i = 0;i< drawButtonName.length;i++)
		{
			name = drawButtonName[i];
			button = new JButton(name);
			button.setName(name);
			drawButtons.add(button);
			buttonpanel.add(button);	
		}		
	}
	
	public static void main(String[] args) {
		int width = 800;
		int height = 500;
		CAD cad = new CAD(width,height);	
	}
	
	
}

//frame.setVisible(true);
//frame.pack();
//model.Init(commandButtons);
