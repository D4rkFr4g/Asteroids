import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


/**
 * Hud object overlays the top of the screen with game information for the player.
 * 
 * @author Zane Melcho
 */
public class Hud implements Drawable
{

    /**
     * Constructor for objects of class HUD
     * @param currentScore The ScoreKeeper object that tracks lives and score.
     * @param frameWidth The width of the frame window.
     * @param frameHeight The height of the frame window.
     */
    public Hud(ScoreKeeper currentScore, double frameWidth, double frameHeight)
    {
        try 
          {
             image = ImageIO.read(new File("lives.png"));
          }
          catch (IOException ex)
          {
             System.out.println("Can't read image.");
          }
        
        this.currentScore = currentScore;
        this.frameWidth = (float)frameWidth;
        this.frameHeight = (float)frameHeight;
     
        isGameOver = false;
        
    }
        
    /**
     * Draws the Hud object on the screen
     */
    public void draw(Graphics2D g2)
    {
        // Sets the Font to selected Font
        g2.setFont(new Font ("Eras Demi ITC",Font.BOLD, 24));
        g2.setColor(Color.WHITE);
        //Draws the Text on the screen in the upper left corner
        g2.drawString("Score: " + currentScore.getScore(), 20,20);
        g2.drawString("Multiplier x" + currentScore.getBonus(), frameWidth - 180, 20);
        g2.drawString("Lives:", 250, 20);       
        
        // Draws the number of ships for lives left
        int spacing = 320;
        for (int i = 0; i < currentScore.getLives(); i++)
        {
            g2.drawImage(image, spacing, 5, null);
            spacing += image.getWidth(null) + 5;
        }

        // Game Over text
        if (currentScore.getLives() <= 0)
        {
            g2.drawString("Game Over", frameWidth / 2 - 40 , frameHeight / 2);
            g2.drawString("Press Enter to Play Again", frameWidth / 2 - 120, frameHeight / 2  + 20);
        }
        
        g2.setColor(Color.BLACK);
    }
    
    private ScoreKeeper currentScore;
    private boolean isGameOver;
    private float frameWidth;
    private float frameHeight;
    private Image image;
}
