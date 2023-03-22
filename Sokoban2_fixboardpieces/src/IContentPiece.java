import java.awt.Color;
import java.util.ArrayList;

import javalib.worldimages.FromFileImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.RotateImage;
import javalib.worldimages.WorldImage;
import tester.Tester;

interface IContentPiece {
  // draws the piece
  WorldImage draw();

  // gets Posn
  Posn getPosn();

  // Returns false unless the board piece is a trophy that is on top of a target
  // of the same color
  boolean sameColorTrophy(Target target);

  // Returns whether is a Player
  boolean isPlayer();

  // Returns whether is a Hole
  boolean isHole();

  // Returns whether it's a spot where the Player can move
  boolean isMoveableSpot();

  // Returns whether it's a pushable spot
  boolean isPushableSpot();

  // Replaces the content at a location with something else
  ArrayList<ArrayList<IContentPiece>> replace(ArrayList<ArrayList<IContentPiece>> levelContents,
      Posn movedPosn, IContentPiece thirdpiece);

  // Produces a copy of the piece
  IContentPiece copyContentPiece();

  IContentPiece newContentPiece(Posn movedPosn);

}

class ExamplesContentPiece {

  IContentPiece playerRight = new Player(">", new Posn(3,5));
  IContentPiece playerLeft = new Player("<", new Posn(7,1));
  IContentPiece playerUp = new Player("^", new Posn(4,2));
  IContentPiece playerDown = new Player("v", new Posn(9,10));

  IContentPiece hole = new Hole(new Posn(4,4));

  IContentPiece box = new Box(new Posn(5,3));

  IContentPiece trophyBlue = new Trophy("b", new Posn(7,1));
  IContentPiece trophyGreen = new Trophy("g", new Posn(6,3));
  IContentPiece trophyRed = new Trophy("r", new Posn(4,3));
  IContentPiece trophyYellow = new Trophy("y", new Posn(6,9));

  IContentPiece blankSpace = new BlankSpace(new Posn(4,1));


  void testDrawAllPieces(Tester t) {

    //Player
    boolean drawPlayerRight = t.checkExpect(playerRight.draw(), new RotateImage(new FromFileImage("player.png"), 0));
    boolean drawPlayerLeft = t.checkExpect(playerLeft.draw(), new RotateImage(new FromFileImage("player.png"), 180));
    boolean drawPlayerUp = t.checkExpect(playerUp.draw(),  new RotateImage(new FromFileImage("player.png"), 270));
    boolean drawPlayerDown = t.checkExpect(playerDown.draw(), new RotateImage(new FromFileImage("player.png"), 90));

    //Hole
    boolean drawHole = t.checkExpect(hole.draw(), new FromFileImage("hole.png"));

    //Box
    boolean drawBox = t.checkExpect(box.draw(), new FromFileImage("box.png"));

    //Trophy
    boolean drawTrophyBlue = t.checkExpect(trophyBlue.draw(), 
        new FromFileImage("trophy_blue.png"));
    boolean drawTrophyGreen = t.checkExpect(trophyGreen.draw(), 
        new FromFileImage("trophy_green.png"));
    boolean drawTrophyRed = t.checkExpect(trophyRed.draw(),
        new FromFileImage("trophy_red.png"));
    boolean drawTrophyYellow = t.checkExpect(trophyYellow.draw(),
        new FromFileImage("trophy_yellow.png"));

    //BlankSpace
    boolean drawBlankSpace = t.checkExpect(blankSpace.draw(), 
        new RectangleImage(120, 120, OutlineMode.OUTLINE, Color.WHITE));
  }

  void testGetPosnAllPieces(Tester t) {

    //Player
    boolean getPosnPlayerRight = t.checkExpect(playerRight.getPosn(), new Posn(3,5));
    boolean getPosnPlayerLeft = t.checkExpect(playerLeft.getPosn(), new Posn(7,1));
    boolean getPosnPlayerUp = t.checkExpect(playerUp.getPosn(), new Posn(4,2));
    boolean getPosnPlayerDown = t.checkExpect(playerDown.getPosn(), new Posn(9,10));

    //Hole
    boolean getPosnHole = t.checkExpect(hole.getPosn(), new Posn(4,4));

    //Box
    boolean getPosnBox = t.checkExpect(box.getPosn(), new Posn(5,3));

    //Trophy
    boolean getPosnTrophyBlue = t.checkExpect(trophyBlue.getPosn(), new Posn(7, 1));
    boolean getPosnTrophyGreen = t.checkExpect(trophyGreen.getPosn(), new Posn(6,3));
    boolean getPosnTrophyRed = t.checkExpect(trophyRed.getPosn(), new Posn(4,3));
    boolean getPosnTrophyYellow = t.checkExpect(trophyYellow.getPosn(), new Posn(6,9));

    //BlankSpace
    boolean getPosnBlankSpace = t.checkExpect(blankSpace.getPosn(), new Posn(4,1));
  }

  void testSameColorTrophyBlue(Tester t) {

    Target targetBlue = new Target("B", new Posn(3,4));

    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.sameColorTrophy(targetBlue), false);
    boolean testPlayerLeft = t.checkExpect(playerLeft.sameColorTrophy(targetBlue), false);
    boolean testPlayerUp = t.checkExpect(playerUp.sameColorTrophy(targetBlue), false);
    boolean testPlayerDown = t.checkExpect(playerDown.sameColorTrophy(targetBlue), false);

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.sameColorTrophy(targetBlue), false);

    //Box
    boolean testBox = t.checkExpect(box.sameColorTrophy(targetBlue), false);

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.sameColorTrophy(targetBlue), true);
    boolean testTrophyGreen = t.checkExpect(trophyGreen.sameColorTrophy(targetBlue), false);
    boolean testTrophyRed = t.checkExpect(trophyRed.sameColorTrophy(targetBlue), false);
    boolean testTrophyYellow = t.checkExpect(trophyYellow.sameColorTrophy(targetBlue), false);

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.sameColorTrophy(targetBlue), false);
  }

  void testSameColorTrophyGreen(Tester t) {

    Target targetGreen = new Target("G", new Posn(3,4));

    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.sameColorTrophy(targetGreen), false);
    boolean testPlayerLeft = t.checkExpect(playerLeft.sameColorTrophy(targetGreen), false);
    boolean testPlayerUp = t.checkExpect(playerUp.sameColorTrophy(targetGreen), false);
    boolean testPlayerDown = t.checkExpect(playerDown.sameColorTrophy(targetGreen), false);

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.sameColorTrophy(targetGreen), false);

    //Box
    boolean testBox = t.checkExpect(box.sameColorTrophy(targetGreen), false);

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.sameColorTrophy(targetGreen), false);
    boolean testTrophyGreen = t.checkExpect(trophyGreen.sameColorTrophy(targetGreen), true);
    boolean testTrophyRed = t.checkExpect(trophyRed.sameColorTrophy(targetGreen), false);
    boolean testTrophyYellow = t.checkExpect(trophyYellow.sameColorTrophy(targetGreen), false);

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.sameColorTrophy(targetGreen), false);
  }

  void testSameColorTrophyRed(Tester t) {

    Target targetRed = new Target("R", new Posn(3,4));

    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.sameColorTrophy(targetRed), false);
    boolean testPlayerLeft = t.checkExpect(playerLeft.sameColorTrophy(targetRed), false);
    boolean testPlayerUp = t.checkExpect(playerUp.sameColorTrophy(targetRed), false);
    boolean testPlayerDown = t.checkExpect(playerDown.sameColorTrophy(targetRed), false);

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.sameColorTrophy(targetRed), false);

    //Box
    boolean testBox = t.checkExpect(box.sameColorTrophy(targetRed), false);

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.sameColorTrophy(targetRed), false);
    boolean testTrophyGreen = t.checkExpect(trophyGreen.sameColorTrophy(targetRed), false);
    boolean testTrophyRed = t.checkExpect(trophyRed.sameColorTrophy(targetRed), true);
    boolean testTrophyYellow = t.checkExpect(trophyYellow.sameColorTrophy(targetRed), false);

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.sameColorTrophy(targetRed), false);
  }

  void testSameColorTrophyYellow(Tester t) {

    Target targetYellow= new Target("Y", new Posn(3,4));

    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.sameColorTrophy(targetYellow), false);
    boolean testPlayerLeft = t.checkExpect(playerLeft.sameColorTrophy(targetYellow), false);
    boolean testPlayerUp = t.checkExpect(playerUp.sameColorTrophy(targetYellow), false);
    boolean testPlayerDown = t.checkExpect(playerDown.sameColorTrophy(targetYellow), false);

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.sameColorTrophy(targetYellow), false);

    //Box
    boolean testBox = t.checkExpect(box.sameColorTrophy(targetYellow), false);

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.sameColorTrophy(targetYellow), false);
    boolean testTrophyGreen = t.checkExpect(trophyGreen.sameColorTrophy(targetYellow), false);
    boolean testTrophyRed = t.checkExpect(trophyRed.sameColorTrophy(targetYellow), false);
    boolean testTrophyYellow = t.checkExpect(trophyYellow.sameColorTrophy(targetYellow), true);

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.sameColorTrophy(targetYellow), false);
  }

  void testIsPlayerAllPieces(Tester t) {
    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.isPlayer(), true);
    boolean testPlayerLeft = t.checkExpect(playerLeft.isPlayer(), true);
    boolean testPlayerUp = t.checkExpect(playerUp.isPlayer(), true);
    boolean testPlayerDown = t.checkExpect(playerDown.isPlayer(), true);

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.isPlayer(), false);

    //Box
    boolean testBox = t.checkExpect(box.isPlayer(), false);

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.isPlayer(), false);
    boolean testTrophyGreen = t.checkExpect(trophyGreen.isPlayer(), false);
    boolean testTrophyRed = t.checkExpect(trophyRed.isPlayer(), false);
    boolean testTrophyYellow = t.checkExpect(trophyYellow.isPlayer(), false);

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.isPlayer(), false);
  }

  void testIsHoleAllPieces(Tester t) {
    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.isHole(), false);
    boolean testPlayerLeft = t.checkExpect(playerLeft.isHole(), false);
    boolean testPlayerUp = t.checkExpect(playerUp.isHole(), false);
    boolean testPlayerDown = t.checkExpect(playerDown.isHole(), false);

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.isHole(), true);

    //Box
    boolean testBox = t.checkExpect(box.isHole(), false);

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.isHole(), false);
    boolean testTrophyGreen = t.checkExpect(trophyGreen.isHole(), false);
    boolean testTrophyRed = t.checkExpect(trophyRed.isHole(), false);
    boolean testTrophyYellow = t.checkExpect(trophyYellow.isHole(), false);

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.isHole(), false);
  }

  void testIsMoveableSpotAllPieces(Tester t) {
    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.isMoveableSpot(), false);
    boolean testPlayerLeft = t.checkExpect(playerLeft.isMoveableSpot(), false);
    boolean testPlayerUp = t.checkExpect(playerUp.isMoveableSpot(), false);
    boolean testPlayerDown = t.checkExpect(playerDown.isMoveableSpot(), false);

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.isMoveableSpot(), true);

    //Box
    boolean testBox = t.checkExpect(box.isMoveableSpot(), false);

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.isMoveableSpot(), false);
    boolean testTrophyGreen = t.checkExpect(trophyGreen.isMoveableSpot(), false);
    boolean testTrophyRed = t.checkExpect(trophyRed.isMoveableSpot(), false);
    boolean testTrophyYellow = t.checkExpect(trophyYellow.isMoveableSpot(), false);

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.isMoveableSpot(), true);
  }

  void testIsPushableSpotAllPieces(Tester t) {
    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.isPushableSpot(), false);
    boolean testPlayerLeft = t.checkExpect(playerLeft.isPushableSpot(), false);
    boolean testPlayerUp = t.checkExpect(playerUp.isPushableSpot(), false);
    boolean testPlayerDown = t.checkExpect(playerDown.isPushableSpot(), false);

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.isPushableSpot(), false);

    //Box
    boolean testBox = t.checkExpect(box.isPushableSpot(), true);

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.isPushableSpot(), true);
    boolean testTrophyGreen = t.checkExpect(trophyGreen.isPushableSpot(), true);
    boolean testTrophyRed = t.checkExpect(trophyRed.isPushableSpot(), true);
    boolean testTrophyYellow = t.checkExpect(trophyYellow.isPushableSpot(), true);

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.isPushableSpot(), false);
  }

  void testReplace(Tester t) {

    ArrayList<ArrayList<IContentPiece>> replaceArr = new ArrayList<>();
    ArrayList<IContentPiece> row1 = new ArrayList<>();
    
    row1.add(new BlankSpace(new Posn(0,0)));
    row1.add(new BlankSpace(new Posn(0,1)));
    ArrayList<IContentPiece> row2 = new ArrayList<>();
    row2.add(new BlankSpace(new Posn(1,0)));
    row2.add(new BlankSpace(new Posn(1,1)));
    replaceArr.add(row1);
    replaceArr.add(row2);
    
    ArrayList<ArrayList<IContentPiece>> destArr = new ArrayList<>();
    ArrayList<IContentPiece> row21 = new ArrayList<>();
    
    row21.add(new BlankSpace(new Posn(0,0)));
    row21.add(new BlankSpace(new Posn(0,1)));
    ArrayList<IContentPiece> row22 = new ArrayList<>();
    row22.add(new BlankSpace(new Posn(1,0)));
    row22.add(new Box(new Posn(1,1)));
    replaceArr.add(row21);
    replaceArr.add(row22);
    
    boolean testPlayer = t.checkExpect(playerRight.replace(replaceArr, new Posn(1,1),
        new BlankSpace(new Posn(0,0))), replaceArr);
    boolean testBox = t.checkExpect(box.replace(replaceArr, new Posn(1,1),
        new BlankSpace(new Posn(0,0))), destArr);
   

  }


  void testCopyContentPieceAllPieces(Tester t) {

    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.copyContentPiece(), new Player(">", new Posn(3,5)));
    boolean testPlayerLeft = t.checkExpect(playerLeft.copyContentPiece(), new Player("<", new Posn(7,1)));
    boolean testPlayerUp = t.checkExpect(playerUp.copyContentPiece(), new Player("^", new Posn(4,2)));
    boolean testPlayerDown = t.checkExpect(playerDown.copyContentPiece(), new Player("v", new Posn(9,10)));

    //Hole
    boolean testgetPosnHole = t.checkExpect(hole.copyContentPiece(), new Hole(new Posn(4,4)));

    //Box
    boolean testBox = t.checkExpect(box.copyContentPiece(), new Box(new Posn(5,3)));

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.copyContentPiece(), new Trophy("b", new Posn(7,1)));
    boolean testTrophyGreen = t.checkExpect(trophyGreen.copyContentPiece(), new Trophy("g", new Posn(6,3)));
    boolean testTrophyRed = t.checkExpect(trophyRed.copyContentPiece(), new Trophy("r", new Posn(4,3)));
    boolean testTrophyYellow = t.checkExpect(trophyYellow.copyContentPiece(), new Trophy("y", new Posn(6,9)));

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.copyContentPiece(), new BlankSpace(new Posn(4,1)));
  }

  void testNewContentPieceAllPieces(Tester t) {
    //Player
    boolean testPlayerRight = t.checkExpect(playerRight.newContentPiece(new Posn(10,5)), new Player(">", new Posn(10,5)));
    boolean testPlayerLeft = t.checkExpect(playerLeft.newContentPiece(new Posn(10,5)), new Player("<", new Posn(10,5)));
    boolean testPlayerUp = t.checkExpect(playerUp.newContentPiece(new Posn(7,5)), new Player("^", new Posn(7, 5)));
    boolean testPlayerDown = t.checkExpect(playerDown.newContentPiece(new Posn(10,5)), new Player("v", new Posn(10,5)));

    //Hole
    boolean testHole = t.checkExpect(hole.newContentPiece(new Posn(10,5)), new Hole(new Posn(4,4)));

    //Box
    boolean testBox = t.checkExpect(box.newContentPiece(new Posn(-5,1)), new Box(new Posn(-5,1)));

    //Trophy
    boolean testTrophyBlue = t.checkExpect(trophyBlue.newContentPiece(new Posn(10,5)), new Trophy("b", new Posn(10,5)));
    boolean testTrophyGreen = t.checkExpect(trophyGreen.newContentPiece(new Posn(10,5)), new Trophy("g", new Posn(10,5)));
    boolean testTrophyRed = t.checkExpect(trophyRed.newContentPiece(new Posn(10,5)), new Trophy("r", new Posn(10,5)));
    boolean testTrophyYellow = t.checkExpect(trophyYellow.newContentPiece(new Posn(10,5)), new Trophy("y", new Posn(10,5)));

    //BlankSpace
    boolean testBlankSpace = t.checkExpect(blankSpace.newContentPiece(new Posn(10,5)), new BlankSpace(new Posn(10,5)));
  }
}
