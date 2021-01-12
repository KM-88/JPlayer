package framew.cmd;

import java.util.LinkedList;
import java.util.List;

public class Option implements Cloneable {

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Option cloneOption = (Option) super.clone();
		List<String> copyArgs = new LinkedList<String>();
		copyArgs.addAll(getArgs());
		cloneOption.SetArgs(copyArgs);
		return cloneOption;
	}

	public List<String> getArgs() {
		return args;
	}

	public void SetArgs(List<String> arg) {
		this.args = arg;
	}

	private Command cmd;
	private char name;

	private String longName;

	private int no_of_args = 1;

	private List<String> args = new LinkedList<String>();

	private boolean isOptional = true;

	@Override
	public String toString() {
		StringBuilder argsString = new StringBuilder();
		for (String str : args)
			argsString.append(str);
		return "Option [name=" + name + ", longName=" + longName + ", no_of_args=" + no_of_args + ", args="
				+ argsString.toString() + ", isOptional=" + isOptional + ", isDynamic=" + isDynamic + ", Command=" + cmd
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		result = prime * result + (isDynamic ? 1231 : 1237);
		result = prime * result + (isOptional ? 1231 : 1237);
		result = prime * result + ((longName == null) ? 0 : longName.hashCode());
		result = prime * result + name;
		result = prime * result + no_of_args;
		if (null != cmd)
			result = prime * cmd.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option other = (Option) obj;
		if (args == null) {
			if (other.args != null)
				return false;
		} else if (!args.equals(other.args))
			return false;
		if (isDynamic != other.isDynamic)
			return false;
		if (isOptional != other.isOptional)
			return false;
		if (longName == null) {
			if (other.longName != null)
				return false;
		} else if (!longName.equals(other.longName))
			return false;
		if (name != other.name)
			return false;
		if (no_of_args != other.no_of_args)
			return false;
		if (cmd != other.cmd)
			return false;
		return true;
	}

	/**
	 * Set this true to make accept varying no of arguments
	 * 
	 */
	private boolean isDynamic = false;

	public int getNo_of_args() {
		return no_of_args;
	}

	/**
	 * @param name
	 * @param longName
	 * @param no_of_args
	 * @param isOptional
	 * @param isDynamic
	 */
	public Option(char name, String longName, int no_of_args, boolean isOptional, boolean isDynamic) {
		super();
		this.name = name;
		this.longName = longName;
		this.no_of_args = no_of_args;
		this.isOptional = isOptional;
		this.isDynamic = isDynamic;
	}

	/**
	 * @param cmd
	 * @param name
	 * @param longName
	 * @param no_of_args
	 * @param args
	 * @param isOptional
	 * @param isDynamic
	 */
	public Option(char name, Command cmd, String longName, int no_of_args, boolean isOptional, boolean isDynamic) {
		super();
		this.cmd = cmd;
		this.name = name;
		this.longName = longName;
		this.no_of_args = no_of_args;
		this.isOptional = isOptional;
		this.isDynamic = isDynamic;
	}

	/**
	 * @param name
	 * @param longName
	 */
	public Option(char name, String longName) {
		super();
		this.name = name;
		this.longName = longName;
	}

	/**
	 * @param cmd
	 * @param name
	 * @param longName
	 * @param no_of_args
	 */
	public Option(char name, Command cmd, String longName, int no_of_args) {
		super();
		this.cmd = cmd;
		this.name = name;
		this.longName = longName;
		this.no_of_args = no_of_args;
	}

	/**
	 * @param name
	 * @param longName
	 * @param no_of_args
	 */
	public Option(char name, String longName, int no_of_args) {
		super();
		this.name = name;
		this.longName = longName;
		this.no_of_args = no_of_args;
	}

	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public boolean isDynamic() {
		return isDynamic;
	}

	public void setDynamic(boolean isDynamic) {
		this.isDynamic = isDynamic;
	}

	public void setNo_of_args(int no_of_args) {
		this.no_of_args = no_of_args;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

}
