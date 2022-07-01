package application;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.Node;

public class GameBoardController {

	@FXML
	private GridPane grid;

	// Creates the gameBoard to be used in the game
	private Board gameBoard = new Board(); 
	
	
	
	// handler for left and right mouse clicks on board
	public void buttonClick(MouseEvent m) {
		
		// typecast source of mouseEvent to node so that we can get row and column
		Node n = (Node) m.getSource();
		
		// initialize row and column to be 0 to cover null cases
		int row = 0;
		int col = 0;

		// if row and or col are not null, get their positions
		if (GridPane.getRowIndex(n) != null) {
			row = GridPane.getRowIndex(n);
		}
		if (GridPane.getColumnIndex(n) != null) {
			col = GridPane.getColumnIndex(n);
		}
		
		// on mouse 1 click, set the calling controller and execute revealCell with above
		// row and col
		if (m.getButton() == MouseButton.PRIMARY) {
			if(!gameBoard.getCells()[row][col].isFlagged()) { //If the cell is flagged, the player is unable to reveal it.
				gameBoard.setCallingController(this);
				gameBoard.revealCell(row, col);
			}
			
		}
		
		// on mouse 2 click, get the cell and its related button
		else if (m.getButton() == MouseButton.SECONDARY) {
			
			Cell cell = gameBoard.getCells()[row][col];
			Button b = (Button) m.getSource();
			
			// if the cell has not been revealed, flag or unflag it based upon
			// its current flag state
			if (!cell.isRevealed()) {
				
				if (cell.isFlagged()) {
					cell.flag();
					b.setText("");
					b.setStyle(null);
				}
				
				else {
					cell.flag();
					b.setText("?");
					
					b.setStyle("-fx-font-weight: bold;" + 
							   "-fx-text-fill: crimson;" +
							   "-fx-font-size: 20;");
				}
			}
		}
	}
	
	
	// This method actually tells the board to display what is on the button
	public void cellRevealed(int row, int col) { 
		
		//If mine, reveal button with an X on it and trigger the gameEnd() method in board
		if (gameBoard.getCells()[row][col].isMine()) { 
			
			//Because buttons are stored in a 1D array of children of grid, access them with (row + (10*col))
			Button b = (Button) grid.getChildren().get(row + (10 * col));
			
			// set text and style of bomb to be capital x bold and size 20
			b.setText("X");

			b.setStyle("-fx-font-weight: bold;" + 
					   "-fx-font-size: 20;");
			
			// if bomb is hit, execute gameEnd() method
			gameBoard.gameEnd();
		}
		
		// if cell is revealed, get the number of bombs around the cell and display it inside of the cell
		else if (gameBoard.getCells()[row][col].isRevealed()) {
			
			//Because buttons are stored in a 1D array of children of grid, access them with (row + (10*col))
			Button b = (Button) grid.getChildren().get(row + (10 * col)); 
			int num = gameBoard.getCells()[row][col].mineNumber();
		
			//set the text to be the mine number and style it relative to the number
			b.setText(num + "");

			switch(num) {
			
			case 0: b.setStyle("-fx-font-size: 20;" +
							   "-fx-font-weight: bold;" +
					           "-fx-text-fill: mintcream;"); break;
					           
			case 1: b.setStyle("-fx-font-size: 20;" +
					   		   "-fx-font-weight: bold;" +
			           		   "-fx-text-fill: cornflowerblue;"); break;
			           		   
			case 2: b.setStyle("-fx-font-size: 20;" +
					   	       "-fx-font-weight: bold;" +
			                   "-fx-text-fill: mediumseagreen;"); break;
			                   
			case 3: b.setStyle("-fx-font-size: 20;" +
					   		   "-fx-font-weight: bold;" +
			                   "-fx-text-fill: gold;"); break;
			                   
			case 4: b.setStyle("-fx-font-size: 20;" +
							   "-fx-font-weight: bold;" +
			           		   "-fx-text-fill: darkgoldenrod;"); break;
			           		   
			default: break;
			}
		}
	}
	
	
	//Sets every button on the grid back to blank
	public void mask() {
		
		for(int i = 0; i < grid.getChildren().size(); i++) {
			
			try {
				Button b = (Button) grid.getChildren().get(i);
				b.setText("");
			}
			
			catch(ClassCastException e) {
				
			}
		}
	}
	
}


