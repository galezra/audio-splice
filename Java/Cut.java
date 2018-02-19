import java.util.ArrayList;

public class Cut {
  /**
   * Standard sampling rate for audio file.
   */
  public static final double SAMPLE_RATE = 44100;
  /**
   * Number of channels in the file
   */
  public final int NUMCHANNELS;
  /**
   * An array of doubles generated prior to walking through the audio file.
   */
  private double[] originalSamples;
  /**
   * The total time of reading in a file.
   */
  private double totalTime;
  /**
   * An ArrayList of doubles which are potential splittable times.
   */
  private ArrayList<Double> splits;
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
   * Instatniates a new Cut class.
   * @param String filename the path to the audio file (.wave).
   */
  public Cut(String filename, int numChans) {
    this.NUMCHANNELS = numChans;
    this.originalSamples = StdAudio.read(filename);
    this.splits = possibleSplits();
    this.totalTime = (StdAudio.read(filename)).length/(SAMPLE_RATE*NUMCHANNELS);
    this.convertTime();
  }

  /**
   * Generates double values for which there could be a gap in conversation.
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
   * For testing...
   */
  public static void main(String[] args) {
    String tempFile = "ComfyConvo.wav";
    Cut c = new Cut(tempFile, 2);
    // System.out.println(c.getTimes());
    double f = 0;
    for(int i = 0; i < c.getTimes().size(); i++) {
      f += c.getTimes().get(i);
    }
    f /= c.NUMCHANNELS;
    System.out.println(f);
    System.out.println(c.getTotalTime());
  }
}
