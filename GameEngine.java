import java.util.ArrayList;
import javax.swing.JFrame;
import java.util.Random;
import java.awt.geom.Rectangle2D;
import java.util.Date;


/**
 * GameEngine runs the Asteroids game using all available classes. 
 * @author Zane Melcho
 * @version 2.0.0
 */
public class GameEngine
{
    public static void main(String[] args)
    {
        //Sets up and initializes the JFrame object
        JFrame frame = new JFrame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setTitle("Asteroids");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Initial variables not changed by the playAgain loop
        Random r = new Random();
        boolean playAgain = true;
        boolean soundsLoaded = false;
        boolean skipTitle = false;
        
        while(playAgain)
        {
            
            // Initial ArrayLists
            ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
            ArrayList<Asteroid> asteroidList = new ArrayList<Asteroid>();
            ArrayList<Drawable> drawableList = new ArrayList<Drawable>();
            ArrayList<ScoreKeeper> scoreList = new ArrayList<ScoreKeeper>();

            // Initial Objects
            Ship firefly = new Ship(FRAME_WIDTH / 2 - (SHIP_WIDTH / 2), FRAME_HEIGHT / 2 - (SHIP_HEIGHT / 2), STARTING_ANGLE, SHIP_ACCELERATION, MAX_SPEED, FRAME_WIDTH, FRAME_HEIGHT);
            ScoreKeeper currentScore = new ScoreKeeper(STARTING_LIVES);
            CollisionDetector ruth = new CollisionDetector();
            Hud hud = new Hud(currentScore, FRAME_WIDTH, FRAME_HEIGHT);
            Background bg = new Background();
            
            // Initial Variables
            int level = 0;

            boolean isRespawning = false;
            
            
           TitleScreen titleScreen = new TitleScreen(FRAME_WIDTH, FRAME_HEIGHT);

           //sets up the very first screen before loading prevents a blank screen when play again.
          drawableList.add(bg);
          
            // Sets up the Component to be drawn
            AsteroidsComponent asteroidsComponent = new AsteroidsComponent(drawableList);
            asteroidsComponent.setDoubleBuffered(true);
            // Adds Key Listener and gives it focus.
            asteroidsComponent.addKeyListener(new KeyCatcher());
            frame.add(asteroidsComponent);
            asteroidsComponent.setFocusable(true);
            frame.setVisible(true);

            // Checks to see if the sounds are loaded and loads them if they haven't been already
            if (!soundsLoaded)
            {
                SoundEffect.init();
                soundsLoaded = true;
            }
            

            
//************************************************************                
// **********    Title Screen   ********************************
//************************************************************                
            //SoundEffect.volume = SoundEffect.Volume.MUTE
            
            long clock = 0;
            long titleClock = 0;
            
            if (!skipTitle)
            {
                // Plays the Title Screen Music
                SoundEffect.TITLE.play();    
                
                while (!KeyCatcher.enterPressed())
                {
                    Date start = new Date();
            
                    //Clear the ArrayLists
                    drawableList.clear();
                    
                    drawableList.add(bg);
                    ArrayList<Asteroid> titleList = titleScreen.getDrawing();
                
                    for (Asteroid asteroid : titleList)
                    {
                        drawableList.add(asteroid);
                    }

                    drawableList.add(titleScreen);
                    
                    asteroidsComponent.paintImmediately(asteroidsComponent.getX(), asteroidsComponent.getY(), asteroidsComponent.getWidth(), asteroidsComponent.getHeight());

                    if (clock >= titleClock + MOVEMENT_INTERVAL)
                    {
                        titleScreen.update();
                        titleClock = clock;
                    }
                    
                    Date end = new Date();
                    long timePerFrame = (end.getTime() - start.getTime());
                    clock += timePerFrame;
                }
            }

            frame.setVisible(true);
            
            //Beginning of game after Title Screen
            boolean gameOver = false;
            SoundEffect.TITLE.stop();

            // Timers
            clock = 0;
            long moveClock = 0;
            long keyClock = 0;
            long hyperSpaceClock = 0;
            long bulletClock = 0;

            
//************************************************************                
// **********    Game Start   *********************************
//************************************************************                            
            while(!gameOver)
            {
                Date start = new Date();
                SoundEffect.THEME.loop();

//************************************************************                
// **********    Create all the Asteroids for the Level   ******
//************************************************************                
                // If all asteroids are destroyed start next level with more asteroids
                if (asteroidList.size() == 0)
                {
                    level++;
                    
                    int shipLocation = 0;
                    //Finds out which third of the screen the ship is in
                    if (firefly.getX() < FRAME_WIDTH / 3)
                        shipLocation = 0;
                    else if (firefly.getX() >= FRAME_WIDTH / 3 && firefly.getX() < (FRAME_WIDTH * 2) / 3)
                        shipLocation = 1;
                    else
                        shipLocation = 2;
                    
                    int thirdWidth = (FRAME_WIDTH / 3);
                    for (int k = 0; k < STARTING_ASTEROIDS + level; k++)
                    {
                        // Ensures the ship section has no asteroids
                        int asteroidX = r.nextInt(thirdWidth - ASTEROID_WIDTH) + ((shipLocation + 1) % 3) * thirdWidth; //Places an Asteroid in the third of the screen to the right of the ship
                        if (r.nextInt(2) == 0)
                            asteroidX = (asteroidX + thirdWidth) % FRAME_WIDTH; //Places an Asteroid in the other third of the screen
                        
                        int asteroidY = r.nextInt(frame.getHeight() - ASTEROID_HEIGHT); // Starts the y value on the screen
                        
                        int asteroidAngle = r.nextInt(360);
                        
                        asteroidList.add(new Asteroid(asteroidX, asteroidY, asteroidAngle, LARGE, frame.getWidth(), frame.getHeight()));
                    }  
                }
                
//************************************************************                
// **********    Check for Key Presses   ********************
//************************************************************
        
                if (clock >= keyClock + KEY_INTERVAL && currentScore.getLives() > 0 && !isRespawning)
                {
                    if (KeyCatcher.spacePressed()) //Space bar
                    {
                        // Check to see if the number of bullets fired is less than the max 
                        // Check to see if the time between shots is sufficient 
                        // If both are true a bullet is fired.
                        if (clock >= bulletClock + BULLET_INTERVAL && bulletList.size() < BULLET_MAX)
                        {
                            bulletList.add(new Bullet(firefly.getX(), firefly.getY(), firefly.getMiddleX(), firefly.getMiddleY(), firefly.getVelocity(), firefly.getAngleOfDirection(), FRAME_WIDTH, FRAME_HEIGHT));    
                            SoundEffect.PHASER.play();
                            bulletClock = clock;
                        }
                        
                    } 
               
                    if (KeyCatcher.upPressed()) // Up
                    {
                        firefly.thrust();
                        SoundEffect.THRUST.loop();
                    }
                    else
                    {
                        SoundEffect.THRUST.stop();
                        firefly.stopThrust();
                    }

                    if (KeyCatcher.leftPressed()) //Left
                    {
                        firefly.rotateShip(LEFT);
                    }
                 
                    if (KeyCatcher.rightPressed()) //Right
                    {
                        firefly.rotateShip(RIGHT);
                    }
                 
                    if (KeyCatcher.downPressed()) //Down
                    {
                        if (clock >= hyperSpaceClock + HYPER_INTERVAL)
                        {
                            SoundEffect.HYPER.play();
                            firefly.hyperSpace();
                            hyperSpaceClock = clock;
                        }
                    }
                    
                    keyClock = clock;
                }
  
               
//************************************************************                
// **********    Move all objects   ****************************
//************************************************************                
                if (clock >= moveClock + MOVEMENT_INTERVAL) // Moves every 10ms
                {
                    firefly.move();
                
                    for (Asteroid asteroid : asteroidList)
                        asteroid.move();
                
                    for (Bullet bullet : bulletList)
                        bullet.move();
                
                    // If the bullet travels past a certain distance the bullet is removed from the arrayList
                    for (int i = 0; i < bulletList.size(); i++)
                    {      
                        if (bulletList.get(i).distanceTraveled() > BULLET_DISTANCE)
                            bulletList.remove(i);
                    }
                    
                    moveClock = clock;
                }
               
//************************************************************                
// **********    Check for Collisions   ***********************
//************************************************************
                //Checks for Bullets colliding with Asteroids
                for(int i = 0; i < bulletList.size(); i++)
                {
                     for(int k = 0; k < asteroidList.size(); k++)
                     {
                         // If a bullet hits an Asteroid
                         if (ruth.checkCollision(asteroidList.get(k).getBoundary(), bulletList.get(i).getBoundary()))
                         {                            
                             // Calculates score times a bonus of having more small Asteroids on screen.
                             int score = currentScore.getBonus() * asteroidList.get(k).getPoints();

                             currentScore.addScore(score);
                             
                             asteroidList.get(k).getAngle();
                             
                             int newAngle = r.nextInt(25) + 6;
                             //Adds two new MEDIUM asteroids if asteroid shot was LARGE
                             if (asteroidList.get(k).getSize() > 1)
                             {
                                for (int j = 0; j < 2; j++)
                                {
                                    asteroidList.add(new Asteroid(asteroidList.get(k).getX() + 15, asteroidList.get(k).getY() + 15, asteroidList.get(k).getAngle() + newAngle, (int)asteroidList.get(k).getSize() - 1, frame.getWidth(), frame.getHeight()));
                                    newAngle = newAngle * -1;
                                }
                             }
                             
                             SoundEffect.EXPLODE.play();

                             // Remove bullet, asteroid and stop checking
                             bulletList.remove(i);
                             asteroidList.remove(k);
                             k = asteroidList.size();
                         }      
                     }
                 }
//**************************************************************************************************                
                 //Check for Ship Colliding with Asteroids
                 if (!isRespawning && currentScore.getLives() > 0)
                 {
                    for(int i = 0; i < asteroidList.size(); i++)
                    {
                         // Checks ship has hit an asteroid
                         if (ruth.checkCollision(firefly.getBoundary(), asteroidList.get(i).getBoundary()))
                         {
                            SoundEffect.EXPLODE.play();

                            isRespawning = true;
                            currentScore.lostLife();
                            SoundEffect.THRUST.stop();
                                
                            int newAngle = r.nextInt(25) + 6;
                            //Adds two new MEDIUM asteroids if asteroid shot was LARGE
                            if (asteroidList.get(i).getSize() > 1)
                            {
                                for (int j = 0; j < 2; j++)
                                {
                                    asteroidList.add(new Asteroid(asteroidList.get(i).getX() + 15, asteroidList.get(i).getY() + 15, asteroidList.get(i).getAngle() + newAngle, (int)asteroidList.get(i).getSize() - 1, frame.getWidth(), frame.getHeight()));
                                    newAngle = newAngle * -1;
                                }
                            }
                            asteroidList.remove(i);
                            i = asteroidList.size();
                         }
                      } 
                    }
                 
                 //Adjusts the bonus multiplier
                 // Counts the number of small Asteroids on screen
                 int numberOfSmall = 0;
                 for (Asteroid asteroid : asteroidList)
                 {
                    if (asteroid.getSize() == 1)
                        numberOfSmall++;
                 }            
                 //Sets the new bonus multiplier
                currentScore.setBonus(1 + (numberOfSmall / 2));

//************************************************************                
// **********    Respawn    **********************************
//************************************************************                                
                // Respawn as long as the Center is clear
                if (isRespawning)
                {
                    int size = 100;
                    boolean isClear = true;

                    for (int i=0; i < asteroidList.size(); i++)
                    {    
                        Rectangle2D.Double spawnBox = new Rectangle2D.Double(FRAME_WIDTH / 2 - size, FRAME_HEIGHT / 2 - size, size * 2, size * 2);
                        if (ruth.checkCollision(asteroidList.get(i).getBoundary(), spawnBox))
                        {
                            isClear = false;
                        }
                    }
                    if (isClear)
                    {
                        firefly = new Ship(FRAME_WIDTH / 2 - (SHIP_WIDTH / 2), FRAME_HEIGHT / 2 - (SHIP_HEIGHT / 2), STARTING_ANGLE, SHIP_ACCELERATION, MAX_SPEED, FRAME_WIDTH, FRAME_HEIGHT);
                        isRespawning = false;
                    }
                }

//************************************************************                
// **********    Draw Objects  *******************************
//************************************************************                
                //Clear and  Copy all Drawable objects to the drawableList
                drawableList.clear();
                    
                drawableList.add(bg);
                
                for (Asteroid asteroid : asteroidList)
                    drawableList.add(asteroid);
                    
                for (Bullet bullet : bulletList)
                    drawableList.add(bullet);
                
                 if (currentScore.getLives() > 0 && !isRespawning)
                    drawableList.add(firefly);
                    
                 drawableList.add(hud);
                    
                asteroidsComponent.paintImmediately(asteroidsComponent.getX(), asteroidsComponent.getY(), asteroidsComponent.getWidth(), asteroidsComponent.getHeight());

//************************************************************                
// **********    Updates   ************************************
//************************************************************                           
                
                // Check for GameOver
                if (currentScore.getLives() <= 0)
                 {
                      gameOver = true;
                 }
                 
                 Date end = new Date();
                 long timePerFrame = (end.getTime() - start.getTime());
                 clock += timePerFrame;
//                 System.out.println("Time Per Frame = " + timePerFrame);
//                 System.out.println("Clock = " + clock / 1000 + "s");
                 
            }// End of while(!gameOver)
            
            boolean restart = false;
            SoundEffect.THRUST.stop();
            SoundEffect.THEME.stop();
            SoundEffect.GAMEOVER.play();
            
            // Gives the player a chance to play again
            while (!restart)
            {
                if (KeyCatcher.enterPressed()) // Enter
                {
                    restart = true;
                    skipTitle = true;

                    SoundEffect.GAMEOVER.stop();
                }
                else if (KeyCatcher.escPressed())
                {
                    restart = true;
                    skipTitle = false;
                }   
            }// End of while (!restart)
            // Clears out all ArrayLists that are drawable.
            asteroidList.clear();
            bulletList.clear();
            drawableList.clear();
            SoundEffect.GAMEOVER.stop();
            
        }// End of while(playAgain)
        
//        frame.dispose(); // Just closes frame window doesn't kill app
    }// End of main()
    
    // Constant Variables
    public static final int RIGHT = 2;
    public static final int LEFT = -2;
    public static final int LARGE = 3;
    public static final int MEDIUM = 2;
    public static final int SMALL = 1;
    public static final int STARTING_ASTEROIDS = 3;
    public static final int BULLET_DISTANCE = 500;
    public static final int BULLET_MAX = 7;
    public static final int STARTING_LIVES = 3;
    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 768;
    public static final int ASTEROID_WIDTH = 150;
    public static final int ASTEROID_HEIGHT = 170;
    public static final int SHIP_WIDTH = 71;
    public static final int SHIP_HEIGHT = 75;
    public static final int STARTING_ANGLE = 270;
    //Speed Constansts
    public static final double SHIP_ACCELERATION = .05;// * 2;
    public static final double MAX_SPEED = 4.5;// * 2;
    public static final int MOVEMENT_INTERVAL = 10;// * 2; // In milliseconds.
    public static final int KEY_INTERVAL = 10;// * 2; // in milliseconds\
    public static final int HYPER_INTERVAL = 1000;
    public static final int BULLET_INTERVAL = 250;
    
}
