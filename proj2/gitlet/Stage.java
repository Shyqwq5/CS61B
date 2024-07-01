package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class Stage implements Serializable {
    public TreeMap<File, String> addList;
    public ArrayList<File> rmList;

    public Stage() {
        this.addList=new TreeMap<>();
        this.rmList=new ArrayList<>();
    }
    }






