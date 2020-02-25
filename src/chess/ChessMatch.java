package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	//Classe principal do sistema, onde sera implementada toda regra do jogo.
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); //Listas para controle de pecas capturadas
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8,8); // Construtor para definir o tamanho do tabuleiro.
		turn = 1;
		currentPlayer = Color.WHITE; //Partida sempre comeca com as pecas brancas
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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
	
	//Metodo criado para imprimir as posicoes possiveis de destino dada a posicao de origem.
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	//Metodos responsaveis em movimentar a peca no tabuleiro
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); //Convertendo posicao origem de formato xadrez para formato normal de matriz
		Position target = targetPosition.toPosition(); //Convertendo posicao destino de formato xadrez para formato normal de matriz
		validateSourcePosition(source); //ANtes de mover a peca, verificar de existe peca na posicao informada
		validadeTargetPosition(source, target); //Valida se posicao destino pode receber a peca origem escolhida
		Piece capturedPiece = makeMove(source, target);
		nextTurn(); //Troca de turno apos movimentar peca
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove (Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) { //Se houver peca capturada, retirar a peca da lista do tabuleiro e adicionar peca na lista de pecas capturadas
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) { //Verifica se nao existe peca na posicao informada
			throw new ChessException("Nao existe peca na posicao de origem informada");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { //Verifica se peca escolhida para movimentar é do jogador atual
			throw new ChessException("A escolha de peca nao é sua");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {// Verifica se existe possivel movimento para a peca escolhida
			throw new ChessException("Nao ha movimento possivel para a peca escolhida");
		}
	}
	
	private void validadeTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {//Verifica se a peca na posicao source pode ser moviemntada na posicao target escolhida
			throw new ChessException("A peca escolhida nao pode ser movida para a posicao de destino");
		}
	}
	
	// Metodo responsavel pela troca de turno da partida 
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //If ternário
	}
	
	//Metodo criado para que a inicializacao da partida (metodo initialSetup()) seja efetuada pela camada de xadrez(chess) e nao mais pelo board
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); //Adicionando pecas do tabuleiro na lista para controle de pecas capturadas
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
