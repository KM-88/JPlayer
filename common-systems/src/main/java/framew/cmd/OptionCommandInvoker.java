package framew.cmd;

public class OptionCommandInvoker {

	private Option option;
	
	private Command command;

	/**
	 * @param option
	 * @param command
	 */
	public OptionCommandInvoker(Option option, Command command) {
		super();
		this.option = option;
		this.command = command;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
	
}
