package chess;

import boardgame.BoardException;

//Excecao criada para camada chess (jogo xadrez)
//Considerando que uma excecao de xadrez tambem pode ser uma excecao de tabuleiro, a ChessException pode herdar a BoardException
//para simplificar a tratativa do programa.

public class ChessException extends BoardException { 
	
	private static final long serialVersionUID = 1L;
	
	public ChessException(String msg) {
		super(msg);
	}
}
