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
        //check if element is already present
        while(current != null) {
            if(item.compareTo(current.i) >= 0) {
                if (item.compareTo(current.i) == 0) {
                    found = true;
                    break;
                }
            }
            previous = current;
            current = current.next;
        }

        if (found){ //increment number of instance by one as element is already present
            current.instance++;
        }
        else if(previous == null){ //ordered linked list is null so add the element as head
            Node newNode = new Node(item);
            head = newNode;
            newNode.next = current;
        } else { //add the element after head and so on
            Node newNode = new Node(item);
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
            previous = current;
            current = current.next;
    }

    if (found){//increment number of instance by one as element is already present
        current.instance+= node.instance;
    } else if(previous == null){ //ordered linked list is null so add the element as head
        //cannot assign node directly to head and do further operations
        //as further operation like node.next = current was changing original node
        //so first created duplicate of node and then assigned and performed further operations
        Node newNode = new Node(node.i);
        newNode.instance = node.instance;
        newNode.next = node.next;
        head = newNode;
        newNode.next = current;
    } else { //add the element after head and so on
        Node newNode = new Node(node.i);
        newNode.instance = node.instance;
        newNode.next = node.next;
        previous.next = newNode;
        newNode.next = current;
    }
}

    @Override
	public int search(String item) {
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

        //search all elements in ordered linked list with required number of instances
        // and if is found then add it to list and return
        while(current != null) {
            if (current.instance == instanceCount) {
                elementList.add(current.i);
            }
            current = current.next;
        }
        return elementList;
    } // end of searchByInstance


    @Override
	public boolean contains(String item) {
        Node current = head;
        //look for the required element in whole ordered linked list and if found return true
        while(current != null) {
            if(current.i.equals(item)){
                return true;
            }
            current = current.next;
        }
        return false;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        Node current = head;
        Node previous = null;
        while(current != null) {
            if (current.i.equals(item)){ //if element is present decrement its number of instance by one
                current.instance--;
                //if the element is found and after above decrement operation its instance became zero
                //then remove that element from array
                if (current.instance == 0) {
                    //if removed element is head, update the head by its next node
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
        //sorting ordered linked list in descending order of their number of instances
        sortedLinkedList();
        Node current = head;
        //store all the element in required pattern into string builder and return
        while(current != null){
            nodes.append(current.i).append(":").append(current.instance).append("\n");
            current = current.next;
        }
        return nodes.toString();
    } // end of OrderedPrint

    /**
     * Method to sort ordered linked list in descending order of their number of instances
     */
    public void sortedLinkedList(){
        boolean isChanged;
        do {
            Node current = head;
            Node previous = null;
            Node next = head.next;
            isChanged = false;

            while (next != null) {
                if (current.instance < next.instance) {
                    isChanged = true;
                    if (previous != null) {
                        Node temp = next.next;
                        previous.next = next;
                        next.next = current;
                        current.next = temp;
                    } else {
                        Node temp = next.next;
                        head = next;
                        next.next = current;
                        current.next = temp;
                    }
                    previous = next;
                    next = current.next;
                } else {
                    previous = current;
                    current = next;
                    next = next.next;
                }
            }
        } while (isChanged);
    }

    @Override
	public String printRange(String lower, String upper) {
        StringBuilder nodes = new StringBuilder();
        Node current = head;
        while(current != null) {
            //element within the given range including lower and upper are stored in string builder and returned
            if (lower.compareTo(current.i) <= 0 && upper.compareTo(current.i) >= 0) {
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
        return nodes.toString();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        if (other instanceof OrderedLinkedListMultiset) {

            //newOrderedLinkedList is created to store union of both the ordered linked list
            OrderedLinkedListMultiset newOrderedLinkedList = new OrderedLinkedListMultiset();
            Node currentNode = head;

            //all elements of first ordered linked list are store in third ordered linked list
            while (currentNode != null) {
                newOrderedLinkedList.add(currentNode);
                currentNode = currentNode.next;
            }
            OrderedLinkedListMultiset otherOrderedLinkedList = (OrderedLinkedListMultiset) other;

            //'other' ordered linked list is stored in otherOrderedLinkedList
            Node otherNode = otherOrderedLinkedList.head;
            boolean found;

            //if element in otherOrderedLinkedList is already in newOrderedLinkedList then update
            // only number of instances in newOrderedLinkedList otherwise add that element in newOrderedLinkedList
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
                    //if element is present in both ordered linked list, then add that element
                    // in newOrderedLinkedList with its lowest number of instances
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
                    //if element is present in both ordered linked list and number of instances of that element
                    // in first ordered linked list is greater than that of second ordered linked list,
                    // then store the element in newOrderedLinkedList with difference
                    if (currentNode.instance - otherNode.instance > 0) {
                        Node newNode = new Node(currentNode.i);
                        newNode.instance = currentNode.instance - otherNode.instance;
                        newNode.next = currentNode.next;
                        newOrderedLinkedList.add(newNode);
                    }
                } else {
                    //if ordered linked list element from first ordered linked list is not present
                    // in second ordered linked list, add the element as it is in newOrderedLinkedList
                    newOrderedLinkedList.add(currentNode);
                }

                currentNode = currentNode.next;
            }
            return newOrderedLinkedList;
        }
        return null;
    } // end of difference()

} // end of class OrderedLinkedListMultiset
