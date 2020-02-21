package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	//Classe principal do sistema, onde sera implementada toda regra do jogo.
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8); // Construtor para definir o tamanho do tabuleiro.
		initialSetup();
	}
	
	//Metodo ira retornar uma matriz com as pecas de xadrez correspondente a partida atual 
	//Metodo esta acessando a classe ChessPiece devido a classe Piece da camada chess(tabuleiro) estar protegida.
	public ChessPiece[][] getPiece(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); //Efetuar downcast devido a matriz mat ser do tipo ChessPiece.
			}
		}
		return mat;
	}
	
	//Metodo responsavel em iniciar a partida do jogo.
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.BLACK), new Position(7,1));
		board.placePiece(new King(board, Color.WHITE), new Position(0,4));
	}
}
