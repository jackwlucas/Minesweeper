package application;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class tests {
Board gameBoard;
	
	@Before 
	public void setup() {
		gameBoard = new Board();
		
	}
	
	@Test
	public void correctSize() {
		System.out.println(gameBoard.getCells().length * gameBoard.getCells()[0].length);
		assertEquals(100, gameBoard.getCells().length * gameBoard.getCells()[0].length);
	}
	
	@Test
	public void correctMines() {
		int numMines = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if (gameBoard.getCells()[i][j].isMine()) {
					numMines++;
				}
			}
		}
		
		assertEquals(numMines, 10);
	}
	
	
	@Test 
	public void checkNum() {
		int correct;
		int randomX = (int) Math.random() * 10;
		int randomY = (int) Math.random() * 10;
		Cell toCheck = gameBoard.getCells()[randomX][randomY];
		
		if(toCheck.isMine()) {
			
		}
		else {
		correct = sumOfNeighbors(randomX, randomY);
		
		assertEquals(correct, toCheck.mineNumber());
		}
	}
	
	public int sumOfNeighbors(int x, int y) {
		int sum = 0; 
		sum += topLeftNeighbor(x,y);
		sum += topNeighbor(x,y);
		sum += topRightNeighbor(x,y);
		sum += leftNeighbor(x,y);
		sum += rightNeighbor(x,y);
		sum += bottomLeftNeighbor(x,y);
		sum += bottomNeighbor(x,y);
		sum += bottomRightNeighbor(x,y);
		return sum;
	}
	
	public int topLeftNeighbor(int x, int y) {
		try {
			if(gameBoard.getCells()[x-1][y-1].isMine()) {
				return 1;
			}
		}
		catch(IndexOutOfBoundsException ioe) {
			
		}
		return 0;
	}
	public int topNeighbor(int x, int y) {
		try {
			if(gameBoard.getCells()[x][y-1].isMine()) {
				return 1;
			}
		}
		catch(IndexOutOfBoundsException ioe) {
			
		}
		return 0;
	}
	public int topRightNeighbor(int x, int y) {
		try {
			if(gameBoard.getCells()[x+1][y-1].isMine()) {
				return 1;
			}
		}
		catch(IndexOutOfBoundsException ioe) {
			
		}
		return 0;
	}
	public int leftNeighbor(int x, int y) {
		try {
			if(gameBoard.getCells()[x-1][y].isMine()) {
				return 1;
			}
		}
		catch(IndexOutOfBoundsException ioe) {
			
		}
		return 0;
	}
	
	public int rightNeighbor(int x, int y) {
		try {
			if(gameBoard.getCells()[x+1][y].isMine()) {
				return 1;
			}
		}
		catch(IndexOutOfBoundsException ioe) {
			
		}
		return 0;
	}
	public int bottomLeftNeighbor(int x, int y) {
		try {
			if(gameBoard.getCells()[x-1][y+1].isMine()) {
				return 1;
			}
		}
		catch(IndexOutOfBoundsException ioe) {
			
		}
		return 0;
	}
	public int bottomNeighbor(int x, int y) {
		try {
			if(gameBoard.getCells()[x][y+1].isMine()) {
				return 1;
			}
		}
		catch(IndexOutOfBoundsException ioe) {
			
		}
		return 0;
	}
	public int bottomRightNeighbor(int x, int y) {
		try {
			if(gameBoard.getCells()[x+1][y+1].isMine()) {
				return 1;
			}
		}
		catch(IndexOutOfBoundsException ioe) {
			
		}
		return 0;
	}
}

