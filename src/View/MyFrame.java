package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import CADMain.CAD;
import Model.Data;
import Model.Model;
import Mouse.ClickListener;
import Mouse.MotionListener;
import Shape.DrawMe;

//布局相关的内容
public class MyFrame extends JFrame {
	private HashMap<String,JPanel> panels;
	private Model model;
	
	private MyPanel buttonPanel;
	public MyFrame(int width,int height)
	{
		model = CAD.model;
		
		panels = new HashMap<String,JPanel>();
		MyPanel panel = new MyPanel();
		panel.setBackground(new Color(1,250,250));
		panels.put("drawpanel", panel);
		
		add(panel,BorderLayout.CENTER);	
		panel.addMouseListener(new ClickListener());
		panel.addMouseMotionListener(new MotionListener());
		
		panel = new MyPanel();
		panel.setBackground(new Color(100,250,250));
		panels.put("buttonpanel", panel);
		add(panel, BorderLayout.NORTH);
		
		setSize(width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		//frame.setLocationRelativeTo(null);
		setTitle("myCAD");
		setVisible(true);
	}
	
	//获得panel
	public JPanel GetPanel(String name)
	{
		return panels.get(name);
	}
	
		
	private class MyPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			model.DrawWithData(g);
//			HashMap<DrawMe,Shape> drawData = model.GetDraws();
//			for(DrawMe draw:drawData.keySet())
//			{
//				draw.Draw(g, drawData.get(draw));
//			}
		}
	}	
}
