package gitlet;

import java.io.File;

import static gitlet.Utils.readObject;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            throw new RuntimeException("Must have at least one argument");
        }
        String firstArg = args[0];
        String secondArg = args[1];
        String thirdArg = args[2];
        switch(firstArg) {

            case "init":
                //("init", args, 1);
                Repository.initcommand();
                break;

            case "add":
                //validateNumArgs("add", args, 2);
                Repository.addcommand(secondArg);
                break;

            case "rm":
                //validateNumArgs("rm", args, 2);
                Repository.rmcommand(secondArg);
                break;

            case "commit":
                if(args.length == 1)
                {
                    throw new RuntimeException("Please enter a commit message.");
            }
                //validateNumArgs("commit", args, 2);
                Repository.makecommit(secondArg);
                break;

            case "checkout":

                Repository.checkoutcommand();
                break;


            case "log":
                //validateNumArgs("log", args, 1);
                Repository.logcommand();
                break;
            case "global-log":
                //validateNumArgs("global-log", args, 1);
                Repository.globallogcommand();
                break;
            case "find":
                //validateNumArgs("find", args, 2);
                Repository.findcommand(secondArg);
                break;

            case "status":
                break;
            case "branch":
                break;
            case "rm-branch":
                break;
            case "reset":
                break;
            case "merge":
                break;}}

    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Invalid number of arguments for: %s.", cmd));
        }







        }
    }

