package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	//Classe principal do sistema, onde sera implementada toda regra do jogo.
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if (testCheck(currentPlayer)) { // Apos movimentar peca, verificar se jogador atual esta se colocando em check
			undoMove(source, target, capturedPiece);
			throw new ChessException("Voce nao pode se colocar em check");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; //Teste para saber se o oponente esta em check
		
		if (testCheckMate(opponent(currentPlayer))) { //Verifica se oponente esta em checkmate, se estiver ja finaliza a partida
			checkMate = true;
		}
		else {
			nextTurn(); //Troca de turno apos movimentar peca
		}
		
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove (Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) { //Se houver peca capturada, retirar a peca da lista do tabuleiro e adicionar peca na lista de pecas capturadas
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// Implementando movimento da peca Torre no Roque Pequeno
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { //Se peca que esta sendo movimentada for Rei e e posicao destino for igual posicao origem + 2, movimentar a peca Torre para posicao do movimento Roque Pequeno
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// Implementando movimento da peca Torre no Roque Grande
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) { //Se peca que esta sendo movimentada for Rei e e posicao destino for igual posicao origem - 2, movimentar a peca Torre para posicao do movimento Roque Grande
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		return capturedPiece;
	}
	
	//Metodo para desfazer movimento, em caso de xeque
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece != null) { //Caso exist peca capturada, devolver para posicao de destino, retirar da lista de pecas caputadas e adicionar na lista de pecas do tabuleiro
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// Implementando movimento da peca Torre no Roque Pequeno
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { //Se peca que esta sendo movimentada for Rei e e posicao destino for igual posicao origem + 2, movimentar a peca Torre para posicao do movimento Roque Pequeno
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		// Implementando movimento da peca Torre no Roque Grande
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) { //Se peca que esta sendo movimentada for Rei e e posicao destino for igual posicao origem - 2, movimentar a peca Torre para posicao do movimento Roque Grande
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
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
	
	//Metodo para retornar a cor do oponente atual
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	//Metodo criado para localizar o rei na lista atual do tabuleiro da cor passada pelo argumento, usado no metedo testeCheck
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Nao existe rei " + color + "no tabuleiro"); //Excecao nao pode ocorrer
	}
	
	//Metodo para testar se movimento colocara jogador atual em check
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	//Metodo para testar se jogo esta em Checkmate
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) { //Se movimento nao por partida em check, ja retornar para continuidade de movimento
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) { //Percorrer lista com todas as pecas do jogador atual do tabuleiro
			boolean[][] mat = p.possibleMoves(); //Matriz com movimentos possiveis da peca atual
			for (int i=0; i<board.getRows(); i++) { //For para percorrer matriz de  movimentos possiveis da peca atual
				for (int j=0; j<board.getColumns(); j++) {
					if (mat[i][j]) { //Se movimento atual for verdadeiro, efetuar movimento no tabuleiro (makeMove) para testar se peca atual deixa o jogo em check
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color); //Apos fazer movimento, testar se esta em check
						undoMove(source, target, capturedPiece); //Desfazer movimento
						if (!testCheck) { //Se movimento atual nao retornar check, retornar false
							return false;
						}
						
					}
					
				}
			}
		}
		return true;
	}
	
	//Metodo criado para que a inicializacao da partida (metodo initialSetup()) seja efetuada pela camada de xadrez(chess) e nao mais pelo board
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); //Adicionando pecas do tabuleiro na lista para controle de pecas capturadas
	}
	
	//Metodo responsavel em iniciar a partida do jogo.
	private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}
