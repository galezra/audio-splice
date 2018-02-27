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
import java.util.*;

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

  /**
  * Breaks "segment" apart and finds the sections of the file in which
  * the values of the sound wave are less than the median value.
  * These sections are then deemeed "silence". The two dimensional ArrayList
  * returned is an array containing THE INDEXES of all of these silences.
  */
  private ArrayList<ArrayList<Integer>> findPauses(ArrayList<Double> segment) {
    ArrayList<ArrayList<Integer>> indicesOfSilence = new ArrayList<ArrayList<Integer>>();

    double median = ChunkedUpWav.calculateMedian(segment);
    System.out.println("Median: " + median);
    int sum = 0;

    ArrayList<Double> silence = new ArrayList<Double>();
    ArrayList<Integer> indOfSilence = new ArrayList<Integer>();

    for(int i = 0; i < segment.size(); i++) {
      if(segment.get(i) < median) {
        silence.add(segment.get(i));
        indOfSilence.add(i);
      }
      else { // when segment.get(i) is no longer "silence"
        if(silence.size() > 3000) { // where 1000/41000 = the amount of time that the silence lasts for. In this case, so long as silence > 1000, it counts as a "legitimate" silence.
        indicesOfSilence.add(indOfSilence);
        indOfSilence.clear();
      }
    }
  }
  // System.out.println(indicesOfSilence);
  return indicesOfSilence;
}
/**
* New cutter method. Let's see how this goes.
*/
  // public double[] cutApart(ArrayList<Double> clipToCut) {
  //   ArrayList<ArrayList<Integer>> indicesOfSilence = this.findPauses(clipToCut);
  //   ArrayList<Integer> oneSilence = new ArrayList<Integer>();
  //   for(int i = 0; i < indicesOfSilence.size(); i++) {
  //     oneSilence = indicesOfSilence.get(i);
  //   }
  // }
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
    Processor c = new Processor("wavfiles/CheynCleaned.wav");

    ChunkedUpWav cuw = new ChunkedUpWav(c.fileName, 90);
    ArrayList<ArrayList<Double>> c2 = cuw.getChunks2();
    c.findPauses(c2.get(56));
  }
}
