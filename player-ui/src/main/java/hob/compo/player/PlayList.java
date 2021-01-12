package hob.compo.player;

import java.util.ArrayList;
import java.util.List;

import core.global.ApplicationConstantsFramework;
import hob.compo.engine.PlayerThreadGenerator;
import hob.compo.obj.PlayListItems;
import hob.compo.ui.PlayListPanel;


public class PlayList {

	private static List<PlayListItems> playListItems = new ArrayList<>();

	private static int current = 0;

	public static void play(int cmd) {

	}

	public static void playNext() {
		PlayListItems item = getByCurrentReference(1);
		current = current + 1;
		PlayerThreadGenerator.start(item);
	}

	public static void playPrevious() {
		PlayListItems item = getByCurrentReference(-1);
		current = current - 1;
		PlayerThreadGenerator.start(item);
	}

	public static void reset() {
		playListItems = new ArrayList<>();
	}

	public static PlayListItems getItem(int cmd) {
		return playListItems.get(cmd);
	}

	public static PlayListItems getNext() {
		return getByCurrentReference(current++);
	}

	public static void add(PlayListItems item) {
		playListItems.add(item);
		PlayListPanel playListPanel = (PlayListPanel) ApplicationConstantsFramework.getByName("playListPanel");
		if (null != playListPanel)
			playListPanel.updateItems();
		PlayerThreadGenerator.update();

	}

	public static void addAllFiles(List<String> files) {
		for (String file : files)
			add(new PlayListItems(file, file));
	}

	public static List<PlayListItems> getAllCurrentItemses() {
		return playListItems;
	}

	public static PlayListItems getByCurrentReference(int i) {
		return getItem(i);
	}

}
