package framew.cmd;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import core.global.ApplicationConstantsTest;
import framew.log.Logger;

public class OptionsParserTest {

	private static Logger logger = ApplicationConstantsTest.getLogger();
	List<Option> options;
	Map<Option, List<Option>> parsedOption;

	@Test
	public void invokeOptionsTest() {
		String methodName = "invokeOptionsTest()";
		options = new LinkedList<Option>();
		String arg = "f somefile.txt l /usr/share";
		Option opt1 = new Option('f', "File names");
		Option opt2 = new Option('l', "Library Names");
		opt1.setCmd(new TempCommand("test1"));
		opt2.setCmd(new TempCommand("test2"));
		options.add(opt1);
		options.add(opt2);
		logger.debug(getClass(), methodName, "Available options" + opt1.toString() + "\n" + opt2.toString());
		try {
			parsedOption = OptionsParser.parseOptions(options, arg);
			OptionsParser.invokeOptions(parsedOption);
			logger.debug(getClass(), methodName, "Invoked options");
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void parseOptionsTest() {
		String methodName = "parseOptionsTest()";
		options = new LinkedList<Option>();
		String arg = "f somefile.txt l /usr/share";
		Option opt1 = new Option('f', "File names");
		Option opt2 = new Option('l', "Library Names");
		options.add(opt1);
		options.add(opt2);
		try {
			parsedOption = OptionsParser.parseOptions(options, arg);
			for (Entry<Option, List<Option>> optionEntry : parsedOption.entrySet()) {
				logger.debug(getClass(), methodName, "\n Key = " + optionEntry.getKey() + "\n Value = "
						+ optionEntry.getValue() + "\n size = " + optionEntry.getValue().size());
				if (options.contains(optionEntry.getKey())) {
					Assertions.assertTrue(true);
					for (Option option : optionEntry.getValue())
						logger.debug(getClass(), methodName, option.toString());
				}
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			Assertions.assertTrue(false);
		}
	}

	@Test
	public void parseOptionsDynamicTest() {
		String methodName = "parseOptionsDynamicTest()";
		List<Option> options = new LinkedList<Option>();
		String arg = "f somefile.txt file2.txt l /usr/share";
		Option opt1 = new Option('f', "File names");
		opt1.setDynamic(true);
		options.add(opt1);
		Option opt2 = new Option('l', "Library Names");
		options.add(opt2);
		Map<Option, List<Option>> parsedOption;
		try {
			parsedOption = OptionsParser.parseOptions(options, arg);
			for (Entry<Option, List<Option>> optionEntry : parsedOption.entrySet()) {
				logger.debug(getClass(), methodName, "\n Key = " + optionEntry.getKey() + "\n Value = "
						+ optionEntry.getValue() + "\n size = " + optionEntry.getValue().size());
				if (optionEntry.getKey() == opt1 || optionEntry.getKey() == opt2)
					Assertions.assertTrue(true);
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			Assertions.assertTrue(false);
		}
	}

	@Test
	public void parseOptionsDynamicTest2() {
		String methodName = "parseOptionsDynamicTest()";
		List<Option> options = new LinkedList<Option>();
		String arg = "f somefile.txt file2.txt l /usr/share f thisfile.txt thatfile.txt l /home /share";
		Option opt1 = new Option('f', "File names");
		opt1.setDynamic(true);
		options.add(opt1);
		Option opt2 = new Option('l', "Library Names");
		opt2.setDynamic(true);
		options.add(opt2);
		Map<Option, List<Option>> parsedOption;
		try {
			parsedOption = OptionsParser.parseOptions(options, arg);
			for (Entry<Option, List<Option>> optionEntry : parsedOption.entrySet()) {
				logger.debug(getClass(), methodName, "\n Key = " + optionEntry.getKey() + "\n Value = "
						+ optionEntry.getValue() + "\n size = " + optionEntry.getValue().size());
				if (options.contains(optionEntry.getKey())) {
					Assertions.assertTrue(true);
					for (Option option : optionEntry.getValue())
						logger.debug(getClass(), methodName, option.toString());
				}
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			Assertions.assertTrue(false);
		}
	}
}

class TempCommand extends Command {
	private Logger logger = ApplicationConstantsTest.getLogger();

	public TempCommand(String name) {
		super(name);
	}

	@Override
	public void run() {
		logger.debug(getClass(), "run", "Running" + this.name);
	}

}
