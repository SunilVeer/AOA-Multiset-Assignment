package implementation;

import java.util.ArrayList;
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
    StringBuilder nodes = new StringBuilder();
    OrderedLinkedListMultiset orderedLinkedList = new OrderedLinkedListMultiset();

    public DualLinkedListMultiset(){
        head = null;
        secondHead = null;
    }


    @Override
	public void add(String item) {
        // Implement me!
        orderedLinkedList.add(item);

        Node current = secondHead;
        //orderedLinkedList.add((RmitMultiset)current);
        Node previous = null;
        boolean found = false;
        while(current != null) {
            System.out.println("current in while: " + current.i);
            if (item.compareTo(current.i) == 0){
                found = true;
                break;
            }
            //System.out.println("current: " + current.i);
            previous = current;
            current = current.next;
            //System.out.println("after current: " + current.i);
        }

        if (found){
            if (previous!=null)
            System.out.println("prev : " + previous.i);
            current.instance++;
            if (current.next != null) {
                Node previousNode = previous;
                Node currentNode = current;
                found = false;
                while (current.next != null) {
                    //&& currentNode.instance > (current.next.instance)) {
                    if (currentNode.instance < current.next.instance) {
                        found = true;
                        break;
                    }
                    previous = current;
                    System.out.println("previous : " + previous.i);
                    current = current.next;
                    //System.out.println("after current: " + current.i);
                }

                if (found) {
                    if (previous != null)
                        System.out.println("previous outside : " + previous.i);
                    if (current!=null)
                        System.out.println("current outside : " + current.i);
                } else {
                    if (previous != null)
                        System.out.println("previous else : " + previous.i);
                    if (current!=null)
                        System.out.println("current else : " + current.i);
                }


                if (currentNode != null)
                System.out.println("currentNode.i : " + currentNode.i);
                if (previous != null)
                System.out.println("previous.i : " + previous.i);


                if (!currentNode.i.equals(current.i)) {
                    String temp = currentNode.i;
                    currentNode.i = current.i;
                    current.i = temp;
                    int temp1 = currentNode.instance;
                    currentNode.instance = current.instance;
                    current.instance = temp1;

                }
            }
        } else {
            Node newNode = new Node(item);
            //System.out.println("current else if: " + newNode.i);
            newNode.next = secondHead;
            secondHead = newNode;
        }

        //remove second linked list
        current = secondHead;
        while(current != null) {
            System.out.println("current 2 is: " + current.i + " " + current.instance);
            current = current.next;
        }

    } // end of add()

    @Override
    public void add(Node node) {
        orderedLinkedList.add(node);

        Node current = secondHead;
        //orderedLinkedList.add((RmitMultiset)current);
        Node previous = null;
        boolean found = false;
        while(current != null) {
            System.out.println("current in while: " + current.i);
            if (node.i.compareTo(current.i) == 0){
                found = true;
                break;
            }
            //System.out.println("current: " + current.i);
            previous = current;
            current = current.next;
            //System.out.println("after current: " + current.i);
        }

        if (found){
            if (previous!=null)
                System.out.println("prev : " + previous.i);
            current.instance++;
            if (current.next != null) {
                Node previousNode = previous;
                Node currentNode = current;
                found = false;
                while (current.next != null) {
                    //&& currentNode.instance > (current.next.instance)) {
                    if (currentNode.instance < current.next.instance) {
                        found = true;
                        break;
                    }
                    previous = current;
                    System.out.println("previous : " + previous.i);
                    current = current.next;
                    //System.out.println("after current: " + current.i);
                }

                if (found) {
                    if (previous != null)
                        System.out.println("previous outside : " + previous.i);
                    if (current!=null)
                        System.out.println("current outside : " + current.i);
                } else {
                    if (previous != null)
                        System.out.println("previous else : " + previous.i);
                    if (current!=null)
                        System.out.println("current else : " + current.i);
                }


                if (currentNode != null)
                    System.out.println("currentNode.i : " + currentNode.i);
                if (previous != null)
                    System.out.println("previous.i : " + previous.i);


                if (!currentNode.i.equals(current.i)) {
                    //||previousNode == null) {
                    if (previousNode == null) {
                        secondHead = currentNode.next;
                        System.out.println("second head : " + secondHead.i);
                        //secondHead.next = currentNode;
                    } else {
                        previousNode.next = currentNode.next;
                        if (previousNode.next != null)
                            System.out.println("previousNode else : " + previousNode.next.i);
                    }
                    //if (current.next!=null){
                    currentNode.next = current.next;
                    if (currentNode.next != null)
                        System.out.println("currentNode if  : " + currentNode.next.i);
                    //}
                    current.next = currentNode;
                    if (current.next != null)
                        System.out.println("current if  : " + current.next.i);
                }
            }
        } else {
            //Node newNode = new Node(item);
            //System.out.println("current else if: " + newNode.i);
            node.next = secondHead;
            secondHead = node;
        }

        //print second linked list
        current = secondHead;
        while(current != null) {
            System.out.println("current 2 is: " + current.i + " " + current.instance);
            current = current.next;
        }
    }


    @Override
    public int search(String item) {
        return orderedLinkedList.search(item);
    } // end of search()


    @Override
    public List<String> searchByInstance(int instanceCount) {
        return orderedLinkedList.searchByInstance(instanceCount);
    } // end of searchByInstance


    @Override
    public boolean contains(String item) {
        return orderedLinkedList.contains(item);
    } // end of contains()


    @Override
    public void removeOne(String item) {
        // Implement me!
            //remove from first linked list
            orderedLinkedList.removeOne(item);

            Node currentFirstLinkedList = orderedLinkedList.head;
            while (currentFirstLinkedList != null) {
                System.out.println("current 1 is: " + currentFirstLinkedList.i + " " + currentFirstLinkedList.instance);
                currentFirstLinkedList = currentFirstLinkedList.next;
            }

            //remove from second linked list
            boolean found = false;
            Node current = secondHead;
            Node previous = null;
            while (current != null) {
                if (item.equals(current.i)) {
                    current.instance--;
                    if (current.instance == 0) {
                        //removed node is a head node
                        if (previous == null){
                            secondHead = current.next;
                        } else {
                            previous.next = current.next;
                        }
                    } else {
                        //removed node is not a head node
                        System.out.println("in else");
                        Node currentNode = secondHead;
                        Node previousNode = null;
                        while (currentNode != null) {
                            System.out.println("while currentNode : " + currentNode.i);
                            if (currentNode.instance >= current.instance) {
                                break;
                            }
                            previousNode = currentNode;
                            currentNode = currentNode.next;
                        }

                        if (previousNode != null)
                            System.out.println("previousNode : " + previousNode.i);
                        if (currentNode != null)
                            System.out.println("currentNode : " + currentNode.i);

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
                //System.out.println("current 2 is: " + current.i + " " + current.instance);
                previous = current;
                current = current.next;
            }
            //print second linked list
            current = secondHead;
            while (current != null) {
                System.out.println("current 2 is: " + current.i + " " + current.instance);
                current = current.next;
            }
    } // end of removeOne()


    @Override
    public String print() {
        nodes = new StringBuilder();
        return printDualLinkedList(secondHead);
    } // end of OrderedPrint

    //printing second linked list in reversed order using recursion as it is in ascending order
    private String printDualLinkedList(Node node) {
        if (node == null)
            return "";
        printDualLinkedList(node.next);
        nodes.append(node.i).append(":").append(node.instance).append("\n");
        return nodes.toString();
    }


    @Override
    public String printRange(String lower, String upper) {
        return orderedLinkedList.printRange(lower, upper);
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        if (other instanceof DualLinkedListMultiset) {
            DualLinkedListMultiset newDualLinkedList = new DualLinkedListMultiset();
            Node currentFirstLinkedList = orderedLinkedList.head;
            while(currentFirstLinkedList != null) {
                // add comment
                Node newNode = new Node(currentFirstLinkedList.i);
                newNode.instance = currentFirstLinkedList.instance;
                newNode.next = currentFirstLinkedList.next;
                newDualLinkedList.add(newNode);
                //System.out.println("current 1 is: " + currentFirstLinkedList.i + " " + currentFirstLinkedList.instance);
                currentFirstLinkedList = currentFirstLinkedList.next;
            }
            DualLinkedListMultiset otherOrderedLinkedList = (DualLinkedListMultiset) other;
            Node otherNode = otherOrderedLinkedList.orderedLinkedList.head;
            boolean found;
            System.out.println("otherNode : " + otherNode.i);
            while (otherNode != null) {
                found = false;
                currentFirstLinkedList = newDualLinkedList.orderedLinkedList.head;
                System.out.println("currentFirstLinkedList : " + currentFirstLinkedList.i);
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
                    System.out.println("otherNode 2 : " + otherNode.i);
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
        // Placeholder, please update.
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
                    if (currentNode.instance - otherNode.instance > 0) {
                        Node newNode = new Node(currentNode.i);
                        newNode.instance = currentNode.instance - otherNode.instance;
                        newNode.next = currentNode.next;
                        newDualLinkedList.add(newNode);
                    }
                } else {
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
