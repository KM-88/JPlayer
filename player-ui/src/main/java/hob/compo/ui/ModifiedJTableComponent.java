/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.ui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

import javax.swing.JTable;

import app.exception.ResourceNotAvailableException;
import framew.global.ApplicationConstantsUI;
import framew.global.ApplicationResourceUI;
import framew.log.Logger;
import hob.compo.player.PlayList;
import hob.compo.obj.PlayListItems;

/**
 *
 * @author kranti
 */
public class ModifiedJTableComponent extends JTable implements ViewComponent {

	private FileNameRowTableModel tableModel;

	public void initialize() {
		tableModel = new FileNameRowTableModel();
		this.setModel(tableModel);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Logger log = (Logger) ApplicationConstantsUI.getLogger();
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					String path = (String) target.getModel().getValueAt(row, 1);
					String fileName = (String) target.getModel().getValueAt(row, 0);
					PlayListItems item = new PlayListItems(path, fileName);
					PlayList.add(item);
					log.debug(ModifiedJTableComponent.class, "mouseClicked()", "New Item for playlist -> " + item);
					MainUIComponents.updatePlayingComponents();
				}
			}
		});
	}

	@Override
	public void refreshData() {
		tableModel.updateData();
		tableModel.fireTableDataChanged();
		System.gc();
	}

	@Override
	public Component getRegisteredComponent() {
		return this;
	}

	public void setSearchString(String searchString) {
		tableModel.searchUpdate(searchString);
	}

	@Override
	public void searchUpdate(String searchText) {
		tableModel.searchUpdate(searchText);
	}

}
