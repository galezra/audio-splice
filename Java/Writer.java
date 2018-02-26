import java.io.*;
public class Writer {
  public static void makeFolder(String folderName) {
    File file = new File(folderName);
    if (!file.exists()) {
      if (file.mkdir()) {
        System.out.println("Directory is created!");
      } else {
        System.out.println("Failed to create directory!");
      }
    }
  }
  public static void main(String[] args) {
    Writer.makeFolder("/Users/naluconcepcion/Desktop/audio-splice/Java/wavfiles");
  }
}
