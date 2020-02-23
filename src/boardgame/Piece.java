package boardgame;

public abstract class Piece { //Necessario alterar o tipo da classe para abstrato tambem devido a criacao do metodo abstrato
	
	// Posicao nao corresponde a posicao da peca, sendo assim protegida "#" para ser acessada apenas dentro do mesmo pacote e subclasse.
	protected Position position; 
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null; 
	}

	protected Board getBoard() { 
		return board;
	}
	
	//Set retirado pois o tabuleiro nao pode ser alterado.
	
	public abstract boolean[][] possibleMoves(); // Metodo abstrato pq cada peca possui suas regras de movimento, onde sera definida em suas respectivas classes.
	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	//Metodo para verificar se existe possivel movimento na matriz para a peca
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}	
}
