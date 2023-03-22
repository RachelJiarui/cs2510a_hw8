import java.awt.Color;

import javalib.worldimages.FromFileImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;
import tester.Tester;

interface IFloorPiece {
  // draws the piece
  WorldImage draw();

  // gets Posn
  Posn getPosn();

  // Returns true unless the board piece is a target on the floor that doesn't 
  // have a trophy of the same color on top of it in the contents
  boolean sameColorTarget(IContentPiece contentBP);

  // Produces a copy of this piece
  IFloorPiece copyFloorPiece();

  boolean isIce();

  // Determines if this is ice
}

class ExamplesFloorPiece {

  // Targets
  IFloorPiece targetBlue = new Target("B", new Posn(3,4));
  IFloorPiece targetGreen = new Target("G", new Posn(6,10));
  IFloorPiece targetRed = new Target("R", new Posn(8,7));
  IFloorPiece targetYellow = new Target("Y", new Posn(1,0));

  // IceBlock
  IFloorPiece iceBlock = new IceBlock(new Posn(4,5));

  // BlankSpace
  IFloorPiece blankSpace = new BlankSpace(new Posn(6,4));

  void testDrawAllPieces(Tester t) {

    // Targets
    boolean testTargetBlue = t.checkExpect(targetBlue.draw(), 
        new FromFileImage("target_blue.png"));
    boolean testTargetGreen = t.checkExpect(targetGreen.draw(), 
        new FromFileImage("target_green.png"));
    boolean testTargetRed = t.checkExpect(targetRed.draw(), 
        new FromFileImage("target_red.png"));
    boolean testTargetYellow = t.checkExpect(targetYellow.draw(), 
        new FromFileImage("target_yellow.png"));

    //ice
    boolean testIceBlock = t.checkExpect(iceBlock.draw(), 
        new FromFileImage("ice.png"));

    //blankspace
    boolean testBlankSpace = t.checkExpect(blankSpace.draw(),  
        new RectangleImage(120, 120, OutlineMode.OUTLINE, Color.WHITE));
  }

  void testGetPosnAllPieces(Tester t) {
    // Targets
    boolean testTargetBlue = t.checkExpect(targetBlue.getPosn(), new Posn(3,4));
    boolean testTargetGreen = t.checkExpect(targetGreen.getPosn(), new Posn(6,10));
    boolean testTargetRed = t.checkExpect(targetRed.getPosn(), new Posn(8,7));
    boolean testTargetYellow = t.checkExpect(targetYellow.getPosn(), new Posn(1,0));

    //ice
    boolean testIceBlock = t.checkExpect(iceBlock.getPosn(), new Posn(4,5));

    //blankspace
    boolean testBlankSpace = t.checkExpect(blankSpace.getPosn(), new Posn(6,4));
  }

  void testSameColorTargetBlue(Tester t) {

    IContentPiece trophyBlue = new Trophy("b", new Posn(0,0));

    // Targets
    boolean testTargetBlue = t.checkExpect(targetBlue.sameColorTarget(trophyBlue), true);
    boolean testTargetGreen = t.checkExpect(targetGreen.sameColorTarget(trophyBlue), false);
    boolean testTargetRed = t.checkExpect(targetRed.sameColorTarget(trophyBlue), false);
    boolean testTargetYellow = t.checkExpect(targetYellow.sameColorTarget(trophyBlue), false);

    //ice
    boolean testIceBlock = t.checkExpect(iceBlock.sameColorTarget(trophyBlue), true);

    //blankspace
    boolean testBlankSpace = t.checkExpect(blankSpace.sameColorTarget(trophyBlue), true);

  }
  void testSameColorTargetRed(Tester t) {

    IContentPiece trophyRed = new Trophy("r", new Posn(0,0));

    // Targets
    boolean testTargetBlue = t.checkExpect(targetBlue.sameColorTarget(trophyRed), false);
    boolean testTargetGreen = t.checkExpect(targetGreen.sameColorTarget(trophyRed), false);
    boolean testTargetRed = t.checkExpect(targetRed.sameColorTarget(trophyRed), true);
    boolean testTargetYellow = t.checkExpect(targetYellow.sameColorTarget(trophyRed), false);

    //ice
    boolean testIceBlock = t.checkExpect(iceBlock.sameColorTarget(trophyRed), true);

    //blankspace
    boolean testBlankSpace = t.checkExpect(blankSpace.sameColorTarget(trophyRed), true);

  }

  void testSameColorTargetGreen(Tester t) {

    IContentPiece trophyGreen = new Trophy("g", new Posn(0,0));

    // Targets
    boolean testTargetBlue = t.checkExpect(targetBlue.sameColorTarget(trophyGreen), false);
    boolean testTargetGreen = t.checkExpect(targetGreen.sameColorTarget(trophyGreen), true);
    boolean testTargetRed = t.checkExpect(targetRed.sameColorTarget(trophyGreen), false);
    boolean testTargetYellow = t.checkExpect(targetYellow.sameColorTarget(trophyGreen), false);

    //ice
    boolean testIceBlock = t.checkExpect(iceBlock.sameColorTarget(trophyGreen), true);

    //blankspace
    boolean testBlankSpace = t.checkExpect(blankSpace.sameColorTarget(trophyGreen), true);

  }

  void testSameColorTargetGreenYellow(Tester t) {
    IContentPiece trophyYellow = new Trophy("y", new Posn(0,0));

    // Targets
    boolean testTargetBlue = t.checkExpect(targetBlue.sameColorTarget(trophyYellow), false);
    boolean testTargetGreen = t.checkExpect(targetGreen.sameColorTarget(trophyYellow), false);
    boolean testTargetRed = t.checkExpect(targetRed.sameColorTarget(trophyYellow), false);
    boolean testTargetYellow = t.checkExpect(targetYellow.sameColorTarget(trophyYellow), true);

    //ice
    boolean testIceBlock = t.checkExpect(iceBlock.sameColorTarget(trophyYellow), true);

    //blankspace
    boolean testBlankSpace = t.checkExpect(blankSpace.sameColorTarget(trophyYellow), true);

  }

  void testCopyFloorPiece(Tester t) {

    // Targets
    boolean testTargetBlue = t.checkExpect(targetBlue.copyFloorPiece(), new Target("B", new Posn(3,4)));
    boolean testTargetGreen = t.checkExpect(targetGreen.copyFloorPiece(), new Target("G", new Posn(6,10)));
    boolean testTargetRed = t.checkExpect(targetRed.copyFloorPiece(), new Target("R", new Posn(8,7)));
    boolean testTargetYellow = t.checkExpect(targetYellow.copyFloorPiece(), new Target("Y", new Posn(1,0)));

    //ice
    boolean testIceBlock = t.checkExpect(iceBlock.copyFloorPiece(), new IceBlock(new Posn(4,5)));

    //blankspace
    boolean testBlankSpace = t.checkExpect(blankSpace.copyFloorPiece(), new BlankSpace(new Posn(6,4)));
  }

  void testIsIce(Tester t) {
    // Targets
    boolean testTargetBlue = t.checkExpect(targetBlue.isIce(), false);
    boolean testTargetGreen = t.checkExpect(targetGreen.isIce(), false);
    boolean testTargetRed = t.checkExpect(targetRed.isIce(), false);
    boolean testTargetYellow = t.checkExpect(targetYellow.isIce(), false);

    //ice
    boolean testIceBlock = t.checkExpect(iceBlock.isIce(), true);

    //blankspace
    boolean testBlankSpace = t.checkExpect(blankSpace.isIce(), false);

  }
}