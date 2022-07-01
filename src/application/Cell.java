package application;

import javafx.fxml.FXMLLoader;

public class Cell {
	private boolean isMine; 
	private int neighboringMines;
	private boolean flagged;
	private boolean isRevealed;
	private int row; 
	private int col;
	
	
	
	public Cell() { //initializes a Cell object. Because much of the info relies on the other cells in the grid being generated, everything is set to default here. 
		isMine = false; 
		neighboringMines = 0; 
		flagged = false; 
		isRevealed = false;
		
		//All modification of these variables will be done in board, using setter methods from below.
	}
	
	public Cell(int r, int c) {
		isMine = false; 
		neighboringMines = 0; 
		flagged = false; 
		isRevealed = false;
		row = r;
		col = c;
	
	
	}
	
	
	public void reveal() { //This sets the boolean property of isRevealed to true.
		isRevealed = true; 
	}
	
	
	public void makeMine() { //Setter method to set the mine property to true.
		this.isMine = true;
	}
	
	public void flag() { //Tells that the cell is now flagged as a mine by the player

		if (!flagged) {
			flagged = true;
		} else {
			flagged = false;
		}
	}
	
	public void setMineNumber(int numMines) { //This is called from Board to tell the cell how many of its neighbors are mines
		neighboringMines = numMines;
	}
	
	public int mineNumber() { //gets the number of neighbors that are mines. 
		return neighboringMines;
	}
	
	public boolean isFlagged() {//checks if the given cell has been marked as a mine by the player
		return this.flagged;
	}
	
	public boolean isMine() { //checks if the given cell is a mine. 
		return this.isMine;
	}
	
	public boolean isRevealed() { //checks if the given cell has been revealed already.
		return this.isRevealed;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	

}
