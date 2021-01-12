/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.obj;

/**
 *
 * @author kranti
 */
public class FileTypeEnum {
    
	private int ordinal;

	private String value;

	public FileTypeEnum(int ordinal, String value) {
		this.ordinal = ordinal;
		this.value = value;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static FileTypeEnum FILE = new FileTypeEnum(0, "FILE");

	public static FileTypeEnum DIRECTORY = new FileTypeEnum(1, "DIRECTORY");

}
