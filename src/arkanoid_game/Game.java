package arkanoid_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, ActionListener {
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 40;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	private int ballPossX = 120;
	private int ballPossY = 350;
	private int ballXDir = -1;
	private int ballYDir = -2;
	
	private MapGenerator map;
	
	public Game() {
		map = new MapGenerator(5, 8);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.white);
		g.fillRect(1, 1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//scores
		g.setColor(Color.black);
		g.setFont(new Font("Crimson Roman", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		//the paddle
		g.setColor(Color.red);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.blue);
		g.fillOval(ballPossX, ballPossY, 20, 20);
		
		if(totalBricks <=0) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.gray);
			g.setFont(new Font("Crimson Roman", Font.BOLD, 30));
			g.drawString("You Won, Score: "+score, 250, 300);
			
			g.setFont(new Font("Crimson Roman", Font.BOLD, 20));
			g.drawString("Press Enter to play again", 250, 350);
		}
		
		if(ballPossY > 570) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.gray);
			g.setFont(new Font("Crimson Roman", Font.BOLD, 30));
			g.drawString("Game Over, Score: "+score, 200, 300);
			
			g.setFont(new Font("Crimson Roman", Font.BOLD, 20));
			g.drawString("Press Enter to replay the game", 200, 350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballPossX, ballPossY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYDir = - ballYDir;
			}
			A: for(int i = 0; i<map.map.length; i++) {
				for(int j = 0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeigth = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeigth);
						Rectangle ballRect = new Rectangle(ballPossX, ballPossY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score +=2;
							
							if(ballPossX + 19<=brickRect.x || ballPossX + 1 >= brickRect.x + brickRect.width) {
								ballXDir = -ballXDir;
							} else {
								ballYDir = -ballYDir;
							}
							
							break A;
						}
					}
				}
			}
			
			ballPossX += ballXDir;
			ballPossY += ballYDir;
			if(ballPossX < 0) {
				ballXDir = -ballXDir;
			}
			if(ballPossY < 0) {
				ballYDir = -ballYDir;
			}
			if(ballPossX > 670) {
				ballXDir = -ballXDir;
			}
		}
		
		repaint();
		
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPossX = 120;
				ballPossY = 350;
				ballXDir = -1;
				ballYDir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 80;
				map = new MapGenerator(5, 8);
				
				repaint();
			}
		}
		
	}
	public void moveRight() {
		play = true;
		playerX +=20;
	}
	public void moveLeft() {
		play = true;
		playerX -=20;
	}

}
