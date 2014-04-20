import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Write a description of class Bullet here.
 * 
 * @author Zane Melcho 
 * @version 0.0.1
 * 11/10/09 7:50pm - 12:08AM
 */
public class Bullet implements Drawable
{
    /**
     * Constructor for objects of class Bullet
     * @param x X-coordinate.
     * @param y Y-coordinate.
     * @param xVelocity The speed of movement in the x axis.
     * @param yVelocity The speed of movement in the y axis.
     */
    public Bullet(double x, double y, double middleX, double middleY, double speed, double angle, int fWidth, int fHeight)
    {
          try 
          {
             image = ImageIO.read(new File("bullet.png"));
          }
          catch (IOException ex)
          {
             System.out.println("Can't read image.");
          }

        this.x = x;
        this.y = y;
        this.middleX = middleX;
        this.middleY = middleY;
        this.startX = x;
        this.startY = y;
        this.speed = speed;
        this.angle = angle;
        this.fWidth = fWidth;
        this.fHeight = fHeight;
        
        boundary = new Point2D.Double(x, y);
        
        //Sets the intial velocities plus the current speed of the object that shoots the bullets
        xVelocity = Math.cos(Math.toRadians(this.angle)) * (speed + BULLET_SPEED);
        yVelocity = Math.sin(Math.toRadians(this.angle)) * (speed +  BULLET_SPEED);   
        
    }
    
    /**
     * Moves the bullet once in the direction it is traveling
     */
    public void move()
    {        
        // Controls wrap around in the x-axis
        if (x >= fWidth)
        {
            double temp =  middleX - x;
            x = -1 * image.getWidth(null);
            middleX = x + temp;
        }
        else if (x <= -image.getWidth(null))
        {
            double temp = middleX - x;
            x = fWidth;
            middleX = x + temp;
        }
        // Controls wrap around in the y-axis
        if (y >= fHeight) //Right side of screen
        {
            double temp = middleY - y;
            y = -1 * image.getHeight(null);
            middleY = y + temp;
        }
        else if (y <= -image.getHeight(null)) //Left side of screen
        {
            double temp = middleY - y;
            y = fHeight;
            middleY = y + temp;
        }
        
        // Update the x,y values to actually move the bullet object
        x += xVelocity;
        y += yVelocity;
        
        //Update the midde x,y coordinates of the object that shoots the bullets.
        middleX += xVelocity;
        middleY += yVelocity;
        
        
        // Calculates the distance traveled for each time the bullet moves.
        double dX = Math.abs(xVelocity);
        double dY = Math.abs(yVelocity);
        
        distance += Math.sqrt((dX * dX) + (dY * dY));
    }
    
    /**
     * Retrieves the X-coordinate
     * @return x The X-coordinate.
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * Retrieves the Y-coordinate
     * @return y The Y-coordinate.
     */
    public double getY()
    {
        return y;
    }
    
     /**
     * Retrieves the starting X-coordinate
     * @return x The starting X-coordinate.
     */
    public double getStartX()
    {
        return startX;
    }
    
    /**
     * Retrieves the starting Y-coordinate
     * @return y The starting Y-coordinate.
     */
    public double getStartY()
    {
        return startY;
    }
    
    /**
     * Retrieves the speed along the X axis
     * @return xVelocity The speed along the X axis.
     */
    public double getXVelocity()
    {
        return xVelocity;
    }
    
    /**
     * Retrieves the speed along the Y axis
     * @return yVelocity The speed along the Y axis.
     */
    public double getYVelocity()
    {
        return yVelocity;
    }
    
    /**
     * Calculates the total distance traveled by the bullet
     * @return The distance in pixels the bullet has traveled.
     */
    public double distanceTraveled()
    {
        return distance;
    }
    
    /**
     * Retrieves the boundary of the bullet
     * @return Returns the boundary shape of the bullet object
     */
    public Point2D.Double getBoundary()
    {
        return boundary;
    }
    
    /**
     * Draws a bullet
     * @param g2 the graphics object on which to draw this bullet
     */
     public void draw(Graphics2D g2)
     {   
         AffineTransform identity = new AffineTransform();
         AffineTransform trans = new AffineTransform();
         AffineTransform bound = new AffineTransform();
         
         // Sets the identity to the top left of the ship
         identity.translate(middleX - image.getWidth(null) / 2, y);
         trans.setTransform(identity);
         
         bound.setTransform(identity);
         // Sets the boundary point to the top center of the ship
         bound.translate(image.getWidth(null) / 2, 0);
         
         //Rotates the bullet and boundary on the Rotation point that is in the middle of the object that shoots the bullets
         trans.rotate(Math.toRadians(angle + 90), image.getWidth(null) / 2, (middleY - y));
         bound.rotate(Math.toRadians(angle + 90), 0, (middleY - y));
         
         g2.drawImage(image, trans, null);

         //Boundary point for Collision detection
         boundary = new Point2D.Double(bound.getTranslateX(), bound.getTranslateY());
         
/*         
         //Displays points for the boundary and for the rotational point
         g2.setColor(Color.BLUE);
         g2.fill(new Ellipse2D.Double(boundary.getX(), boundary.getY(), 2, 2));
         
         g2.fill(new Ellipse2D.Double(middleX, middleY, 5, 5)); 
*/         
     }
    
    private double xVelocity;
    private double yVelocity;
    private double x;
    private double y;
    private double middleX;
    private double middleY;
    private double startX;
    private double startY;
    private int fWidth;
    private int fHeight;
    private double angle;
    private double speed;
    private double distance = 0;
    private final static double BULLET_SPEED = 5;// * 2;
    private Image image;
    private Point2D.Double boundary;
}
