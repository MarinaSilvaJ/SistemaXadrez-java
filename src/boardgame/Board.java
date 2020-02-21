package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; // Matriz
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) { //Implementando BoardException para que nao crie tabuleiro com menos de uma linha e uma coluna
			throw new BoardException("Erro criando tabuleiro: É necessário que tenha no minimo uma linha e uma coluna");
		}	
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	// Retirado setRows para que nao seja permitido alterar a qtd de linhas do tabuleiro

	public int getColumns() {
		return columns;
	}

	// Retirado setColumns para que nao seja permitido alterar a qtd de colunas do tabuleiro
	
	// Retornar matriz pieces(peca) pela linha e coluna
	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) { //Implementando BoardException caso a posicao informada nao exista.
			throw new BoardException("Posicao nao existe no tabuleiro");
		}
		return pieces[row][column];
	}
	
	// Sobrecarga do metodo acima que retorna a matriz pieces(peca) pela posicao
	public Piece piece(Position position) {
		if(!positionExists(position)) { //Implementando BoardException caso a posicao informada nao exista.
			throw new BoardException("Posicao nao existe no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//Ira atribuir a matriz piece a peca e posicao que veio como argumento (colocar a peca na posicao informada no tabuleiro)
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) { // Implementando BoardException para verificar se ja existe alguma peca na posicao que foi passada
			throw new BoardException("Já existe uma peca na posicao: " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	//Verifica se a posicao existe no tabuleiro dada uma linha e coluna
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	//Verifica se a posicao existe dada a posicao, reaproveitando o metodo acima.
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	//Verifica se existe uma peca na posicao informada via parametro
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) { //Implementando BoardException caso a posicao informada nao exista, antes de verificar se esta ocupada
			throw new BoardException("Posicao nao existe no tabuleiro");
		}
		return piece(position) != null;
	}
	
}
