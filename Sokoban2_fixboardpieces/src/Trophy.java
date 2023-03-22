import java.util.ArrayList;
import javalib.worldimages.*;

// Represents a trophy in a level with a color and position value. The goal of the game
// is to make sure a trophy is on top of each target with their colors matching. In a
// completed version of the game, the trophy should be pushable by the player
class Trophy extends ABoardPiece implements IContentPiece {
  String color;
  boolean inMovement;
  String direction;

  // A constructor for a trophy with a color and position value
  Trophy(String color, Posn posn) {
    super(posn);
    this.color = color;
    this.inMovement = false;
    this.direction = ".";
  }

  // Returns position of this Trophy
  public Posn getPosn() {
    return this.posn;
  }
  
  // Draws a trophy. The sprite depends on the trophy's color value
  public WorldImage draw() {
    /* Template
     * Contains everything from the class template
     */
    if (this.color.equals("b")) {
      return new FromFileImage("trophy_blue.png");
    }
    if (this.color.equals("g")) {
      return new FromFileImage("trophy_green.png");
    }
    if (this.color.equals("r")) {
      return new FromFileImage("trophy_red.png");
    }
    else {
      return new FromFileImage("trophy_yellow.png");
    }
  }

  // Returns whether the board piece in the contents in the same position of
  // a target is a trophy of the same color. Since this board piece is a trophy,
  // it returns true if the trophy is the same color as the target
  public boolean sameColorTrophy(Target target) {
    return this.color.equalsIgnoreCase(target.color);
  }

  // Returns whether this trophy is a player, which it is not
  public boolean isPlayer() {
    /* Template
     * Contains everything from the class template
     */
    return false;
  }

  // Returns whether this trophy can be stood on, which it cannot
  public boolean isMoveableSpot() {
    return false;
  }

  // updates the position of this Trophy in the given 2d ArrayList
  public ArrayList<ArrayList<IContentPiece>> replace(ArrayList<ArrayList<IContentPiece>> levelContents,
      Posn movedPosn, IContentPiece thirdpiece) {
    if (thirdpiece.isHole()) {
      return new Utils().replaceContent(levelContents, new BlankSpace(movedPosn));
    } else {
      return new Utils().replaceContent(levelContents, new Trophy(this.color, movedPosn));
    }
  }

  // Returns whether this trophy can be pushed, which it can
  public boolean isPushableSpot() {

    return true;
  }

  // returns whether this Trophy is a hole
  public boolean isHole() {
    return false;
  }

  // returns copy of this Trophy
  public IContentPiece copyContentPiece() {
    return new Trophy(this.color, new Posn(this.posn.x, this.posn.y));
  }

  @Override
  public IContentPiece newContentPiece(Posn movedPosn) {
    return new Trophy(this.color, movedPosn);
  }
}