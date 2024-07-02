package gitlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Branch implements Serializable {
    public String splitpoint;
    public String branchnow;
    public ArrayList<String> commits;
    public HashMap<String, String> branchs;

    public Branch() {

        splitpoint = null;
        commits = new ArrayList<String>();
        branchnow = null;
        branchs = new HashMap<>();

    }
}

