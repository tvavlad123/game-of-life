package gameoflife.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gameoflife.model.Board;
import gameoflife.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;


/**
 * Control data flow and update view
 *
 * @author VladAlexandru
 */
public class Controller implements Initializable {
    @FXML
    private Board board;


    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private Button btnRand;

    @FXML
    private Button btnClear;

    @FXML
    private Slider fieldBoardSize;

    private GameModel model;

    /**
     * Initialize model (including board size)
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        model = new GameModel(board);
        model.setTimelineDelay(100);
        board.setSize(20);
        fieldBoardSize.setValue(board.getSize());
        fieldBoardSize.valueProperty().addListener((ov, old_val, new_val) -> resizeAction());
    }

    /**
     * Requests the next operation
     */
    public void nextIterationAction() {
        model.oneIteration();
    }

    /**
     * Generates a random live cell
     */
    public void randCellAction() {
        model.randomLivelySquare(6);
    }

    /**
     * Plays the game
     */
    public void startAction() {
        btnStart.setDisable(true);
        btnStop.setDisable(false);
        btnClear.setDisable(true);
        btnRand.setDisable(true);
        fieldBoardSize.setDisable(true);

        board.setBlocked(true);
        model.getTimeline().play();
    }

    /**
     * Stops the game
     */
    public void stopAction() {
        btnStart.setDisable(false);
        btnStop.setDisable(true);
        btnClear.setDisable(false);
        btnRand.setDisable(false);
        fieldBoardSize.setDisable(false);

        board.setBlocked(false);
        model.getTimeline().stop();
    }

    /**
     * Clears the board
     */
    public void clearAction() {
        model.clearBoard();
    }

    /**
     */
    public void resizeAction() {
        board.setSize((int) fieldBoardSize.getValue());
    }
}