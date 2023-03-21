import java.util.ArrayList;
import javalib.worldimages.*;

// Represents an immovable wall with a position value. It cannot be moved
// by the player, and the player cannot move through the wall
class Wall extends ABoardPiece implements IContentPiece {
  boolean inMovement;
  String direction;

  // A constructor for a wall with a position value
  Wall(Posn posn) {
    super(posn);
    this.inMovement = false;
    this.direction = ".";
  }

  // Returns position of this Wall
  public Posn getPosn() {
    return this.posn;
  }

  // Draws a wall
  public WorldImage draw() {
    /* Template
     * Contains everything from the class template
     */
    return new FromFileImage("wall.png");
  }

  // Returns whether this wall is a player, which it is not
  public boolean isPlayer() {
    /* Template
     * Contains everything from the class template
     */
    return false;
  }

  // Returns whether this wall can be stood on
  public boolean isMoveableSpot() {
    return false;
  }

  // returns whether this wall can be pushed
  public boolean isPushableSpot() {
    return false;
  }

  // returns an updated arraylist 
  public ArrayList<ArrayList<IContentPiece>> replace(ArrayList<ArrayList<IContentPiece>> levelContents,
      Posn movedPosn, IContentPiece thirdpiece) {
    return levelContents;
  }

  // returns whether this wall is a hole
  public boolean isHole() {
    return false;
  }

  // returns a copy of this Wall
  public IContentPiece copyContentPiece() {
    return new Wall(new Posn(this.posn.x, this.posn.y));
  }
}