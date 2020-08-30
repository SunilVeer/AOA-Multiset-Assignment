package implementation;

import java.util.List;

/**
 * Dual linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class DualLinkedListMultiset extends RmitMultiset
{
    //head for linked list ordered (ascending) by the element.
    private Node head;
    //head for linked list ordered by instance count.
    private Node secondHead;
    OrderedLinkedListMultiset orderedLinkedList = new OrderedLinkedListMultiset();

    public DualLinkedListMultiset(){
        head = null;
        secondHead = null;
    }


    @Override
	public void add(String item) {
        //add element in first linked list among two, stored in ascending order of the elements
        orderedLinkedList.add(item);

        //add element in second linked list among two, stored in ascending order of number of instances
        Node current = secondHead;
        Node previous = null;
        boolean found = false;
        while(current != null) {
            if (item.compareTo(current.i) == 0){
                found = true;
                break;
            }
            previous = current;
            current = current.next;
        }

        if (found){ //increment number of instance by one as element is already present
            current.instance++;
            if (current.next != null) {
                Node currentNode = current;
                while (current.next != null) {
                    if (currentNode.instance < current.next.instance) {
                        //if above condition is true then break so we can get current node
                        // to perform further operations
                        break;
                    }
                    current = current.next;
                }

                // if we get current and currentNode same, then don't need to change its position while adding
                //as linked list is already sorted by number of instances
                if (!currentNode.i.equals(current.i)) {
                    //swap the nodes to keep linked list sorted by number of instances
                    String temp = currentNode.i;
                    currentNode.i = current.i;
                    current.i = temp;
                    int temp1 = currentNode.instance;
                    currentNode.instance = current.instance;
                    current.instance = temp1;
                }
            }
        } else {
            //if not found then add it as a head
            //adding this at head as now added node has only one instance and sorted list can be maintained
            Node newNode = new Node(item);
            newNode.next = secondHead;
            secondHead = newNode;
        }
    } // end of add()

    @Override
    public void add(Node node) {
        //add element in first linked list among two
        orderedLinkedList.add(node);

        //add element in second linked list among two
        Node current = secondHead;
        Node previous = null;
        boolean found = false;
        while(current != null) {
            if (node.i.compareTo(current.i) == 0){
                found = true;
                break;
            }
            previous = current;
            current = current.next;
        }

        if (found){ //increment number of instance by one as element is already present
            current.instance++;
            if (current.next != null) {
                Node previousNode = previous;
                Node currentNode = current;
                while (current.next != null) {
                    if (currentNode.instance < current.next.instance) {
                        //if above condition is true then break so we can get current node
                        // to perform further operations
                        break;
                    }
                    current = current.next;
                }

                // if we get current and currentNode same, then don't need to change its position while adding
                //as linked list is already sorted by number of instances
                if (!currentNode.i.equals(current.i)) {
                    //swap the nodes to keep linked list sorted by number of instances
                    if (previousNode == null) {
                        secondHead = currentNode.next;
                    } else {
                        previousNode.next = currentNode.next;
                    }
                    currentNode.next = current.next;
                    current.next = currentNode;
                }
            }
        } else {
            //if not found then add it as a head
            //adding this at head as now added node has only one instance and sorted list can be maintained
            node.next = secondHead;
            secondHead = node;
        }
    }


    @Override
    public int search(String item) {
        //as we can use first linked list among two, which is similar to orderedlinkedlist, to search an element
        //search method of orderedLinkedList is called and output is returned
        return orderedLinkedList.search(item);
    } // end of search()


    @Override
    public List<String> searchByInstance(int instanceCount) {
        //as we can use first linked list among two, which is similar to orderedlinkedlist, to search an element
        //by its instance count, searchByInstance method of orderedLinkedList is called and output is returned
        return orderedLinkedList.searchByInstance(instanceCount);
    } // end of searchByInstance


    @Override
    public boolean contains(String item) {
        //as we can use first linked list among two, which is similar to orderedlinkedlist, to check
        // whether the element is present in duallinkedlist,
        // contains method of orderedLinkedList is called and output is returned
        return orderedLinkedList.contains(item);
    } // end of contains()


    @Override
    public void removeOne(String item) {
            //remove from first linked list
            orderedLinkedList.removeOne(item);

            //remove from second linked list
            boolean found = false;
            Node current = secondHead;
            Node previous = null;
            while (current != null) {
                if (item.equals(current.i)) {
                    current.instance--;
                    //if the element is found and after above decrement operation its instance became zero
                    //then remove that element from dual linked list
                    if (current.instance == 0) {
                        if (previous == null){ //removed node is a head node
                            secondHead = current.next;
                        } else { //removed node is not a head node
                            previous.next = current.next;
                        }
                    } else {
                        Node currentNode = secondHead;
                        Node previousNode = null;
                        while (currentNode != null) {
                            if (currentNode.instance >= current.instance) {
                                //if above condition is true then break so we can get currentNode
                                // to perform further operations
                                break;
                            }
                            previousNode = currentNode;
                            currentNode = currentNode.next;
                        }

                        //swap the nodes to keep linked list sorted by number of instances
                        if (previousNode == null){
                            if(previous != null) {
                                previous.next = current.next;
                            }
                            current.next = currentNode;
                            secondHead = current;
                        }
                        break;
                    }
                }
                previous = current;
                current = current.next;
            }
    } // end of removeOne()


    @Override
    public String print() {
        //as we can use first linked list among two, which is similar to orderedlinkedlist, to print all elements
        //print method of orderedLinkedList is called and output is returned
        return orderedLinkedList.print();
    } // end of OrderedPrint


    @Override
    public String printRange(String lower, String upper) {
        //as we can use first linked list among two, which is similar to orderedlinkedlist, to print all elements
        //printRange method of orderedLinkedList is called and output is returned
        return orderedLinkedList.printRange(lower, upper);
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        if (other instanceof DualLinkedListMultiset) {
            //newDualLinkedList is created to store union of both the ordered linked list
            DualLinkedListMultiset newDualLinkedList = new DualLinkedListMultiset();
            Node currentFirstLinkedList = orderedLinkedList.head;

            while(currentFirstLinkedList != null) {
                // created newNode and then added in newDualLinkedList
                Node newNode = new Node(currentFirstLinkedList.i);
                newNode.instance = currentFirstLinkedList.instance;
                newNode.next = currentFirstLinkedList.next;
                newDualLinkedList.add(newNode);
                currentFirstLinkedList = currentFirstLinkedList.next;
            }

            DualLinkedListMultiset otherDualLinkedList = (DualLinkedListMultiset) other;
            //head of 'other' ordered linked list is stored in otherNode
            Node otherNode = otherDualLinkedList.orderedLinkedList.head;
            boolean found;
            //if element in otherDualLinkedList is already in newDualLinkedList then update
            // only number of instances in newDualLinkedList otherwise add that element in newDualLinkedList
            while (otherNode != null) {
                found = false;
                currentFirstLinkedList = newDualLinkedList.orderedLinkedList.head;
                while (currentFirstLinkedList != null) {
                    if (otherNode.i.equals(currentFirstLinkedList.i)) {
                        found = true;
                        break;
                    }
                    currentFirstLinkedList = currentFirstLinkedList.next;
                }
                if (found){
                    currentFirstLinkedList.instance += otherNode.instance;
                } else {
                    Node newNode = new Node(otherNode.i);
                    newNode.instance = otherNode.instance;
                    newNode.next = otherNode.next;
                    newDualLinkedList.add(newNode);
                }
                otherNode = otherNode.next;
            }

            return newDualLinkedList;
        }
        return null;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        if (other instanceof DualLinkedListMultiset) {
            DualLinkedListMultiset newDualLinkedList = new DualLinkedListMultiset();
            Node currentNode = orderedLinkedList.head;
            DualLinkedListMultiset otherDualLinkedList = (DualLinkedListMultiset) other;
            Node otherNode;
            while (currentNode != null) {
                otherNode = otherDualLinkedList.orderedLinkedList.head;
                while (otherNode != null) {
                    //if element is present in both ordered linked list, then add that element
                    // in newOrderedLinkedList with its lowest number of instances
                    if (currentNode.i.equals(otherNode.i)) {
                        if (currentNode.instance < otherNode.instance) {
                            Node newNode = new Node(currentNode.i);
                            newNode.instance = currentNode.instance;
                            newNode.next = currentNode.next;
                            newDualLinkedList.add(newNode);
                        } else {
                            Node newNode = new Node(otherNode.i);
                            newNode.instance = otherNode.instance;
                            newNode.next = otherNode.next;
                            newDualLinkedList.add(newNode);
                        }
                    }
                    otherNode = otherNode.next;
                }
                currentNode = currentNode.next;
            }
            return newDualLinkedList;
        }
        return null;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        if (other instanceof DualLinkedListMultiset) {
            DualLinkedListMultiset newDualLinkedList = new DualLinkedListMultiset();
            Node currentNode = orderedLinkedList.head;
            DualLinkedListMultiset otherDualLinkedList = (DualLinkedListMultiset) other;
            Node otherNode;
            boolean found;
            while (currentNode != null) {
                found = false;
                otherNode = otherDualLinkedList.orderedLinkedList.head;
                while (otherNode != null) {
                    if (currentNode.i.equals(otherNode.i)) {
                        found = true;
                        break;
                    }
                    otherNode = otherNode.next;
                }

                if (found) {
                    //if element is present in both dual linked list and number of instances of that element
                    // in first dual linked list is greater than that of second dual linked list,
                    // then store the element in newDualLinkedList with difference
                    if (currentNode.instance - otherNode.instance > 0) {
                        Node newNode = new Node(currentNode.i);
                        newNode.instance = currentNode.instance - otherNode.instance;
                        newNode.next = currentNode.next;
                        newDualLinkedList.add(newNode);
                    }
                } else {
                    //if dual linked list element from first dual linked list is not present
                    // in second dual linked list, add the element as it is in newDualLinkedList
                    Node newNode = new Node(currentNode.i);
                    newNode.instance = currentNode.instance;
                    newNode.next = currentNode.next;
                    newDualLinkedList.add(newNode);
                }

                currentNode = currentNode.next;
            }
            return newDualLinkedList;
        }
        return null;
    } // end of difference()

} // end of class DualLinkedListMultiset
