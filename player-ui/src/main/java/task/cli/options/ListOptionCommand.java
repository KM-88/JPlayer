package task.cli.options;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import core.global.ApplicationConstantsFramework;
import framew.cmd.Command;
import lib.inf.FileSystemScanner;
import task.cli.NativeCacheCLINonDaemonThread;

public class ListOptionCommand extends Command {

	public ListOptionCommand(String name, List<String> args) {
		super(name, args);
	}

	public ListOptionCommand(String name) {
		super(name);
	}

	@Override
	public void run() {
		String str = args.size() > 0 ? args.get(0) : NativeCacheCLINonDaemonThread.getPwd();
		try {
			(((FileSystemScanner) ApplicationConstantsFramework
					.getByName(ApplicationConstantsFramework.FILE_SYSTEM_SCANNER))
							.getFilesAndDirectories(Paths.get(str), 1)).forEach((k, v) -> {
								System.out.println(k);
								v.forEach((v1) -> {
									System.out.println("    " + v1);
								});
							});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
