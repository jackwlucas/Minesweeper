package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EndGameWindowController {
	
	@FXML 
	private Button restartButton;
	
	@FXML 
	private Button endGameButton;
	
	@FXML 
	private Label endGameLabel;
	
	@FXML 
	private AnchorPane mainPane;
	
	//is a reference to the Board class, so that methods from it may be used above
	private Board callingBoard;
	
	
	@FXML
	public void restartButtonClicked(ActionEvent e) { //Hides this window and resets the game board
		
		mainPane.getScene().getWindow().hide();
		callingBoard.reset();
	}
	
	
	@FXML 
	public void endGameButtonClicked(ActionEvent e) { //Closes the whole application
	
		mainPane.getScene().getWindow().hide();
		callingBoard.exit();
	}

	
	public void setCallingController(Board b) {
		callingBoard = b;
	}
	
	public void changeText(String s) { //changes the text of endGameLabel
		endGameLabel.setText(s);
		
		
		
	}
	
	public void backgroundStyle(String s) {
		mainPane.setStyle(s);
	}
}
