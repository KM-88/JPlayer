/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import core.global.ApplicationConstants;
import framew.api.infc.Library;
import framew.api.infc.LibraryManager;
import framew.cmd.Option;
import framew.cmd.OptionsParser;
import framew.global.ApplicationConstantsUI;
import framew.log.Logger;
import framew.threads.api.NonDaemonThread;
import hob.compo.engine.PlayerThreadGenerator;
import hob.compo.obj.PlayListItems;

/**
 *
 * @author kranti
 */
public class NativeCacheCLINonDaemonThread extends NonDaemonThread {

	private static Logger log;

	private static final String CLI_OPTIONS = "CLI_OPTIONS";

	private static List<Option> optionsList;

	private static String pwd = "";

	private static LibraryManager libraryManager;

	public static String getPwd() {
		return pwd;
	}

	public static void setPwd(String pwd) {
		NativeCacheCLINonDaemonThread.pwd = pwd;
	}

	private Scanner scanner;

	public NativeCacheCLINonDaemonThread(String name) {
		super(name);
	}

	@Override
	public void execute() {
		log.debug(NativeCacheCLINonDaemonThread.class, "execute()", "Executing CLI :");
		if (null == pwd || pwd.isEmpty()) {
			pwd = getChoosenPWD();
		}
		System.out.print("[" + pwd + "]: ");
		String str = scanner.nextLine();
		interprete2(str);
	}

	private String getChoosenPWD() {
		int i = 1;
		if (null != libraryManager.getAllLibraries() && libraryManager.getAllLibraries().size() != 0) {
			System.out.println("Choose PWD");
			for (Library library : libraryManager.getAllLibraries()) {
				System.out.println(i + " - " + library.getPath());
				i++;
			}
			Integer choice = scanner.nextInt();
			i = 1;
			for (Library library : libraryManager.getAllLibraries()) {
				System.out.println(i + " - " + library.getPath());
				if (choice == i) {
					pwd = library.getPath().toString();
					break;
				}
				i++;
			}
		} else {
			System.out.println("Provide PWD");
			String libraryPath = scanner.nextLine();
			Library library = new Library(Paths.get(libraryPath));
			libraryManager.addLibrary(library);
			pwd = libraryPath;
		}
		return pwd;
	}

	private void interprete2(String string) {
		try {
			Map<Option, List<Option>> parsedOptions = OptionsParser.parseOptions(optionsList, string);
			OptionsParser.invokeOptions(parsedOptions);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	private void interprete(String string) throws IOException {
		String[] str = string.split(" ");
		String args = string.substring(str[0].length()).trim();
		switch (str[0]) {
		case "pwd":
			System.out.println(pwd);
			break;
		case "cd":
			String tempPwdStart = "", tempPwdEnd = "";
			if (Files.exists(Paths.get(pwd + "//" + args))) {
				String[] directories = args.split("//");
				String[] pwdPaths = pwd.split("//");
				int instance = 0;
				for (String directory : directories)
					if (directory.equals("..")) {
						System.out.println(directory);
						instance++;
					} else
						tempPwdEnd = tempPwdEnd + directory + "//";
				for (int i = 0; i < pwdPaths.length - instance; i++)
					tempPwdStart = tempPwdStart.concat(pwdPaths[i]).concat("//");
			}
			pwd = tempPwdStart.concat(tempPwdEnd);
			break;
		case "ls":

			break;
		case "play":
			try {
				PlayerThreadGenerator.start(new PlayListItems(pwd + "//" + string.substring(str[0].length()).trim(),
						string.substring(str[0].length()).trim()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case "chpwd":
			pwd = getChoosenPWD();
			break;
		}

	}

	@Override
	public void init() {
		setRefreshInterval(300);
		scanner = new Scanner(System.in);
		log = (Logger) ApplicationConstantsUI.getLogger();
		log.debug(NativeCacheCLINonDaemonThread.class, "performStartUpActivites", "Initialisation : Starting");
		optionsList = (List<Option>) ApplicationConstantsUI.getBean(CLI_OPTIONS);
		libraryManager = (LibraryManager) ApplicationConstantsUI.getByName(ApplicationConstantsUI.LIBRARY_MANAGER);
		log.debug(NativeCacheCLINonDaemonThread.class, "performStartUpActivites", "Initialisation : Finished");
	}

}
