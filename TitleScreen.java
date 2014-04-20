import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * Displays the starting Title Screen Graphics
 * 
 * @author Zane Melcho
 */
public class TitleScreen implements Drawable
{
    /**
     * Constructor for objects of class TitleScreen
     * @param frameWidth The Width of the frame window
     * @param frameHeight The Height of the frame window
     */
    public TitleScreen(int frameWidth, int frameHeight)
    {
        try 
          {
             image = ImageIO.read(new File("logo.png"));
          }
          catch (IOException ex)
          {
             System.out.println("Can't read image.");
          }
          
          this.frameWidth = frameWidth;
          this.frameHeight = frameHeight;
          
          asteroidList = new ArrayList<Asteroid>();
          Random r = new Random();
          
          //Creates 10 random asteroids
          for (int i = 1; i <= 10; i++)
          {
            int x = r.nextInt(frameWidth - GameEngine.ASTEROID_WIDTH);
            int y = r.nextInt(frameHeight - GameEngine.ASTEROID_HEIGHT);
            double angle = r.nextDouble() * 359;
            
            int size = r.nextInt(3) + 1;
            
            asteroidList.add(new Asteroid(x, y, angle, size, frameWidth,frameHeight ));
          }
 
    }
    
    /**
     * Moves Asteroids on the title screen
     */
    public void update()
    {        
        for (Asteroid asteroid : asteroidList)
            asteroid.move();
    }
    
    /**
     * Retrieves the Asteroids to be drawn
     * @return asteroidList The ArrayList contiaining asteroids to be drawn.
     */
    public ArrayList<Asteroid> getDrawing()
    {
        return asteroidList;
    }

    /**
     * Draws titleScreen object.
     * @param g2 A Graphics2D object
     */
    public void draw(Graphics2D g2)
    {
        g2.drawImage(image,frameWidth / 2 - (image.getWidth(null) / 2), frameHeight / 2 - (image.getHeight(null) / 2), null);
        
        // Sets the Font to selected Font
        g2.setFont(new Font ("Eras Demi ITC",Font.BOLD, 24));
        g2.setColor(Color.WHITE);
        //Draws the Text on the screen in the upper left corner
        g2.drawString("Coded By: Jeremy Lozano, Tin Tran, and Zane Melcho", (frameWidth / 2) - 340,frameHeight - 50);
        
        g2.setFont(new Font ("Eras Demi ITC",Font.BOLD, 14));
        g2.drawString("Press Enter to Play", (frameWidth / 2) - 65, (frameHeight / 2) + 50);
    }
    
    private Image image;
    private int frameWidth;
    private int frameHeight;
    private ArrayList<Asteroid> asteroidList;
}
