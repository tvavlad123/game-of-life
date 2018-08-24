package gameoflife.model;

public class Helper {
	private int x;
	private int y;

	private int state;

	public Helper(int x, int y, int state) {
		this.x = x;
		this.y = y;
		this.state = state;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getState() {
		return state;
	}
}