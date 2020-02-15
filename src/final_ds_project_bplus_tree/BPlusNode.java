/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_ds_project_bplus_tree;

import java.util.Arrays;

/**
 *
 * @author well come
 */
public class BPlusNode{
    boolean isleaf;
    BPlusNode[] childrens;
    int[ ] keys;
    BPlusNode next;
    BPlusNode parent;
    public BPlusNode(int m){
        childrens=new BPlusNode [m];
        keys=new int[m-1];
        Arrays.fill(keys,-1);
        isleaf=true;
        next=null;
        parent=null;
    }
}
