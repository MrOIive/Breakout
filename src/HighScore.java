import java.io.*;
import java.util.ArrayList;

public class HighScore {

  public static int getHighScore() {
    try {
      BufferedReader in = new BufferedReader(new FileReader("HighScores"));
      int ln = Integer.valueOf(in.readLine());
      in.close();
      return ln;
    } catch (Exception e) {return 0;}
  }

  public static void setScore(int hs) {
    boolean placedIt = false;
    ArrayList<Integer> list = new ArrayList<Integer>();
    BufferedReader in = null;
    try { 
      in = new BufferedReader(new FileReader("HighScores"));
      int line;
      while ((line = Integer.valueOf(in.readLine())) != 0) {
        if (line > hs || placedIt)
          list.add(list.size(), line);
        else {
          placedIt = true;
          list.add(list.size(), hs);
          list.add(list.size(), line);
        }
      }
      if (!placedIt) {
        list.add(list.size(), hs);
      }
      in.close();
      PrintWriter pw = new PrintWriter("HighScores");
      for (int i = 0; i < list.size(); i++) {
         pw.println(list.get(i));
      } 
      pw.close();
    } catch (Exception e) {
      try {
        if (!placedIt) {
          list.add(list.size(), hs);
        }
        in.close();
        if (list.size() == 0) {
          list.add(hs);
        }
        PrintWriter pw = new PrintWriter("HighScores");
        for (int i = 0; i < list.size(); i++) {
           pw.println(list.get(i));
        }
        pw.close();
      } catch (Exception ex) {}
    } finally {}
  }
}