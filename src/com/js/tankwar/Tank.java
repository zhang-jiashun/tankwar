package com.js.tankwar;

import java.util.Vector;

/**
 * 坦克类
 * @author js.zhang
 *
 */
public class Tank {
	//坦克的横坐标
	private int x;
	//坦克的纵坐标
	private int y;
	//坦克的半径
	private int r = 15;
	
	public int getR() {
		return r;
	}

	/**
	 * 坦克方向，0表示上，1表示下，2表示左，3表示右
	 */
	private int direct;
	//坦克的速度
	private int speed = 5;
	//坦克是否活着
	public boolean isAlive = true;
	//子弹
	public Vector<Shot> shots = new Vector<Shot>();
	
	/**
	 * 构造函数
	 * @param x 坦克的横坐标
	 * @param y 坦克的纵坐标
	 * @param direct 坦克的方向
	 */
	public Tank(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
	
	public void fire() {
		Shot s = null;
		//判断坦克的方向
		if (this.direct == 0) {
			//向上
			s = new Shot(this.x + 10, this.y - 10, this.direct);
			shots.add(s);
		} else if (this.direct == 1) {
			//向下
			s = new Shot(this.x + 10, this.y + 30, this.direct);
			shots.add(s);
		} else if (this.direct == 2) {
			//向左
			s = new Shot(this.x - 10, this.y + 10, this.direct);
			shots.add(s);
		} else if (this.direct == 3) {
			//向右
			s = new Shot(this.x + 30, this.y + 10, this.direct);
			shots.add(s);
		}
		new Thread(s).start();
	}

	/**
	 * 向上移动
	 */
	public void moveUp() {
		this.y -= this.speed;
	}
	
	/**
	 * 向下移动
	 */
	public void moveDown() {
		this.y += this.speed;
	}
	
	/**
	 * 向左移动
	 */
	public void moveLeft() {
		this.x -= this.speed;
	}
	
	/**
	 * 向右移动
	 */
	public void moveRight() {
		this.x += this.speed;
	}
	
	/**
	 * 得到坦克方向
	 * @return 0表示上，1表示下，2表示左，3表示右
	 */
	public int getDirect() {
		return direct;
	}

	/**
	 * 设置坦克方向
	 * @param direct 0表示上，1表示下，2表示左，3表示右
	 */
	public void setDirect(int direct) {
		this.direct = direct;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
