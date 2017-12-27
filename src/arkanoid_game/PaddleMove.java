package arkanoid_game;

public interface PaddleMove {

    /**
     * @return the playerX
     */
    int getPlayerX();

    void moveLeft();

    void moveRight();

    /**
     * @param play the play to set
     */
    void setPlay(boolean play);

    /**
     * @param playerX the playerX to set
     */
    void setPlayerX(int playerX);
    
}
