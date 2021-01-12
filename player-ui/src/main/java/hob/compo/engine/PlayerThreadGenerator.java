package hob.compo.engine;

import java.util.Scanner;

import hob.compo.obj.PlayListItems;
import hob.compo.player.PlayList;
import hob.compo.ui.MainUIComponents;

public class PlayerThreadGenerator {

	private static Thread thread;

	private static PlayerThread playerThread;

	private static Boolean isPaused = false;

	private static PlayListItems currentPlayItem;

	public static synchronized void start(PlayListItems playItem) {
		System.out.println("Starting player thread" + playItem.getResourceName());
		playerThread = new PlayerThread(playItem);
		thread = new Thread(playerThread);
		currentPlayItem = playItem;
		thread.setName(currentPlayItem.getResourceName());
		thread.start();
	}

	public static void start() {
		synchronized (isPaused) {
			isPaused = false;
		}
		System.err.println("Let's just kill this fucking thread man" + thread);
		if (null != thread) {
			System.out.println(thread.getState());
			synchronized (thread) {
				thread.interrupt();
				System.out.println("Killed the player thread");
			}
		}

		synchronized (currentPlayItem) {
			MainUIComponents.updatePlayingComponents();
			start(currentPlayItem);
		}
	}

	public static synchronized void pause() {
		isPaused = true;
		if (null != playerThread.getPlayer()) {
			playerThread.getPlayer().pause();
		} else if (null == thread || !thread.isAlive()) {
			currentPlayItem = null;
		}
	}

	public static synchronized void resume() {
		isPaused = false;
		if (null != playerThread.getPlayer()) {
			playerThread.getPlayer().resume();
		} else if (null == thread || !thread.isAlive()) {
			currentPlayItem = null;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		currentPlayItem = getPlayItems("/media/D/UNO/English/9000 Days.mp3", "Test");
		PlayerThreadGenerator.start();
		boolean done = false;
		System.out.println("Pause - 1\tResume - 2\t Exit - 3");
		while (!done) {
			System.out.print("\n$>");
			int i = sc.nextInt();
			switch (i) {
			case 1:
				playerThread.getPlayer().pause();
				break;
			case 2:
				playerThread.getPlayer().resume();
				break;
			case 3:
				done = true;
			}
		}
		System.err.println("Done with Switch");
	}

	public static PlayListItems getPlayItems(String url, String name) {
		PlayListItems playItem = new PlayListItems();
		playItem.setPathToResource(url);
		playItem.setResourceName(name);
		return playItem;
	}

	public static synchronized void update() {
		if (null == thread || !thread.isAlive())
			PlayList.playNext();
	}

	public static synchronized void play() {
		if (isPaused) {
			resume();
		} else {
			currentPlayItem = PlayList.getNext();
			start();
		}
	}

	public static PlayListItems getCurrentPlayListItem() {
		return currentPlayItem;
	}

	public static boolean getCurrentStatus() {
		return !isPaused;
	}
}
