package adt;

public class MyLinkedList {

    MyNode head;

    final static MyNode DUMMY = new MyNode("dummy head", null);

    public MyLinkedList() {
        head = DUMMY;
    }

}