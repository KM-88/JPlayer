/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import hob.compo.obj.PlayListItems;
import hob.compo.player.PlayList;


/**
 *
 * @author kranti
 */
public class PlayListPanel extends JPanel {

	private final JPanel itemListPanel = new JPanel();

	private final JLabel mainLabel;

	public PlayListPanel() {
		this.mainLabel = new JLabel("Items");
		this.setLayout(new GridLayout(2, 1));
		this.add(mainLabel);
		this.add(itemListPanel);
	}

	public void updateItems() {
		List<PlayListItems> playItems = PlayList.getAllCurrentItemses();
		itemListPanel.removeAll();
		itemListPanel.setLayout(new GridLayout(playItems.size(), 1, 0, 2));
		boolean isOddEven = false;
		for (PlayListItems playItem : playItems) {
			itemListPanel.add(new PlayListItemsLabel(playItem, isOddEven));
			isOddEven = !isOddEven;
		}
		this.repaint();
	}

	public void addItem(PlayListItems items) {
		itemListPanel.add(new JLabel(items.getResourceName()));
	}

}

class PlayListItemsLabel extends JLabel {

	private PlayListItems playListItems;

	public PlayListItemsLabel(PlayListItems playListItems, boolean isOddEven) {
		super(playListItems.getResourceName());
		this.playListItems = playListItems;
		this.addMouseListener(new PlayListItemLabelMouseAdapter());
		this.setToolTipText("Play " + this.playListItems.getResourceName());
		this.setForeground(isOddEven ? Color.gray : Color.white);
	}

	public PlayListItems getPlayListItems() {
		return playListItems;
	}

	public void setPlayListItems(PlayListItems playListItems) {
		this.playListItems = playListItems;
	}
}

class PlayListItemLabelMouseAdapter extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (e.getButton() == MouseEvent.BUTTON1) {
			PlayListItemsLabel playListItemsLabel = (PlayListItemsLabel) e.getSource();
			PlayListItems item = playListItemsLabel.getPlayListItems();
			PlayList.add(item);
		} else {
			new JPopupMenu("Removing this item : Not implmented yet");
		}
	}

}
