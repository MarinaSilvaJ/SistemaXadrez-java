package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	//Movimentacoes possiveis do peao: Ir para frente duas casas se for primeira jogada, ir para as diagonais se estiver vazio ou se tiver peca adversaria
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		if (getColor() == Color.WHITE) { //Se for o turno do jogador pecas brancas
			p.setValue(position.getRow() - 1, position.getColumn()); //Testando se movimento for uma casa para frente
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValue(position.getRow()  - 2, position.getColumn()); //Testando se movimento for duas casas para frente
			Position p2 = new Position(position.getRow() - 1, position.getColumn()); //Variavel para testar antes se a peca a frente esta vazia e se posicao existe
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValue(position.getRow() - 1, position.getColumn() - 1); //Testando se movimento for casa da diagonal esquerda
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValue(position.getRow() - 1, position.getColumn() + 1); //Testando se movimento for casa da diagonal direita
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		else { //Se movimento atual for pecas pretas
			p.setValue(position.getRow() + 1, position.getColumn()); //Testando se movimento for uma casa para frente
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValue(position.getRow()  + 2, position.getColumn()); //Testando se movimento for duas casas para frente
			Position p2 = new Position(position.getRow() - 1, position.getColumn()); //Variavel para testar antes se a peca a frente esta vazia e se posicao existe
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValue(position.getRow() + 1, position.getColumn() - 1); //Testando se movimento for casa da diagonal esquerda
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValue(position.getRow() + 1, position.getColumn() + 1); //Testando se movimento for casa da diagonal direita
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
}
