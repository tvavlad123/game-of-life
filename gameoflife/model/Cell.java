package gameoflife.model;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 * The buttons on the board grid Can be either active or inactive (state of the
 * button)
 *
 * @author VladAlexandru
 */
public class Cell extends Button {

    public static final byte STATE_INACTIVE = 0;

    public static final byte STATE_ACTIVE = 1;

    private byte state;

    private String styleClassDefault;

    private String styleClassActive;

    public Cell() {
        super();
        this.setStyleClassDefault("btn-board");
        this.setStyleClassActive("active");
        this.setState(STATE_INACTIVE);
    }

    public void refreshStyleClass() {
        ObservableList<String> styleClass = this.getStyleClass();
        styleClass.remove("button");

        switch (this.getState()) {
            case STATE_ACTIVE:
                styleClass.addAll(this.getStyleClassDefault(), this.getStyleClassActive());
                break;
            case STATE_INACTIVE:
            default:
                styleClass.add(this.getStyleClassDefault());
                styleClass.remove(this.getStyleClassActive());
                break;
        }
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        if (state < STATE_INACTIVE || state > STATE_ACTIVE) {
            throw new IllegalArgumentException("unknown state");
        }

        this.state = state;
        this.refreshStyleClass();
    }

    public boolean isActive() {
        return this.state == STATE_ACTIVE;
    }

    public String getStyleClassDefault() {
        return styleClassDefault;
    }

    public void setStyleClassDefault(String styleClassDefault) {
        this.styleClassDefault = styleClassDefault;
    }

    public String getStyleClassActive() {
        return styleClassActive;
    }

    public void setStyleClassActive(String styleClassActive) {
        this.styleClassActive = styleClassActive;
    }
}