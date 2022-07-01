package application;

import java.io.IOException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Board {
	private Cell[][] cells;
	private GameBoardController gameBoardController;
	private EndGameWindowController endGameWindowController;
	private int revealedCells = 0;
	
	public Board() { //Initializes the board with a new 2D array of Cell objects.
		cells = new Cell[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) { //fills all 100 indices with a new default Cell object
				cells[i][j] = new Cell(i, j);
			}
		}
		
		for(int i = 0; i<  10; i++) { //Fills the board with 10 mines
			int x = (int)(Math.random() * 10);
			int y = (int)(Math.random() * 10);
			if(cells[x][y].isMine()) { //To protect against duplicates - loop again
				i--;
			}
			else { //if the xy pair isnt already a mine, then make it one
				cells[x][y].makeMine();
			}
		}
		
		//Read through each cell of the board and ask if its neighbors are mines
		int mines = 0;
		for(int i = 0; i < 10; i ++) {
			for(int j = 0; j < 10; j++) {
				
				checkNeighbors(i, j);
			}
		}
		
		
		
		this.unmaskBoard();
	}
	

	
	
	
	private Cell[] getNeighbors(int x, int y) {
		Cell[] neighbors = new Cell[8];
		neighbors[0] = getTopLeftNeighbor(x, y);
		neighbors[1] = getTopNeighbor(x, y);
		neighbors[2] = getTopRightNeighbor(x, y);
		neighbors[3] = getLeftNeighbor(x, y);
		neighbors[4] = getRightNeighbor(x, y);
		neighbors[5] = getBottomLeftNeighbor(x, y);
		neighbors[6] = getBottomNeighbor(x, y);
		neighbors[7] = getBottomRightNeighbor(x, y);
		return neighbors;
	}
	private void checkNeighbors(int x, int y) { //checks each neighbor of a cell, counts number of mines, and then sets the original cells mine number. Could be cleaner
		Cell[] neighbors = getNeighbors(x,y);
		int numMines = 0;
		for(int i = 0; i < 8; i++) {
			if(neighbors[i].isMine()) {
				numMines++;
			}
		}
		
		cells[x][y].setMineNumber(numMines);
		
	}
	
	
	
	public void unmaskBoard() { //For debugging purposes, reveal the whole board in the console. Mentioned in assignment description. 
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(cells[i][j].isMine()) {
					System.out.print("x ");
				}
				else {
					System.out.print(cells[i][j].mineNumber() + " ");
				}
			}
			System.out.println();
		}
	}
	
	public void revealCell(int row, int col) { //Takes the row and column of the cell to be revealed and reveals it. Will then recursively call itself to reveal neighbors.
		
		Cell[] neighbors = getNeighbors(row, col); //Gets all of the cells neighbors for use in loop below
		if(!cells[row][col].isRevealed()) { //This fixes the bug of clicking only one cell until you win
			if(!cells[row][col].isMine()) { //This ensures that clicking a mine as your 90th cell will not count as a win, by not counting towards your total.
				revealedCells++;
			}
		}
		cells[row][col].reveal(); //Tells the cell class to set the isRevealed property to true
		
		gameBoardController.cellRevealed(row, col); //Interacts with the GUI to visibly reveal the cell 
		
		if(revealedCells == 90) { //Win condition
			gameEnd();
		}

		
		if(cells[row][col].mineNumber() == 0) { //This ensures that only the neighbors of blank cells are revealed recursively
			for(Cell neighbor: neighbors) {
				if(neighbor.getRow() == -1) { //The neighbors methods set the row and col to -1 if they weren't in the grid, so this eliminates references to cells outside of the grid
				
				}
				else if(cells[neighbor.getRow()][neighbor.getCol()].isRevealed()) { //if the cell is already revealed, do nothing
				
				}
				else{ 
					revealCell(neighbor.getRow(), neighbor.getCol()); //Finally, recurse since the cell needs to be revealed
				}
			} 
		}
		
		
	}
	
	private Stage windowStage;
	private EndGameWindowController endGameWindow;
	public void gameEnd() { //Checks whether game win/loss conditions are met. If not, do nothing. If so, end the game
		if(windowStage == null) //If new window is not already made, make it
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/EndGameWindow.fxml")); //Gets the proper FXML file to bring up a second window
			AnchorPane windowRoot;
			try //All below instantiates all the necessary components to bring up a second window
			{
				windowRoot = (AnchorPane) loader.load();
				Scene windowScene = new Scene(windowRoot);
				windowStage = new Stage();
				windowStage.setScene(windowScene);
				endGameWindow = (EndGameWindowController) loader.getController();
				endGameWindow.setCallingController(this);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		
		if(revealedCells == 90) {
			endGameWindow.changeText("You Win!");
			endGameWindow.backgroundStyle("-fx-background-color: rgba(60, 179, 113, 0.5)");
		}
		else {
			endGameWindow.changeText("Game Over!");
			endGameWindow.backgroundStyle("-fx-background-color: rgba(220, 20, 60, 0.5)");
		}
		windowStage.show(); //Displays the other window
	//	System.out.println("Game Over! You hit a mine!");
	}
	
	
	
	
	public Cell[][] getCells(){  //accessor method for the GameBoardController
		return this.cells;
	}
	
	public void reset() { //Reinitializes the board, and clears the GUI
		cells = new Cell[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) { //fills all 100 indices with a new default Cell object
				cells[i][j] = new Cell(i, j);
			}
		}
		
		for(int i = 0; i<  10; i++) { //Fills the board with 10 mines
			int x = (int)(Math.random() * 10);
			int y = (int)(Math.random() * 10);
			if(cells[x][y].isMine()) { //To protect against duplicates - loop again
				i--;
			}
			else { //if the xy pair isnt already a mine, then make it one
				cells[x][y].makeMine();
			}
		}
		
		//Read through each cell of the board and ask if its neighbors are mines
		int mines = 0;
		for(int i = 0; i < 10; i ++) {
			for(int j = 0; j < 10; j++) {
				
				checkNeighbors(i, j);
			}
		}
		
		revealedCells = 0; //Reset counter of revealed cells (Win condition)
		
		
		
		this.unmaskBoard(); //reveals the board in the console
		
		gameBoardController.mask(); //Tells the gameBoard GUI to reset itself
	}
	
	public void exit() { //Closes the program. Called from the EndGameWindowController
		System.exit(0);
	}
	
	
	//All of the below follow the same process for getting the corresponding neighbor of a particular cell. All below used by checkNeighbors method
	private Cell getTopLeftNeighbor(int x, int y) {
		try {
			return cells[x-1][y-1];
		}
		catch(IndexOutOfBoundsException ex) {
			
		}
		return new Cell(-1, -1);
	}
	private Cell getTopRightNeighbor(int x, int y) {
		try {
			return cells[x+1][y-1];
		}
		catch(IndexOutOfBoundsException ex) {
			
		}
		return new Cell(-1, -1);
	}
	private Cell getBottomLeftNeighbor(int x, int y) {
		try {
			return cells[x-1][y+1];
		}
		catch(IndexOutOfBoundsException ex) {
			
		}
		return new Cell(-1, -1);
	}
	private Cell getBottomRightNeighbor(int x, int y) {
		try {
			return cells[x+1][y+1];
		}
		catch(IndexOutOfBoundsException ex) {
			
		}
		return new Cell(-1, -1);
	}
	private Cell getLeftNeighbor(int x, int y) {
		try {
			return cells[x-1][y];
		}
		catch(IndexOutOfBoundsException ex) {
			
		}
		return new Cell(-1, -1);
	}
	
	private Cell getRightNeighbor(int x, int y) {
		try {
			return cells[x+1][y];
		}
		catch(IndexOutOfBoundsException ex) {
			
		}
		return new Cell(-1, -1);
	}
	
	private Cell getBottomNeighbor(int x, int y) {
		try {
			return cells[x][y+1];
		}
		catch(IndexOutOfBoundsException ex) {
			
		}
		return new Cell(-1, -1);
		
	}
	
	private Cell getTopNeighbor(int x, int y) {
		try {
			return cells[x][y - 1];
		}
		catch(IndexOutOfBoundsException ex) {
			
		}
		return new Cell(-1, -1);
		
	}
	
	public void setCallingController(GameBoardController g) { //When the first button is revealed, connection established between this class and the GUI
		this.gameBoardController = g;
	}
	
	public void setCallingController(EndGameWindowController e) {
		this.endGameWindowController = e;
	}
}
