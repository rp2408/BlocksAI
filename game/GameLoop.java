package game;

import javax.swing.WindowConstants;

public class GameLoop {

	Window w;
	int width = 500;
	int height = 1000;
	Block b[] = new Block[100];
	Block player;
	int counter;
	int points;
	
	long startGame;

	int speedPlayer = 2;
	float speedBlocks=1;

	public static void main(String[] args) {
		new GameLoop();
	}

	public GameLoop() {
		w = new Window(width, height);
		w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		while(true) {
			loop();
			}
		
		
	}

	public void loop() {
		boolean run = true;

		b = new Block[100];
		player = new Block((width / 2) - 7, height - 100, 15, 15);
		points=0;
		
		long start, duration;

		startGame=System.currentTimeMillis();
		
		while (run) {
			start = System.currentTimeMillis();

			calculateSpeedBlocks();
			respawnBlocks();
			w.clearBackground();		
			paintBlocks();
			moveBlocks();
			movePlayer();
			w.paintPlayer(player);		
			w.showString(20, 50, Integer.toString(points));

			if (collision()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				break;
			}

			duration = System.currentTimeMillis() - start;
			try {
				if (duration < 16) {
					Thread.sleep(16 - duration);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public void calculateSpeedBlocks() {
		long passedTime=System.currentTimeMillis()-startGame;
		speedBlocks=(float) (0.5*Math.log((passedTime/10000.0)+1)+1);
	}
	
	public void paintBlocks() {
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				w.paintBlocks(b[i]);
			}
		}
		w.repaint();
	}
	
	public void moveBlocks() {
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				if (b[i].y > height) {
					b[i] = null;
					points++;
				} else {
					b[i].y = b[i].y + speedBlocks;
				}
			}
		}
	}

	public void movePlayer() {

		if (w.keyPressed[0]) {// w

			if (player.y - speedPlayer > 31) {
				player.y = player.y - speedPlayer;
			} else {
				player.y = 31;
			}

		} else if (w.keyPressed[2]) {// s

			if (player.y + speedPlayer < height - 24) {
				player.y = player.y + speedPlayer;
			} else {
				player.y = height - 24;
			}
		}

		if (w.keyPressed[1]) {// a

			if (player.x - speedPlayer > 8) {
				player.x = player.x - speedPlayer;
			} else {
				player.x = 8;
			}

		} else if (w.keyPressed[3]) {// d

			if (player.x + speedPlayer < width - 24) {
				player.x = player.x + speedPlayer;
			} else {
				player.x = width - 24;
			}

		}
	}

	public boolean collision() {
		boolean collided = false;

		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				if (player.y < b[i].y + b[i].sizeY && player.y + 15 > b[i].y) {
					if (player.x + 15 > b[i].x && player.x < b[i].x + b[i].sizeX)
						collided = true;
				}
			}
		}

		return collided;
	}

	public int createNewBlock() {
		double maxSizeX = width * 0.2;
		double sizeX = maxSizeX - ((Math.random()) * (maxSizeX - 10));

		double factor = Math.random() + 0.5;
		double sizeY = sizeX * factor;

		double x = Math.random() * (width - sizeX + 200) - 100;
		double y = Math.random() * 20;

		boolean saved = false;
		int i = 0;

		while (!saved) {
			if (b[i] == null) {
				b[i] = new Block((int) x, (int) (-sizeY - y), (int) sizeX, (int) sizeY);
				saved = true;
			} else if (i < b.length - 1) {
				i++;
			} else {
				System.err.println("no place in array");
			}
		}
		return (int) sizeY;
	}

	public void respawnBlocks() {
		if (counter <= 0) {
			int s = createNewBlock();
			counter = (int) (s / (2*speedBlocks));
		} else {
			counter--;
		}
	}
}
