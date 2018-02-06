import java.util.ArrayList;
public class Cut {;
  private double[] preSplit;
  private double[] splits;
  public Cut(String fn) {
    preSplit = StdAudio.read(fn);
    splits = possiSplit(fn);
  }
  private double[] possiSplit(String filename) {
    ArrayList<Double> lessZeroes = new ArrayList();
    ArrayList<Double> greaterZeroes = new ArrayList();
    ArrayList<Double> zeroes = new ArrayList();
    double[] arrZeroes;
    for(int i = 0; i < preSplit.length; i++) {
      double val = preSplit[i];
      if(val < 0) {
        lessZeroes.add(val);
      }
      else if(val > 0) {
        greaterZeroes.add(val);
      }
      else {
        zeroes.add(val);
      }
    }
    arrZeroes = new double[greaterZeroes.size()];
    for(int i = 0; i < greaterZeroes.size(); i++) {
      arrZeroes[i] = greaterZeroes.get(i);
    }
    return arrZeroes;
  }
  public double[] getSplits() {
    return splits;
  }
  // public double[] pairTimes(double[] splits) {
  //
  // }
  public static void main(String[] args) {
    String tempFile = "1-welcome.wav";
    Cut c = new Cut(tempFile);
    double[] tempSplits = c.getSplits();
    // System.out.print("[");
    // for(int i = 0; i < tempSplits.length; i++) {
    //   System.out.print(tempSplits[i] + ", ");
    // }
    // System.out.print("]");
    System.out.println(tempSplits.length);
  }
}
