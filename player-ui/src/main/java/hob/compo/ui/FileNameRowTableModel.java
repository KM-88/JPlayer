/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import app.EventMessage;
import core.obj.FileSystemObject;
import core.obj.FileTypeEnum;
import framew.api.impl.LibraryItemSearchString;
import framew.api.infc.LibraryManager;
import framew.global.ApplicationConstantsUI;
import framew.log.Logger;
import framew.threads.threadgen.TaskEventExecutorRegistry;

/**
 *
 * @author kranti
 */
public class FileNameRowTableModel extends DefaultTableModel {

	private String searchText = "";
	
	private framew.api.infc.LibraryItemSearch LibraryItemSearch = new LibraryItemSearchString(searchText);

	public void updateData() {
		final String methodName = "updateData()";
		Vector<Vector> data;
		if (null != this.getDataVector()) {
			data = this.getDataVector();
			data.removeAllElements();
		} else {
			data = getDefaultDataVector();
		}
		Logger log = (Logger) ApplicationConstantsUI.getLogger();
		Integer index = 0;
		LibraryManager libraryManager = (LibraryManager) ApplicationConstantsUI.getByName("libraryManager");
		Map<FileSystemObject, List<FileSystemObject>> objects = libraryManager.match(LibraryItemSearch);
		//Cache cache = CacheManager.getNamedCache("nativeCache_fileSystem");
		//Map<Object, Object> objects = (Map<Object, Object>) cache.getAllElements();
		log.info(FileNameRowTableModel.class, methodName, "Cache Size : " + objects.size());
		Vector<String> vector;
		List<FileSystemObject> sortList = new LinkedList<>();
		objects.forEach((k, v) -> {
			v.forEach((object) -> {
				if ((FileTypeEnum.FILE.equals(object.getObjectTypeEnum()) && containsSearchString(object))) {
					sortList.add(object);
				}
			});
		});
		/*for (Map.Entry<FileSystemObject, List<FileSystemObject>> entrySet : objects.entrySet()) {
			FileSystemObject object = (FileSystemObject) entrySet.getValue();
			if ((FileTypeEnum.FILE.equals(object.getObjectTypeEnum()) && containsSearchString(object))) {
				sortList.add(object);
			}
		}*/
		sortList.sort((FileSystemObject a, FileSystemObject b) -> a.getName().compareTo(b.getName()));
		for (FileSystemObject fileSystemObject : sortList) {
			vector = new Vector<>();
			addDataToVector(fileSystemObject, vector);
			data.add(vector);
			index++;
		}
		log.debug(FileNameRowTableModel.class, methodName, "Elements Loaded : " + index + " of " + objects.size());
		this.setDataVector(data, getColumnNamesVector());
	}

	private boolean containsSearchString(FileSystemObject object) {
		return searchText.isEmpty() || object.getName().contains(searchText) || object.getLocation().contains(searchText);
	}

	public void searchUpdate(String searchText) {
		this.searchText = searchText;
		EventMessage eventMessage = (EventMessage) ((Map) ApplicationConstantsUI.getByName("EVENT_MESSAGES"))
				.get("PERIODIC_VIEW_UPDATE_EXECUTION_EVENT");
		TaskEventExecutorRegistry.registerEventMessage(eventMessage);
	}

	private Vector<Vector> getDefaultDataVector() {
		return new Vector<>();
	}

	private Vector<String> getColumnNamesVector() {
		Vector<String> columnNames = new Vector<>();
		String[] columns = { "Name", "Location" };
		for (int i = 0; i < columns.length; i++) {
			columnNames.add(columns[i]);
		}
		return columnNames;
	}

	private void addDataToVector(FileSystemObject object, Vector<String> vector) {
		vector.add(object.getName());
		vector.add(object.getLocation());
	}

}
