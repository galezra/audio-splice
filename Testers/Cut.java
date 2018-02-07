import java.util.ArrayList;
public class Cut {
// Fields
  public static final double SAMPLE_RATE = 22050.0;
  private double[] preSplit; // array of doubles generated prior to being stripped
  private double[] splits; // array of doubles which are potential splittable times
  private double[] times; // !!!!! TIMES FOR POSSIBLE SPLIT!! HOW TO DETERMINE??
  private double totTime; // total time of reading in file
  private ArrayList<Integer> numPrev = new ArrayList<Integer>(); // holds # doubles between splits, useful for conversion to time data
// Instantiation
  public Cut(String fn) {
    preSplit = StdAudio.read(fn);
    splits = possiSplits(fn);
    totTime = (StdAudio.readByte(fn)).length/SAMPLE_RATE;
    // times = convertTime(splits);
  }
// Heavy Algorithms Section
  private double[] possiSplits(String filename) {
    ArrayList<Double> lessZeroes = new ArrayList();
    ArrayList<Double> greaterZeroes = new ArrayList();
    ArrayList<Double> zeroes = new ArrayList();

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
  private double[] convertTime(double[] arr) { // I really need to figure out how to convert from a particular byte to a time
    
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
    double[] tempSplits = c.getSplits();
    System.out.println(c.numPrev);
  }
}
