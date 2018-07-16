package com.js.tankwar;

/**
 * 敌军坦克
 * 
 * @author js.zhang
 * 
 */
public class EnemyTank extends Tank implements Runnable {
	//敌方坦克的总数量
	public static int num = 0;
	
	public EnemyTank(int x, int y, int direct) {
		super(x, y, direct);
	}

	@Override
	public void run() {
		while (this.isAlive) {
			//生成一个0-100的随机数
			int ran = (int)(Math.random() * (100));
			if (ran == 0) {
				//换方向
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//生成一个随机方向
				int randomDirect = (int)(Math.random() * 4);
				this.setDirect(randomDirect);
			} else if (ran >0 && ran < 90) {
				//移动
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.move();
			} else if (ran >= 90) {
				//发子弹
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.fire();
			}
		}
	}

	public void move() {
		// 根据坦克的方向移动
		if (this.getDirect() == 0) {
			// 坦克的纵坐标大于0才能向上移动，否则坦克将跑出地图
			if (this.getY() > 0) {
				this.moveUp();
			} else {
				//坦克碰到墙壁会随机转方向
				int randomDirect = (int)(Math.random() * 4);
				this.setDirect(randomDirect);
			}
		} else if (this.getDirect() == 1) {
			// 坦克的纵坐标小于600-30才能向下移动
			if (this.getY() < (600 - 80)) {
				this.moveDown();
			} else {
				//坦克碰到墙壁会随机转方向
				int randomDirect = (int)(Math.random() * 4);
				this.setDirect(randomDirect);
			}
		} else if (this.getDirect() == 2) {
			// 坦克的横坐标大于0才能向左移动
			if (this.getX() > 0) {
				this.moveLeft();
			} else {
				//坦克碰到墙壁会随机转方向
				int randomDirect = (int)(Math.random() * 4);
				this.setDirect(randomDirect);
			}
		} else if (this.getDirect() == 3) {
			// 坦克的横坐标小于800-30才能向右移动
			if (this.getX() < (800 - 50)) {
				this.moveRight();
			} else {
				//坦克碰到墙壁会随机转方向
				int randomDirect = (int)(Math.random() * 4);
				this.setDirect(randomDirect);
			}
		}
	}
}
