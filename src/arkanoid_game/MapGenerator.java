package arkanoid_game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

    /**
     * @return the map
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(int[][] map) {
        this.map = map;
    }

    /**
     * @return the brickWidth
     */
    public int getBrickWidth() {
        return brickWidth;
    }

    /**
     * @param brickWidth the brickWidth to set
     */
    public void setBrickWidth(int brickWidth) {
        this.brickWidth = brickWidth;
    }

    /**
     * @return the brickHeight
     */
    public int getBrickHeight() {
        return brickHeight;
    }

    /**
     * @param brickHeight the brickHeight to set
     */
    public void setBrickHeight(int brickHeight) {
        this.brickHeight = brickHeight;
    }

    private int map[][];
    private int brickWidth;
    private int brickHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int[] map1 : map) {
            for (int j = 0; j < map[0].length; j++) {
                map1[j] = 1;
            }
        }

        brickWidth = 540 / col;
        brickHeight = 220 / row;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < getMap().length; i++) {
            for (int j = 0; j < getMap()[0].length; j++) {
                if (getMap()[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * getBrickWidth() + 80, i * getBrickHeight() + 50, getBrickWidth(), getBrickHeight());

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * getBrickWidth() + 80, i * getBrickHeight() + 50, getBrickWidth(), getBrickHeight());
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        getMap()[row][col] = value;
    }
}
