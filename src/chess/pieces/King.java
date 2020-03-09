package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

//Peca Rei
public class King extends ChessPiece{

	private ChessMatch chessMatch; 
	
	public King(Board board, Color color, ChessMatch chessMatch) { //Adicionando argumento chessMatch para que a classe Rei acesse a partida (Implementacao jogada Roque)
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "R"; 
	}
	
	//Metodo auxiliar para verificar se posicao passada é vazia(nula) ou se a peca é adversaria, para poder movimentar
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) { //Metodo para testar se a Torre esta apta para jogada Roque
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0; //Retorna true se posicao nao for nula, se peca da posicao for Torre, se a cor da  peca for a cor da jogada atual e se movimento de partida for zero
	}	
	
	//Movimentos para Rei: Uma casa em qualquer direcao a partir da propria posicao desde que metodo acima retorne verdadeiro e posicao exista
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//acima
		p.setValue(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//abaixo
		p.setValue(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//esquerda
		p.setValue(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//direita
		p.setValue(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//noroeste
		p.setValue(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//nordeste
		p.setValue(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudoeste
		p.setValue(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudeste
		p.setValue(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Movimento especial Roque: Só podera executar se as pecas Rei e Torre nao foram movimentadas, partida nao estiver em check e casas dos lados estiverem vazias
		if (getMoveCount() == 0 && !chessMatch.getCheck()) { 
			//Testando Roque Pequeno (Lado direito)
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3); //Verificando a terceira posicao a direita depois do Rei
			if (testRookCastling(posT1)) { // Se posicao for Torre e estiver apta ao movimento Roque, verificar se as duas posicoes ao lado esquerdo estao vazias
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {//Se as posicoes estiverem vazias, retornar para matriz de movimentos possiveis verdadeiro (Apto a jogada Roque pequeno)
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			//Testando Roque Grande (Lado esquerdo)
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4); //Verificando a quarrta posicao a esquerda depois do Rei
			if (testRookCastling(posT2)) { // Se posicao for Torre e estiver apta ao movimento Roque, verificar se as tres posicoes ao lado direito estao vazias
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {//Se as posicoes estiverem vazias, retornar para matriz de movimentos possiveis verdadeiro (Apto a jogada Roque Grande)
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}	
		}
		
		return mat;
	}
}
