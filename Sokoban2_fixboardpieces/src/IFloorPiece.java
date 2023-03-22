import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

interface IFloorPiece {
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
  
  // Produces a copy of this piece
  IFloorPiece copyFloorPiece();

  boolean isIce();
  
  // Determines if this is ice
}
