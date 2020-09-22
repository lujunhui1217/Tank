package main;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author ½����
 * @data 2020-09-10 ������̹�˴�ս�Ŀͻ���
 */
public class Tank extends Frame{
	int x = 100;
	int y = 100;
	public static final int TANK_WIDTH = 30;
	public static final int TANK_HEIGHT = 30;

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Image offScreenImage = null;// ͨ����Ա�������ڴ���׼������ͼƬ�Ŀռ�

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		g.setColor(c);
		
		x += 10;
	}
	/**
	 * @author ½����
	 * @data 2020-09-18
	 * ��һ������ͼǩ��ʾ���棬���ƴ������
	 * 
	 * */
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {//ֻ�����ڴ�û��ͼƬ���ܴ���
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);//����ħ�����ݣ��ó�������
		}
		
		Graphics gOffScreen=offScreenImage.getGraphics();//��ȡ����ͼƬ�Ļ��ʣ��û���Ҫʹ�ã������������
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);//���û�����ɫ
		gOffScreen.fillOval(0,0,GAME_WIDTH, GAME_HEIGHT);//�����壬ԭ��Ϊ��ɫ��Ҫ�����ʼ���ɫ
		gOffScreen.setColor(c);//����ԭ����ɫ
		
		paint(gOffScreen);//������ͼƬ�Ļ����ύ��paint����������������
		
		g.drawImage(offScreenImage, 0, 0, null);//������ͼƬ�ύ��������
	
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
	 * ���ñ�����������ʾTank���л���Ϸ���壬����Ļ�ϻ�һ�����壬��Ϊ̹�˴�ս����Ϸ�ռ�
	 * 
	 */
	public void LunchFrame() {
		this.setResizable(false);
		this.setTitle("̹�˴�ս");
		this.setLocation(400, 500);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setVisible(true);
		
		this.setBackground(Color.GREEN);
		new Thread(new PaintThread()).start();

		this.addWindowListener(new WindowAdapter(){ //����һ���̳�WindowAdapter

			@Override  //����WindowAdapter���windowClosing
			public void windowClosing(WindowEvent e) {
				System.exit(HEIGHT);
			}
		});//��Ӽ���������Ҫ����һ����������

	}

}
 