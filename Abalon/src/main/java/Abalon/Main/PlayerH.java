package Abalon.Main;

import Abalon.UI.BoardUI;
import Abalon.UI.Hexagon;

import java.util.Scanner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File; 
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;  

import java.lang.InterruptedException;

public class PlayerH implements Player {
	private BoardUI board;
	public int turn;

	//private Lock lock = new ReentrantLock();
	//private Condition inputObtained = lock.newCondition();
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
            switch(e.getCode()) {
                case Q: dir = MoveDirection.TOP_LEFT; break;
                case A: dir = MoveDirection.LEFT; break;
                case Z: dir = MoveDirection.BOTTOM_LEFT; break;
                case E: dir = MoveDirection.TOP_RIGHT; break;
                case D: dir = MoveDirection.RIGHT; break;
                case C: dir = MoveDirection.BOTTOM_RIGHT; break; 
                case ENTER: flag_inputObtained = true;
                			System.out.println("enter pressed");
                            //notify();
                            break;
            }
        } 
    };

	@Override
	public Move collectMove() {//throws InterruptedException {
		Move ans = new Move();
		ans.turn = turn;
		
		Hexagon.accessableScene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
		while (!flag_inputObtained) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		/*try { 
			waitForInput();
		} catch (InterruptedException e) {
			System.out.println("input interrupted");
		}*/
		/*Thread inpThread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
		     	Hexagon.accessableScene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);   
            }
        });*/
     
        /*lock.lock();
        try {
     		//inpThread.setDaemon(false);
        	inpThread.start();

        	while(!flag_inputObtained) {
        		wait();
        	}
        } finally {
        	lock.unlock();
        }*/

        //try {
        	Hexagon.accessableScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
        //} catch (Exception e) {
        //	System.out.println("failed to remove key handler, aborting...");
        //	System.exit(0);
        //}
        ans.dir = dir;
        ans.pushing = board.getSelected();
        board.unselect();
        flag_inputObtained = false;

        System.out.println(ans);

        return ans;
	
		/*Scanner scr = new Scanner(System.in);
		int numb = scr.nextInt();
		int[][] a = new int[numb][2];

		for (int i = 0; i < numb; i++) {
			int x = scr.nextInt();
			int y = scr.nextInt();
			a[i][0] = x;
			a[i][1] = y;
		}

		Move ans = new Move();
		ans.pushing = a;
		ans.turn = turn;

		int dir = scr.nextInt();
		switch (dir) {
			case 1: ans.dir = MoveDirection.TOP_LEFT; break;
			case 2: ans.dir = MoveDirection.LEFT; break;
			case 3: ans.dir = MoveDirection.BOTTOM_LEFT; break;
			case 4: ans.dir = MoveDirection.TOP_RIGHT; break;
			case 5: ans.dir = MoveDirection.RIGHT; break;
			case 6: ans.dir = MoveDirection.BOTTOM_RIGHT; break;
		}

		return ans;*/
	}
}