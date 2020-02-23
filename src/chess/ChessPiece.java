package chess;

import boardgame.Board;
import boardgame.Piece;

//Devido a classe tambem ser generica como a classe Piece, manter como abstrata devido a heranca
//Implementar as regras do metodo possibleMoves somente nas classes especificas de cada peca.
public abstract class ChessPiece extends Piece{ 
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

   // Set retirado pois a cor nao pode ser alterada.
}
