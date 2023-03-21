import tester.*;

import java.awt.Color;
import java.util.ArrayList;

import javalib.funworld.*;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;

// Represents a World that plays a level of Sokoban with a level, screen width and
// screen height values
class SokobanWorld extends javalib.funworld.World {

  Level level;
  int screenWidth;
  int screenHeight;
  ArrayList<Level> prevLevels;

  // A constructor for a SokobanWorld that takes in a level and decides the
  // screenWidth and screenHeight values by setting them as the level's
  // width and height values
  SokobanWorld(Level level) {
    this.level = level;
    this.screenWidth = level.width;
    this.screenHeight = level.height;
    Level firstCopy = new Level(level);
    this.prevLevels = new ArrayList<>();
    prevLevels.add(firstCopy);
  }

  /*
   * Template: Fields: ...this.level... -- Level ...this.screenWidth... -- int
   * ...this.screenHeight... -- int Methods: ...this.makeScene()... -- WorldScene
   * ...this.onKeyEvent(String)... -- SokobanWorld Methods on Fields:
   * ...this.level.draw()... -- WorldImage ...this.level.levelWon()... -- boolean
   * ...this.level.findPlayerPosn()... -- Posn
   * ...this.level.isMoveableSpot(Posn)... -- boolean
   */

  // Makes a world scene containing the rendered level in a sized window
  public WorldScene makeScene() {
    /*
     * Template: Contains everything from the class template
     */
    return new WorldScene(this.level.width, this.level.height).placeImageXY(this.level.draw(),
        this.level.width / 2, this.level.height / 2);
  }

  // Changes the SokobanWorld based on key inputs. If a player board piece tries
  // walking
  // into a blank space, this means changing the player location and possibly its
  // sprite.
  // If a player board piece tries walking into a non-blank space board piece,
  // this means
  // possibly changing the player sprite
  public SokobanWorld onKeyEvent(String key) {

    // MOVING PLAYER
    if (key.equals("up")) {
      this.level.movePlayer(this.level.getPlayer(),
          new Posn(this.level.findPlayerPosn().x - 1, this.level.findPlayerPosn().y), "^");
    }
    if (key.equals("left")) {
      this.level.movePlayer(this.level.getPlayer(),
          new Posn(this.level.findPlayerPosn().x, this.level.findPlayerPosn().y - 1), "<");

    }
    if (key.equals("down")) {
      this.level.movePlayer(this.level.getPlayer(),
          new Posn(this.level.findPlayerPosn().x + 1, this.level.findPlayerPosn().y), "v");

    }
    if (key.equals("right")) {
      this.level.movePlayer(this.level.getPlayer(),
          new Posn(this.level.findPlayerPosn().x, this.level.findPlayerPosn().y + 1), ">");
    }

    // UNDO FEATURE
    if (key.equals("u")) {
      if (this.prevLevels.size() > 1) {
        System.out.println("UNDO BEING HIT!");
        // gets the last element in prev levels list
        this.level = new Level(this.prevLevels.get(this.prevLevels.size() - 2));
        // removes the last previous level because it's become our current level
        this.prevLevels.remove(this.prevLevels.size() - 1);
      }
      // else don't do anything, level remains the same and nothing is added to
      // previous levels
    } else {
      Level copyLevel = new Level(this.level);
      this.prevLevels.add(copyLevel);
    }
    
    System.out.println("Length: " + this.prevLevels.size());

    World checkEnd = this.checkLevelEnd();

    return this;
  }

  // checks whether or not the level should end

  public World checkLevelEnd() {

    if (this.level.levelWon() || !this.level.hasPlayer()) {

      return this.endOfWorld("END");

    }

    return this;

  }

  // overrides lastScene method to output custom ending message

  public WorldScene lastScene(String msg) {

    WorldScene background = new WorldScene(this.screenWidth, this.screenHeight);

    return background.placeImageXY(new TextImage(msg, 40, Color.red), this.screenWidth / 2,
        this.screenHeight / 2);

  }
}

class ExamplesSokobanWorld {
  Level level1 = new Level(
      "________\n" +
      "___R____\n" +
      "________\n" +
      "_B____Y_\n" +
      "________\n" +
      "___G____\n" +
      "________",
      "__WWW___\n" +
      "__W_WW__\n" +
      "WWWr_WWW\n" +
      "W_b>yB_W\n" +
      "WW_gWWWW\n" +
      "_WW_W___\n" +
      "__WWW___");

  Level level2 = new Level(
      "______\n" + "____R_\n" + "______\n" + "______\n" + "_G____\n" + "______",
      "WWWWWW\n" + "W_r__W\n" + "W_<__W\n" + "W__g_W\n" + "W____W\n" + "WWWWWW");

  Level level3 = new Level(
      "______\n" + "___R__\n" + "______\n" + "_G____\n" + "______\n" + "______",
      "WWWWWW\n" + "W__r_W\n" + "W_<__W\n" + "Wg___W\n" + "W____W\n" + "WWWWWW");
  Level level4 = new Level("B_\n" + "__", "B_\n" + "v_");

  Level level5 = new Level("B_\n" + "__", "b_\n" + "v_");

  Level level6 = new Level(
      "______\n" + "___R__\n" + "______\n" + "_G____\n" + "______\n" + "______",
      "WWWWWW\n" + "W__r_W\n" + "W_<__W\n" + "W_g__W\n" + "W____W\n" + "WWWWWW");
  Level levelWithHole = new Level(
      "_______\n" + "___Y___\n" + "_______\n" + "_G_____\n" + "_______\n" + "___R___\n" + "_______",
      "WWWWWWW\n" + "W_____W\n" + "W__y__W\n" + "W_g>BhW\n" + "W__r__W\n" + "W_____W\n"
          + "WWWWWWW");

  SokobanWorld world1 = new SokobanWorld(level1);
  SokobanWorld world2 = new SokobanWorld(level2);
  SokobanWorld world3 = new SokobanWorld(level3);
  SokobanWorld world4 = new SokobanWorld(level4);
  SokobanWorld world5 = new SokobanWorld(level5);
  SokobanWorld world6 = new SokobanWorld(level6);
  SokobanWorld worldHole = new SokobanWorld(levelWithHole);

  boolean testSokobanWorld(Tester t) {
    return world1.bigBang(world1.screenWidth, world1.screenHeight, 0.1);
  }

  /*
  // CHECKING END
  // checks that last scene executes what we want
  boolean testLastScene(Tester t) {
    WorldScene background = new WorldScene(world3.screenWidth, world3.screenHeight);
    WorldScene endScene = background.placeImageXY(new TextImage("END", 40, Color.red),
        world3.screenWidth / 2, world3.screenHeight / 2);

    return t.checkExpect(world3.lastScene("END"), endScene);
  }

  // checks that if the level has won, we see level scene
  boolean testCheckLevelEndLevelWon(Tester t) {
    return t.checkExpect(world3.checkLevelEnd(), world3.endOfWorld("END"));
  }

  // checks that if the level has not been won, the world is returned as normal
  boolean testCheckLevelEndLevelNotWon(Tester t) {
    return t.checkExpect(world1.checkLevelEnd(), world1);
  }

  // checks if the player still exists, the world is returned as normals
  boolean testChecKLevelEndHasPlayer(Tester t) {
    return t.checkExpect(world2.checkLevelEnd(), world2);
  }
  */
}
