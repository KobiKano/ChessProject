package Game.AI;

import Game.Pieces.*;

import java.util.LinkedList;

public class Openers {

  //private fields
  private Pawn p1;
  private Pawn p2;
  private Pawn p3;
  private Pawn p4;
  private Pawn p5;
  private Pawn p6;
  private Pawn p7;
  private Pawn p8;
  private Rook r1;
  private Rook r2;
  private Knight k1;
  private Knight k2;
  private Bishop b1;
  private Bishop b2;
  private Queen q;
  private King K;
  private LinkedList<PieceMove> moves;
  private int index = 0;


  public Openers(int openingID, boolean isWhite, LinkedList<Tile> tiles, boolean useOpening) {
    //check if openings being used
    if (!useOpening) {
      moves = new LinkedList<>();  //size zero linked list
      return;
    }

    System.out.println("Using opening: " + openingID);
    //find all pieces based on color
    if (isWhite) {
      for (Tile tile : tiles) {
        if (tile.tileNumber == 6) {
          p1 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 6 + 8) {
          p2 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 6 + 2*8) {
          p3 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 6 + 3*8) {
          p4 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 6 + 4*8) {
          p5 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 6 + 5*8) {
          p6 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 6 + 6*8) {
          p7 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 6 + 7*8) {
          p8 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 7) {
          r1 = (Rook) tile.currPiece;
        }
        else if (tile.tileNumber == 7 + 8) {
          k1 = (Knight) tile.currPiece;
        }
        else if (tile.tileNumber == 7 + 2*8) {
          b1 = (Bishop) tile.currPiece;
        }
        else if (tile.tileNumber == 7 + 3*8) {
          q = (Queen) tile.currPiece;
        }
        else if (tile.tileNumber == 7 + 4*8) {
          K = (King) tile.currPiece;
        }
        else if (tile.tileNumber == 7 + 5*8) {
          b2 = (Bishop) tile.currPiece;
        }
        else if (tile.tileNumber == 7 + 6*8) {
          k2 = (Knight) tile.currPiece;
        }
        else if (tile.tileNumber == 7 + 7*8) {
          r2 = (Rook) tile.currPiece;
        }
      }
    }
    else {
      for (Tile tile : tiles) {
        if (tile.tileNumber == 1) {
          p1 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 1 + 8) {
          p2 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 1 + 2*8) {
          p3 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 1 + 3*8) {
          p4 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 1 + 4*8) {
          p5 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 1 + 5*8) {
          p6 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 1 + 6*8) {
          p7 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 1 + 7*8) {
          p8 = (Pawn) tile.currPiece;
        }
        else if (tile.tileNumber == 0) {
          r1 = (Rook) tile.currPiece;
        }
        else if (tile.tileNumber == 8) {
          k1 = (Knight) tile.currPiece;
        }
        else if (tile.tileNumber == 2*8) {
          b1 = (Bishop) tile.currPiece;
        }
        else if (tile.tileNumber == 3*8) {
          q = (Queen) tile.currPiece;
        }
        else if (tile.tileNumber == 4*8) {
          K = (King) tile.currPiece;
        }
        else if (tile.tileNumber == 5*8) {
          b2 = (Bishop) tile.currPiece;
        }
        else if (tile.tileNumber == 6*8) {
          k2 = (Knight) tile.currPiece;
        }
        else if (tile.tileNumber == 7*8) {
          r2 = (Rook) tile.currPiece;
        }
      }
    }

    //find moves
    if (isWhite) {
      //check ID
      if (openingID == 1) {
        moves = QueensGambit();
      }
      if (openingID == 2) {
        moves = KingsGambit();
      }
      if (openingID == 3) {
        moves = ViennaGame();
      }
      if (openingID == 4) {
        moves = SmithMorraGambit();
      }
      else {
        moves = RetiOpening();
      }
    }
    else {
      if (openingID == 1) {
        moves = FrenchDefense();
      }
      if (openingID == 2) {
        moves = PircDefense();
      }
      if (openingID == 3) {
        moves = SicilianDefense();
      }
      if (openingID == 4) {
        moves = AlekhineDefense();
      }
      else {
        moves = BudapestGambit();
      }
    }
  }

  public boolean moreMoves() {
    //check if there are any moves left
    if (index == moves.size()) {
      System.out.println("AI taking over");
      return false;
    }
    //default return
    return true;
  }

  public PieceMove getNextMove() {
    return moves.get(index++);
  }

  private LinkedList<PieceMove> FrenchDefense() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(p5, p5.getCurrTile().below));
    //move two
    output.add(new PieceMove(p4, p4.getCurrTile().below.below));

    return output;
  }

  private LinkedList<PieceMove> PircDefense() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(p4, p4.getCurrTile().below));
    //move two
    output.add(new PieceMove(k2, k2.getCurrTile().below.below.left));
    //move three
    output.add(new PieceMove(p7, p7.getCurrTile().below));

    return output;
  }

  private LinkedList<PieceMove> SicilianDefense() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(p3, p3.getCurrTile().below.below));

    return output;
  }

  private LinkedList<PieceMove> AlekhineDefense() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(p4, p4.getCurrTile().below.below));
    //move two
    output.add(new PieceMove(p3, p3.getCurrTile().below));

    return output;
  }

  private LinkedList<PieceMove> BudapestGambit() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(k2, k2.getCurrTile().below.below.left));
    //move two
    output.add(new PieceMove(p3, p3.getCurrTile().below.below));
    //move three
    output.add(new PieceMove(p2, p2.getCurrTile().below.below));

    return output;
  }

  private LinkedList<PieceMove> QueensGambit() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(p5, p5.getCurrTile().above.above));
    //move two
    output.add(new PieceMove(p6, p6.getCurrTile().above.above));

    return output;
  }

  private LinkedList<PieceMove> KingsGambit() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(p5, p5.getCurrTile().above.above));
    //move two
    output.add(new PieceMove(k1, k1.getCurrTile().above.above.right));

    return output;
  }

  private LinkedList<PieceMove> ViennaGame() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(p5, p5.getCurrTile().above.above));
    //move two
    output.add(new PieceMove(k2, k2.getCurrTile().above.above.left));

    return output;
  }

  private LinkedList<PieceMove> SmithMorraGambit() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(p3, p3.getCurrTile().above.above));
    //move two
    output.add(new PieceMove(p4, p4.getCurrTile().above.above));
    //move three
    output.add(new PieceMove(p7, p7.getCurrTile().above));

    return output;
  }

  private LinkedList<PieceMove> RetiOpening() {
    LinkedList<PieceMove> output = new LinkedList<>();

    //move one
    output.add(new PieceMove(k1, k1.getCurrTile().above.above.right));
    //move two
    output.add(new PieceMove(k2, k2.getCurrTile().above.above.left));
    //move three
    output.add(new PieceMove(p5,p5.getCurrTile().above.above));

    return output;
  }
}
