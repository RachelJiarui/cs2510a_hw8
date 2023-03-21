import javalib.worldimages.Posn;

abstract class ABoardPiece {
  Posn posn;
  
  ABoardPiece(Posn posn) {
    this.posn = posn;
  }
  
  // Returns true unless the board piece is a target on the floor that doesn't 
  // have a trophy of the same color on top of it in the contents
  public boolean sameColorTarget(IContentPiece contentBP) {
    return true;
  }

  // Returns false unless the board piece is a trophy that is on top of a target
  // of the same color
  public boolean sameColorTrophy(Target target) {
    return false;
  }
}
