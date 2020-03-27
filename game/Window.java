package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Window extends JFrame {

	int width;
	int height;
	Graphics g;
	boolean keyPressed[]=new boolean[4];//wasd

	public Window(int pWidth, int pHeight) {
		width = pWidth;
		height = pHeight;
		setSize(width, height);
		setTitle("Blocks");
		setResizable(false);
		setVisible(true);
		setFocusable(true);
		
		KeyListener keyListener = new MyKeyListener();
		addKeyListener(keyListener);
		
		g = getGraphics();
		
		clearBackground();
		repaint();
	}

	public void paint(Graphics g) {

	}

	public void paintBlocks(Block b) {
		g.drawRect((int)b.x, (int)b.y, (int)b.sizeX, (int)b.sizeY);
	}

	public void paintPlayer(Block b) {
		g.setColor(Color.red);
		g.drawRect((int)b.x, (int)b.y, (int)b.sizeX, (int)b.sizeY);
	}

	public void clearBackground() {
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
	}
	
	public void showString(int x, int y, String s) {
		g.setColor(Color.white);
		g.drawString(s, x, y);
	}

	public class MyKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
				case 87:
					keyPressed[0]=true;
					break;
				case 65:
					keyPressed[1]=true;
					break;
				case 83:
					keyPressed[2]=true;
					break;
				case 68:
					keyPressed[3]=true;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
				case 87:
					keyPressed[0]=false;
					break;
				case 65:
					keyPressed[1]=false;
					break;
				case 83:
					keyPressed[2]=false;
					break;
				case 68:
					keyPressed[3]=false;
					break;
			}

		}
	}

}
