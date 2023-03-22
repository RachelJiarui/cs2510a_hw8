
import java.awt.Color;
import java.util.ArrayList;
import javalib.worldimages.*;
import tester.*;

// storage for methods involving array lists as arguments
class ArrayUtils {

  // transforms an array list of content into a world image
  WorldImage drawContent(ArrayList<ArrayList<IContentPiece>> contents) {

    WorldImage sokobanLevel = new EmptyImage();

    for (ArrayList<IContentPiece> boardRow : contents) {
      WorldImage sokobanRow = new EmptyImage();

      for (IContentPiece piece : boardRow) {
        sokobanRow = new BesideImage(sokobanRow, piece.draw());
      }

      sokobanLevel = new AboveImage(sokobanLevel, sokobanRow); 

    }

    return sokobanLevel;
  }
  
  // transforms an array list of floor into a world image
  WorldImage drawFloor(ArrayList<ArrayList<IFloorPiece>> contents) {

    WorldImage sokobanLevel = new EmptyImage();

    for (ArrayList<IFloorPiece> boardRow : contents) {
      WorldImage sokobanRow = new EmptyImage();

      for (IFloorPiece piece : boardRow) {
        sokobanRow = new BesideImage(sokobanRow, piece.draw());
      }

      sokobanLevel = new AboveImage(sokobanLevel, sokobanRow); 

    }

    return sokobanLevel;
  }

  // finds the player's position on the level contents
  Posn findPlayer(ArrayList<ArrayList<IContentPiece>> levelContents) {
    for (ArrayList<IContentPiece> row : levelContents) {
      for (IContentPiece item : row) {
        if (item.isPlayer()) {
          return item.getPosn();
        }
      }
    }
    // if execution gets here, that means no player was found
    throw new IllegalArgumentException("There must be a player in the level");
  }
  
  // gets the player in the level contents
  IContentPiece getPlayer(ArrayList<ArrayList<IContentPiece>> levelContents) {
    for (ArrayList<IContentPiece> row : levelContents) {
      for (IContentPiece item : row) {
        if (item.isPlayer()) {
          return item;
        }
      }
    }
    // if execution gets here, that means no player was found
    throw new IllegalArgumentException("There must be a player in the level");
  }

  // gets the IContentPiece at a position
  IContentPiece getContentPieceAt(Posn posn, ArrayList<ArrayList<IContentPiece>> level) {
    for (ArrayList<IContentPiece> row : level) {
      for (IContentPiece item : row) {
        if (item.getPosn().equals(posn)) {
          return item;
        }
      }
    }

    throw new RuntimeException("Given position is off the board");
  }
  
  // gets the IFloorPiece at a position
  IFloorPiece getFloorPieceAt(Posn posn, ArrayList<ArrayList<IFloorPiece>> level) {
    for (ArrayList<IFloorPiece> row : level) {
      for (IFloorPiece item : row) {
        if (item.getPosn().equals(posn)) {
          return item;
        }
      }
    }

    throw new RuntimeException("Given position is off the board");
  }

  // determines if the level has been completed
  boolean levelWon(ArrayList<ArrayList<IFloorPiece>> levelFloor,
      ArrayList<ArrayList<IContentPiece>> levelContents) {
    for (ArrayList<IFloorPiece> row : levelFloor) {
      for (IFloorPiece item : row) {
        IContentPiece content = this.getContentPieceAt(item.getPosn(), levelContents);
        if (!item.sameColorTarget(content)) {
          return false;
        }
      }
    }

    return true;
  }

  // checks if there exists a player on the given level
  boolean hasPlayer(ArrayList<ArrayList<IContentPiece>> levelContents) {

    for (ArrayList<IContentPiece> row : levelContents) {
      for (IContentPiece item : row) {
        if (item.isPlayer()) {
          return true;
        }
      }
    }

    return false;

  }

  IFloorPiece getCorrespondingFloorPiece(ArrayList<ArrayList<IFloorPiece>> levelFloor,
      Posn posn) {
    for (ArrayList<IFloorPiece> row : levelFloor) {
      for (IFloorPiece item : row) {
        if (item.getPosn().equals(posn)) {
          return item;
        }
      }
    }
    throw new RuntimeException("No corresponding floor piece");
  }

  public IContentPiece getCorrespondingContentPiece(ArrayList<ArrayList<IContentPiece>> levelContent,
      Posn posn) {
    for (ArrayList<IContentPiece> row : levelContent) {
      for (IContentPiece item : row) {
        if (item.getPosn().equals(posn)) {
          return item;
        }
      }
    }
    throw new RuntimeException("No corresponding floor piece");
  }
}

class ExamplesArrayUtils {
  boolean testHasPlayerTrue(Tester t) {
    // contains all of the pieces 
    ArrayList<ArrayList<IContentPiece>> levelContents = new ArrayList<>();
    // all of the different pieces are drawn in the correct spot
    ArrayList<IContentPiece> row1 = new ArrayList<>();
    row1.add(new Wall(new Posn(0,0)));
    row1.add(new BlankSpace(new Posn(0,1)));
    row1.add(new Box(new Posn(0,2)));
    ArrayList<IContentPiece> row2 = new ArrayList<>();
    row2.add(new Player("<",new Posn(1,0)));
    row2.add(new Trophy("b",new Posn(1,1)));
    row2.add(new Trophy("b",new Posn(1,2)));
    ArrayList<IContentPiece> row3 = new ArrayList<>();
    row3.add(new Hole(new Posn(2,0)));
    row3.add(new BlankSpace(new Posn(2,1)));
    row3.add(new BlankSpace(new Posn(2,2)));

    levelContents.add(row1);
    levelContents.add(row2);
    levelContents.add(row3);

    return t.checkExpect(new ArrayUtils().hasPlayer(levelContents), true);
  }

  boolean testHasPlayerFalse(Tester t) {
    // all of the board pieces do not register as a player
    ArrayList<ArrayList<IContentPiece>> levelContents = new ArrayList<>();
    // all of the different pieces are drawn in the correct spot
    ArrayList<IContentPiece> row1 = new ArrayList<>();
    row1.add(new Wall(new Posn(0,0)));
    row1.add(new BlankSpace(new Posn(0,1)));
    row1.add(new Box(new Posn(0,2)));
    ArrayList<IContentPiece> row2 = new ArrayList<>();
    row2.add(new Hole(new Posn(1,0)));
    row2.add(new Trophy("b",new Posn(1,1)));
    row2.add(new Trophy("b",new Posn(1,2)));
    ArrayList<IContentPiece> row3 = new ArrayList<>();
    row3.add(new BlankSpace(new Posn(2,0)));
    row3.add(new BlankSpace(new Posn(2,1)));
    row3.add(new BlankSpace(new Posn(2,2)));

    levelContents.add(row1);
    levelContents.add(row2);
    levelContents.add(row3);

    return t.checkExpect(new ArrayUtils().hasPlayer(levelContents), false);
  }

  /*
  boolean testDrawAllPieces(Tester t) {

    WorldImage blank = new RectangleImage(120, 120, OutlineMode.OUTLINE, Color.WHITE);

    WorldImage allPieces = new AboveImage(new BesideImage(new FromFileImage("wall.png"), 
        new BesideImage(blank, new FromFileImage("box.png"))),
        new AboveImage(new BesideImage(new RotateImage(new FromFileImage("player.png"), 180),
            new BesideImage(new FromFileImage("target_blue.png"), 
                new FromFileImage("trophy_blue.png"))),
            new BesideImage(blank, new BesideImage(blank, blank))));


    ArrayList<ArrayList<IContentPiece>> levelContents = new ArrayList<>();
    // all of the different pieces are drawn in the correct spot
    ArrayList<IContentPiece> row1 = new ArrayList<>();
    row1.add(new Wall(new Posn(0,0)));
    row1.add(new BlankSpace(new Posn(0,1)));
    row1.add(new Box(new Posn(0,2)));
    ArrayList<IContentPiece> row2 = new ArrayList<>();
    row2.add(new Player("<",new Posn(1,0)));
    row2.add(new Trophy("b",new Posn(1,1)));
    row2.add(new Trophy("b",new Posn(1,2)));
    ArrayList<IContentPiece> row3 = new ArrayList<>();
    row3.add(new BlankSpace(new Posn(2,0)));
    row3.add(new BlankSpace(new Posn(2,1)));
    row3.add(new BlankSpace(new Posn(2,2)));

    levelContents.add(row1);
    levelContents.add(row2);
    levelContents.add(row3);


    return t.checkExpect(new ArrayUtils().drawContent(levelContents), allPieces);

  }
  */

  boolean testFindPlayerWithPlayerInCorner(Tester t) {
    ArrayList<IContentPiece> row1PlayerInCorner = new ArrayList<>();
    row1PlayerInCorner.add(new Wall(new Posn(0,0)));
    row1PlayerInCorner.add(new Wall(new Posn(1,0)));
    row1PlayerInCorner.add(new Wall(new Posn(2,0)));
    ArrayList<IContentPiece> row2PlayerInCorner = new ArrayList<>();
    row2PlayerInCorner.add(new Wall(new Posn(0,1)));
    row2PlayerInCorner.add(new Wall(new Posn(1,1)));
    row2PlayerInCorner.add(new Wall(new Posn(2,1)));
    ArrayList<IContentPiece> row3PlayerInCorner = new ArrayList<>();
    row3PlayerInCorner.add(new Wall(new Posn(0,2)));
    row3PlayerInCorner.add(new Wall(new Posn(1,2)));
    row3PlayerInCorner.add(new Player("<", new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelContentsPlayerInCorner = new ArrayList<>();
    levelContentsPlayerInCorner.add(row1PlayerInCorner);
    levelContentsPlayerInCorner.add(row2PlayerInCorner);
    levelContentsPlayerInCorner.add(row3PlayerInCorner);


    return t.checkExpect(new ArrayUtils().findPlayer(levelContentsPlayerInCorner), new Posn(2,2));
  }

  boolean testFindPlayerPopulatedMap(Tester t) {
    ArrayList<IContentPiece> row1Player = new ArrayList<>();
    row1Player.add(new Wall(new Posn(0,0)));
    row1Player.add(new Box(new Posn(1,0)));
    row1Player.add(new Trophy("r", new Posn(2,0)));
    ArrayList<IContentPiece> row2Player = new ArrayList<>();
    row2Player.add(new Wall(new Posn(0,1)));
    row2Player.add(new Box(new Posn(1,1)));
    row2Player.add(new Trophy("b", new Posn(2,1)));
    ArrayList<IContentPiece> row3Player = new ArrayList<>();
    row3Player.add(new Wall(new Posn(0,2)));
    row3Player.add(new Trophy("r", new Posn(1,2)));
    row3Player.add(new Player("<", new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelContentsPlayer = new ArrayList<>();
    levelContentsPlayer.add(row1Player);
    levelContentsPlayer.add(row2Player);
    levelContentsPlayer.add(row3Player);

    return t.checkExpect(new ArrayUtils().findPlayer(levelContentsPlayer), new Posn(2,2));
  }

  boolean testFindNoPlayer(Tester t) {
    ArrayList<IContentPiece> row1Player = new ArrayList<>();
    row1Player.add(new Wall(new Posn(0,0)));
    row1Player.add(new Box(new Posn(1,0)));
    row1Player.add(new Trophy("r", new Posn(2,0)));
    ArrayList<IContentPiece> row2Player = new ArrayList<>();
    row2Player.add(new Wall(new Posn(0,1)));
    row2Player.add(new Box(new Posn(1,1)));
    row2Player.add(new Trophy("b", new Posn(2,1)));
    ArrayList<IContentPiece> row3Player = new ArrayList<>();
    row3Player.add(new Wall(new Posn(0,2)));
    row3Player.add(new Trophy("r", new Posn(1,2)));
    row3Player.add(new Wall(new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelContentsPlayer = new ArrayList<>();
    levelContentsPlayer.add(row1Player);
    levelContentsPlayer.add(row2Player);
    levelContentsPlayer.add(row3Player);

    return t.checkException(new IllegalArgumentException("There must be a player in the level"),
        new ArrayUtils(), "findPlayer", levelContentsPlayer);
  }

  boolean testGetPieceAt(Tester t) {
    ArrayList<IContentPiece> row1Player = new ArrayList<>();
    row1Player.add(new Wall(new Posn(0,0)));
    row1Player.add(new Box(new Posn(1,0)));
    row1Player.add(new Trophy("r", new Posn(2,0)));
    ArrayList<IContentPiece> row2Player = new ArrayList<>();
    row2Player.add(new Wall(new Posn(0,1)));
    row2Player.add(new Box(new Posn(1,1)));
    row2Player.add(new Trophy("b", new Posn(2,1)));
    ArrayList<IContentPiece> row3Player = new ArrayList<>();
    row3Player.add(new Wall(new Posn(0,2)));
    row3Player.add(new Trophy("r", new Posn(1,2)));
    row3Player.add(new Player("<", new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelContentsPlayer = new ArrayList<>();
    levelContentsPlayer.add(row1Player);
    levelContentsPlayer.add(row2Player);
    levelContentsPlayer.add(row3Player);

    return t.checkExpect(new ArrayUtils().getContentPieceAt(new Posn(2,2), levelContentsPlayer),
        new Player("<", new Posn(2,2)))
        && t.checkExpect(new ArrayUtils().getContentPieceAt(new Posn(2,1), levelContentsPlayer),
            new Trophy("b", new Posn(2,1)));
  }

  boolean testGetPieceAtInvalidPosn(Tester t) {
    ArrayList<IContentPiece> row1Player = new ArrayList<>();
    row1Player.add(new Wall(new Posn(0,0)));
    row1Player.add(new Box(new Posn(1,0)));
    row1Player.add(new Trophy("r", new Posn(2,0)));
    ArrayList<IContentPiece> row2Player = new ArrayList<>();
    row2Player.add(new Wall(new Posn(0,1)));
    row2Player.add(new Box(new Posn(1,1)));
    row2Player.add(new Trophy("b", new Posn(2,1)));
    ArrayList<IContentPiece> row3Player = new ArrayList<>();
    row3Player.add(new Wall(new Posn(0,2)));
    row3Player.add(new Trophy("r", new Posn(1,2)));
    row3Player.add(new Player("<", new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelContentsPlayer = new ArrayList<>();
    levelContentsPlayer.add(row1Player);
    levelContentsPlayer.add(row2Player);
    levelContentsPlayer.add(row3Player);

    return t.checkException(new RuntimeException("Given position is off the board"),
        new ArrayUtils(), "getContentPieceAt", new Posn(3,3), levelContentsPlayer);
  }

  boolean testLevelWon(Tester t) {
    ArrayList<IFloorPiece> row1 = new ArrayList<>();
    row1.add(new BlankSpace(new Posn(0,0)));
    row1.add(new BlankSpace(new Posn(1,0)));
    row1.add(new Target("r", new Posn(2,0)));
    ArrayList<IFloorPiece> row2 = new ArrayList<>();
    row2.add(new BlankSpace(new Posn(0,1)));
    row2.add(new BlankSpace(new Posn(1,1)));
    row2.add(new Target("b", new Posn(2,1)));
    ArrayList<IFloorPiece> row3 = new ArrayList<>();
    row3.add(new BlankSpace(new Posn(0,2)));
    row3.add(new Target("r", new Posn(1,2)));
    row3.add(new BlankSpace(new Posn(2,2)));

    ArrayList<ArrayList<IFloorPiece>> levelFloor = new ArrayList<>();
    levelFloor.add(row1);
    levelFloor.add(row2);
    levelFloor.add(row3);

    ArrayList<IContentPiece> row1b = new ArrayList<>();
    row1b.add(new Wall(new Posn(0,0)));
    row1b.add(new Box(new Posn(1,0)));
    row1b.add(new Trophy("R", new Posn(2,0)));
    ArrayList<IContentPiece> row2b = new ArrayList<>();
    row2b.add(new Wall(new Posn(0,1)));
    row2b.add(new Box(new Posn(1,1)));
    row2b.add(new Trophy("B", new Posn(2,1)));
    ArrayList<IContentPiece> row3b = new ArrayList<>();
    row3b.add(new Wall(new Posn(0,2)));
    row3b.add(new Trophy("R", new Posn(1,2)));
    row3b.add(new Player("<", new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelContents = new ArrayList<>();
    levelContents.add(row1b);
    levelContents.add(row2b);
    levelContents.add(row3b);

    return t.checkExpect(new ArrayUtils().levelWon(levelFloor, levelContents), true);
  }

  boolean testLevelNotWon(Tester t) {
    ArrayList<IFloorPiece> row1 = new ArrayList<>();
    row1.add(new BlankSpace(new Posn(0,0)));
    row1.add(new BlankSpace(new Posn(1,0)));
    row1.add(new Target("r", new Posn(2,0)));
    ArrayList<IFloorPiece> row2 = new ArrayList<>();
    row2.add(new BlankSpace(new Posn(0,1)));
    row2.add(new BlankSpace(new Posn(1,1)));
    row2.add(new Target("b", new Posn(2,1)));
    ArrayList<IFloorPiece> row3 = new ArrayList<>();
    row3.add(new BlankSpace(new Posn(0,2)));
    row3.add(new Target("r", new Posn(1,2)));
    row3.add(new BlankSpace(new Posn(2,2)));

    ArrayList<ArrayList<IFloorPiece>> levelFloor = new ArrayList<>();
    levelFloor.add(row1);
    levelFloor.add(row2);
    levelFloor.add(row3);

    ArrayList<IContentPiece> row1b = new ArrayList<>();
    row1b.add(new Wall(new Posn(0,0)));
    row1b.add(new Box(new Posn(1,0)));
    row1b.add(new Trophy("R", new Posn(2,0)));
    ArrayList<IContentPiece> row2b = new ArrayList<>();
    row2b.add(new Wall(new Posn(0,1)));
    row2b.add(new Box(new Posn(1,1)));
    row2b.add(new Trophy("B", new Posn(2,1)));
    ArrayList<IContentPiece> row3b = new ArrayList<>();
    row3b.add(new Wall(new Posn(0,2)));
    row3b.add(new BlankSpace(new Posn(1,2)));
    row3b.add(new Player("<", new Posn(2,2)));

    ArrayList<ArrayList<IContentPiece>> levelContents = new ArrayList<>();
    levelContents.add(row1b);
    levelContents.add(row2b);
    levelContents.add(row3b);

    return t.checkExpect(new ArrayUtils().levelWon(levelFloor, levelContents), false);
  }
}

