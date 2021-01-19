package Abalone.Main;

import Abalone.UI.BoardUI;
import Abalone.UI.Hexagon;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.lang.InterruptedException;

public class PlayerH implements Player {
	private BoardUI board;
	public int turn;

	public boolean flag_inputObtained = false;

	public PlayerH() {

	}

	@Override
	public void setTurn(int turn) {
		this.turn = turn;
	}

	@Override
	public void setBoard(BoardUI board) {
		this.board = board;
	}

	private MoveDirection dir;

	EventHandler keyHandler  = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent e) {
			switch (e.getCode()) {
				case Q:
					dir = MoveDirection.TOP_LEFT;
					break;
				case A:
					dir = MoveDirection.LEFT;
					break;
				case Z:
					dir = MoveDirection.BOTTOM_LEFT;
					break;
				case E:
					dir = MoveDirection.TOP_RIGHT;
					break;
				case D:
					dir = MoveDirection.RIGHT;
					break;
				case C:
					dir = MoveDirection.BOTTOM_RIGHT;
					break;
				case ENTER:
					if (dir != null) {
						setDone();
					}
					break;
				case P:
					if (dir != null) {
						setDone();
					}
					break;
			}
		}
	};

	public synchronized void setDone() {
		flag_inputObtained = true;
		System.out.println("enter pressed");
		this.notifyAll();
	}

	public synchronized void waitUntilDone() {
		while (!flag_inputObtained) {
			try {
				this.wait();
			} catch (InterruptedException e) {

			}
		}
	}

	@Override
	public Move collectMove() {
		Move ans = new Move();
		ans.turn = turn;

		Thread inpThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Hexagon.accessableScene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
			}
		});
		inpThread.start();
		waitUntilDone();

		Hexagon.accessableScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyHandler);

		ans.dir = dir;
		ans.pushing = board.getSelected();
		board.unselect();
		flag_inputObtained = false;

		System.out.println(ans);

		dir = null;

		return ans;
	}
}
