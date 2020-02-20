package boardgame;

public class Piece {
	
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

}
