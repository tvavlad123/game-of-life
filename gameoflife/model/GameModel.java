package gameoflife.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model for our application (part of MVC) Interacts with Board
 *
 * @author VladAlexandru
 */
public class GameModel {

    private Board board;

    private Timeline timeline;

    public GameModel(Board board) {
        this.board = board;
        timeline = new Timeline();
        setTimelineDelay(10);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void oneIteration() {

        int xCoord, yCoord;

        ArrayList<Board.BoardSquare> activeNeighbors;
        List<Helper> helpers = new ArrayList<>();

        for (Board.BoardSquare square : board.getAllSquares()) {
            activeNeighbors = this
                    .getActiveSquares(board.getSquareNeighbors(square.getRow(), square.getCol()));
            Cell button = square.getButton();
            xCoord = square.getRow();
            yCoord = square.getCol();

            if (button.isActive()) {
                if (activeNeighbors.size() > 3 || activeNeighbors.size() < 2) {
                    helpers.add(new Helper(xCoord, yCoord, 0));
                } else {

                    helpers.add(new Helper(xCoord, yCoord, 2));
                }
            } else {
                if (activeNeighbors.size() == 3) {
                    helpers.add(new Helper(xCoord, yCoord, 1));
                }
            }
        }

        for (Helper h : helpers) {
            if (h.getState() == 1)
                Board.squares[h.getX()][h.getY()].getButton().setState(Cell.STATE_ACTIVE);
            if (h.getState() == 0)
                Board.squares[h.getX()][h.getY()].getButton().setState(Cell.STATE_INACTIVE);
        }
    }

    public ArrayList<Board.BoardSquare> getActiveSquares(ArrayList<Board.BoardSquare> squares) {
        ArrayList<Board.BoardSquare> collection = new ArrayList<>();

        for (Board.BoardSquare square : squares) {
            if (square.getButton().isActive()) {
                collection.add(square);
            }
        }

        return collection;
    }

    public void randomLivelySquare(int range) {
        Random rand = new Random();
        for (Board.BoardSquare square : board.getAllSquares()) {
            if (0 == rand.nextInt(range)) {
                square.getButton().setState(Cell.STATE_ACTIVE);
            } else {
                square.getButton().setState(Cell.STATE_INACTIVE);
            }
        }
    }

    public void clearBoard() {
        board.setSize(board.getSize());
    }

    public void setTimelineDelay(int delay) {
        getTimeline().stop();
        getTimeline().getKeyFrames().clear();
        if (delay < 10) {
            getTimeline().getKeyFrames().add(createKeyFrame(10));
        } else {
            getTimeline().getKeyFrames().add(createKeyFrame(delay));
        }
    }

    private KeyFrame createKeyFrame(int delay) {
        return new KeyFrame(Duration.millis(delay), event -> oneIteration());
    }

    public Timeline getTimeline() {
        return timeline;
    }
}