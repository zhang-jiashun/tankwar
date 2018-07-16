package com.js.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JPanel;

/**
 * 战场地图
 * 
 * @author js.zhang
 * 
 */
public class WarMap extends JPanel implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;

	// 定义一个英雄坦克
	Hero hero = null;

	// 定义坦克组
	Vector<Tank> tanks = new Vector<Tank>();
	
	//定义敌方坦克最高数量
	int enemyNum = 10;
	
	// 构造函数
	public WarMap() {
		// 初始化我的坦克
		this.hero = new Hero(400, 300, 0);
		tanks.add(this.hero);
		// 初始化敌人的坦克
		for (int i = 0; i < enemyNum; i++) {
			randomCreateEnemyTank();
		}
	}

	/**
	 * 随机生成一个敌方坦克
	 */
	public void randomCreateEnemyTank() {
		//随机敌人坦克的横坐标
		int et_x = (int)(Math.random() * (800 - 30));
		//随机敌人坦克的纵坐标
		int et_y = (int)(Math.random() * (600 - 50));
		//随机敌人坦克的方向
		int et_direct = (int)(Math.random() * (4));
		//创建敌人的坦克
		EnemyTank et = new EnemyTank(et_x, et_y, et_direct);
		//敌方坦克总数量加一
		EnemyTank.num++;
		
		//把敌人的坦克放入坦克组
		tanks.add(et);
		
		new Thread(et).start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 设置背景颜色
		this.setBackground(Color.GREEN);
		// 画出坦克
		for (int i = 0; i < tanks.size(); i++) {
			//取出坦克
			Tank t = tanks.get(i);
			//如果此坦克还活着
			if (t.isAlive) {
				//画出此坦克
				this.drawTank(t, g);				
			} else {
				//否则，移除此坦克
				tanks.remove(t);
			}
		}
		// 画子弹
		for (int i = 0; i < tanks.size(); i++) {
			for (int j = 0; j < tanks.get(i).shots.size(); j++) {
				//取出每个坦克发出的子弹
				Shot s = tanks.get(i).shots.get(j);
				//如果子弹还活着
				if (s.isAlive) {
					//画出子弹
					this.drawShot(s, g);
				} else {
					//否则，移除子弹
					tanks.get(i).shots.remove(s);
				}
			}
		}
		//如果子弹和坦克相撞，子弹和坦克都死亡
		for (int i = 0; i < tanks.size(); i++) {
			for (int j = 0; j < tanks.get(i).shots.size(); j++) {
				//取出坦克里的子弹
				Shot s = tanks.get(i).shots.get(j);
				for (int k = 0; k < tanks.size(); k++) {
					//取出坦克
					Tank t = tanks.get(k);
					//判断子弹和坦克是否相撞
					this.hitTank(s, t);
				}
			}
		}
	}

	/**
	 * 如果子弹和坦克相撞，shot.isAlive = false,tank.isAlive = false
	 * @param s 子弹
	 * @param t 坦克
	 */
	public void hitTank(Shot s, Tank t) {
		//如果s是t打出的子弹，t不会被击毁
		for (int i = 0; i < t.shots.size(); i++) {
			if (s == t.shots.get(i)) {
				return;
			}
		}
		
		//如果s是敌方坦克打出的子弹，则不会击毁敌方坦克
		for (Tank tank : tanks) {
			//如果tank是敌方坦克
			if (tank instanceof EnemyTank) {
				//取出tank发出的子弹
				for (int j = 0; j < tank.shots.size(); j++) {
					//如果在tank的子弹中找到了s，并且t也是敌方坦克
					if (s == tank.shots.get(j) && t instanceof EnemyTank) {
						//坦克不会被击毁
						return;
					}
				}
			}
		}
		
		//算出坦克中心的横坐标
		int tCenterX = t.getX() + t.getR();
		//算出坦克中心的纵坐标
		int tCenterY = t.getY() + t.getR();
		//算出子弹中心的横坐标
		int sCenterX = s.getX() + s.getR();
		//算出子弹中心的纵坐标
		int sCenterY = s.getY() + s.getR();
		//横坐标距离
		int x_s = Math.abs(tCenterX - sCenterX);
		//纵坐标距离
		int y_s = Math.abs(tCenterY - sCenterY);
		//算出坦克中心点和子弹中心点的距离
		double c_s = Math.sqrt(x_s * x_s + y_s * y_s);
		//如果坦克中心点和子弹中心点的距离小于坦克和子弹的半径和，表示坦克和相撞
		if (c_s < (t.getR() + s.getR())) {
			t.isAlive = false;
			s.isAlive = false;
			
			//如果t是敌方坦克，则敌方坦克的总数量减一
			if (t instanceof EnemyTank) {
				EnemyTank.num--;
			}
		}
	}

	/**
	 * 画坦克
	 * 
	 * @param tank
	 *            坦克
	 * @param g
	 *            画笔
	 */
	public void drawTank(Tank tank, Graphics g) {
		// 判断坦克类型
		if (tank instanceof Hero) {
			// 定义画笔颜色为红色，画出的是Hero坦克
			g.setColor(Color.RED);
		} else {
			// 定义画笔颜色为蓝色，画敌人的坦克
			g.setColor(Color.BLUE);
		}

		int x = tank.getX();
		int y = tank.getY();
		
		//圆的半径
		int r = tank.getR();
		//直线的长度
		int l = 20;
		
		//画圆
		g.fillOval(x, y, r * 2, r * 2);
		
		//设置画笔颜色（画直线用）
		g.setColor(Color.BLACK);
		// 判断坦克的方向，呼出直线
		if (tank.getDirect() == 0) {	
			// 向上移动
			g.drawLine(x + r, y + r, x + r, (y + r) - l);
		} else if (tank.getDirect() == 1) {
			// 向下移动
			g.drawLine(x + r, y + r, x + r, (y + r) + l);
		} else if (tank.getDirect() == 2) {
			// 向左移动
			g.drawLine(x + r, y + r, (x + r) - l, y + r);
		} else if (tank.getDirect() == 3) {
			// 向右移动
			g.drawLine(x + r, y + r, (x + r) + l, y + r);
		}
	}

	/**
	 * 画子弹
	 * 
	 * @param s
	 *            子弹
	 * @param g
	 *            画笔
	 */
	public void drawShot(Shot s, Graphics g) {
		// 设置画笔颜色
		g.setColor(Color.BLACK);

		// 画子弹
		g.fillOval(s.getX(), s.getY(), s.getR() * 2, s.getR() * 2);
	}

	@Override
	// 按下处理，设置坦克方向
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			// 向上
			this.hero.setDirect(0);
			this.hero.moveUp();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			// 向下
			this.hero.setDirect(1);
			this.hero.moveDown();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// 向左
			this.hero.setDirect(2);
			this.hero.moveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// 向右
			this.hero.setDirect(3);
			this.hero.moveRight();
		}

		// 按下F键，坦克开火
		if (e.getKeyCode() == KeyEvent.VK_F) {
			this.hero.fire();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// 每间隔50毫秒重画一次
		while (true) {

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//如果敌方坦克的总数量<10，有几率随机生成一个敌方坦克
			if (EnemyTank.num < 10) {
				//生成一个随机数
				int ran = (int)(Math.random() * 100);
				if (ran == 0) {
					randomCreateEnemyTank();
				}
			}
			
			this.repaint();
		}
	}
}
