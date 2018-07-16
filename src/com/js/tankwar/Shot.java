package com.js.tankwar;

/**
 * 子弹类
 * @author js.zhang
 *
 */
public class Shot implements Runnable {
	//横坐标
	private int x;
	//纵坐标
	private int y;
	//子弹的半径
	private int r = 5;
	//方向
	private int direct;
	//速度
	private int speed = 20;
	//设置子弹是否存活
	public boolean isAlive = true;
	
	/**
	 * 构造函数
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @param direct 子弹方向
	 */
	public Shot(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
	
	/**
	 * 得到子弹方向
	 * @return 0表示上，1表示下，2表示左，3表示右
	 */
	public int getDirect() {
		return direct;
	}
	
	public int getR() {
		return r;
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

	@Override
	public void run() {
		//每间隔50毫秒子弹移动一次
		while (this.isAlive) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (this.direct == 0) {
				//向上
				this.y -= this.speed;
			} else if (this.direct == 1) {
				//向下
				this.y += this.speed;
			} else if (this.direct == 2) {
				//向左
				this.x -= this.speed;
			} else if (this.direct == 3) {
				//向右
				this.x += this.speed;
			}
			
			//如果子弹在WarMap之外，子弹死亡
			if (this.x < 0 || this.x > 800 || this.y < 0 || this.y > 600) {
				this.isAlive = false;
			}
		}
	}

	
}
