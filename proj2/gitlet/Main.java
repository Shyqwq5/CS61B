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
        String secondArg;
        String thirdArg;
        String fourthArg;
        switch(firstArg) {

            case "init":
                //("init", args, 1);
                Repository.initcommand();
                break;

            case "add":
                secondArg = args[1];
                //validateNumArgs("add", args, 2);
                Repository.addcommand(secondArg);
                break;

            case "rm":
                secondArg = args[1];
                //validateNumArgs("rm", args, 2);
                Repository.rmcommand(secondArg);
                break;

            case "commit":
                if(args.length == 1)
                {
                    throw new RuntimeException("Please enter a commit message.");
            }
                secondArg = args[1];
                //validateNumArgs("commit", args, 2);
                Repository.makecommit(secondArg);
                break;

            case "checkout":
                if(args.length == 3){
                    thirdArg = args[2];
                    Repository.checkoutfile(thirdArg);}
                if(args.length == 4){
                    secondArg = args[1];
                    fourthArg = args[3];
                    Repository.checkoutcommit(secondArg,fourthArg);}
                if(args.length == 2){
                    secondArg = args[1];
                    Repository.checkoutbranch(secondArg);}
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
                secondArg = args[1];
                //validateNumArgs("find", args, 2);
                Repository.findcommand(secondArg);
                break;

            case "status":
                Repository.statuscommand();
                break;

            case "branch":
                secondArg = args[1];
                Repository.branchcommand(secondArg);
                break;

            case "rm-branch":
                secondArg = args[1];
                Repository.rmbranchcommand(secondArg);
                break;

            case "reset":
                secondArg = args[1];
                Repository.resetcommand(secondArg);
                break;

            case "merge":
                secondArg = args[1];
                Repository.mergecommand(secondArg);
                break;
        }
    }

    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Invalid number of arguments for: %s.", cmd));
        }







        }
    }

