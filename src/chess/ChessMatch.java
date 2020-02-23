package chess;

import boardgame.Board;
import boardgame.Piece;
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
	
	//Metodos responsaveis em movimentar a peca no tabuleiro
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); //Convertendo posicao origem de formato xadrez para formato normal de matriz
		Position target = targetPosition.toPosition(); //Convertendo posicao destino de formato xadrez para formato normal de matriz
		validateSourcePosition(source); //ANtes de mover a peca, verificar de existe peca na posicao informada
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove (Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) { //Verifica se nao existe peca na posicao informada
			throw new ChessException("Nao existe peca na posicao de origem informada");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Nao ha movimento possivel para a peca escolhida");
		}
	}
	
	//Metodo criado para que a inicializacao da partida (metodo initialSetup()) seja efetuada pela camada de xadrez(chess) e nao mais pelo board
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	//Metodo responsavel em iniciar a partida do jogo.
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
