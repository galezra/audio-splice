/*
*  Purpose of class:
*  Each object of the class will 
*
*
*
*
*
*
*
*
*
*/

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
// import javax.swing.*;
// import javax.sound.sampled.*;
public class Cutter {

  private SoundClipTest sct;
  private float timeElapsed;
  // Initializes cutter & determines the total playtime of the file
  public Cutter(String s) {
    File file = new File("1-welcome.wav");
    // AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
    // AudioFormat format = audioInputStream.getFormat();
    float audioFileLength = file.length();
    // int frameSize = format.getFrameSize();
    // float frameRate = format.getFrameRate();
    // float durationInSeconds = (audioFileLength / (frameSize * frameRate));
    timeElapsed = file.length()/1000/60;
    // timeElapsed = durationInSeconds;

  }
  // public (wavFile) generateFlags() {
  //
  // }
  // Tester main
  public static void main(String[] args) {
    Cutter c = new Cutter("1-welcome.wav");

    System.out.println("time elapsed: " + c.timeElapsed);
  }
  public float getTimeEl() {
    return this.timeElapsed;
  }
}
