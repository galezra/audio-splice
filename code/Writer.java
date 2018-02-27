import java.io.*;
public class Writer {
  private Processor process;

  public Writer(String s) {
    process = new Processor(s);
  }

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
  /**
  * Write new .wav files which are the different regions of speech from the inputted file.
  */
  public void makeWaves(String pathToFile) {
    String flName = pathToFile.substring(pathToFile.lastIndexOf("/") + 1);
    double[] ogs = process.getOriginalSamples(); // og samples

    for(int j = 0; j < process.getOriginalSamples().length; j++) { // loop through the entire file, byte per byte
      for(int i = 0; i < process.getSplits().size(); i++) { // in total, there should be splits.size() new .wav files created
        double[] snippet = new double[process.getBytesPerGap().get(i)];
        for(int f = 0; f < process.getBytesPerGap().get(i); f++) {
          snippet[j] = ogs[j];
        }
        String newWaveFile = pathToFile + " " + i;
        StdAudio.save(newWaveFile + ".wav", snippet);
      }
    }
  }

  public static void main(String[] args) {
    Writer.makeFolder("/Users/naluconcepcion/Desktop/audio-splice/Java/wavfiles");
  }
}
