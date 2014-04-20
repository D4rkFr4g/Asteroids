import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Write a description of class KeyCatcher here.
 * 
 * @author Zane Melcho 
 */
public class KeyCatcher implements KeyListener
{
    /**
     * Method is fired everytime a key is pressed
     * @param e KeyEvent object describing the key pressed
     */
    public void keyPressed(KeyEvent e)
    {
        //System.out.println("Key = " + e.getKeyChar() + " = " + e.getKeyCode());
        
        key = e.getKeyCode();
        
        if (key == 32)
            space = true;
        else if (key == 38)
            up = true;
        else if (key == 37)
            left = true;
        else if (key == 39)
            right = true;
        else if (key == 40)
            down = true;
        else if (key == 10)
            enter = true;
        else if (key == 27)
            esc = true;

    }
    
    /**
     * Method is fired everytime a key is let go.
     * @param e KeyEvent object describing the key released.
     */
    public void keyReleased(KeyEvent e)
    {
        //System.out.println("Key = " + e);
        key = e.getKeyCode();
        
        if (key == 32)
            space = false;
        else if (key == 38)
            up = false;
        else if (key == 37)
            left = false;
        else if (key == 39)
            right = false;
        else if (key == 40)
            down = false;
        else if (key == 10)
            enter = false;
        else if (key == 27)
            esc = false;
            
    }
    
    /**
     * Method is fired everytime a typing key is held down
     * @param e KeyEvent object describing the key typed
     */
    public void keyTyped(KeyEvent e)
    {
        //System.out.println("Key = " + e);
    }
    
    /**
     * Retrieves boolean of whether or not the space key is currently being pressed.
     * @return space True if space key is held down false if not held down. 
     */
    public static boolean spacePressed()
    {
        return space;    
    }
    
    /**
     * Retrieves boolean of whether or not the up key is currently being pressed.
     * @return up True if up key is held down false if not held down. 
     */
    public static boolean upPressed()
    {
        return up;
    }
    
    /**
     * Retrieves boolean of whether or not the left key is currently being pressed.
     * @return left True if left key is held down false if not held down. 
     */
    public static boolean leftPressed()
    {
        return left;
    }
    
    /**
     * Retrieves boolean of whether or not the right key is currently being pressed.
     * @return right True if right key is held down false if not held down. 
     */
    public static boolean rightPressed()
    {
        return right;
    }
    
    /**
     * Retrieves boolean of whether or not the down key is currently being pressed.
     * @return down True if down key is held down false if not held down. 
     */
    public static boolean downPressed()
    {
        return down;
    }
    
    /**
     * Retrieves boolean of whether or not the enter key is currently being pressed.
     * @return enter True if enter key is held down false if not held down. 
     */
    public static boolean enterPressed()
    {
        return enter;
    }
    
    /**
     * Retrieves boolean of whether or not the esc key is currently being pressed.
     * @return esc True if esc key is held down false if not held down. 
     */
    public static boolean escPressed()
    {
        return esc;
    }
    
    
    private static int key;
    private static boolean space;
    private static boolean up;
    private static boolean left;
    private static boolean right;
    private static boolean down;
    private static boolean enter;
    private static boolean esc;

}
