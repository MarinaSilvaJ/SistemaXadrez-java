package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; // Matriz
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	// Retornar matriz pieces(peca) pela linha e coluna
	public Piece piece(int row, int column) {
		return pieces[row][column];
	}
	
	// Sobrecarga do metodo acima que retorna a matriz pieces(peca) pela posicao
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//Ira atribuir a matriz piece a peca e posicao que veio como argumento.
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
}
