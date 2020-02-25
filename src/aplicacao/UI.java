package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

//User Interface

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	//Metodo para limpar tela 
	public static void cleanScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	//Metodo respondavel em ler a posicao informada pelo usuario
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine(); // Recebendo a posicao que o usuario informar
			char column = s.charAt(0); //Atribuindo primeira posicao da string para a variavel coluna.
			int row = Integer.parseInt(s.substring(1)); //Recortando string e convertendo para int para atribuir a variavel linha
			return new ChessPosition(column, row);
		}
		catch (RuntimeException e) { //Exception validar caso usuario informe uma posicao que esteja fora dos criterios da msg
			throw new InputMismatchException("Erro para instanciar posicao do xadrez: Valores validos sao de a1 até h8");
		}
	}
	
	//Metodo para imprimir na tela o turno e jogador atual
	public static void printMatch(ChessMatch chessMatch) {
		printBoard(chessMatch.getPiece());
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		System.out.println("Waiting Player: " + chessMatch.getCurrentPlayer());
	}
	
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false); 
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	//Metodo sobrecarga para quando precisar colorir o fundo com os possiveis movimentos de acordo com a matriz possibleMoves recebida pelo parametro
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	// Metodo para impressao de uma peca que sera utilizada na montagem do layout
	// pelo metodo acima.
	private static void printPiece(ChessPiece piece, boolean background) { 
		if (background) { 
			System.out.print(ANSI_PURPLE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} 
		else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

}
