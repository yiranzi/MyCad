package CADMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Model;
import Mouse.Control;
import View.MyFrame;
import View.View;

//这是程序的入口
//界面按钮jpanel保存在这里
public class CAD {
	public static final Model model = new Model();
	public static final View view = new View();
	public static final Control control = new Control();

	private final JPanel buttonpanel;
	private final JPanel colorpanel;
	private static MyFrame frame;

	private JButton colorChosser = new JButton();
	// JFrame frame;

	// 这个hash完全没有用.别乱用.这个数据根本不需要获得.用数字更方便
	// private HashMap<String,JButton> commandButtons = new
	// HashMap<String,JButton>();
	private ArrayList<JButton> settingButtons = new ArrayList<JButton>();
	private ArrayList<JButton> drawButtons = new ArrayList<JButton>();
	// private String[] buttonName = {"hello","world","shape","create"};
	private String[] settingButtonName = { "draw 1", "draw 222", "增大粗细", "减小粗细", "颜色选择" };
	private String[] drawButtonName = { "drawcircle", "drawrect", "drawstring", "drawline", "drawbox" };

	// 初始化原始的操作按钮

	public CAD(int width, int height) {
		frame = new MyFrame(width, height);
		buttonpanel = frame.GetPanel("buttonpanel");
		colorpanel = frame.GetPanel("colorchooser");

		this.Init();
		model.Init(settingButtons, drawButtons);
		model.SetView(view);
		view.SetFrame(frame);// 绑定上frame

		view.UpadteAndDraw();
		frame.setVisible(true);

	}

	// 获得面板
	public static JFrame GetFrame() {
		return frame;
	}

	private void Init() {
		SetButton();
	}

	private void SetButton() {
		JButton button;
		String name;
		// CommandButton();
		for (int i = 0; i < settingButtonName.length; i++) {
			name = settingButtonName[i];
			button = new JButton(name);
			button.setName(name);
			settingButtons.add(button);
			buttonpanel.add(button);
		}

		// DrawButton();
		for (int i = 0; i < drawButtonName.length; i++) {
			name = drawButtonName[i];
			button = new JButton(name);
			button.setName(name);
			drawButtons.add(button);
			buttonpanel.add(button);
		}
	}

	public static void main(String[] args) {
		int width = 1000;
		int height = 500;
		CAD cad = new CAD(width, height);
	}

}

// frame.setVisible(true);
// frame.pack();
// model.Init(commandButtons);
