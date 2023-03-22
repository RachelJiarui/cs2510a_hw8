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


    // if player standing on ice and next is ice, we want player to see if it can move
    // if player is NOT standing on ice but next to it is ice, we want to see if player can move
    if ((floorPiece.isIce() && destFloorPiece.isIce()) || (!floorPiece.isIce() && destFloorPiece.isIce())) {
      
      // if next spot is empty of content
      if (destPiece.isMoveableSpot()) {
        
        // if next spot is a hole
        if (destPiece.isHole()) {

          // player fills the hole
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(dest));

        // if next spot is not a hole
        } else {

          // move player to next spot
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, new Player(dir, dest));

        }

      // if next spot has something pushable (box/trophy) and the spot next to that object is empty
      // and we're on ice or that object is on ice
      } else if (destPiece.isPushableSpot() && thirdPiece.isMoveableSpot()) {

        // move the moveable object to the next next spot, which is open
        // this.levelContents = destPiece.replace(this.levelContents, movedPosn, thirdPiece);
        this.levelContents = this.moveObject(destPiece, dir);

        // move the player to the next spot
        this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(player.getPosn()));
        this.levelContents = new Utils().replaceContent(this.levelContents, new Player(dir, dest));


      // if the next spot doesn't have anything pushable and it's not empty (wall)
      } else {

        // just change icon
        this.changePlayerIcon(player.getPosn(), dir);
        return 0;

      }

      // after all the checking, we want to keep moving the player because we're on ice
      // this is the representation of the player with information on its new position
      Player movedPlayer = new Player(dir, dest);

      // call recursively to keep moving this player BECAUSE it is on ice! we want to keep checking
      // if the follow things are on ice, if there are objects in the way, etc.
      return this.movePlayer(movedPlayer, movedPosn, dir);

    // if we're not on ice and there is no ice in the direction we're moving
    } else {

      // if the next spot is no ice and there's no object there
      if (destPiece.isMoveableSpot()) {
        
        // if the next spot is a hole
        if (destPiece.isHole()) {

          // fill the hole with the player
          this.levelContents = new Utils().replaceContent(this.levelContents, 
              new BlankSpace(player.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents,
              new BlankSpace(dest));

        // if the next spot is NOT a hole, its a blank space
        } else {

          // move the player jut one spot
          this.levelContents = new Utils().replaceContent(this.levelContents,
              new BlankSpace(player.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents,
              new Player(dir, dest));

        }

        return 1;
        
      // if the next spot has no ice but there's something we can push, with the third space being a moveable spot
      // for the object to go to (might be a hole)
      } else if (destPiece.isPushableSpot() && thirdPiece.isMoveableSpot()) {

        // have the moveObject function handle the object's movement in the direction we're going
        // this.levelContents = destPiece.replace(this.levelContents, movedPosn, thirdPiece);
        this.levelContents = this.moveObject(destPiece, dir);

        // move the player to the place where there's the object
        this.levelContents = new Utils().replaceContent(this.levelContents,
            new BlankSpace(player.getPosn()));
        this.levelContents = new Utils().replaceContent(this.levelContents, new Player(dir, dest));

        return 1;

      }

      // if the next spot is NOT open and does NOT have ice (wall)
      else {

        // change the way the player is facing
        this.changePlayerIcon(player.getPosn(), dir);
        return 0;

      }

    }

  }


  ArrayList<ArrayList<IContentPiece>> moveObject(IContentPiece curPiece, String dir) {

    Posn movedPosn = new Utils().adjacentPosn(curPiece.getPosn(), dir);
    Posn thirdPosn = new Utils().adjacentPosn(movedPosn, dir);


    IFloorPiece destFloorPiece = new ArrayUtils().getCorrespondingFloorPiece(this.levelFloor, movedPosn);
    IFloorPiece curFloorPiece = new ArrayUtils().getCorrespondingFloorPiece(levelFloor, curPiece.getPosn());
    IContentPiece nextPiece = new ArrayUtils().getContentPieceAt(movedPosn, this.levelContents);
    IContentPiece thirdPiece = new ArrayUtils().getContentPieceAt(thirdPosn, this.levelContents);

   

    // if this object is not standing on ice, but the next spot is ice
    // we want the object to move but not replace the thing behind it as blank space
    // because that's where the player is going to move
    if(!curFloorPiece.isIce() && destFloorPiece.isIce() || curFloorPiece.isIce() && destFloorPiece.isIce()) {

      // if the next place has no object standing in it's way
      if (nextPiece.isMoveableSpot()) {

        // if the next place is a hole
        if (nextPiece.isHole()) {

          // replace the hole with the object, changing that destination place into a blank space
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(curPiece.getPosn()));
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(movedPosn));

        // if the next place is not a hole, and the object is on ice or going on ice
        } else {

          // move the object to the destination place and call move object recursively
          this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(curPiece.getPosn()));

          IContentPiece movedCurPiece = curPiece.newContentPiece(movedPosn);
          this.levelContents = new Utils().replaceContent(this.levelContents, movedCurPiece);
          this.levelContents = this.moveObject(movedCurPiece, dir);

        }

      }

      // if there's an object standing its way, nothing should change
      return this.levelContents;
   

    // if this object is standing on ice, and the next spot is ice, we want the object to keep
    // sliding. also we'd like the object before it to be replaced by a blank space (what if the player and ice
    // start on the ice together)

    } else if (nextPiece.isMoveableSpot()) {

      if (nextPiece.isHole()) {

        this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(curPiece.getPosn()));
        this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(movedPosn));

      } else {

        this.levelContents = new Utils().replaceContent(this.levelContents, new BlankSpace(curPiece.getPosn()));
        IContentPiece movedCurPiece = curPiece.newContentPiece(movedPosn);
        this.levelContents = new Utils().replaceContent(this.levelContents, movedCurPiece);

      }

    }

    return this.levelContents;

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