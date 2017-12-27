package arkanoid_game;

import javax.swing.*;
import junit.framework.TestCase;

public class TestWindow extends TestCase {
    
    JFrame wind;
    
    public TestWindow() {
    }
    
    public void setUp() {
        wind = new JFrame();
        wind.setVisible(true);
    }
    
    public void tearDown() {
        wind.dispose();
    }
    
    public void testIsShowing(){
        assertTrue(wind.isShowing());
    }
    
    
}
