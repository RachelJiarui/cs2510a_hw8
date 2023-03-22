import java.util.ArrayList;
import javalib.worldimages.*;
import tester.*;

// Represent a Sokoban level with two lists of lists of board pieces, one representing
// the level's floor and one representing the level's contents, as well as two integers, 
// one representing the level's width and another representing its height
class Level {
  ArrayList<ArrayList<IFloorPiece>> levelFloor;
  ArrayList<ArrayList<IContentPiece>> levelContents;
  int width;
  int height;

  // A constructor for a level which takes in the level's floor and contents
  // already
  // formatted to lists of lists of board pieces. It also uses these lists to
  // calculate
  // the level's width and height. It throws an IllegalArgumentException if the
  // level's floor
  // and contents don't have the same number of rows and the same number of
  // IBoardPieces
  // in each row, or if the level contents doesn't include a player
  Level(ArrayList<ArrayList<IFloorPiece>> levelFloor,
      ArrayList<ArrayList<IContentPiece>> levelContents) {
    if (levelFloor.size() != levelContents.size()) {
      int rowLen = levelFloor.get(0).size();
      for (int i = 0; i < levelFloor.size(); i += 1) {
        if (levelFloor.get(i).size() != rowLen || levelContents.get(i).size() != rowLen) {
          throw new IllegalArgumentException("Both the floor and contents need to have "
              + "the same number of rows with the same number of IBoardPieces in each row");
        }
      }
    }

    if (new ArrayUtils().findPlayer(levelContents).x < 0) {
      throw new IllegalArgumentException("There must be a player in the level");
    }

    this.levelFloor = levelFloor;
    this.levelContents = levelContents;
    this.width = levelContents.get(0).size() * 120;
    this.height = levelContents.size() * 120;
  }

  // A constructor for a level which takes in two strings representing the level's
  // floor and contents, converting them both to lists of lists of board pieces.
  // It uses these new lists of lists of board pieces to calculate the level's
  // width and height
  Level(String stringLevelFloor, String stringLevelContents) {
    this(new Utils().toFloorLevel(stringLevelFloor, 0, 0),
        new Utils().toContentLevel(stringLevelContents, 0, 0));
  }

  // A constructor which copies the level given
  Level(Level reference) {
    this.width = reference.width;
    this.height = reference.height;

    // copy level floor
    ArrayList<ArrayList<IFloorPiece>> copyFloor = new ArrayList<>();
    for (ArrayList<IFloorPiece> row : reference.levelFloor) {
      ArrayList<IFloorPiece> copyFloorRow = new ArrayList<>();
      for (IFloorPiece item : row) {
        IFloorPiece copyItem = item.copyFloorPiece();
        copyFloorRow.add(copyItem);
      }
      copyFloor.add(copyFloorRow);
    }
    this.levelFloor = copyFloor;

    // copy content floor
    ArrayList<ArrayList<IContentPiece>> copyContent = new ArrayList<>();
    for (ArrayList<IContentPiece> row : reference.levelContents) {
      ArrayList<IContentPiece> copyContentRow = new ArrayList<>();
      for (IContentPiece item : row) {
        IContentPiece copyItem = item.copyContentPiece();
        copyContentRow.add(copyItem);
      }
      copyContent.add(copyContentRow);
    }
    this.levelContents = copyContent;
  }

  // Returns a visual representation of the level by drawing each individual
  // board piece and formatting them into a rectangular grid
  WorldImage draw() {
    return new OverlayImage(new ArrayUtils().drawContent(this.levelContents),
        new ArrayUtils().drawFloor(this.levelFloor));
  }

  // Returns true when every target on the level's floor has a trophy of
  // the same color above it in the level's contents
  boolean levelWon() {
    /*
     * Template Contains everything from class template
     */
    return new ArrayUtils().levelWon(this.levelFloor, this.levelContents);
  }

  // Returns the player's position in a level
  public Posn findPlayerPosn() {

    return new ArrayUtils().findPlayer(this.levelContents);
  }

  // Returns the player on the level
  public IContentPiece getPlayer() {
    return new ArrayUtils().getPlayer(this.levelContents);
  }

  // Returns whether or not the board piece in the level contents at the
  // indicated position is a blank space, meaning the player can move to it
  public boolean isMoveableSpot(Posn posn) {
    return new ArrayUtils().getContentPieceAt(posn, this.levelContents).isMoveableSpot();
  }

  // Returns the position of the end of the ice slide in a given a starting position
  // and a direction
  public Posn getEndOfIceSlide(Posn start, String dir) {
    int X_LIMIT = this.levelContents.size();
    int Y_LIMIT = this.levelContents.get(0).size();

    if (dir.equals(">")) {
      Posn examinePos = new Posn(start.x, start.y + 1);
      while(new ArrayUtils().getFloorPieceAt(examinePos, this.levelFloor).isIce()
          && examinePos.y < Y_LIMIT) {
        examinePos = new Posn(examinePos.x, examinePos.y + 1);
      }
      return examinePos;
    } else if (dir.equals("^")) {
      Posn examinePos = new Posn(start.x - 1, start.y);
      while(new ArrayUtils().getFloorPieceAt(examinePos, this.levelFloor).isIce()
          && examinePos.x > 0) {
        examinePos = new Posn(examinePos.x, examinePos.y + 1);
      }
      return examinePos;
    } else if (dir.equals("v")) {
      Posn examinePos = new Posn(start.x + 1, start.y);
      while(new ArrayUtils().getFloorPieceAt(examinePos, this.levelFloor).isIce()
          && examinePos.x < X_LIMIT) {
        examinePos = new Posn(examinePos.x, examinePos.y + 1);
      }
      return examinePos;
    } else if (dir.equals("<")) {
      Posn examinePos = new Posn(start.x, start.y - 1);
      while(new ArrayUtils().getFloorPieceAt(examinePos, this.levelFloor).isIce()
          && examinePos.y > 0) {
        examinePos = new Posn(examinePos.x, examinePos.y + 1);
      }
      return examinePos;
    }

    throw new RuntimeException("Direction given is invalid");
  }

  // moves the player in this level
  public int movePlayer(IContentPiece player, Posn dest, String dir) {
    Posn movedPosn = new Utils().adjacentPosn(dest, dir);

    // if the player is trying to move off the screen, just change the icon but
    // don't move user
    if (movedPosn.x >= this.levelFloor.size() || movedPosn.x < 0
        || movedPosn.y >= this.levelFloor.get(0).size() || movedPosn.y < 0) {
      this.changePlayerIcon(player.getPosn(), dir);
      return 0;
    }

    // find more information about the destination piece
    IContentPiece destPiece = new ArrayUtils().getContentPieceAt(dest, this.levelContents);
    IFloorPiece floorPiece = new ArrayUtils().getCorrespondingFloorPiece(levelFloor, player.getPosn());
    IFloorPiece destFloorPiece = new ArrayUtils().getCorrespondingFloorPiece(levelFloor, dest);
    IContentPiece thirdPiece = new ArrayUtils().getContentPieceAt(movedPosn, this.levelContents);

    if ((floorPiece.isIce() && destFloorPiece.isIce()) || (!floorPiece.isIce() && destFloorPiece.isIce())) {
      if (destPiece.isMoveableSpot()) {
        if (destPiece.isHole()) {
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(dest));
        } else {
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, new Player(dir, dest));
        }
      }
      else if (destPiece.isPushableSpot() && thirdPiece.isMoveableSpot()) {

        this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
        this.levelContents = new Utils().replaceContent(this.levelContents, new Player(dir, dest));

        this.levelContents = destPiece.replace(this.levelContents, movedPosn, thirdPiece);
      }
      else {
        this.changePlayerIcon(player.getPosn(), dir);
      }
      Player movedPlayer = new Player(dir, dest);

      return this.movePlayer(movedPlayer, movedPosn, dir);

    } else {
      if (destPiece.isMoveableSpot()) {
        if (destPiece.isHole()) {
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(dest));
        } else {
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, new Player(dir, dest));
        }
        return 1;
      }
      else if (destPiece.isPushableSpot() && thirdPiece.isMoveableSpot()) {
        
        
        this.moveObject(destPiece, dir);
        destPiece = new ArrayUtils().getContentPieceAt(dest, this.levelContents);
        
        this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
        
        
        this.levelContents = new Utils().replaceContent(this.levelContents, new Player(dir, dest));
        
        this.levelContents = destPiece.replace(this.levelContents, movedPosn, thirdPiece);

        return 1;
      }
      else {
        this.changePlayerIcon(player.getPosn(), dir);
        return 0;
      }
    }
  }

  void moveObject(IContentPiece curPiece, String dir) {
    Posn movedPosn = new Utils().adjacentPosn(curPiece.getPosn(), dir);
    Posn thirdPosn = new Utils().adjacentPosn(movedPosn, dir);

    // if the player is trying to move off the screen, just change the icon but
    // don't move user
    if (movedPosn.x >= this.levelFloor.size() || movedPosn.x < 0
        || movedPosn.y >= this.levelFloor.get(0).size() || movedPosn.y < 0) {
      return;
    }

    
    IFloorPiece destFloorPiece = new ArrayUtils().getCorrespondingFloorPiece(this.levelFloor, movedPosn);
    IFloorPiece curFloorPiece = new ArrayUtils().getCorrespondingFloorPiece(levelFloor, curPiece.getPosn());
    IContentPiece nextPiece = new ArrayUtils().getContentPieceAt(movedPosn, this.levelContents);
    IContentPiece thirdPiece = new ArrayUtils().getContentPieceAt(thirdPosn, this.levelContents);


    if(curFloorPiece.isIce() || destFloorPiece.isIce()) {
      if (nextPiece.isMoveableSpot()) {
        if (nextPiece.isHole()) {
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(curPiece.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(movedPosn));
        } else {
          IContentPiece movedCurPiece = curPiece.newContentPiece(movedPosn);
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(curPiece.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, movedCurPiece);
          this.moveObject(movedCurPiece, dir);
        }
      }

    } else if (nextPiece.isPushableSpot() && thirdPiece.isMoveableSpot()) {
      
      this.moveObject(nextPiece, dir);
      
      this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(curPiece.getPosn()));
      this.levelContents = new Utils().replaceContent(this.levelContents, curPiece.newContentPiece(movedPosn));
    }

  }

  // changes the player icon to the direction given
  public void changePlayerIcon(Posn posn, String dir) {
    this.levelContents = new Utils().replaceContent(this.levelContents, new Player(dir, posn));
  }

  // checks if this level has a player

  public boolean hasPlayer() {
    return new ArrayUtils().hasPlayer(this.levelContents);
  }
}

class ExamplesLevel {

  Level smallLevel;
  // resets the level

  void resetSmallLevel() {
    this.smallLevel = new Level(
        "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n"
            + "_______",
            "WWWWWWW\n" + "W_____W\n" + "W__y__W\n" + "W_g>b_W\n" + "W__r__W\n" + "W_____W\n"
                + "WWWWWWW");
  }

  boolean testMovePlayerRight(Tester t) {

    this.resetSmallLevel();

    Level smallLevelMovedRight = new Level(
        "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n"
            + "_______",
            "WWWWWWW\n" + "W_____W\n" + "W__y__W\n" + "W_g_>bW\n" + "W__r__W\n" + "W_____W\n"
                + "WWWWWWW");

    this.smallLevel.movePlayer(new Posn(3, 3), new Posn(3, 4), ">");
    return t.checkExpect(smallLevel.levelContents, smallLevelMovedRight.levelContents);

  }

  boolean testMovePlayerLeft(Tester t) {

    this.resetSmallLevel();

    Level smallLevelMovedLeft = new Level(
        "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n"
            + "_______",
            "WWWWWWW\n" + "W_____W\n" + "W__y__W\n" + "Wg<_b_W\n" + "W__r__W\n" + "W_____W\n"
                + "WWWWWWW");

    this.smallLevel.movePlayer(new Posn(3, 3), new Posn(3, 2), "<");
    return t.checkExpect(smallLevel.levelContents, smallLevelMovedLeft.levelContents);

  }

  boolean testMovePlayerUp(Tester t) {

    this.resetSmallLevel();

    Level smallLevelMovedUp = new Level(
        "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n"
            + "_______",
            "WWWWWWW\n" + "W__y__W\n" + "W__^__W\n" + "W_g_b_W\n" + "W__r__W\n" + "W_____W\n"
                + "WWWWWWW");

    this.smallLevel.movePlayer(new Posn(3, 3), new Posn(2, 3), "^");
    return t.checkExpect(smallLevel.levelContents, smallLevelMovedUp.levelContents);

  }

  boolean testMovePlayerDown(Tester t) {

    this.resetSmallLevel();

    Level smallLevelMovedDown = new Level(
        "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n"
            + "_______",
            "WWWWWWW\n" + "W_____W\n" + "W__y__W\n" + "W_g_b_W\n" + "W__v__W\n" + "W__r__W\n"
                + "WWWWWWW");

    this.smallLevel.movePlayer(new Posn(3, 3), new Posn(4, 3), "v");
    return t.checkExpect(smallLevel.levelContents, smallLevelMovedDown.levelContents);

  }

  boolean testHoleDisappear(Tester t) {
    Level levelWithHole = new Level(
        "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n"
            + "_______",
            "WWWWWWW\n" + "W_____W\n" + "W__y__W\n" + "W_g>bhW\n" + "W__r__W\n" + "W_____W\n"
                + "WWWWWWW");

    Level levelWithHoleRight = new Level(
        "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n" + "_______\n"
            + "_______",
            "WWWWWWW\n" + "W_____W\n" + "W__y__W\n" + "W_g_>_W\n" + "W__r__W\n" + "W_____W\n"
                + "WWWWWWW");

    levelWithHole.movePlayer(new Posn(3, 3), new Posn(3, 4), ">");
    return t.checkExpect(levelWithHole.levelContents, levelWithHoleRight.levelContents);
  }

}