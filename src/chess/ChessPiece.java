package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

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
	
	//Metodo para verificar se existe uma peca do oponente na posicao passada pelo argumento
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
}
