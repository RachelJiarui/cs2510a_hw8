import java.util.ArrayList;
import javalib.worldimages.*;

// Represents a target on the floor with a color and position value. Because
// it is on the floor, the player and objects moved by the player can be on top
// of a target. The level is won when every target has a trophy of the same
// color on top of it
class Target extends ABoardPiece implements IFloorPiece {
  String color;

  // A constructor for a target with a color and position value
  Target(String color, Posn posn) {
    super(posn);
    this.color = color;
  }

  // Returns position of this Target
  public Posn getPosn() {
    return this.posn;
  }

  // Draws a target. The sprite of the target depends on its color value
  public WorldImage draw() {
    /* Template
     * Contains everything from the class template
     */
    if (this.color.equals("B")) {
      return new FromFileImage("target_blue.png");
    }
    if (this.color.equals("G")) {
      return new FromFileImage("target_green.png");
    }
    if (this.color.equals("R")) {
      return new FromFileImage("target_red.png");
    }
    else {
      return new FromFileImage("target_yellow.png");
    }
  }

  // Returns whether a floor board piece is a target with a trophy of the same
  // color on it. Since this is a target, it checks that the object in the contents
  // of this position is a trophy of the same color
  @Override
  public boolean sameColorTarget(IContentPiece contentBP) {
    return contentBP.sameColorTrophy(this);
  }

  // Returns whether the board piece in the contents in the same position of
  // a target is a trophy of the same color. Since this board piece isn't a trophy,
  // it always returns false, since that means the contents above the target
  // isn't a trophy of the same color
  @Override
  public boolean sameColorTrophy(Target target) {
    return false;
  }

  // Returns whether this target is a player, which it is not
  public boolean isPlayer() {
    /* Template
     * Contains everything from the class template
     */
    return false;
  }

  // Returns whether this target can be stood on
  public boolean isMoveableSpot() {

    return false;
  }

  // returns whether this Target can be pushed
  public boolean isPushableSpot() {
    return false;
  }

  // determines if this is a hole
  public boolean isHole() {
    return false;
  }
  
  // return a copy of this target
  public IFloorPiece copyFloorPiece() {
    return new Target(this.color, new Posn(this.posn.x, this.posn.y));
  }


  public boolean isIce() {
    return false;
  }
}