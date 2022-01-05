package adt;

public class MyNode {
    
    private Object data;
    private MyNode next;

    public MyNode(Object data, MyNode next) {

        this.data = data;
        this.next = next;

    }

    public boolean equals(Object o) {

        MyNode other = (MyNode) o;

        //two nodes are considered equal if their contents are equal
        return (this.data.equals( other.getData()));

    }

    public Object getData() {
        return data;
    }

    public MyNode getNext() {
        return next;
    }

    public void setData(Object newData) {
        data = newData;
    }

    public void setNext(MyNode newNext) {
        next = newNext;
    }

}
