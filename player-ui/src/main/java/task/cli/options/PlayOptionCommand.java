package task.cli.options;

import java.util.List;

import framew.cmd.Command;
import hob.compo.engine.PlayerThreadGenerator;
import hob.compo.obj.PlayListItems;
import task.cli.NativeCacheCLINonDaemonThread;

public class PlayOptionCommand extends Command {

	public PlayOptionCommand(String name, List<String> args) {
		super(name, args);
	}

	public PlayOptionCommand(String name) {
		super(name);
	}

	@Override
	public void run() {
		String fileName = "";
		try {
			for (String arg : args) {
				fileName = fileName.concat(arg);
				if (arg.contains(".mp3"))
					PlayerThreadGenerator.start(
							new PlayListItems(NativeCacheCLINonDaemonThread.getPwd() + "//" + fileName, fileName));
				else
					fileName = fileName.concat(" ");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
