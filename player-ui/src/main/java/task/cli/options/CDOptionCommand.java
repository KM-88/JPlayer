package task.cli.options;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import framew.cmd.Command;
import task.cli.NativeCacheCLINonDaemonThread;

public class CDOptionCommand extends Command {

	public CDOptionCommand(String name) {
		super(name);
	}

	@Override
	public void run() {
		String path = NativeCacheCLINonDaemonThread.getPwd() + File.separator + args.get(0);
		if (Files.exists(Paths.get(path))) {
			NativeCacheCLINonDaemonThread.setPwd(Paths.get(path).normalize().toString());
		}
	}

}
