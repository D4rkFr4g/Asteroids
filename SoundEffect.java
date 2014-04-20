import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * Loads and controls playback of .wav files
 * @author Zane Melcho
 */
public enum SoundEffect {
   THEME("theme.wav"),
   TITLE("titleScreen.wav"),
   PHASER("laser.wav"),
   THRUST("thrust.wav"),
   EXPLODE("dig_explosion.wav"),
   GAMEOVER("gameover.wav"),
   HYPER("hyperspace.wav");

   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;

   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;

   // Constructor to construct each element of the enum with its own sound file.
   SoundEffect(String soundFileName) {
      try {
         // Use URL (instead of File) to read from disk and JAR.
         URL url = this.getClass().getClassLoader().getResource(soundFileName);
         // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   /**
    *  Plays the sound resetting back to start if it's already playing
    */
   public void play() 
   {
      if (volume != Volume.MUTE) 
      {     
        clip.stop();   // Stop the player if it is still running
        clip.setFramePosition(0); // rewind to the beginning
          
        clip.start();     // Start playing
      }
   }
   
   /**
    * Loops the sound indefinetly
    */
   public void loop() 
   {
      if (volume != Volume.MUTE) 
      {
         if (!clip.isRunning())
         {
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
         }
      }
   }
   
   /**
    * Stops the sound
    */
   public void stop()
   {
       if (volume != Volume.MUTE)
       {
           if (clip.isRunning())
           clip.stop();
       }
    }

   /**
    * Preloads all of the sound files
    */
   static void init() 
   {
      values(); // calls the constructor for all the elements
   }
}