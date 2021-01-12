package framew.global;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by kranti on 2/27/16.
 */
public class CommandLineParameterEvaluator {

    private static String START_IN_CONSOLE = "-c";
    private static String[] singleArgs = {START_IN_CONSOLE,"-v"};
    private static String[] multiArgs = {"-f", "-l"};

    public static String SINGLE_ARGUMENTS = "SINGLE_ARGUMENTS";
    public static String MULTIPLE_ARGUMENTS = "MULTIPLE_ARGUMENTS";

    public static Map<String, Object> evaluateArguments(String[] args){
        Map<String, Object> argumentList = new HashMap<String, Object>();
        List<String> singleArgsList = new LinkedList<String>();
        Map<String, List<String>> multiArgsMap = new HashMap<String, List<String>>();
        argumentList.put(SINGLE_ARGUMENTS,singleArgsList);
        argumentList.put(MULTIPLE_ARGUMENTS,multiArgsMap);
        for(int i=0; i < args.length; i++){
            if(contains(singleArgs, args[i]))
                singleArgsList.add(args[i]);
            else {
                if (contains(multiArgs, args[i])) {
                    int j = i + 1;
                    boolean valuesPassed = false;
                    List<String> multiArgsList = new LinkedList<String>();
                    while (j < args.length && !contains(singleArgs, args[j]) && !contains(multiArgs, args[j])) {
                        valuesPassed = true;
                        multiArgsList.add(args[j]);
                        j++;
                    }
                    if(!valuesPassed){
                        printHelp();
                        break;
                    }
                    multiArgsMap.put(args[i], multiArgsList);
                    i = j - 1;
                } else {
                    printHelp();
                    break;
                }
            }
        }
        return argumentList;
    }

    private static void printHelp(){
        String[][] singleArgsHelp = {{"-c", "Start Application in Commandline without GUI\n"+"Usage :\n"+" -c <Other Arguments>"}};
        String[][] multiArgsHelp = {{"-f","Pass the files to play followed by this argument\nUsage :\n -f File1 File2 <Other Arguments>"}};
        for(String[] arg: singleArgsHelp){
            for(String string: arg)
                System.out.println(string);
        }
        for(String[] arg: multiArgsHelp){
            for(String string: arg)
                System.out.println(string);
        }
        System.exit(-1);
    }

    private static boolean contains(String[] args, String param){
        for (int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase(param))
                return true;
        }
        return false;
    }
}
