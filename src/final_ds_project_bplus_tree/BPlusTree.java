/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_ds_project_bplus_tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author well come
 */
public class BPlusTree{
    BPlusNode root;
    int m;
    int size;
    public BPlusTree(int m_order){
        m=m_order;
        root=null;
        size=0;
    }
    
    public BPlusNode[] search(int value){
        BPlusNode temp;
        temp=root;
        BPlusNode previous=null;
        BPlusNode[] two= new BPlusNode[2];
        int i,j;
        while(true){
            if(temp.isleaf){
                for(i=0;i<m-1;i++){
                    if(temp.keys[i]==value){
                        two[0]=temp;
                        break;
                    }
                }
                two[0]=temp;
                break;
            }
            else{
                for(i=0;i<m-1;i++){
                    if(value<temp.keys[i]){
                        previous=temp;
                        temp=temp.childrens[i];
                        break;
                    }
                    else{
                        if(value>=temp.keys[i] ){
                            if(i==m-2){
                                previous=temp;
                                for(j=0;j<m;j++){
                                    temp=previous.childrens[j];
                                    if(temp!=null && temp.keys[0]>=value && temp.keys[0]!=-1){
                                        temp=previous.childrens[j];
                                        break;
                                    }
                                    else{
                                        temp=previous.childrens[j+1];
                                        break;
                                    }
                                }
                                break;
                            }
                            else{
                                if(value<temp.keys[i+1] ){
                                    previous=temp;
                                    temp=temp.childrens[i+1];
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        two[1]=previous;
        return two;
    }
    
    public void insert(int value){
        
        int i,j,k;
        if(root==null){
            BPlusNode newa=new BPlusNode(m);
            for(i=0;i<m-1;i++){
                if(newa.keys[i]==-1){
                    newa.keys[i]=value;
                    root=newa;
                    size++;
                    return;
                }
            }
        }
        if(root.keys[m-2]==-1 && root.isleaf ){
            for(i=0;i<m-1;i++){
                if(root.keys[i]>value){
                    for(j=m-2;root.keys[j]!=-1;j--){
                        
                    }
                    for(k=j-1;k>=i;k--){
                        root.keys[k+1]=root.keys[k];
                        root.childrens[k+2]=root.childrens[k+1];
                    }
                    root.keys[i]=value;
                    root.childrens[i]=null;
                    size++;
                    return;
                }
                if(root.keys[i]==-1){
                    root.keys[i]=value;
                    root.childrens[i]=null;
                    size++;
                    return;
                }
            }
        }
            
        BPlusNode[] second=search(value);
        BPlusNode insertHere=second[0];
        BPlusNode par=second[1];
        insertHere.parent=par;
        if(insertHere.keys[m-2]==-1){
            for(i=0;i<m-1;i++){
                if(insertHere.keys[i]==-1){
                    insertHere.keys[i]=value;
                    insertHere.parent=par;
                }
                if(insertHere.keys[i]>value){
                    for(j=m-2;insertHere.keys[j]!=-1;j--){
                        
                    }
                    for(k=j-1;k>=i;k--){
                        insertHere.keys[k+1]=insertHere.keys[k];
                        insertHere.childrens[k+2]=insertHere.childrens[k+1];
                    }
                    insertHere.keys[i]=value;
                    insertHere.childrens[i]=null;
                    break;
                }
                if(insertHere.keys[i]==-1){
                    insertHere.keys[i]=value;
                    insertHere.childrens[i]=null;
                    break;
                }
            }
        }
        else{
            //splitting
            split(insertHere,par,value);
//          
        }
        size++;
    }
    public void split(BPlusNode kuchBhi,BPlusNode par,int value){
        int i,j=0,k;
        if(kuchBhi!=root){
            BPlusNode newNode=new BPlusNode(m+1);
            for(i=0;i<m-1;i++){
                newNode.keys[i]=kuchBhi.keys[i];
            }
            specialSort(newNode,value);
            int median=newNode.keys[(m)/2];
            //check if parent is also full;
            
            if(par.keys[m-2]!=-1){
                split(par,par.parent,median);
            }
            else{
                for(i=0;i<m-1;i++){
                    if(par.keys[i]>median){
                        for(j=m-2;par.keys[j]!=-1;j--){
                        
                        }
                        for(k=j-1;k>=i;k--){
                            par.keys[k+1]=par.keys[k];
                            par.childrens[k+2]=par.childrens[k+1];
                        }
                        par.keys[i]=median;
                        par.childrens[i]=null;
                        break;
                    }
                    if(par.keys[i]==-1){
                        par.keys[i]=median;
                        par.childrens[i]=null;
                        break;
                    }
                }
                int leftChildren=i;
                int rightChildren=i+1;
                int[] temp=new int[m-(m/2)+1];
                j=0;
                for(i=(m/2);i<m;i++){
                    temp[j++]=newNode.keys[i];
                    newNode.keys[i]=-1;
                }
                BPlusNode other=new BPlusNode(m); 
                for(i=0;i<j;i++){
                    other.keys[i]=temp[i];
                }
                for(i=(m/2);i<m-1;i++){
                    kuchBhi.keys[i]=-1;
                }
                for(i=0;i<m/2;i++){
                    kuchBhi.keys[i]=newNode.keys[i];
                }
                par.childrens[leftChildren]=kuchBhi;
                par.childrens[rightChildren]=other;
                kuchBhi.parent=par;
                other.parent=par;
                kuchBhi.next=other;
                par.isleaf=false;
                kuchBhi.isleaf=true;
                other.isleaf=true;
            }
        }
        else{
            //WHEN KUCHBHI IS OUR ROOT
            BPlusNode extra=new BPlusNode(m+1); 
            for(i=0;i<m-1;i++){
                extra.keys[i]=kuchBhi.keys[i];
            }
            extra=specialSort(extra,value);
            
            BPlusNode newRoot=new BPlusNode(m); 
            newRoot.keys[0]=extra.keys[m/2];
            
            int[] temp=new int[m-(m/2)+1];
            for(i=(m/2);i<m;i++){
                temp[j++]=extra.keys[i];
                extra.keys[i]=-1;
            }
            
            BPlusNode other=new BPlusNode(m); 
            for(i=0;i<j;i++){
                other.keys[i]=temp[i];
            }
            for(i=(m/2);i<m-1;i++){
                kuchBhi.keys[i]=-1;
            }
            for(i=0;i<m/2;i++){
                kuchBhi.keys[i]=extra.keys[i];
            }
            newRoot.childrens[0]=kuchBhi;
            newRoot.childrens[1]=other;
            kuchBhi.parent=newRoot;
            other.parent=newRoot;
            kuchBhi.next=other;
            root=newRoot;
            root.isleaf=false;
            kuchBhi.isleaf=true;
            other.isleaf=true;
        }
    }
    
    public BPlusNode specialSort(BPlusNode p,int val){
        int i,j,k;
        for(i=0;i<m;i++){
            if(p.keys[i]>val){
                for(j=m-1;p.keys[j]!=-1;j--){

                }
                for(k=j-1;k>=i;k--){
                    p.keys[k+1]=p.keys[k];
                    p.childrens[k+2]=p.childrens[k+1];
                }
                p.keys[i]=val;
                p.childrens[i]=null;
                break;
            }
            if(p.keys[i]==-1){
                p.keys[i]=val;
                p.childrens[i]=null;
                break;
            }
        }
        return p;
    }
    public void preOrderTraversal(){
        if(root!=null){
            Stack <BPlusNode > s1=new Stack<>();
            int i;
            System.out.println(" \n\nPRE ORDER TRAVERSAL : ");
            s1.push(root);
            while(!s1.isEmpty()){
                BPlusNode temp=s1.pop();
                for(i=0;i<m-1;i++){
                    if(temp.keys[i]!=-1){
                        System.out.print(temp.keys[i]+" , ");
                    }
                }
                for(i=m-1;i>=0;i--){
                    if (temp.childrens[i] != null) {
                        s1.push(temp.childrens[i]);
                    }
                }
                System.out.println("");
            }
            System.out.println("\n\n");
                
        }
        else{
            System.out.println("Tree is empty.");
        }
    }
    public void inOrderIterative (){
        if(root!=null && !root.isleaf){
            System.out.println("\n\nINORDER TRAVERSAL USING LINKED LIST : ");
            BPlusNode first=findFirstleaf();
            while(first!=null){
                for(int i=0;i<m-1;i++){
                    if(i<(m-1) && first.keys[i]!=-1){
                        System.out.print(first.keys[i]+" ");
                    }
                }
                System.out.print(" ---->  ");
                first=first.next;
            }
            System.out.println("null\n\n");
        }
        else{
            if(root.isleaf){
                for(int i=0;i<m-1;i++){
                    if(root.keys[i]!=-1){
                        System.out.print(root.keys[i]+" , ");
                    }
                }
                System.out.print(" ----> null\n\n ");
            }
            else{
                System.out.println("Tree is empty.");
            }
        }
    }
    public void levelOrderTraversal(){
        Queue q1=new LinkedList();
        if(this.root!=null){
            System.out.println("\n\n LEVEL ORDER TRAVERSAL : ");
            q1.add(root);
            while(!q1.isEmpty()){
                BPlusNode temp=(BPlusNode) q1.remove();
                for(int i=0;i<m-1;i++){
                    if(temp.keys[i]!=-1){
                        System.out.print(temp.keys[i]+" , ");
                    }
                }
                for(int i=0;i<m;i++){
                    if (temp.childrens[i] != null) {
                        q1.add(temp.childrens[i]);
                    }
                }
                System.out.println("");
            }
            System.out.println("\n\n");
        }
        else{
            System.out.println(" Tree is Empty.");
        }
    }

    private BPlusNode findFirstleaf() {
        BPlusNode temp=root;
        while(temp.childrens[0]!=null){
            temp=temp.childrens[0];
        }
        return temp;
    }
}