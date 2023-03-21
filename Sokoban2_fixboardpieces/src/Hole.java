import java.util.ArrayList;
import javalib.worldimages.*;

class Hole extends ABoardPiece implements IContentPiece {
  Hole(Posn posn) {
    super(posn);
  }
  
  // Returns position
  public Posn getPosn() {
    return this.posn;
  }

  // Returns a visual image of a Sokoban board piece
  public WorldImage draw() {
    // stub for now
    return new FromFileImage("hole.png");
  }

  // Returns whether or not the board piece is a player
  public boolean isPlayer() {
    return false;
  }

  // Returns whether or not the board piece can be stood on
  public boolean isMoveableSpot() {
    return true;
  }


  // Returns whether or not this hole can be pushed, which it cannot
  public boolean isPushableSpot() {
    return false;
  }

  // updates the position of this Hole in the given 2d ArrayList
  public ArrayList<ArrayList<IContentPiece>> replace(ArrayList<ArrayList<IContentPiece>> levelContents,
      Posn movedPosn, IContentPiece thirdpiece) {
    return levelContents;
  }

  // returns whether this Hole is a hole
  public boolean isHole() {
    return true;
  }
  
  // returns a copy of this hole
  public IContentPiece copyContentPiece() {
    return new Hole(new Posn(this.posn.x, this.posn.y));
  }
}
