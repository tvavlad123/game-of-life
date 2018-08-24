package gameoflife.model;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Board grid for GoL
 *
 * @author VladAlexandru
 */
public class Board extends GridPane {

    public static BoardSquare[][] squares;

    private int size;

    private boolean blocked;

    public class BoardSquare {

        private Cell button;

        private int row;

        private int col;

        public BoardSquare(Cell button, int row, int col) {
            this.button = button;
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public Cell getButton() {
            return button;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        squares = new BoardSquare[size][size];
        this.generate();
    }

    public ArrayList<Board.BoardSquare> getAllSquares() {
        ArrayList<Board.BoardSquare> collection = new ArrayList<Board.BoardSquare>();

        for (int row = 0; row < this.getSize(); ++row) {
            collection.addAll(Arrays.asList(squares[row]));
        }

        return collection;
    }

    public boolean isSquareExists(int row, int col) {
        if (row >= 0 && row < this.getSize() && col >= 0 && col < this.getSize()) {
            return true;
        } else {
            return false;
        }
    }

    private void cellPositionValidation(int row, int col) {
        if (row < 0 || row >= this.getSize()) {
            throw new ArrayIndexOutOfBoundsException("row index out of bounds");
        }

        if (col < 0 || col >= this.getSize()) {
            throw new ArrayIndexOutOfBoundsException("col index out of bounds");
        }
    }

    public void setSquare(int row, int col, Cell button) {
        this.cellPositionValidation(row, col);
        squares[row][col] = new BoardSquare(button, row, col);
        this.add(button, col, row);
    }

    public Board.BoardSquare getSquare(int row, int col) {
        this.cellPositionValidation(row, col);
        return squares[row][col];
    }

    private void generate() {
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();

        for (int i = 0; i < this.getSize(); ++i) {
            this.getColumnConstraints().add(new ColumnConstraints());
            this.getRowConstraints().add(new RowConstraints());
        }

        for (int row = 0; row < this.getSize(); ++row) {
            for (int col = 0; col < this.getSize(); ++col) {
                final Cell button = new Cell();
                button.setOnAction(event -> {
                    if (!isBlocked()) {
                        if (button.isActive()) {
                            button.setState(Cell.STATE_INACTIVE);
                        } else {
                            button.setState(Cell.STATE_ACTIVE);
                        }
                    }
                });

                this.setSquare(row, col, button);
            }
        }
    }

    public ArrayList<Board.BoardSquare> getSquareNeighbors(int row, int col) {
        this.cellPositionValidation(row, col);
        ArrayList<Board.BoardSquare> list = new ArrayList<Board.BoardSquare>();

        for (int i = col - 1; i < col + 2; ++i) {
            if (this.isSquareExists(row - 1, i)) {
                list.add(this.getSquare(row - 1, i));
            }

            if (this.isSquareExists(row + 1, i)) {
                list.add(this.getSquare(row + 1, i));
            }
        }

        if (this.isSquareExists(row, col - 1)) {
            list.add(this.getSquare(row, col - 1));
        }

        if (this.isSquareExists(row, col + 1)) {
            list.add(this.getSquare(row, col + 1));
        }

        return list;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}