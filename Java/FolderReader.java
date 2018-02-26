import java.io.File;
import java.util.ArrayList;

public class FolderReader {
  private ArrayList<String> soundFiles = new ArrayList<String>();

  public FolderReader(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    for(int i = 0; i < listOfFiles.length; i++) {
      String f = (listOfFiles[i]).getName();
      if(f.length() >= 11) {
        if(f.substring(f.length() - 11, f.length()).equals("Cleaned.wav")) {
          soundFiles.add(f);
        }
      }
    }
  }
  public ArrayList<String> getSoundFiles() {
    return soundFiles;
  }
  /**
  * For testing...
  */
  public static void main(String[] args) {
    try {
      String path = "/Users/naluconcepcion/Desktop/audio-splice/Java/wavfiles";
      FolderReader a = new FolderReader(path);
      // FolderReader b = new FolderReader(args[0]);
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("correct usage: java FolderReader <path to Folder containing .wav files>");
    }
    catch (NullPointerException e) {
      System.out.println("path does not exist or is not a valid path");
    }
  }
}
