import java.io.File;
import java.util.ArrayList;

public class Analyst {
  private ArrayList<String> voices = new ArrayList<String>();
  private ArrayList<Double> wavFile = new ArrayList<Double>();
  private ArrayList<Processor> convos = new ArrayList<Processor>();

  public Analyst(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    for(int i = 0; i < listOfFiles.length; i++) {
      String f = (listOfFiles[i]).getName();
      if(f.substring(f.length() - 4, f.length()).equals(".wav")) {
        voices.add(f);
      }
    }
    this.makeProcessors();
    System.out.println(voices);
  }

  public void makeProcessors() {
    for(int i = 0; i < voices.size(); i++) {
      convos.add(new Processor(voices.get(i)));
    }
  }

  public static void main(String[] args) {

    // String path = "/Users/naluconcepcion/Desktop/audio-splice/Java/wavfiles";
    // Analyst a = new Analyst(path);

    try {
      Analyst b = new Analyst(args[0]);
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("correct usage: java Analyst <path to .wav file>");
    }
    catch (NullPointerException e) {
      System.out.println("path does not exist or is not a valid path");
    }
  }
}
