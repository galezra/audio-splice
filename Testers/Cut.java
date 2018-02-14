import java.util.ArrayList;
public class Cut {
  // Fields
  public static final double SAMPLE_RATE = 22050.0;
  private double[] preSplit; // array of doubles generated prior to being stripped
  private double[] splits; // array of doubles which are potential splittable times

  private double totTime; // total time of reading in file
  private ArrayList<Integer> numPrev = new ArrayList<Integer>(); // holds # doubles between splits, useful for conversion to time data
  private ArrayList<Double> times = new ArrayList<Double>();
  // Instantiation
  public Cut(String fn) {
    preSplit = StdAudio.read(fn);
    splits = possiSplits();
    totTime = (StdAudio.readByte(fn)).length/SAMPLE_RATE;
  }
  // Heavy Algorithms Section
  private double[] possiSplits() {
    ArrayList<Double> lessZeroes = new ArrayList<Double>();
    ArrayList<Double> greaterZeroes = new ArrayList<Double>();
    ArrayList<Double> zeroes = new ArrayList<Double>();

    // section for setting numPrev
    int prevs = 0;
    for(int i = 0; i < preSplit.length; i++) {
      double val = preSplit[i];
      prevs++;
      if(val < 0) {
        lessZeroes.add(val);
        // numPrev.add(prevs);
        // prevs = 0;
      }
      else if(val > 0) {
        greaterZeroes.add(val);
        // numPrev.add(prevs);
        // prevs = 0;
      }
      else {
        zeroes.add(val);
        numPrev.add(prevs);
        prevs = 0;
      }
    }
    double[] arrZeroes;
    arrZeroes = new double[greaterZeroes.size()];
    for(int i = 0; i < greaterZeroes.size(); i++) {
      arrZeroes[i] = greaterZeroes.get(i);
    }
    return arrZeroes;
  }
  // private double[] likelySplit(double[] arr) {
  //   for(int i = 0; i < arr.length; i++) {
  //     if(arr[i] )
  //   }
  // }
  // Getters
  private void convertTime() { // I really need to figure out how to convert from a particular byte to a time
    double d = 0.0;
    for(int i = 0; i < numPrev.size(); i++) {
      d = numPrev.get(i) / SAMPLE_RATE;
      times.add(d);
    }
  }
  public double[] getSplits() {
    return splits;
  }
  public double getFileTime() {
    return totTime;
  }
  public static void main(String[] args) {
    String tempFile = "1-welcome.wav";
    Cut c = new Cut(tempFile);
    System.out.println(c.times);
  }
}
