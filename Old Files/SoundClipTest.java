/*
* Framework code from https://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
* Has been modified so that constructor now takes in a String parameter to be set as the audio
* file to be processed.
* Purpose: Read in and play an AudioFile.
*/

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundClipTest extends JFrame {

  private String str;
  // Constructor
  public SoundClipTest(String s) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Test Sound Clip");
    this.setSize(300, 200);
    this.setVisible(true);

    try {
      // Open an audio input stream.
      URL url = getClass().getClassLoader().getResource(s);
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
      // Get a sound clip resource.
      Clip clip = AudioSystem.getClip();
      // Open clip and load samples from the audio input stream.
      clip.open(audioIn);
      clip.start();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new SoundClipTest(args[0]);
  }
}
