import java.util.ArrayList;
import javalib.worldimages.*;

// Represents a player in a level with a position value and a direction 
// value representing which direction the player is facing. The player piece
// is moved in the level by the actual player using the arrow keys. In a final
// version of the game, the player should be able to move boxes and trophies
// around to try to put a trophy on each target of the same color, resulting in a win
class Player extends ABoardPiece implements IContentPiece {
  String direction;
  boolean inMovement;

  // A constructor for a player, with a direction and position value
  Player(String direction, Posn posn) {
    super(posn);
    this.direction = direction;
  }

  // Returns player position
  public Posn getPosn() {
    return this.posn;
  }
  
  // Puts this player in the given movement
  public void setMovement(boolean i) {
    this.inMovement = i;
  }

  // Draws a player. The sprite's rotation depends on the direction value of the player
  public WorldImage draw() {
    if (this.direction.equals(">")) {
      return new RotateImage(new FromFileImage("player.png"), 0);
    }
    if (this.direction.equals("<")) {
      return new RotateImage(new FromFileImage("player.png"), 180);
    }
    if (this.direction.equals("^")) {
      return new RotateImage(new FromFileImage("player.png"), 270);
    }
    else {
      return new RotateImage(new FromFileImage("player.png"), 90);
    }
  }

  // Returns whether this player is a player, which it is
  public boolean isPlayer() {
    return true;
  }

  // Returns whether this player can be stood on
  public boolean isMoveableSpot() {
    return false;
  }

  // Returns whether this player can be pushed
  public boolean isPushableSpot() {
    return false;
  }

  // Returns an updated arrayList
  public ArrayList<ArrayList<IContentPiece>> replace(ArrayList<ArrayList<IContentPiece>> levelContents,
      Posn movedPosn, IContentPiece thirdpiece) {
    return levelContents;
  }

  // returns whether this player is a hole
  public boolean isHole() {
    return false;
  }
  
  // returns copy of this Player
  public IContentPiece copyContentPiece() {
    return new Player(this.direction, new Posn(this.posn.x, this.posn.y));
  }
}