package gitlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.nio.file.Path;
import java.nio.file.Paths;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /** The current working directory. */
    public static Commit head_commit;
    public static TreeMap<File, String> currentMap;
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMIT_DIR = join(GITLET_DIR, "commit");
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");
    public static final File ADD_DIR = join(STAGE_DIR, "add");
    public static final File RM_DIR = join(STAGE_DIR, "rm");
    public static final File BLOB_DIR = join(GITLET_DIR, "bolb");
    public static final File HEAD = join(GITLET_DIR, "headcommit");
    public static final File BRANCH = join(GITLET_DIR, "branch");
    public static final File STAGE  = join(STAGE_DIR, "stage");
    //every direction should have

    public static void initcommand() {
        if (GITLET_DIR.exists()){
            throw new RuntimeException("A Gitlet version-control system already exists in the current directory.");}
        else {GITLET_DIR.mkdir();
            COMMIT_DIR.mkdir();
            BLOB_DIR.mkdir();
            STAGE_DIR.mkdir();
            ADD_DIR.mkdir();
            RM_DIR.mkdir();

            try {
                HEAD.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);}

            try {
                BRANCH.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);}

            Branch state = new Branch();
            writeObject(Repository.BRANCH,state);

            try {
                STAGE.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);}
            Stage initStage = new Stage();
            writeObject(Repository.STAGE,initStage);


            Commit init_command = new Commit("initial commit");
            init_command.makeinit();



        }
    }

    public static void addcommand(String filename){
        head_commit = gethead();
        currentMap = head_commit.treeMap;

        File here = new File(System.getProperty("user.dir"));
        File this_file = join(here, filename);
        Stage stage_now = readObject(Repository.STAGE,Stage.class);
        TreeMap<File, String> addList = stage_now.addList;

        if (this_file.exists()){
            byte[] contents = readContents(this_file);
            String file_sha1 = sha1(filename,contents);
            File add_dir = join(ADD_DIR, filename);
            File add_file = join(add_dir, file_sha1);

            if (currentMap.containsKey(this_file)){
                if(currentMap.get(this_file).equals(file_sha1)){
                    if(addList.containsKey(this_file)){
                        addList.remove(this_file);}
                    else {return;}
                    }
                else {
                    addList.put(this_file, file_sha1);
                    writeObject(Repository.STAGE,stage_now);
                    Utils.forcecreat(add_file);
                    writeContents(add_file,contents);
                }
            }
            else {
                addList.put(this_file, file_sha1);
                writeObject(Repository.STAGE,stage_now);
                Utils.forcecreat(add_file);
                writeContents(add_file,contents);
            }
        }
        else  throw new RuntimeException("File does not exist.");
}
            //commit_map 有没有同样的文件
            //没有 --如果addlist 有同路径同名文件 更新
            //有 --如果addlist 有同路径同名文件 删除
            //addlist file 跟新 sha1文件夹 内 包含此文件

            //commmit 更新把所有add 的文件全部复制到blob文件
            //并且跟新map，对于每个add的文件，如果有相同路径的同名文件更换，其它添加到map

    public static void rmcommand(String filename){
        //判断是否在addlist 中，如果在就移除
        //判断是否在current_map 中，如果有就放在rm文件夹中，并且尝试删除工作目录的该文件
        File here = new File(System.getProperty("user.dir"));
        File this_file = join(here, filename);
        Stage now = readObject(Repository.STAGE,Stage.class);
        TreeMap<File, String> addList = now.addList;
        ArrayList<File> rmList = now.rmList;
        if (addList.containsKey(this_file)){
            addList.remove(this_file);
        }
        if (currentMap.containsKey(this_file)){
            rmList.add(this_file);
            Utils.restrictedDelete(filename);
        }
    }

    public static void makecommit(String name){
        Commit new_commit = new Commit(name);
        new_commit.makecommit();
    }

    public static void logcommand(){
        String sha1_head = sha1((Object) serialize(readObject(HEAD,Commit.class)));
        serialcommit(sha1_head);
    }

    public static void globallogcommand(){
        /*if (COMMIT_DIR.isDirectory()) {
            File[] commits = COMMIT_DIR.listFiles();
                for (File file : commits) {
                    String sha1 = file.getName();
                    printcommit(sha1);}
        }*/

        Branch branch = readObject(Repository.BRANCH, Branch.class);
        for(String commit:branch.commits){
            printcommit(commit);
        }

    }

    public static void findcommand(String message){
        //search all commit contain this_message
        //print like the log
        if (COMMIT_DIR.isDirectory()) {
            File[] commits = COMMIT_DIR.listFiles();
            for (File file : commits) {
                if (file.getName().equals(message)) {
                    printcommit(file.getName());}
            }
        }
    }

    public static void statuscommand(){
        //print like follow
        //=== Branches ===
        //*master
        //other-branch
        //
        //=== Staged Files ===
        //wug.txt
        //wug2.txt
        //
        //=== Removed Files ===
        //goodbye.txt
        //
        //=== Modifications Not Staged For Commit ===
        //
        //=== Untracked Files ===
        //
        //finish

        Branch branch = readObject(Repository.BRANCH, Branch.class);
        String parent = sha1((Object) readContents(Repository.HEAD));
        Stage now = readObject(Repository.STAGE, Stage.class);

        System.out.println("=== Branches ===");
        for(String branchname:branch.branchs.keySet()){
            if(branchname.equals(branch.branchnow)){
                System.out.println("*"+branchname);
            }
            else System.out.println(branchname);
        }
        System.out.println();

        System.out.println("=== Staged Files ===");
        for (File file : now.addList.keySet()) {
            System.out.println(file.getName());
        }
        System.out.println();

        System.out.println("=== Removed Files ===");
        for (File file : now.rmList) {
            System.out.println(file.getName());
        }

        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();

    }
    public static void checkoutfile(String filename){
        File here = new File(System.getProperty("user.dir"));
        File this_file = join(here, filename);
        head_commit = gethead();
        currentMap = head_commit.treeMap;
        if (currentMap.containsKey(this_file)){
            String sha =currentMap.get(this_file);
            if(!this_file.exists()){forcecreat(this_file);
            }
            File blobfile = join(BLOB_DIR,filename,sha);
            writeContents(this_file,readContents(blobfile));
            return;
        }
        System.out.println("File does not exist in that commit.");

    }

    public static void checkoutcommit(String commitid,String filename){
        Branch branch = readObject(Repository.BRANCH, Branch.class);
        for(String commit:branch.commits){
            if(commit.startsWith(commitid)){
                File thisfile = join(COMMIT_DIR,commit);
                Commit this_commit = readObject(thisfile,Commit.class);

                File here = new File(System.getProperty("user.dir"));
                File this_file = join(here, filename);
                TreeMap<File,String> this_commitMap = this_commit.treeMap;
                if (this_commitMap.containsKey(this_file)){
                    String sha =this_commitMap.get(this_file);
                    if(!this_file.exists()){forcecreat(this_file);
                    }
                    File blobfile = join(BLOB_DIR,filename,sha);
                    writeContents(this_file,readContents(blobfile));
                    return;
                }
                System.out.println("File does not exist in that commit.");
            }
            System.out.println("No commit with that id exists.");
        }
    }

    public static void checkoutbranch(String branchname){
        Branch branch = readObject(Repository.BRANCH, Branch.class);
        String headsha1 = sha1(readContents(HEAD));
        if (branchname.equals(branch.branchnow)){
                System.out.println("No need to checkout the current branch.");
                return;
            }
        if (!branch.commits.contains(branchname)) {
            System.out.println("No such branch exists.");
            return;
        }
        branch.branchnow = branchname;
        writeObject(Repository.BRANCH,branch);
        changecommit(headsha1,branch.branchs.get(branch.branchnow));
        updatestage();
    }



    public static void branchcommand(String branchname){
        Branch branch = readObject(Repository.BRANCH, Branch.class);
        if (branch.commits.contains(branchname)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        String headsha1 = sha1(readContents(HEAD));
        branch.branchs.put(branchname,headsha1);
        branch.splitpoint = headsha1;
        writeObject(BRANCH,branch);
    }

    public static void rmbranchcommand(String branchname){
        Branch branch = readObject(Repository.BRANCH, Branch.class);

        if (branchname.equals(branch.branchnow)){
            System.out.println("Cannot remove the current branch.");
            return;
        }
        if (!branch.commits.contains(branchname)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        branch.commits.remove(branchname);
        writeObject(BRANCH,branch);
    }

    public static void resetcommand(String commitid){
        String headsha1 = sha1(readContents(HEAD));
        Branch branch = readObject(Repository.BRANCH, Branch.class);
        for(String commit:branch.commits){
            if(commit.startsWith(commitid)){
                changecommit(headsha1,commit);
                branch.branchs.put(branch.branchnow,commit);
                writeObject(BRANCH,branch);
            }
        }
        System.out.println("No commit with that id exists.");
    }

    public static void mergecommand(String branchname){
        Branch branch = readObject(Repository.BRANCH, Branch.class);
        Stage stage = readObject(Repository.STAGE, Stage.class);
        if (!stage.rmList.isEmpty() || !stage.addList.isEmpty()){
            System.out.println("You have uncommitted changes.");
            return;}
        if (!branch.branchs.containsKey(branchname)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (branch.branchnow.equals(branchname)) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        }



        }




        //If there are staged additions or removals present, print the error message and exit.
        //If a branch with the given name does not exist, print the error message
        //If attempting to merge a branch with itself, print the error message
        // If merge would generate an error because the commit that it does has no changes in it, just let the normal commit error message for this go through.
        // If an untracked file in the current commit would be overwritten or deleted by the merge, print and exit;
        // perform this check before doing anything else.
        // You have uncommitted changes.
        // A branch with that name does not exist.
        // Cannot merge a branch with itself.
        // There is an untracked file in the way; delete it, or add and commit it first.






    public static void serialcommit(String sha1){
        File thisfile = join(COMMIT_DIR,sha1);
        Commit commit = readObject(thisfile,Commit.class);
        System.out.println("===");
        System.out.println("commit "+sha1);
        if(commit.merge != null){
            System.out.println(seven(commit.parent));
            System.out.print(" ");
            System.out.print(seven(commit.merge));
        }

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z");
        String formattedDate = formatter.format(commit.date);
        System.out.println("Date: " + formattedDate);
        System.out.println(commit.message);
        System.out.println();

        if (commit.parent != null){serialcommit(commit.parent);}
    }

    public static void printcommit(String sha1){
        File thisfile = join(COMMIT_DIR,sha1);
        Commit commit = readObject(thisfile,Commit.class);
        System.out.println("===");
        System.out.println("commit "+sha1);
        if(commit.merge != null){
            System.out.println(seven(commit.parent));
            System.out.print(" ");
            System.out.print(seven(commit.merge));
        }

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z");
        String formattedDate = formatter.format(commit.date);
        System.out.println("Date: " + formattedDate);
        System.out.println(commit.message);
        System.out.println();
    }


    public static Commit gethead(){
        Commit head = readObject(HEAD, Commit.class);
        return head;
    }



    public static void changecommit(String now,String that){
        File filenow = join(COMMIT_DIR,now);
        Commit commitnow = readObject(filenow,Commit.class);
        File filethat = join(COMMIT_DIR,that);
        Commit committhat = readObject(filethat,Commit.class);
        TreeMap<File, String> nowtree = commitnow.treeMap;
        TreeMap<File, String> thattree = committhat.treeMap;

        for (File file : nowtree.keySet()) {
            if (thattree.containsKey(file)) {
                if(!nowtree.get(file).equals(thattree.get(file))){
                    restrictedDelete(file);
                }
            }
            else restrictedDelete(file);
            }
        for (File file : thattree.keySet()) {
            if(file.exists()){
                if (!sha1(file.getName(),readContents(file)).equals(thattree.get(file))){
                    throw new RuntimeException("There is an untracked file in the way; delete it, or add and commit it first.");
                };
            }
            else {forcecreat(file);
                File blobfile = join(BLOB_DIR,file.getName(),thattree.get(file));
                writeContents(file,readContents(blobfile));
            }
        }

        writeObject(Repository.HEAD,committhat);

    }


}