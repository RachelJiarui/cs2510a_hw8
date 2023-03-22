import java.awt.Color;
import java.util.ArrayList;
import javalib.worldimages.*;

// Represents a blank space on the floor or in the contents with a position
// value. On the floor, it represents a space with no target, and in the contents,
// it represents a space with no other board pieces where the player or
// a pushable object can move to
class BlankSpace extends ABoardPiece implements IFloorPiece, IContentPiece {

  // A constructor for a blank space with a position value
  BlankSpace(Posn posn) {
    super(posn);
  }
  // Draws a blank space, which is just an white outline of a square
  public WorldImage draw() {
    /* Template
     * Contains everything from the class template
     */
    return new RectangleImage(120, 120, OutlineMode.OUTLINE, Color.WHITE);
  }

  // Returns the position value of this blank space
  public Posn getPosn() {
    return this.posn;
  }

  // Returns whether this blank space is a player, which it is not
  public boolean isPlayer() {
    /* Template
     * Contains everything from the class template
     */
    return false;
  }

  // Returns whether this blank space can be stood on
  public boolean isMoveableSpot() {
    return true;
  }

  //updates the position of this blank space in the given 2d ArrayList
  public ArrayList<ArrayList<IContentPiece>> replace(ArrayList<ArrayList<IContentPiece>> levelContents,
      Posn movedPosn, IContentPiece thirdpiece) {
    return new Utils().replaceContent(levelContents, new BlankSpace(movedPosn));
  }

  // returns whether this blank space can be pushed
  public boolean isPushableSpot() {
    return false;
  }

  // returns whether this blankspace is a hole
  public boolean isHole() {
    return false;
  }
  
  // return copy of this Black Space
  public IFloorPiece copyFloorPiece() {
    return new BlankSpace(new Posn(this.posn.x, this.posn.y));
  }
  
  // return copy of this Black Space
  public IContentPiece copyContentPiece() {
    return new BlankSpace(new Posn(this.posn.x, this.posn.y));
  }

  public boolean isIce() {

    return false;
  }
  
  public IContentPiece newContentPiece(Posn movedPosn) {
    return new BlankSpace(movedPosn);
  }
  
}