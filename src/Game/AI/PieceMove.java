package Game.AI;


import Game.Pieces.GameObject;
import Game.Pieces.Tile;

public class PieceMove {
  GameObject piece;
  Tile tile;
  public PieceMove(GameObject piece, Tile tile) {
    this.piece = piece;
    this.tile = tile;
  }

  public GameObject piece() {
    return piece;
  }

  public Tile tile() {
    return tile;
  }

  public void setPiece(GameObject piece) {
    this.piece = piece;
  }

  public void setTile(Tile tile) {
    this.tile = tile;
  }

}
