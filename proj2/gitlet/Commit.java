package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.*;// TODO: You'll likely use this in this class
import static gitlet.Utils.*;
import java.util.ArrayList;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    public String message;
    public String parent;
    public String merge;
    public java.util.Date date;
    public TreeMap<File, String> treeMap;




    /* TODO: fill in the rest of this class. */
    public Commit(String message) {
        this.message = message;
        this.treeMap = new TreeMap<>();
        merge = null;
    }

    public void makeinit(){
        this.date = new java.util.Date();

        //save into commit_dir
        String sha1 = sha1((Object) serialize(this));
        File this_path = join(Repository.COMMIT_DIR, sha1);
        forcecreat(this_path);
        writeObject(this_path, this);

        //write into branch file
        Branch branch = readObject(Repository.BRANCH, Branch.class);

        branch.branchs.put("master",sha1);
        branch.branchnow = "master";
        branch.commits.add(sha1);
        writeObject(Repository.BRANCH, branch);

        //change Head into this commit
        writeObject(Repository.HEAD,this);
    }

    public void makecommit() {
        /*
        store time
        store commit message
        deal all add/rm project / base on recently commit
        store all direction tree & sha1 for each file
        store current commit sha1 as parent
        store sha1 for this commit
        save this commit into file as serial
        move head to this commit
        */
        this.date = new java.util.Date();
        this.parent = sha1((Object) readContents(Repository.HEAD));
        this.treeMap = deepCopyTreeMap(Repository.gethead().treeMap);

        Stage now = readObject(Repository.STAGE, Stage.class);
        TreeMap<File, String> addList = now.addList;
        ArrayList<File> rmList = now.rmList;

        //deal all add message
        if (addList != null) {
            for (File f : addList.keySet()) {
                String file_sha1 = addList.get(f);
                //blob add that file
                File blob_file = join(Repository.BLOB_DIR, f.getName(), file_sha1);
                File add_file = join(Repository.ADD_DIR, f.getName(), file_sha1);
                forcecreat(blob_file);
                writeContents(blob_file, readContents(add_file));
                //tree add that file
                this.treeMap.put(f, file_sha1);
                restrictedDelete(add_file);
            }
        }

        //deal all rm message
        if (rmList != null) for (File f : rmList) {
            //tree remove that file
            this.treeMap.remove(f);
        }

        //save into commit_dir
        String sha1 = sha1((Object) serialize(this));
        File this_path = join(Repository.COMMIT_DIR, sha1);
        forcecreat(this_path);
        writeObject(this_path, this);

        //change Branch head
        Branch branch = readObject(Repository.BRANCH, Branch.class);
        branch.branchs.put(branch.branchnow,sha1);
        branch.commits.add(sha1);
        writeObject(Repository.BRANCH, branch);

        //change Head into this commit
        writeObject(Repository.HEAD,this);

        //update stage
        updatestage();
    }

    public static TreeMap<File,String> deepCopyTreeMap(TreeMap<File, String> original) {
        TreeMap<File, String> copy = new TreeMap<>();
        for (File key : original.keySet()){copy.put(key, original.get(key));}
        return copy;
    }

}
