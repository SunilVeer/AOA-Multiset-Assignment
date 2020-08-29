package implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Ordered linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class OrderedLinkedListMultiset extends RmitMultiset
{
    public Node head;

    public OrderedLinkedListMultiset(){
        head = null;
    }

    @Override
	public void add(String item) {

        Node current = head;
        Node previous = null;
        boolean found = false;
        while(current != null && item.compareTo(current.i) >= 0) {
            if (item.compareTo(current.i) == 0){
                found = true;
                break;
            }
            System.out.println("current: " + current.i);
            previous = current;
            current = current.next;
            //System.out.println("after current: " + current.i);
        }

        if (found){
            current.instance++;
        }
        else if(previous == null){
            Node newNode = new Node(item);
            System.out.println("current else if: " + newNode.i);
            head = newNode;
            newNode.next = current;
        } else {
            Node newNode = new Node(item);
            //System.out.println("current else: " + current.i);
            System.out.println("prev else: " + previous.i);
            previous.next = newNode;
            newNode.next = current;
        }
    } // end of add()

    @Override
    public void add (Node node){
        Node current = head;
        Node previous = null;
        boolean found = false;
        while(current != null && node.i.compareTo(current.i) >= 0) {
            if (node.i.compareTo(current.i) == 0){
                found = true;
                break;
            }
            System.out.println("current: " + current.i);
            previous = current;
            current = current.next;
            //System.out.println("after current: " + current.i);
    }

    if (found){
        current.instance+= node.instance;
    }
    else if(previous == null){
        System.out.println("current else if: " + node.i);
        //cannot assign node directly to head and do further operations
        //as further operation like node.next = current was changing original node
        //so first created duplicate of node and then assigned and performed further operations
        Node newNode = new Node(node.i);
        newNode.instance = node.instance;
        newNode.next = node.next;
        head = newNode;
        newNode.next = current;
    } else {
        //System.out.println("current else: " + current.i);
        System.out.println("prev else: " + previous.i);
        Node newNode = new Node(node.i);
        newNode.instance = node.instance;
        newNode.next = node.next;
        previous.next = newNode;
        newNode.next = current;
    }
}
    @Override
	public int search(String item) {
        // Implement me!
        int searchedInstance = 0;
        boolean found = false;
        Node current = head;
        while(current != null) {
            if(current.i.equals(item)){
                searchedInstance = current.instance;
                found = true;
                break;
            }
            current = current.next;
        }
        if (found)
            return searchedInstance;
        else
            return -1;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
        Node current = head;
        List<String> elementList = new ArrayList<String>();

        while(current != null) {
            if (current.instance == instanceCount) {
                elementList.add(current.i);
            }
            System.out.println("current : " + current.i);
            current = current.next;
        }
        return elementList;
    } // end of searchByInstance


    @Override
	public boolean contains(String item) {
        // Implement me!
        Node current = head;
        while(current != null) {
            if(current.i.equals(item)){
                return true;
            }
            current = current.next;
        }
        // Placeholder, please update.
        return false;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        // Implement me!
        Node current = head;
        Node previous = null;
        while(current != null) {
            if (current.i.equals(item)){
                current.instance--;
                if (current.instance == 0) {
                    if (current == head) {
                        head = current.next;
                        break;
                    } else {
                        previous.next = current.next;
                        break;
                    }
                } else {
                    break;
                }
            }
            previous = current;
            current = current.next;
        }
    } // end of removeOne()


    @Override
	public String print() {
        StringBuilder nodes = new StringBuilder();
        sortedLinkedList();
        Node current = head;
        while(current != null){
            nodes.append(current.i).append(":").append(current.instance).append("\n");
            current = current.next;
        }
        // Placeholder, please update.
        return nodes.toString();
    } // end of OrderedPrint

    public void sortedLinkedList(){
        Node current = head;
        Node next = head.next;
        Node previous = null;
        boolean isChanged = false;

        do {
            isChanged = false;
            while ( next != null ) {
                if (current.instance < next.instance) {
                    isChanged = true;
                    if ( previous != null ) {
                        Node tempNode = next.next;
                        previous.next = next;
                        next.next = current;
                        current.next = tempNode;
                    } else {
                        Node tempNode = next.next;
                        head = next;
                        next.next = current;
                        current.next = tempNode;
                    }
                    previous = next;
                    next = current.next;
                } else {
                    previous = current;
                    current = next;
                    next = next.next;
                }
            }
        } while( isChanged );

    }

    @Override
	public String printRange(String lower, String upper) {
        StringBuilder nodes = new StringBuilder();
        Node current = head;
        while(current != null) {
            if (lower.compareTo(current.i) <= 0 && upper.compareTo(current.i) >= 0) {
                System.out.println("current: " + current.i);
                if (current.next != null) {
                    if (!current.i.equals(current.next.i)) {
                        nodes.append(current.i).append(":").append(search(current.i)).append("\n");
                    }
                } else {
                    nodes.append(current.i).append(":").append(search(current.i)).append("\n");
                }
            }
            current = current.next;
        }
        // Placeholder, please update.
        return nodes.toString();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        if (other instanceof OrderedLinkedListMultiset) {
            OrderedLinkedListMultiset newOrderedLinkedList = new OrderedLinkedListMultiset();
            Node currentNode = head;
            while (currentNode != null) {
                newOrderedLinkedList.add(currentNode);
                currentNode = currentNode.next;
            }
            OrderedLinkedListMultiset otherOrderedLinkedList = (OrderedLinkedListMultiset) other;
            Node otherNode = otherOrderedLinkedList.head;
            boolean found;
            while (otherNode != null) {
                found = false;
                currentNode = newOrderedLinkedList.head;
                while (currentNode != null) {
                    if (otherNode.i.equals(currentNode.i)) {
                        found = true;
                        break;
                    }
                    currentNode = currentNode.next;
                }
                 if (found){
                     currentNode.instance += otherNode.instance;
                 } else {
                     newOrderedLinkedList.add(otherNode);
                 }
                otherNode = otherNode.next;
            }
            return newOrderedLinkedList;
        }

        return null;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        if (other instanceof OrderedLinkedListMultiset) {
            OrderedLinkedListMultiset newOrderedLinkedList = new OrderedLinkedListMultiset();
            Node currentNode = head;
            OrderedLinkedListMultiset otherOrderedLinkedList = (OrderedLinkedListMultiset) other;
            Node otherNode;
            while (currentNode != null) {
                otherNode = otherOrderedLinkedList.head;
                while (otherNode != null) {
                    if (currentNode.i.equals(otherNode.i)) {
                        if (currentNode.instance < otherNode.instance) {
                            newOrderedLinkedList.add(currentNode);
                        } else {
                            newOrderedLinkedList.add(otherNode);
                        }
                    }
                    otherNode = otherNode.next;
                }
                currentNode = currentNode.next;
            }
            return newOrderedLinkedList;
        }
        return null;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        if (other instanceof OrderedLinkedListMultiset) {
            OrderedLinkedListMultiset newOrderedLinkedList = new OrderedLinkedListMultiset();
            Node currentNode = head;
            OrderedLinkedListMultiset otherOrderedLinkedList = (OrderedLinkedListMultiset) other;
            Node otherNode;
            boolean found;
            while (currentNode != null) {
                found = false;
                otherNode = otherOrderedLinkedList.head;
                while (otherNode != null) {
                    if (currentNode.i.equals(otherNode.i)) {
                        found = true;
                        break;
                    }
                    otherNode = otherNode.next;
                }

                if (found) {
                    if (currentNode.instance - otherNode.instance > 0) {
                        Node newNode = new Node(currentNode.i);
                        newNode.instance = currentNode.instance - otherNode.instance;
                        newNode.next = currentNode.next;
                        newOrderedLinkedList.add(newNode);
                    }
                } else {
                    newOrderedLinkedList.add(currentNode);
                }

                currentNode = currentNode.next;
            }
            return newOrderedLinkedList;
        }
        return null;
    } // end of difference()

} // end of class OrderedLinkedListMultiset
