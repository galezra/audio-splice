import java.io.File;
import java.util.ArrayList;
public class Analyst {
  public ArrayList<String> voices = new ArrayList<String>();
  public ArrayList<Double> wavFile = new ArrayList<Double>();


  public Analyst(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    for(int i = 0; i < listOfFiles.length; i++) {
      String f = (listOfFiles[i]).getName();
      if(f.substring(f.length() - 4, f.length()).equals(".wav")) {
        voices.add(f);
      }
    }
    System.out.println(voices);
  }

  public static void studyVoice(String s, int n) {
    Cut c = new Cut(s, n);

  }

  public static void main(String[] args) {
    String path = "/Users/naluconcepcion/Desktop/audio-splice/Java/wavfiles";
    Analyst a = new Analyst(path);
  }
}
