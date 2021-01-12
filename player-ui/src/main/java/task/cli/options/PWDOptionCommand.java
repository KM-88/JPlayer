package task.cli.options;

import framew.cmd.Command;
import task.cli.NativeCacheCLINonDaemonThread;

public class PWDOptionCommand extends Command {

	public PWDOptionCommand(String name) {
		super(name);
	}

	@Override
	public void run() {
		System.out.println(NativeCacheCLINonDaemonThread.getPwd());
	}

}
