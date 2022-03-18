package OUSL;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int noOfChildren;
    private int depth;
    private int childIndex;
    private String value;
    private String condition;
    private Node parent;
    private ArrayList<Node> children;


    public Node() {
    }

    public Node(int depth, String value, Node parent) {
        this.depth = depth;
        this.value = value;
        this.parent = parent;
        this.children = new ArrayList<Node>();
    }




    public int getNoOfChildren() {
        return this.noOfChildren;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChild(Node child) {
        this.children.add(child);
    }

    public ArrayList<Node>getChildren() {
        return this.children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
        //.children = chn.toArray(new Node[chn.size()]);
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex() {
        this.childIndex = this.getParent().getChildren().size() -1;
    }

    public boolean isChildrenExists() {
        return !children.isEmpty();
    }


    public boolean isNextSiblingExists() {
        return this.getChildIndex()+1 < this.parent.children.size();
    }


    public ArrayList<Node> getSiblings(Node node) {
        return node.getParent().getChildren();
    }

    public int getNoOfSiblings() {
        return this.getParent().getNoOfChildren();
    }




    public Node getNextSibling() {
         if(isNextSiblingExists()){
             return this.getParent().getChildAt(this.getChildIndex()+1);
         }else{
             return null;
         }
    }


    public Node getSiblingAt(int index) {
        int ci = this.getChildIndex();
        if(index>=this.getNoOfSiblings()){
            return null;
        }else{
            return this.getParent().getChildAt(index);
        }
    }

    public Node getChildAt(int index) {
        if(this.getChildren().isEmpty()){
            return null;
        }else if(index!=0 && this.getChildren().size() <= index){
            return null;
        }else{
            return this.getChildren().get(index);
        }
    }

    public void setChildAt(int index, Node child) {
        if(index<this.parent.children.size()) this.parent.children.set(index, child);
    }




}
