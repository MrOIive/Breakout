import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {

  final int PANEL_WIDTH = 880;
  final int PANEL_HEIGHT = 650;
  final int PADDLE_WIDTH = 100;
  final int BALL_SIZE = 25;
  Image backgroundImage;
  Image ballImage;
  Image paddleImage;
  JLabel label;
  public static JLabel scoreL;
  JLabel liveL;
  JLabel hsL;
  
  Tile[][] tiles = new Tile[13][6];
  final static int TILE_WIDTH = 13;
  final static int TILE_HEIGHT = 6;

  public static int score = 0;
  public static int balls = 3;
  public static double ballPosX = MyFrame.paddleX + (50-12);
  public static double ballPosY = MyFrame.paddleY - 30;
  public static double angle;
  final float AMB = 0.0174533f;
  public static String direct = "up";

  MyPanel() {}

  public void makePanel() {
	  this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
	    this.setLayout(null);

	    label = new JLabel();
	    label.setFont(new Font("Comic Sans", Font.BOLD, 60));
	    label.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
	    label.setVerticalAlignment(JLabel.CENTER);
	    label.setHorizontalAlignment(JLabel.CENTER);
	    label.setForeground(Color.WHITE);

	    liveL = new JLabel("Balls: 3");
	    liveL.setFont(new Font("Comic Sans", Font.BOLD, 15));
	    liveL.setForeground(Color.WHITE);
	    liveL.setBounds(700, 5, 880, 20);

	    scoreL = new JLabel("Score: 0");
	    scoreL.setFont(new Font("Comic Sans", Font.BOLD, 15));
	    scoreL.setForeground(Color.WHITE);
	    scoreL.setBounds(100, 5, 375, 20);
	    
	    hsL = new JLabel("Highscore: "+HighScore.getHighScore());
	    hsL.setFont(new Font("Comic Sans", Font.BOLD, 15));
	    hsL.setForeground(Color.WHITE);
	    hsL.setBounds(375, 5, 700, 20);

	    this.add(hsL);
	    this.add(label);
	    this.add(scoreL);
	    this.add(liveL);

	    backgroundImage = new ImageIcon("boBackground.jpeg").getImage();
	    ballImage = new ImageIcon("ball.png").getImage();
	    paddleImage = new ImageIcon("paddle.png").getImage();

	    Random r = new Random();
	    angle = (r.nextInt(120) - 60);

	    int addX = 50;
	    int addY = 50;
	    for (int y = 0; y < TILE_HEIGHT; y++) {
	      for (int x = 0; x < TILE_WIDTH; x++) {
	        tiles[x][y] = new Tile(addX, addY, 50, 15);
	        addX += 60;
	      }
	      addX = 50;
	      addY += 25;
	    }
	    MyFrame.move = true;
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g); 
    Graphics2D g2D = (Graphics2D) g;
    g2D.drawImage(backgroundImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
    float hue = 2.90f;
    for (int y = 0; y < TILE_HEIGHT; y++){
      for (int x = 0; x < TILE_WIDTH; x++) {
        if (!tiles[x][y].hit) {
          g2D.setColor(Color.getHSBColor(hue, 0.75f, 0.64f));
          g2D.fillRect(tiles[x][y].x, tiles[x][y].y, tiles[x][y].width, tiles[x][y].height);
        }
      }
      hue -= 0.05f;
    }
    int bx = (int) (ballPosX + .5);
    int by = (int) (ballPosY + .5);
    g2D.drawImage(ballImage, bx, by, BALL_SIZE, BALL_SIZE, null);
    g2D.drawImage(paddleImage, MyFrame.paddleX, MyFrame.paddleY, PADDLE_WIDTH, 15, null);
  }
  
  public void moveBall() {
    Timer timer = new Timer();
    TimerTask timetask = new TimerTask() {
      @Override
      public void run() {
    	if (direct == "up") {
           ballPosY -= 2 * Math.cos(AMB*angle);
           ballPosX += 2 * Math.sin(AMB*angle);
           repaint();
        } else {
           ballPosY += 2 * Math.cos(AMB*angle);
           ballPosX += 2 * Math.sin(AMB*angle);
           repaint();
        }
        if (ballPosX >= (PANEL_WIDTH - BALL_SIZE)) {
        	angle = -angle;
            ballPosX -= 2;
            try {
          		String soundName = "boing.wav";    
          		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
          		Clip clip = AudioSystem.getClip();
          		clip.open(audioInputStream);
          		clip.start();
          	} catch (Exception ex) {}
        } else if (ballPosX <= 0) {
        	angle = -angle;
            ballPosX += 2;
            try {
          		String soundName = "boing.wav";    
          		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
          		Clip clip = AudioSystem.getClip();
          		clip.open(audioInputStream);
          		clip.start();
          	} catch (Exception ex) {}
        } else if (((ballPosY + BALL_SIZE) >= (MyFrame.paddleY - 5) && (ballPosY + BALL_SIZE) <= MyFrame.paddleY)&& ((ballPosX + BALL_SIZE) >= MyFrame.paddleX && ballPosX <= (MyFrame.paddleX + PADDLE_WIDTH))) {
          direct = "up";
          ballPosY -= 2;
          double bx = ballPosX + (BALL_SIZE / 2);
          angle = bx - (MyFrame.paddleX + (PADDLE_WIDTH / 2));
          try {
        		String soundName = "paddleHit.wav";    
        		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
        		Clip clip = AudioSystem.getClip();
        		clip.open(audioInputStream);
        		clip.start();
          } catch (Exception ex) {}
        } else if (ballPosY <= 0) {
        	try {
        		String soundName = "boing.wav";    
          		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
          		Clip clip = AudioSystem.getClip();
          		clip.open(audioInputStream);
          		clip.start();
          } catch (Exception ex) {}
          direct = "down";
          ballPosY += 2;
        }

        boolean touched = false;
        for (int y = 0; y < TILE_HEIGHT; y++) {
          for (int x = 0; x < TILE_WIDTH; x++) {
            if (!tiles[x][y].hit && !touched)
              touched = tiles[x][y].checkTile();
          }
        }
        
        if (touched) {
        	try {
          		String soundName = "explosion.wav";    
          		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
          		Clip clip = AudioSystem.getClip();
          		clip.open(audioInputStream);
          		clip.start();
          	} catch (Exception ex) {}
        }
        
        if (Tile.tilesLeft == 0) {
            timer.cancel();
            MyFrame.move = false;
            MyFrame.keyDown = false;
            label.setText("Your Final Score: "+score);
            HighScore.setScore(score);
            hsL.setText("Highscore: "+HighScore.getHighScore());
         }
        
        if (ballPosY > PANEL_HEIGHT) {
        	balls--;
        	score -= 100;
        	scoreL.setText("Score: "+score);
        	liveL.setText("Balls "+balls);
        	if (balls == 0) {
        		timer.cancel();
                MyFrame.move = false;
                MyFrame.keyDown = false;
                label.setText("Your Final Score: "+score);
                HighScore.setScore(score);
                hsL.setText("Highscore: "+HighScore.getHighScore());
        	} else {
        		timer.cancel();
        		ballPosX = MyFrame.paddleX + (50-12);
        		ballPosY = MyFrame.paddleY - 30;
        		Random r = new Random();
        	    angle = (r.nextInt(120) - 60);
        	    direct = "up";
        	    repaint();
        	    MyFrame.keyDown = false;
        		MyFrame.firstTouch = true;
        	}
        }
      }
    };
    timer.schedule(timetask, 1, 4);
  }
}