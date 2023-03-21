import java.util.ArrayList;
import javalib.worldimages.*;
import tester.*;

// Represents a piece on a Sokoban level's board
interface IBoardPiece {

  // Returns a visual image of a Sokoban board piece
  WorldImage draw();

  // Returns true unless the board piece is a target on the floor that doesn't 
  // have a trophy of the same color on top of it in the contents
  boolean sameColorTarget(IBoardPiece contentBP);

  // Returns false unless the board piece is a trophy that is on top of a target
  // of the same color
  boolean sameColorTrophy(Target target);

  // Returns the position value of a board piece
  Posn getPosn();

  // Returns whether or not the board piece is a player
  boolean isPlayer();

  // Returns whether or not the board piece can be stood on
  boolean isMoveableSpot();

  // Returns whether or not the board piece can be pushed
  boolean isPushableSpot();

  // updates the position of this BoardPiece in the given 2d ArrayList
  ArrayList<ArrayList<IBoardPiece>> replace(ArrayList<ArrayList<IBoardPiece>> levelContents,
      Posn movedPosn, IBoardPiece thirdpiece);

  // returns whether or not the board piece is a hole
  boolean isHole();
  /*
   * Template:
   * Methods:
   * ...this.draw()...                          -- WorldImage
   * ...this.sameColorTarget(IBoardPiece)...    -- boolean
   * ...this.sameColorTrophy(Target)...         -- boolean
   * ...this.getPosn()...                       -- Posn
   * ...this.isPlayer()...                      -- boolean
   * ...this.isBlankSpace()...                  -- boolean
   */

}

/*
class ExamplesIBoardPiece {

  ArrayList<ArrayList<IBoardPiece>> simpLevelContentsAL;

  void resetLevelContents() {
    this.simpLevelContentsAL = new ArrayList<>();
    ArrayList<IBoardPiece> row1 = new ArrayList<>();
    row1.add(new BlankSpace(new Posn(0,0)));
    row1.add(new Box(new Posn(0,1)));
    row1.add(new Hole(new Posn(0,2)));
    row1.add(new Player("v", new Posn(0,3)));
    ArrayList<IBoardPiece> row2 = new ArrayList<>();
    row2.add(new Player(">", new Posn(1,0)));
    row2.add(new Player("<",new Posn(1,1)));
    row2.add(new Player("^", new Posn(1,2)));
    row2.add(new Trophy("r", new Posn(1,3)));
    ArrayList<IBoardPiece> row3 = new ArrayList<>();
    row3.add(new Trophy("g", new Posn(2,0)));
    row3.add(new Trophy("b",new Posn(2,1)));
    row3.add(new Trophy("y", new Posn(2,2)));
    row3.add(new Wall(new Posn(2,3)));

    this.simpLevelContentsAL.add(row1);
    this.simpLevelContentsAL.add(row2);
    this.simpLevelContentsAL.add(row3);
  }

  boolean testReplaceNonPlayerWithHole(Tester t) {

    this.resetLevelContents();

    ArrayList<ArrayList<IBoardPiece>> holeAL = new ArrayList<>();
    ArrayList<IBoardPiece> row1 = new ArrayList<>();
    row1.add(new BlankSpace(new Posn(0,0)));
    row1.add(new Box(new Posn(0,1)));
    row1.add(new BlankSpace(new Posn(0,2)));
    row1.add(new Player("v", new Posn(0,3)));
    ArrayList<IBoardPiece> row2 = new ArrayList<>();
    row2.add(new Player(">", new Posn(1,0)));
    row2.add(new Player("<",new Posn(1,1)));
    row2.add(new Player("^", new Posn(1,2)));
    row2.add(new Trophy("r", new Posn(1,3)));
    ArrayList<IBoardPiece> row3 = new ArrayList<>();
    row3.add(new Trophy("g", new Posn(2,0)));
    row3.add(new Trophy("b",new Posn(2,1)));
    row3.add(new Trophy("y", new Posn(2,2)));
    row3.add(new Wall(new Posn(2,3)));

    holeAL.add(row1);
    holeAL.add(row2);
    holeAL.add(row3);


    IBoardPiece oldBox = new Box(new Posn(0,1));

    return t.checkExpect(oldBox.replace(this.simpLevelContentsAL, new Posn(0,2), 
        new Hole(new Posn(0,2))), holeAL);

  }

  boolean testReplacePlayerWithHole(Tester t) {

    this.resetLevelContents();



    IBoardPiece oldPlayer = new Player("v", new Posn(0,3));

    return t.checkExpect(oldPlayer.replace(this.simpLevelContentsAL, new Posn(0,2), 
        new Hole(new Posn(0,2))), this.simpLevelContentsAL);

  }
  
  boolean testReplacePlayerWithBlankSpace(Tester t) {

    this.resetLevelContents();

    IBoardPiece oldPlayer = new Player(">", new Posn(1,0));

    return t.checkExpect(oldPlayer.replace(this.simpLevelContentsAL, new Posn(0,0), 
        new BlankSpace(new Posn(0,0))), this.simpLevelContentsAL);

  }
  
  boolean testReplaceNonPlayerWithBlankSpace(Tester t) {

    this.resetLevelContents();

    ArrayList<ArrayList<IBoardPiece>> AL = new ArrayList<>();
    ArrayList<IBoardPiece> row1 = new ArrayList<>();
    row1.add(new Box(new Posn(0,0)));
    row1.add(new Box(new Posn(0,1)));
    row1.add(new Hole(new Posn(0,2)));
    row1.add(new Player("v", new Posn(0,3)));
    ArrayList<IBoardPiece> row2 = new ArrayList<>();
    row2.add(new Player(">",new Posn(1,0)));
    row2.add(new Player("<",new Posn(1,1)));
    row2.add(new Player("^", new Posn(1,2)));
    row2.add(new Trophy("r", new Posn(1,3)));
    ArrayList<IBoardPiece> row3 = new ArrayList<>();
    row3.add(new Trophy("g", new Posn(2,0)));
    row3.add(new Trophy("b",new Posn(2,1)));
    row3.add(new Trophy("y", new Posn(2,2)));
    row3.add(new Wall(new Posn(2,3)));

    AL.add(row1);
    AL.add(row2);
    AL.add(row3);


    IBoardPiece oldBox = new Box(new Posn(0,1));

    return t.checkExpect(oldBox.replace(this.simpLevelContentsAL, new Posn(0,0),
        new BlankSpace(new Posn(0,0))), AL);

  }

}
*/

