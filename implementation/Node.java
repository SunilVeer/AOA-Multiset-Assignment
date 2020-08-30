package implementation;
/**
 * Node class implementation for storing elements of Ordered linked list and Dual linked list multiset.
 */
public class Node {
    String i;
    int instance;
    Node next;

    //constructor
    Node(String i) {
        this.i = i;
        this.instance = 1;
        this.next = null;
    }
} // end of class Node
