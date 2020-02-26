package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column; //char devido a coluna ser representada por letras
	private int row;
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) { //Implementando ChessException caso posicao informada for incorreta
			throw new ChessException("Erro para instanciar posicao do xadrez: Valores validos sao de a1 até h8");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	//Retirado setColumn para que nao ocorra risco de alterar a coluna informada

	public int getRow() {
		return row;
	}

	//Retirado setRow para que nao ocorra risco de alterar a linha informada
	
	//Convertendo posicao normal para posicao xadrez
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	//Convertendo posicao xadrez para posicao normal
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row; 
	}
	
}
