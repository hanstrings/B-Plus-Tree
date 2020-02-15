/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_ds_project_bplus_tree;

/**
 *
 * @author well come
 */
public class Final_DS_Project_BPlus_Tree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BPlusTree b1=new BPlusTree(5);
        b1.insert(24);
        b1.insert(35);
        b1.insert(45);
        b1.insert(100);
        b1.insert(15);
        b1.insert(40);
        b1.insert(31);
        b1.levelOrderTraversal();
        b1.preOrderTraversal();
        b1.inOrderIterative();
//        b1.insert(125);
//        b1.insert(4);
//        b1.insert(99);
//        b1.insert(224);
//        b1.insert(1000);
//        b1.insert(51);
//        b1.levelOrderTraversal();
//        b1.preOrderTraversal();
//        b1.inOrderIterative();
    }
    
}
