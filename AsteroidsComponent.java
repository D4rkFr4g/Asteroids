import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.util.ArrayList;

/**
 * Component that holds and draws all objects in the Asteroids game
 * @author Zane Melcho
 */
public class AsteroidsComponent extends JComponent
{  
   
    /**
     * Creates a AsteroidsComponent object
     * @param drawableList An array list that includes all objects to be drawn on screen.
     */
    public AsteroidsComponent(ArrayList<Drawable> drawableList)
    {
        this.drawableList = drawableList;
    }

    /**
     * Paints all objects on screen
     * @param g A Graphics Object
     */
   public void paintComponent(Graphics g)
   {  
       Graphics2D g2 = (Graphics2D) g;
        
       for(Drawable drawables : drawableList)
        drawables.draw(g2);       
   }
   
   private ArrayList<Drawable> drawableList;
}