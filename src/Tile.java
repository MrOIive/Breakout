public class Tile {
  public int x;
  public int y;
  public int width;
  public int height;
  public boolean hit = false;

  public int northWall;
  public int eastWall;
  public int westWall;
  public int southWall;

  public static int tilesLeft = (MyPanel.TILE_WIDTH * MyPanel.TILE_HEIGHT);

  public Tile(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    northWall = y;
    southWall = y + height;
    eastWall = x + width;
    westWall = x;
  }

  public boolean checkTile() {
      double ballX = MyPanel.ballPosX;
      double ballY = MyPanel.ballPosY;

      if ((ballX + 25) >= westWall && ballX <= eastWall) {
        if ((ballY + 25) <= northWall && (ballY + 25) >= (northWall - 5)) {
          MyPanel.direct = "up";
          MyPanel.score += 20;
          MyPanel.scoreL.setText("Score: "+MyPanel.score);
          hit = true;
          tilesLeft--;
          return true;
        }
        if (ballY >= southWall && ballY <= (southWall + 5)) {
          MyPanel.direct = "down";
          MyPanel.score += 20;
          MyPanel.scoreL.setText("Score: "+MyPanel.score);
          hit = true;
          tilesLeft--;
          return true;
        }
      } else if ((ballY + 25) >= northWall && ballY <= southWall) {
        if (ballX >= eastWall && ballX <= (eastWall + 5)) {
          MyPanel.angle = -MyPanel.angle;
          MyPanel.score += 20;
          MyPanel.scoreL.setText("Score: "+MyPanel.score);
          hit = true;
          tilesLeft--;
          return true;
        }
        if ((ballX + 25) <= westWall && (ballX + 25) >= (westWall - 5)) {
          MyPanel.angle = -MyPanel.angle;
          MyPanel.score += 20;
          MyPanel.scoreL.setText("Score: "+MyPanel.score);
          hit = true;
          tilesLeft--;
          return true;
        }
      }
    return false;
    }
}