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
  // REVAMPING ALGORITHM
  // private ArrayList<Double> probableSplits(int numSpeechSwitches) { // this should output only ten
  //
  //
  // }
  // finding quiet space
  private ArrayList<ArrayList<Double>> findPauses(double[] segment) {
    ArrayList<ArrayList<Double>> arrOfSilences = new ArrayList<ArrayList<Double>>();
    double standardDeviation = Processor.calculateSD(segment);
    int sum = 0;
    ArrayList<Double> silence = new ArrayList<Double>();
    for(int f = 0; f < segment.length; f++) {
      sum += f;
    }
    double avg = sum/segment.length;
    for(int i = 0; i < segment.length; i++) {
      if(Math.abs(2 * standardDeviation) > segment[i]) {
        silence.add(segment[i]);
      }
      else {
        if(silence.size() > 22050) {
          arrOfSilences.add(silence);
        }
      }
    }
    System.out.println(arrOfSilences);
    return arrOfSilences;
  }
  // finding quiet space, parameter adjusted for ArrayList<> type
  private ArrayList<ArrayList<Double>> findPauses(ArrayList<Double> segment) {
    ArrayList<ArrayList<Double>> arrOfSilences = new ArrayList<ArrayList<Double>>();
    double standardDeviation = Processor.calculateSD(segment);
    int sum = 0;
    ArrayList<Double> silence = new ArrayList<Double>();
    for(int f = 0; f < segment.size(); f++) {
      sum += f;
    }
    double avg = sum/segment.size();
    for(int i = 0; i < segment.size(); i++) {
      if(Math.abs(2 * standardDeviation) > segment[i]) {
        silence.add(segment[i]);
      }
      else {
        if(silence.size() > 22050) {
          arrOfSilences.add(silence);
        }
      }
    }
    System.out.println(arrOfSilences);
    return arrOfSilences;
  }
  // standard deviation calculator for regular arr
  public static double calculateSD(double[] arr) {
    double sum = 0.0, standardDeviation = 0.0;

    for(double num : arr) {
      sum += num;
    }
    double average = sum/arr.length;
    for(double num : arr) {
      standardDeviation += Math.pow(num - average, 2);
    }
    return Math.sqrt(standardDeviation/arr.length);
  }
  // standard deviation calculator for ArrayList
  public static double calculateSD(ArrayList<Double> arrList) {
    double sum = 0.0, standardDeviation = 0.0;

    for(Double num : arrList) {
      sum += num;
    }
    double average = sum/arrList.size();
    for(Double num : arrList) {
      standardDeviation += Math.pow(num - average, 2);
    }
    return Math.sqrt(standardDeviation/arrList.size());
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
  * @return The number of bytes between different speakers.
  */
  public ArrayList<Integer> getBytesPerGap() {
    return bytesPerGap;
  }
  /**
  * For testing...
  */
  public static void main(String[] args) {
    // FolderReader fp = new FolderReader("wavfiles");
    // ArrayList<Processor> arrFP = fp.makeProcessors();
    // Processor c = arrFP.get(0);
    Processor c = new Processor("wavfiles/ComfyConvoCleaned.wav");

    ChunkedUpWav cuw = new ChunkedUpWav(c.fileName, 10);
    ArrayList<ArrayList<Double>> c2 = cuw.getChunks2();
    c.findPauses(c2.get(0));
    // w.makeWaves("wavfiles/ComfyConvoCleaned.wav");
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
