package hob.compo.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

import core.obj.MP3FileSystemObject;
import framew.global.ApplicationConstantsUI;
import framew.log.Logger;
import hob.compo.obj.PlayListItems;
import javazoom.jl.decoder.JavaLayerException;
import utility.ResourceInputStream;

public class PlayerThread implements Runnable {

	private static Logger log;

	private Thread playerThread;

	private final PlayListItems playListItem;

	private JPlayer player;

	public PlayerThread(PlayListItems playItem) {
		this.playListItem = playItem;
		log = (Logger) ApplicationConstantsUI.getLogger();
	}

	@Override
	public void run() {
		try {
			System.out.println("Player thread Started");
			MP3FileSystemObject mp3FileSystemObject = new MP3FileSystemObject(
					Paths.get(playListItem.getPathToResource()));
			log.info(this.getClass(), "run()", "Player thread Started"+"\t" + "Playing : " + mp3FileSystemObject);
			playerThread = Thread.currentThread();
			player = new JPlayer(generateInputStream(playListItem));
			player.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	private InputStream generateInputStream(PlayListItems playItem) {
		if (null != playItem.getResourceInputStream()
				&& (!((ResourceInputStream) playItem.getResourceInputStream()).isStreamClosed())) {
			return playItem.getResourceInputStream();

		} else {
			InputStream inputStream = null;
			try {
				inputStream = new ResourceInputStream(new File(playItem.getPathToResource()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			playItem.setResourceInputStream(inputStream);
			return inputStream;
		}
	}

	/**
	 * @return the playerThread
	 */
	public Thread getPlayerThread() {
		return playerThread;
	}

	public JPlayer getPlayer() {
		return player;
	}

}
