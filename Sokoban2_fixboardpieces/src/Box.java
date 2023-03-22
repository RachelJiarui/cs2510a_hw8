import java.util.ArrayList;
import javalib.worldimages.*;

// Represents a box in the level with a position value. In a final version of
// the game, the box should be pushable by the player
class Box extends ABoardPiece implements IContentPiece {
  boolean inMovement;
  String direction;
  
  // A constructor for a box with a position value
  Box(Posn posn) {
    super(posn);
    this.inMovement = false;
    this.direction = ".";
  }
  
  // Returns position
  public Posn getPosn() {
    return this.posn;
  }

  // Draws a box
  public WorldImage draw() {
    /* Template
     * Contains everything from the class template
     */
    return new FromFileImage("box.png");
  }

  // Returns whether this box is a player, which it is not
  public boolean isPlayer() {
    /* Template
     * Contains everything from the class template
     */
    return false;
  }

  // Returns whether this box can be stood on
  public boolean isMoveableSpot() {
    return false;
  }

  // Returns whether this box can be pushed
  public boolean isPushableSpot() {
    return true;
  }

  // updates the position of this Box in the given 2d ArrayList
  public ArrayList<ArrayList<IContentPiece>> replace(ArrayList<ArrayList<IContentPiece>> levelContents,
      Posn movedPosn, IContentPiece thirdpiece) {
    if (new ArrayUtils().getContentPieceAt(movedPosn, levelContents).isHole()) {
      return new Utils().replaceContent(levelContents, new BlankSpace(movedPosn));
    } else {
      return new Utils().replaceContent(levelContents, new Box(movedPosn));
    }
  }

  // returns whether this box is a hole
  public boolean isHole() {
    return false;
  }
  
  // returns copy of this Box
  public IContentPiece copyContentPiece() {
    return new Box(new Posn(this.posn.x, this.posn.y));
  }

  public IContentPiece newContentPiece(Posn movedPosn) {
    return new Box(movedPosn);
  }
}