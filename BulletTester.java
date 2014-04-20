import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Random;


/**
 * Write a description of class BulletTester here.
 * 
 * @author Zane Melcho
 */
public class BulletTester 
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(1280, 768);
        frame.setTitle("BulletTester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Random r = new Random();
        
        ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
        ArrayList<Drawable> drawableList = new ArrayList<Drawable>();
        
        // Fills the ArrayList with a number of bullets.
        for (int i = 1; i < 10; i++)
        {
            double angle = r.nextDouble() * 359;
            
            bulletList.add(new Bullet(frame.getWidth() / 2, frame.getHeight() / 2, frame.getWidth() / 2, frame.getHeight() / 2, 5, angle, 1280, 768));
        }
        
        
        //Creates the drawing component and adds it to the frame.
        AsteroidsComponent component = new AsteroidsComponent(drawableList);
        component.setDoubleBuffered(true);
        frame.add(component);
        frame.setVisible(true);
        
        
        boolean gameOver = false;
            while(!gameOver)
            {
                for (Bullet bullet : bulletList)
                bullet.move();
                
                for (int i = 0; i < bulletList.size(); i++)
                {      
                    if (bulletList.get(i).distanceTraveled() > 300)
                    bulletList.remove(i);
                }
                
                //Pause.pause(100);
                
                int listSize = drawableList.size();
                
                for (int k = 0; k < listSize; k++)
                    drawableList.remove(0);
                
                for (Bullet bullet : bulletList)
                    drawableList.add(bullet);
                
                component.paintImmediately(0,0,frame.getWidth(),frame.getHeight());                
            }
    }
    
}
