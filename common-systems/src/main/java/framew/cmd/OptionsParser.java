package framew.cmd;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import core.global.ApplicationConstantsCommon;
import framew.log.Logger;

public class OptionsParser {

	private static Logger logger = ApplicationConstantsCommon.getLogger();

	public static void invokeOptions(Map<Option, List<Option>> parsedOptions) {
		String methodName = "invokeOptions";
		for (Entry<Option, List<Option>> option : parsedOptions.entrySet())
			for (Option opt : option.getValue()) {
				opt.getCmd().setArgs(opt.getArgs());
				opt.getCmd().run();
				logger.debug(OptionsParser.class, methodName, "Invoking option : " + opt.toString());
			}
	}

	public static Map<Option, List<Option>> parseOptions(List<Option> options, String arg)
			throws CloneNotSupportedException {
		String methodName = "parseOptions";
		Map<Option, List<Option>> parsedOptions = new HashMap<Option, List<Option>>();
		for (Option option : options) {
			parsedOptions.put(option, new LinkedList<Option>());
		}
		String[] args = arg.split(" ");
		Option currentOption = null;
		boolean hasOptionChanged = false;
		for (String param : args) {
			hasOptionChanged = false;
			// if(param == option.name)
			// replicate option, set current option to this one
			// else
			// add this to current option as an parameter
			if (currentOption == null || currentOption.isDynamic()) {
				for (Option option : options) {
					if ((option.getLongName().equals(param))
							|| ((param.length() == 1) && (option.getName() == param.charAt(0)))) {
						currentOption = (Option) option.clone();
						parsedOptions.get(option).add(currentOption);
						hasOptionChanged = true;
						break;
					}
				}
			}

			if (null != currentOption && !currentOption.isDynamic() && !hasOptionChanged) {
				if (currentOption.getArgs().size() < currentOption.getNo_of_args()) {
					currentOption.getArgs().add(param);
					logger.debug(OptionsParser.class, methodName, "currentOption.getArgs().add(param)");
					if (currentOption.getArgs().size() == currentOption.getNo_of_args())
						currentOption = null;
					continue;
				} else {
					currentOption = null;
					System.out.println("currentOption = null");
					continue;
				}
			}
			if (null != currentOption && currentOption.isDynamic() && !hasOptionChanged) {
				currentOption.getArgs().add(param);
				logger.debug(OptionsParser.class, methodName,
						"currentOption.getArgs().add(param) - " + currentOption.isDynamic());
				continue;
			}

		}
		return parsedOptions;

	}

}
