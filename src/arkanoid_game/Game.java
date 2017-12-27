package arkanoid_game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, ActionListener, PaddleMove {

    /**
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * @return the map
     */
    public MapGenerator getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(MapGenerator map) {
        this.map = map;
    }

    /**
     * @return the play
     */
    public boolean isPlay() {
        return play;
    }

    /**
     * @param play the play to set
     */
    @Override
    public void setPlay(boolean play) {
        this.play = play;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the totalBricks
     */
    public int getTotalBricks() {
        return totalBricks;
    }

    /**
     * @param totalBricks the totalBricks to set
     */
    public void setTotalBricks(int totalBricks) {
        this.totalBricks = totalBricks;
    }

    /**
     * @return the delay
     */
    public int getDelay() {
        return delay;
    }

    /**
     * @return the playerX
     */
    @Override
    public int getPlayerX() {
        return playerX;
    }

    /**
     * @param playerX the playerX to set
     */
    @Override
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    /**
     * @return the ballPossX
     */
    public int getBallPossX() {
        return ballPossX;
    }

    /**
     * @param ballPossX the ballPossX to set
     */
    public void setBallPossX(int ballPossX) {
        this.ballPossX = ballPossX;
    }

    /**
     * @return the ballPossY
     */
    public int getBallPossY() {
        return ballPossY;
    }

    /**
     * @param ballPossY the ballPossY to set
     */
    public void setBallPossY(int ballPossY) {
        this.ballPossY = ballPossY;
    }

    /**
     * @return the ballXDir
     */
    public int getBallXDir() {
        return ballXDir;
    }

    /**
     * @param ballXDir the ballXDir to set
     */
    public void setBallXDir(int ballXDir) {
        this.ballXDir = ballXDir;
    }

    /**
     * @return the ballYDir
     */
    public int getBallYDir() {
        return ballYDir;
    }

    /**
     * @param ballYDir the ballYDir to set
     */
    public void setBallYDir(int ballYDir) {
        this.ballYDir = ballYDir;
    }

    private final Timer timer;
    private MapGenerator map;
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 60;
    private final int delay = 8;
    private int playerX = 310;
    private int ballPossX = 120;
    private int ballPossY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    public Game() {
        map = new MapGenerator(6, 10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(getDelay(), this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        //background
        g.setColor(Color.white);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        getMap().draw((Graphics2D) g);

        //borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //scores
        g.setColor(Color.black);
        g.setFont(new Font("Crimson Roman", Font.BOLD, 25));
        g.drawString("" + getScore(), 590, 30);

        //the paddle
        g.setColor(Color.red);
        g.fillRect(getPlayerX(), 550, 100, 8);

        //ball
        g.setColor(Color.blue);
        g.fillOval(getBallPossX(), getBallPossY(), 20, 20);

        if (getTotalBricks() <= 0) {
            setPlay(false);
            setBallXDir(0);
            setBallYDir(0);
            g.setColor(Color.gray);
            g.setFont(new Font("Crimson Roman", Font.BOLD, 30));
            g.drawString("You Won, Score: " + getScore(), 250, 300);

            g.setFont(new Font("Crimson Roman", Font.BOLD, 20));
            g.drawString("Press Enter to play again", 250, 350);
        }

        if (getBallPossY() > 570) {
            setPlay(false);
            setBallXDir(0);
            setBallYDir(0);
            g.setColor(Color.gray);
            g.setFont(new Font("Crimson Roman", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + getScore(), 200, 300);

            g.setFont(new Font("Crimson Roman", Font.BOLD, 20));
            g.drawString("Press Enter to replay the game", 200, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getTimer().start();
        if (isPlay()) {
            if (new Rectangle(getBallPossX(), getBallPossY(), 20, 20).intersects(new Rectangle(getPlayerX(), 550, 100, 8))) {
                setBallYDir(-getBallYDir());
            }
            for (int i = 0; i < getMap().getMap().length; i++) {
                for (int j = 0; j < getMap().getMap()[0].length; j++) {
                    if (getMap().getMap()[i][j] > 0) {
                        int brickX = j * getMap().getBrickWidth() + 80;
                        int brickY = i * getMap().getBrickHeight() + 50;
                        int brickWidth = getMap().getBrickWidth();
                        int brickHeigth = getMap().getBrickHeight();

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeigth);
                        Rectangle ballRect = new Rectangle(getBallPossX(), getBallPossY(), 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            getMap().setBrickValue(0, i, j);
                            setTotalBricks(getTotalBricks() - 1);
                            setScore(getScore() + 2);

                            if (getBallPossX() + 19 <= brickRect.x || getBallPossX() + 1 >= brickRect.x + brickRect.width) {
                                setBallXDir(-getBallXDir());
                            } else {
                                setBallYDir(-getBallYDir());
                            }
                        }
                    }
                }
            }

            setBallPossX(getBallPossX() + getBallXDir());
            setBallPossY(getBallPossY() + getBallYDir());
            if (getBallPossX() < 0) {
                setBallXDir(-getBallXDir());
            }
            if (getBallPossY() < 0) {
                setBallYDir(-getBallYDir());
            }
            if (getBallPossX() > 670) {
                setBallXDir(-getBallXDir());
            }
        }

        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int d = e.getKeyCode();
        if (d == KeyEvent.VK_RIGHT) {
            if (getPlayerX() >= 600) {
                setPlayerX(600);
            } else {
                moveRight();
            }
        }
        if (d == KeyEvent.VK_LEFT) {
            if (getPlayerX() < 10) {
                setPlayerX(10);
            } else {
                moveLeft();
            }
        }
        if (d == KeyEvent.VK_ENTER) {
            if (!isPlay()) {
                setPlay(true);
                setBallPossX(120);
                setBallPossY(350);
                setBallXDir(-1);
                setBallYDir(-2);
                setPlayerX(310);
                setScore(0);
                setTotalBricks(80);
                setMap(new MapGenerator(6, 10));

                repaint();
            }
        }

    }

    @Override
    public void moveRight() {
        setPlay(true);
        setPlayerX(getPlayerX() + 20);
    }

    @Override
    public void moveLeft() {
        setPlay(true);
        setPlayerX(getPlayerX() - 20);
    }

}
