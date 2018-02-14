import java.util.ArrayList;
public class Cut {
  // Fields
  public static final double SAMPLE_RATE = 22050.0;
  private double[] originalSamples; // array of doubles generated prior to being stripped
  private double totalTime; // total time of reading in file

  private ArrayList<Double> splits; // array of doubles which are potential splittable times
  // The total number of bytes between each wave of audio data.
  private ArrayList<Integer> bytesPerGap = new ArrayList<Integer>(); // holds # doubles between splits, useful for conversion to time data
  private ArrayList<Double> times = new ArrayList<Double>();
  // Instantiation
  public Cut(String filename) {
    this.originalSamples = StdAudio.read(filename);
    this.splits = possibleSplits();
    this.totalTime = (StdAudio.readByte(filename)).length/SAMPLE_RATE;
    this.convertTime();
  }

  // Heavy Algorithms Section
  private ArrayList<Double> possibleSplits() {
    ArrayList<Double> negativeSamples = new ArrayList<Double>();
    ArrayList<Double> positiveSamples = new ArrayList<Double>();
    ArrayList<Double> nilSamples = new ArrayList<Double>();

    // section for setting numPrev
    //  The number of  doubles that it took to hit a gap. (maybe use interval?)
    int numBytesPerGap = 0;
    for (int i = 0; i < originalSamples.length; i++) {
      double val = originalSamples[i];

      numBytesPerGap++;

      if (val < 0) {
        negativeSamples.add(val);
      }
      else if (val > 0) {
        positiveSamples.add(val);
      }
      else {
        nilSamples.add(val);
        bytesPerGap.add(numBytesPerGap);
        numBytesPerGap = 0;
      }
    }

    return nilSamples;
  }

  private void convertTime() { // I really need to figure out how to convert from a particular byte to a time
    double d = 0.0;

    for (Integer numBytes: this.bytesPerGap) {
      d = numBytes / SAMPLE_RATE;
      times.add(d);
    }
  }

  // Getters

  public ArrayList<Double> getSplits() {
    return splits;
  }

  public ArrayList<Double> getTimes() {
    return times;
  }

  public double getTotalTime() {
    return totalTime;
  }

  public static void main(String[] args) {
    String tempFile = "1-welcome.wav";
    Cut c = new Cut(tempFile);
    System.out.println(c.getTimes());
  }
}
