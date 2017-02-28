package Model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import Shape.ChooseBox;
import Shape.MyShape;
import Shape.ShapeBox;
import Shape.ShapeInside;

//这个类想帮助model分担一些初始化操作.
public class AddDrawFunc {
	public static HashMap<String, MyShape> SetDrawFunc(HashMap<String, MyShape> hashShape) {
		// 设置绘制方法的地方
		// !!!这个命名和cad中的要一致!!!
		// drawshape的父类中有设置属性.
		// 使用终点坐标前的调节位置fixPosition
		// box中在visible方法中有super.draw和一个常规的矩形框绘制.
		
		// 我在这里做了一个shape的子匿名类.他会在开始的时候运行一次,返回一个shape.并重写他的方法.并且保存在hash中
		hashShape.put("drawrect", new ShapeInside() {
			@Override
			protected void DrawShape(Graphics g) {
				// TODO Auto-generated method stub
				g.drawRect(startFinalX, startFinalY, endFinalX - startFinalX, endFinalY - startFinalY);		
			}

			@Override
			protected void fixPosition() {
				// TODO Auto-generated method stub
				super.fixPosition();
				if(endX<startX)
				{
					startFinalX = endX;
					endFinalX = startX;
				}
				if(endY<startY)
				{
					startFinalY = endY;
					endFinalY = startY;
				}
			}

		});

		hashShape.put("drawstring", new ShapeInside(false) {
			@Override
			protected void DrawShape(Graphics g) {
				// TODO Auto-generated method stub
				g.drawString(txt, endFinalX, endFinalY);
			}

		});

		hashShape.put("drawline", new ShapeInside() {
			@Override
			protected void fixPosition() {
				// TODO Auto-generated method stub
				super.fixPosition();
				if (endX < startX) {
					startFinalX = endX;
					endFinalX = startX;
					startFinalY = endY;
					endFinalY = startY;
				}
			}

			@Override
			protected void DrawShape(Graphics g) {
				// TODO Auto-generated method stub
				g.drawLine(startFinalX, startFinalY, endFinalX, endFinalY);				
			}

		});

		hashShape.put("drawcircle", new ShapeInside() {
			@Override
			protected void DrawShape(Graphics g) {
				// TODO Auto-generated method stub
				g.drawOval(startFinalX, startFinalY, endFinalX - startFinalX, endFinalY - startFinalY);
			}
			@Override
			protected void fixPosition() {
				// TODO Auto-generated method stub
				super.fixPosition();
				if(endX<startX)
				{
					startFinalX = endX;
					endFinalX = startX;
				}
				if(endY<startY)
				{
					startFinalY = endY;
					endFinalY = startY;
				}
			}
		});

		//绘制外边框
		hashShape.put("drawbox", new ShapeBox() {

			@Override
			protected void fixPosition() {
				// TODO Auto-generated method stub
				super.fixPosition();
				if(endX<startX)
				{
					startFinalX = endX;
					endFinalX = startX;
				}
				if(endY<startY)
				{
					startFinalY = endY;
					endFinalY = startY;
				}
			}
		});
		
		//绘制外边框
		hashShape.put("choosebox", new ChooseBox() {

			@Override
			protected void fixPosition() {
				// TODO Auto-generated method stub
				super.fixPosition();
				if(endX<startX)
				{
					startFinalX = endX;
					endFinalX = startX;
				}
				if(endY<startY)
				{
					startFinalY = endY;
					endFinalY = startY;
				}
			}
		});
		return hashShape;
	}

}
