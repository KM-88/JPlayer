/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.EventMessage;
import app.exception.ResourceNotAvailableException;
import core.obj.FileSystemObject;
import framew.api.impl.LibraryItemSearchString;
import framew.api.infc.LibraryItemSearch;
import framew.api.infc.LibraryManager;
import framew.global.ApplicationConstantsUI;
import framew.global.ApplicationResourceUI;
import framew.log.Logger;
import framew.threads.threadgen.TaskEventExecutorRegistry;
import hob.compo.obj.PlayListItems;
import hob.compo.player.PlayList;

/**
 *
 * @author kranti
 */
public class FolderViewComponent implements ViewComponent {

	private JPanel mainPanel = new JPanel();
	private Logger log;
	private String searchText = "";
	private LibraryItemSearch LibraryItemSearch;

	@Override
	public void initialize() {
		LibraryItemSearch = new LibraryItemSearchString(searchText);
		log = (Logger) ApplicationConstantsUI.getLogger();
	}

	@Override
	public void refreshData() {
		final String methodName = "refreshData()";
		Integer index = 0;
		LibraryManager libraryManager = (LibraryManager) ApplicationConstantsUI.getByName("libraryManager");
		Map<FileSystemObject, List<FileSystemObject>> objects = libraryManager.match(LibraryItemSearch);
		int entries = 0;
                List<JPanel> folderPanels = new ArrayList<JPanel>();
		for (Map.Entry<FileSystemObject, List<FileSystemObject>> entrySet : objects.entrySet()) {
			JPanel folderPanel = new JPanel();
			FileSystemObject parent = entrySet.getKey();
			List<FileSystemObject> files = entrySet.getValue();
			files.sort((FileSystemObject a, FileSystemObject b) -> a.getName().compareTo(b.getName()));
			folderPanel.setLayout(new GridLayout(1, 2));
			JLabel folderLabel = new FileSystemsObjectLabel(
					null == parent || null == parent.getName() ? "" : parent.getName(), parent);
			folderLabel.addMouseListener(new FolderLabelMouseAdapter());
			folderLabel.setSize(100, files.size() * 14 - 2);
			JPanel filesPanel = new JPanel();
			filesPanel.setLayout(new GridLayout(files.size(), 1, 2, 2));
			filesPanel.setSize(100, files.size() * 14 - 2);
			folderPanel.add(folderLabel);
			for (FileSystemObject file : files) {
				JLabel fileLabel = new FileSystemsObjectLabel(file.getName(), file);
				fileLabel.addMouseListener(new FilesLabelMouseAdapter());
				fileLabel.setSize(100, 10);
				filesPanel.add(fileLabel);
				index++;
			}
			folderPanel.add(filesPanel);
			folderPanels.add(folderPanel);
			entries++;
		}
                mainPanel.removeAll();
                
                mainPanel.add(new JLabel("Fuck java"));
                folderPanels.forEach((folderPanel) -> {mainPanel.add(folderPanel);});
                mainPanel.add(new JLabel("Fuck java"));
		//mainPanel.repaint();
		log.debug(FolderViewComponent.class, methodName, "Elements Loaded : " + index + " of " + entries);

	}

	@Override
	public Component getRegisteredComponent() {
		return mainPanel;
	}

	private boolean containsSearchString(FileSystemObject object) {
		return searchText.isEmpty() || object.getName().contains(searchText)
				|| object.getLocation().contains(searchText);
	}

	@Override
	public void searchUpdate(String searchText) {
		this.searchText = searchText;
		EventMessage eventMessage = (EventMessage) ((Map) ApplicationConstantsUI.getByName("EVENT_MESSAGES"))
				.get("PERIODIC_VIEW_UPDATE_EXECUTION_EVENT");
		TaskEventExecutorRegistry.registerEventMessage(eventMessage);
	}

	private class FilesLabelMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			FileSystemsObjectLabel fsObjectLabel = (FileSystemsObjectLabel) e.getSource();
			FileSystemObject fsObject = fsObjectLabel.getSourceObject();
			System.out.println(fsObjectLabel.getSourceObject());
			PlayListItems item = new PlayListItems(fsObject.getLocation(), fsObject.getName());
			PlayList.add(item);
		}

	}

	private class FolderLabelMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			FileSystemsObjectLabel fsObjectLabel = (FileSystemsObjectLabel) e.getSource();
			System.out.println(fsObjectLabel.getSourceObject());
		}

	}

}
