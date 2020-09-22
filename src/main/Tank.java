package main;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 陆骏辉
 * @data 2020-09-10 本类是坦克大战的客户端
 */
public class Tank extends Frame{
	int x = 100;
	int y = 100;
	public static final int TANK_WIDTH = 30;
	public static final int TANK_HEIGHT = 30;

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Image offScreenImage = null;// 通过成员变量在内存中准备虚拟图片的空间

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		g.setColor(c);
		
		x += 10;
	}
	/**
	 * @author 陆骏辉
	 * @data 2020-09-18
	 * 用一张虚拟图签显示界面，类似川剧变脸
	 * 
	 * */
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {//只有在内存没有图片才能创建
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);//出现魔法数据，用常量代替
		}
		
		Graphics gOffScreen=offScreenImage.getGraphics();//获取虚拟图片的画笔，该画笔要使用，所以起个名字
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);//设置画笔颜色
		gOffScreen.fillOval(0,0,GAME_WIDTH, GAME_HEIGHT);//画窗体，原来为绿色，要给画笔加颜色
		gOffScreen.setColor(c);//保留原来颜色
		
		paint(gOffScreen);//将虚拟图片的画笔提交给paint方法，让他画塔克
		
		g.drawImage(offScreenImage, 0, 0, null);//将虚拟图片提交给窗体绘出
	
	}
	private class PaintThread implements Runnable {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 调用本方法可以显示Tank类中画游戏窗体，在屏幕上画一个窗体，作为坦克大战的游戏空间
	 * 
	 */
	public void LunchFrame() {
		this.setResizable(false);
		this.setTitle("坦克大战");
		this.setLocation(400, 500);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setVisible(true);
		
		this.setBackground(Color.GREEN);
		new Thread(new PaintThread()).start();

		this.addWindowListener(new WindowAdapter(){ //定义一个继承WindowAdapter

			@Override  //覆盖WindowAdapter里的windowClosing
			public void windowClosing(WindowEvent e) {
				System.exit(HEIGHT);
			}
		});//添加监听器，需要输入一个监听对象

	}

}
 