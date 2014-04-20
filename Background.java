import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Object that holds a background image to be displayed
 * 
 * @author Zane Melcho
 */
public class Background implements Drawable
{
    /**
     * Constructor for Background object
     */
    public Background()
    {
        try 
          {
             image = ImageIO.read(new File("starsbg.png"));
          }
          catch (IOException ex)
          {
             System.out.println("Can't read image.");
          }
    }

    /**
     * Draws the background image.
     * @param g2 A Graphics2D object
     */
    public void draw(Graphics2D g2)
    {
       g2.drawImage(image, 0, 0, null);
    }
    
    private Image image;
}

