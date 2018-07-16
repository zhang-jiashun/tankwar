package com.js.tankwar;

import javax.swing.JFrame;

public class WarFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	WarMap warMap = null;
	
	public WarFrame() {
		warMap = new WarMap();
		
		this.add(warMap);
		
		//注册监听
		this.addKeyListener(warMap);
		
		this.setTitle("TankWar");
		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		new Thread(warMap).start();
	}
}
