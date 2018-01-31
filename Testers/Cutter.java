public class Cutter {

  private SoundClipTest sct;
  private long timeElapsed;
// Initializes cutter & determines the total time of the file
  public Cutter(String s) {
    long initialTime = System.currentTimeMillis();
    sct = new SoundClipTest(s);
    long finalTime = System.currentTimeMillis();
    timeElapsed = finalTime - initialTime;
  }
  public (wavFile) generateFlags() {
    
  }
  // Tester main
  public static void main(String[] args) {

  }
}
