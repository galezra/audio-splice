/* Note: All inputted wav files
*        must take the following format:
*        sample rate of 44100
*        2 channels
*        audio sample size 16 bit.
*
*
* This file contains all of the .wav reading and splitting algorithms.
*/

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Processor {
  /**
  * Standard sampling rate for audio file.
  */
  public static final double SAMPLE_RATE = 44100;
  /**
  * An array of double values representing the amplitude of the audio file at various points.
  */
  private double[] originalSamples;
  /**
  * A String representing the name of the file to be processed.
  */
  private String fileName;
  /**
  * The total time of reading in a file.
  */
  private double totalTime;
  /**
  * An ArrayList of doubles which are potential splittable times.
  */
  private ArrayList<Double> splits; // returns all 0.0 amplitude points; ignore
  /**
  * The total number of bytes between each wave of audio data.
  * Useful for conversion to time data.
  */
  private ArrayList<Integer> bytesPerGap = new ArrayList<Integer>();
  /**
  * Times of gaps in the audio sequence.
  */
  private ArrayList<Double> times = new ArrayList<Double>();
  /**
  * Instantiates a new Processor class.
  * @param String filename the path to the audio file (.wave).
  */
  public Processor(String fn) {
    fileName = fn;
    this.originalSamples = StdAudio.read(fn);
    this.splits = possibleSplits();
    this.totalTime = originalSamples.length/(SAMPLE_RATE*2);
    this.convertTime();
  }
  /**
  * Generates double values for which there could be a gap in Processor.
  * In other words, points where there is no sound being produced.
  * @return An array of doubles at which the amplitude of the sound wave is zero.
  */
  private ArrayList<Double> possibleSplits() {
    ArrayList<Double> negativeSamples = new ArrayList<Double>();
    ArrayList<Double> positiveSamples = new ArrayList<Double>();
    ArrayList<Double> nilSamples = new ArrayList<Double>();
    /*
    numDoublesPerGap is set. The below is the algorithm for determining how many
    "samples" there are between zeroes.
    */
    int numDoublesPerGap = 0;
    for (int i = 0; i < originalSamples.length; i++) {
      double val = originalSamples[i];

      numDoublesPerGap++;

      if (val < 0) {
        negativeSamples.add(val);
      }
      else if (val > 0) {
        positiveSamples.add(val);
      }
      else {
        nilSamples.add(val);
        bytesPerGap.add(numDoublesPerGap);
        numDoublesPerGap = 0;
      }
    }

    return nilSamples;
  }
  /**
  * Write new .wav files which are the different regions of speech from the inputted file.
  */
  public void makeWaves(String pathToFile) {
    String flName = pathToFile.substring(pathToFile.lastIndexOf("/") + 1);

    for(int j = 0; j < originalSamples.length; j++) { // loop through the entire file, byte per byte
      for(int i = 0; i < splits.size(); i++) { // in total, there should be splits.size() new .wav files created
        double[] snippet = new double[bytesPerGap.get(i)];
        for(int f = 0; f < bytesPerGap.get(i); f++) {
          snippet[j] = originalSamples[j];
        }
        String newWaveFile = pathToFile + " " + i;
        StdAudio.save(newWaveFile + ".wav", snippet);
      }
    }
  }
  /**
  * Converts the number of doubles between zeroes (`doublesPerGap`) into
  * times so as to create human-readable "flags."
  */
  private void convertTime() {
    double d = 0.0;

    for (Integer numBytes: this.bytesPerGap) {
      d = numBytes / SAMPLE_RATE;
      times.add(d);
    }
  }
  /**
  * @return The splits for the audio file.
  */
  public ArrayList<Double> getSplits() {
    return splits;
  }

  /**
  * @return The times of silence as calculated by the class.
  */
  public ArrayList<Double> getTimes() {
    return times;
  }

  /**
  * The total time of the file.
  * @return The length of the file in seconds.
  */
  public double getTotalTime() {
    return totalTime;
  }
  /**
  * @return The original double values of the wav file.
  */
  public double[] getOriginalSamples() {
    return originalSamples;
  }
  /**
  * For testing...
  */
  public static void main(String[] args) {
    // FolderReader fp = new FolderReader("wavfiles");
    // ArrayList<Processor> arrFP = fp.makeProcessors();
    // Processor c = arrFP.get(0);
    Processor c = new Processor("wavfiles/ComfyConvoCleaned.wav");

    // System.out.println(c.getTimes());
    // double f = 0;
    // for(int i = 0; i < c.getTimes().size(); i++) {
    //   f += c.getTimes().get(i);
    // }
    // f /= 2;
    // System.out.println(f);
    // System.out.println(c.getTotalTime());

    c.makeWaves("wavfiles/ComfyConvoCleaned.wav");
    // System.out.println(c.bytesPerGap);
    // int sum = 0;
    // for(int i = 0; i < c.bytesPerGap.size(); i++) {
    //   sum += c.bytesPerGap.get(i);
    // }
    // int average = sum/c.bytesPerGap.size();
    // System.out.println("avg num bytes: " + average);
    // System.out.println("total time: " + c.totalTime);
    // ArrayList<Double> print = new ArrayList<Double>();
  }
}
