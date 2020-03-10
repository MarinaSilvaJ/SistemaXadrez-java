package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while (!chessMatch.getCheckMate()) {
			try {
				UI.cleanScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.cleanScreen();
				UI.printBoard(chessMatch.getPiece(), possibleMoves);
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				//Apos cada jogada, se houver peca capturada, adicionar na lista
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				//Verificar se existe peca para ser promovida
				if (chessMatch.getPromoted() != null) {
					System.out.print("Entre com a peca para promocao: (B/C/T/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}
				
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		UI.cleanScreen();
		UI.printMatch(chessMatch, captured);

	}

}
