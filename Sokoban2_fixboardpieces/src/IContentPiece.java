import java.util.ArrayList;

import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

interface IContentPiece {
  // draws the piece
  WorldImage draw();
  
  // gets Posn
  Posn getPosn();
  
  // Returns true unless the board piece is a target on the floor that doesn't 
  // have a trophy of the same color on top of it in the contents
  boolean sameColorTarget(IContentPiece contentBP);
  
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
