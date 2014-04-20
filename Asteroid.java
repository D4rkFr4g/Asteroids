/**
  Creates an asteroid, moves its location
  @author Jeremy Lozano
*/

import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.geom.AffineTransform;
import java.util.Random;


public class Asteroid implements Drawable
{
    /**
      Constructs a new asteroid.
      @param xPos the x position
      @param yPos the y position
      @param angle the angle of the asteroid
      @param aSize the size of the asteroid, size can be 1 (small), 2 (medium), or 3 (large).
      @param frameWidth the width of the frame
      @param frameHeight the height of the frame
     */
    public Asteroid(double xPos, double yPos, double anAngle, int aSize, int frameWidth, int frameHeight)
    {
        generator = new Random();
        x = xPos;
        y = yPos;
        fWidth = frameWidth;
        fHeight = frameHeight;
        angle = anAngle;
        rotAngle = anAngle;
        size = aSize;
        
        //Small Asteroid
        if (aSize == 1)
        {
          xVel = SMALL_SPEED * Math.cos(Math.toRadians(angle));
          yVel = SMALL_SPEED * Math.sin(Math.toRadians(angle));
          try 
          {
             image = ImageIO.read(new File("small_asteroid.png"));
          }
          catch (IOException ex)
          {
             System.out.println("Can't read image.");
          }
            boundary = new Rectangle2D.Double(x + (image.getWidth(null)*.25), y + (image.getHeight(null)*.25) , image.getWidth(null) *.5, image.getHeight(null) *.5);
            points = SMALL_POINTS;
        }
        
        //Medium Asteroid
        else if (aSize == 2)
        {
          xVel = MEDIUM_SPEED * Math.cos(Math.toRadians(angle));
          yVel = MEDIUM_SPEED * Math.sin(Math.toRadians(angle));
          try 
          {
             image = ImageIO.read(new File("medium_asteroid.png"));
          }
          catch (IOException ex)
          {
             System.out.println("Can't read image.");
          }
            boundary = new Rectangle2D.Double(x + (image.getWidth(null)*.1), y + (image.getHeight(null)*.1) , image.getWidth(null) *.5, image.getHeight(null) *.5);
            points = MED_POINTS;
        }
        
        //Large Asteroid
        else if (aSize == 3)
        {
          xVel = LARGE_SPEED * Math.cos(Math.toRadians(angle));
          yVel = LARGE_SPEED * Math.sin(Math.toRadians(angle));
          try 
          {
             image = ImageIO.read(new File("large_asteroid.png"));
          }
          catch (IOException ex)
          {
             System.out.println("Can't read image.");
          }
            boundary = new Rectangle2D.Double(x + (image.getWidth(null)*.1), y + (image.getHeight(null) *.1) , image.getWidth(null) * .8, image.getHeight(null) * .8);
            points = LAR_POINTS;
        }
        
        //Sets the direction of the asteroid, 50/50 chance
         int rand = generator.nextInt(2);
         direction = 1;
         if (rand == 0) direction *= -1;
    }
    
    /**
      Moves the asteroid, resets it to the other side of the frame if it is "out of bounds".
     */
    public void move()
    {
        int width = image.getWidth(null);
        int height = image.getWidth(null);
        
        if (x >= fWidth) //x moves out of bounds right
        {
            x = -1 * width;
        }
        else if (x < (0 - width)) //x moves out of bounds left
        {
            x = fWidth;
        }     
        if (y >= fHeight) //y moves out of bounds down
        {
            y = -1 * height;
        }
        else if (y <= (0 - height)) //y moves out of bounds up
        {
            y = fHeight;
        }

        x += xVel;
        y += yVel;
        
        Rectangle2D.Double temp =  new Rectangle2D.Double(x + (image.getWidth(null)*.1), y + (image.getHeight(null) *.1) , image.getWidth(null) * .8, image.getHeight(null) * .8);
        boundary = (Rectangle2D.Double) temp.clone();
        
         //changes the rotational angle to rotate the asteroid
         if (size == 1) rotAngle += (FAST_ANGLE) * direction;
         if (size == 2) rotAngle += (MEDIUM_ANGLE) * direction;
         else if (size == 3) rotAngle += (SLOW_ANGLE) * direction;
    }
    
    /**
      Returns the x position of the asteroid.
      @return x
     */
    public double getX()
    {
        return x;
    }

    /**
      Returns the y position of the asteroid.
      @return y
     */
    public double getY()
    {
        return y;
    }
    
    /**
      Returns the boundary of the asteroid.
      @return boundary
     */
    public Rectangle2D.Double getBoundary()
    {
        return boundary;
    }
    
    /**
      Returns the x velocity of the asteroid.
      @return xVel
     */
    public double getXVelocity()
    {
        return xVel;
    }
    
    /**
      Returns the y velocity of the asteroid.
      @return yVel
     */
    public double getYVelocity()
    {
        return yVel;
    }
    
    /**
      Returns the point value.
      @return points
     */
    public int getPoints()
    {
        return points;
    }
    
    /**
      Returns the velocity angle of the asteroid
      @return angle
     */
    public double getAngle()
    {
        return angle;
    }
    
    /**
      Returns the size of the asteroid
      @return size
     */
    public double getSize()
    {
        return size;
    }
    
    /**
      Draws the asteroid.
     */
    public void draw(Graphics2D g2)
     {
         
         AffineTransform identity = new AffineTransform();
         AffineTransform trans = new AffineTransform();        
         
         identity.translate(x,y);
         trans.setTransform(identity);
         trans.rotate(Math.toRadians(rotAngle), image.getWidth(null) / 2, image.getHeight(null) / 2);
         
         g2.drawImage(image, trans, null);
         
         //g2.draw(boundary);
         //g2.draw(new Rectangle2D.Double(x,y, image.getWidth(null), image.getHeight(null)));
     }
    

    //The final points and speed values can be set here.
    private static final int SMALL_SPEED = 3;// * 2; //Small Asteroid speed
    private static final int MEDIUM_SPEED = 2;// * 2; //Medium Asteroid speed
    private static final int LARGE_SPEED = 1;// * 2; //Large Asteroid speed
    private static final int SMALL_POINTS= 75; //Small Asteroid points
    private static final int MED_POINTS= 50; //Medium Asteroid points
    private static final int LAR_POINTS= 25; //Large Asteroid points
    private static final double FAST_ANGLE = 1;// * 2; //Angle speed for small asteroid
    private static final double MEDIUM_ANGLE = .5;// * 2; //Angle speed for medium asteroid
    private static final double SLOW_ANGLE = .25;// * 2; //Angle speed for large asteroid
    
    private int points, size, direction; //point value for each asteroid size, the size of each asteroid, sets the direction of the asteroid
    private static int fWidth, fHeight; //frame width, frame height
    private double x, y, xVel, yVel, angle, rotAngle; /*x position, y position, x velocity, y velocity,
                                                        the angle of the asteroid's velocity, the rotating angle of the asteroid
                                                      */
    private Rectangle2D.Double boundary; //the bounding rectangle
    private Image image; //The asteroid image
    private Random generator;
    
}