import javalib.worldimages.*;

class IceBlock extends ABoardPiece implements IFloorPiece {
  IceBlock(Posn posn) {
    super(posn);
  }
  
  // draws the piece
  public WorldImage draw() {
    return new FromFileImage("iceblock.png");
  }
  
  // gets Posn
  public Posn getPosn() {
    return this.posn;
  }
  
  // copies this Iceblock
  public IFloorPiece copyFloorPiece() {
    return new IceBlock(new Posn(this.posn.x, this.posn.y));
  }
}
