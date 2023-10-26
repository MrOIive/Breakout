import javax.swing.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements KeyListener {
  MyPanel panel;
  public static int paddleX = 390;
  public static int paddleY = 600;
  public static boolean firstTouch = true;
  public static boolean move = false;
  static boolean keyDown = false;
  boolean keyR = false;
  
  MyFrame() {

    panel = new MyPanel();
    panel.makePanel();
    
    this.setTitle("Breakout");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(880,650);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setIconImage(new ImageIcon("boii.png").getImage());
    this.addKeyListener(this);
    this.add(panel);
    this.pack();
    this.setVisible(true);
    
    TimerTask tt = new TimerTask() {
    	@Override
    	public void run() {
    		if (keyDown) {
    			if (keyR && (paddleX + 100) < 880) {
    				paddleX += 1; 
    				repaint();
    			} else if (!keyR && paddleX > 0){
    				paddleX -= 1; 
    				repaint();
    			}
				
    		}
    	}
    };
    new Timer().schedule(tt, 1, 2);
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {
	  if (move && (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
		  keyDown = false;
	  }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (move) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT: keyR = false; keyDown = true; if (firstTouch) {panel.moveBall(); firstTouch = false;}
          break;
        case KeyEvent.VK_RIGHT: keyR = true; keyDown = true; if (firstTouch) {panel.moveBall(); firstTouch = false;}
          break;
      }
    }
  }
}