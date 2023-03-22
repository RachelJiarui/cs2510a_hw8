import javalib.worldimages.*;
import tester.*;
import java.util.ArrayList;

class Utils {

  // CONVERTING TO CONTENT LEVEL
  // converts a string of level contents to an ArrayList of BoardPieces
  ArrayList<ArrayList<IContentPiece>> toContentLevel(String stringLevel, int x, int y) {

    ArrayList<ArrayList<IContentPiece>> destContents = new ArrayList<>();

    int index = 0;

    while (stringLevel.indexOf("\n") >= 0) {
      String substringContents = stringLevel.substring(0, stringLevel.indexOf("\n"));

      destContents.add(makeContentRow(substringContents, x + index, 0));

      stringLevel = stringLevel.substring(stringLevel.indexOf("\n") + 1);
      index += 1;
    }

    destContents.add(makeContentRow(stringLevel, x + index, 0));
    return destContents;
  }

  // creates an arraylist that represents a row of a Sokoban content level
  ArrayList<IContentPiece> makeContentRow(String stringLevel, int x, int y) {

    ArrayList<IContentPiece> rowContents = new ArrayList<>();

    int index = 0;

    while (!stringLevel.isEmpty()) {
      rowContents.add(this.charToContentPiece(stringLevel.substring(0,1), x, y + index));

      stringLevel = stringLevel.substring(1);
      index += 1;

    }

    return rowContents;
  }

  // Returns a single board piece, which is converted from a one-character string.
  // The type of board piece created depends on whether the character is on the floor or in
  // the contents (as indicated by boolean isFloor) and what the particular character is
  IContentPiece charToContentPiece(String first, int x, int y) {
    if (first.equals("_")) {
      return new BlankSpace(new Posn(x, y));
    }
    if (first.equals("b") || first.equals("g") 
        || first.equals("y") || first.equals("r")) {
      return new Trophy(first, new Posn(x, y));
    }
    if (first.equals("B")) {
      return new Box(new Posn(x, y));
    }
    if (first.equals("W")) {
      return new Wall(new Posn(x, y));
    }
    if (first.equals("h")) {
      return new Hole(new Posn(x, y));
    }
    if (first.equals("<") || first.equals(">")
        || first.equals("^") || first.equals("v")){
      return new Player(first, new Posn(x, y));
    }
    System.out.println("Content piece wrong: " + first);
    throw new RuntimeException("No appropriate character to build piece");
  }
  
  // CONVERTING TO FLOOR LEVEL
  // converts a string of level contents to an ArrayList of BoardPieces
  ArrayList<ArrayList<IFloorPiece>> toFloorLevel(String stringLevel, int x, int y) {

    ArrayList<ArrayList<IFloorPiece>> destContents = new ArrayList<>();

    int index = 0;

    while (stringLevel.indexOf("\n") >= 0) {
      String substringContents = stringLevel.substring(0, stringLevel.indexOf("\n"));

      destContents.add(makeFloorRow(substringContents, x + index, 0));

      stringLevel = stringLevel.substring(stringLevel.indexOf("\n") + 1);
      index += 1;
    }

    destContents.add(makeFloorRow(stringLevel, x + index, 0));
    return destContents;

  }

  // creates an arraylist that represents a row of a Sokoban content level
  ArrayList<IFloorPiece> makeFloorRow(String stringLevel, int x, int y) {

    ArrayList<IFloorPiece> rowContents = new ArrayList<>();

    int index = 0;

    while (!stringLevel.isEmpty()) {
      rowContents.add(this.charToFloorPiece(stringLevel.substring(0,1), x, y + index));

      stringLevel = stringLevel.substring(1);
      index += 1;

    }

    return rowContents;
  }

  // Returns a single board piece, which is converted from a one-character string.
  // The type of board piece created depends on whether the character is on the floor or in
  // the contents (as indicated by boolean isFloor) and what the particular character is
  IFloorPiece charToFloorPiece(String first, int x, int y) {
    if (first.equals("_")) {
      return new BlankSpace(new Posn(x, y));
    }
    if (first.equals("B") || first.equals("G") 
        || first.equals("Y") || first.equals("R")) {
      return new Target(first, new Posn(x, y));
    }
    if (first.equals("i")) {
      return new IceBlock(new Posn(x, y));
    }
    System.out.println("Floor piece wrong: " + first);
    throw new RuntimeException("No appropriate character to build piece");
  }
  
  // replace one content piece with the given content piece
  ArrayList<ArrayList<IContentPiece>> replaceContent(ArrayList<ArrayList<IContentPiece>> level,
      IContentPiece piece) {
    ArrayList<ArrayList<IContentPiece>> newLevel = new ArrayList<>();

    for (ArrayList<IContentPiece> row : level) {
      ArrayList<IContentPiece> newRow = new ArrayList<>();
      for (IContentPiece item : row) {
        if (item.getPosn().equals(piece.getPosn())) {
          //System.out.println("FOUND POSITION");
          newRow.add(piece);
        } else {
          //System.out.println("ADDED NORMAL ITEM");
          newRow.add(item);
        }
      }
      //System.out.println("ADDED ROW");
      newLevel.add(newRow);
    }
    
    return newLevel;
  }
  
  // replace one content piece with the given content piece
  ArrayList<ArrayList<IFloorPiece>> replaceFloor(ArrayList<ArrayList<IFloorPiece>> level,
      IFloorPiece piece) {
    ArrayList<ArrayList<IFloorPiece>> newLevel = new ArrayList<>();

    for (ArrayList<IFloorPiece> row : level) {
      ArrayList<IFloorPiece> newRow = new ArrayList<>();
      for (IFloorPiece item : row) {
        if (item.getPosn().equals(piece.getPosn())) {
          newRow.add(piece);
        } else {
          newRow.add(item);
        }
      }
      newLevel.add(newRow);
    }

    return newLevel;
  }

  // returns the adjacent posn that is in the direction of the given string
  Posn adjacentPosn(Posn old, String dir) {

    if (dir.equals("^")) {
      return new Posn(old.x - 1, old.y);
    } else if (dir.equals("v")) {
      return new Posn(old.x + 1, old.y);
    } else if (dir.equals("<")) {
      return new Posn(old.x, old.y - 1);
    } else {
      return new Posn(old.x, old.y + 1);
    }

  }

}

class ExamplesUtils {

  boolean testToLevelFloorPieces(Tester t) {
    String simpLevelFloor = "YG_\nRB_";

    ArrayList<ArrayList<IFloorPiece>> simpLevelFloorAL = new ArrayList<>();
    ArrayList<IFloorPiece> row1 = new ArrayList<>();
    row1.add(new Target("Y", new Posn(0,0)));
    row1.add(new Target("G",new Posn(0,1)));
    row1.add(new BlankSpace(new Posn(0,2)));
    ArrayList<IFloorPiece> row2 = new ArrayList<>();
    row2.add(new Target("R", new Posn(1,0)));
    row2.add(new Target("B",new Posn(1,1)));
    row2.add(new BlankSpace(new Posn(1,2)));

    simpLevelFloorAL.add(row1);
    simpLevelFloorAL.add(row2);

    return t.checkExpect(new Utils().toFloorLevel(simpLevelFloor, 0, 0), simpLevelFloorAL);
  }

  boolean testToLevelContentPieces(Tester t) {
    String simpLevelContents = "_BBv\n"
        + "><^r\n"
        + "gbyW";
    ArrayList<ArrayList<IContentPiece>> simpLevelContentsAL = new ArrayList<>();
    ArrayList<IContentPiece> row1 = new ArrayList<>();
    row1.add(new BlankSpace(new Posn(0,0)));
    row1.add(new Box(new Posn(0,1)));
    row1.add(new Box(new Posn(0,2)));
    row1.add(new Player("v", new Posn(0,3)));
    ArrayList<IContentPiece> row2 = new ArrayList<>();
    row2.add(new Player(">", new Posn(1,0)));
    row2.add(new Player("<",new Posn(1,1)));
    row2.add(new Player("^", new Posn(1,2)));
    row2.add(new Trophy("r", new Posn(1,3)));
    ArrayList<IContentPiece> row3 = new ArrayList<>();
    row3.add(new Trophy("g", new Posn(2,0)));
    row3.add(new Trophy("b",new Posn(2,1)));
    row3.add(new Trophy("y", new Posn(2,2)));
    row3.add(new Wall(new Posn(2,3)));

    simpLevelContentsAL.add(row1);
    simpLevelContentsAL.add(row2);
    simpLevelContentsAL.add(row3);

    return t.checkExpect(new Utils().toContentLevel(simpLevelContents, 0, 0), simpLevelContentsAL);
  }

  boolean testAdjacentPosn(Tester t) {
    Posn start = new Posn(5,2);

    // adjacent Posn above
    boolean testUp = t.checkExpect(new Utils().adjacentPosn(start, "^"), new Posn(4,2));
    // adjacent Posn below
    boolean testDown = t.checkExpect(new Utils().adjacentPosn(start, "v"), new Posn(6,2));
    // adjacent Posn on the right
    boolean testRight = t.checkExpect(new Utils().adjacentPosn(start, ">"), new Posn(5,3));
    // adjacent Posn on the left
    boolean testLeft = t.checkExpect(new Utils().adjacentPosn(start, "<"), new Posn(5,1));

    return true;

  }


  boolean testReplaceContent(Tester t) {
    ArrayList<IContentPiece> row1before = new ArrayList<>();
    row1before.add(new Wall(new Posn(0,0)));
    row1before.add(new Wall(new Posn(1,0)));
    row1before.add(new Wall(new Posn(2,0)));
    ArrayList<IContentPiece> row2before = new ArrayList<>();
    row2before.add(new Wall(new Posn(0,1)));
    row2before.add(new Wall(new Posn(1,1)));
    row2before.add(new Wall(new Posn(2,1)));
    ArrayList<IContentPiece> row3before = new ArrayList<>();
    row3before.add(new Wall(new Posn(0,2)));
    row3before.add(new Wall(new Posn(1,2)));
    row3before.add(new Player("<", new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelBefore = new ArrayList<>();
    levelBefore.add(row1before);
    levelBefore.add(row2before);
    levelBefore.add(row3before);

    ArrayList<IContentPiece> row1after = new ArrayList<>();
    row1after.add(new Wall(new Posn(0,0)));
    row1after.add(new Wall(new Posn(1,0)));
    row1after.add(new Wall(new Posn(2,0)));
    ArrayList<IContentPiece> row2after = new ArrayList<>();
    row2after.add(new Wall(new Posn(0,1)));
    row2after.add(new Box(new Posn(1,1)));
    row2after.add(new Wall(new Posn(2,1)));
    ArrayList<IContentPiece> row3after = new ArrayList<>();
    row3after.add(new Wall(new Posn(0,2)));
    row3after.add(new Wall(new Posn(1,2)));
    row3after.add(new Player("<", new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelAfter = new ArrayList<>();
    levelAfter.add(row1after);
    levelAfter.add(row2after);
    levelAfter.add(row3after);

    return t.checkExpect(new Utils().replaceContent(levelBefore, new Box(new Posn(1,1))), levelAfter);
  }
}
