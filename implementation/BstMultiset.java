package implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * BST implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class BstMultiset extends RmitMultiset
{
    ArrayMultiset arrayMultiset = new ArrayMultiset();
    ArrayMultiset otherArrayMultiset = new ArrayMultiset();
    List<String> elementList = new ArrayList<String>();

    BstNode smallestNode= null;
    StringBuilder nodes = new StringBuilder();
    protected BstNode rootNode;
    public BstMultiset() {
        rootNode = null;

    }

    // Class for BST node
    private static class BstNode {
        protected BstNode leftNode, rightNode, parentNode;
        int instance;
        String i;
        BstNode(String i) {
            this.i = i;
            this.leftNode = null;
            this.rightNode = null;
            this.parentNode=null;
            this.instance = 1;
        }
    } // end of class BstNode

    @Override
	public void add(String item) {
        boolean found = false;
        BstNode previous = null;
        BstNode newNode = new BstNode(item);
        BstNode current = null;
        if (rootNode == null) {
            rootNode = newNode;
        }
        else {
            current = rootNode;
            while (current != null) {
                if(item.compareTo(current.i)==0) {
                    current.instance++;
                    found=true;
                    break;
                }
                else if (item.compareTo(current.i) > 0) {
                    previous = current;
                    current = current.rightNode;
                } else {
                    previous = current;
                    current = current.leftNode;
                }
            }
            if(previous!=null && !found) {
                //if the item is greater than previous node then it should become the right node else left
                if (item.compareTo(previous.i) > 0) {
                    previous.rightNode = newNode;
                    newNode.parentNode = previous;
                } else {
                    previous.leftNode = newNode;
                    newNode.parentNode = previous;
                }
            }
        }
    } // end of add()


    public void add(BstNode node) {
        boolean found = false;
        BstNode previous = null;
        BstNode current = null;
        if (rootNode == null) {
            rootNode = node;
        }
        else {
            current = rootNode;
            while (current != null) {
                if(node.i.compareTo(current.i)==0) {
                    current.instance++;
                    found=true;
                    break;
                } else if (node.i.compareTo(current.i) > 0) {
                    previous = current;
                    current = current.rightNode;
                } else {
                    previous = current;
                    current = current.leftNode;
                }
                if (found)
                    break;
            }
            if(previous!=null && !found) {
                //if the item is greater than previous node then it should become the right node else left
                if (node.i.compareTo(previous.i) > 0) {
                    previous.rightNode = node;
                    node.parentNode = previous;
                } else {
                    previous.leftNode = node;
                    node.parentNode = previous;
                }
            }
        }
    } // end of add()


    @Override
	public int search(String item) {
        BstNode current = rootNode;
        int searchedInstance = 0;
        boolean found = false;
        while(current!=null) {
            if (item.compareTo(current.i) >= 0) {
                //search item by traversing right nodes of bst, if found set boolean found to true and return number of instances.
                if (current.i.equals(item)) {
                    searchedInstance = current.instance;
                    found = true;
                    break;
                }
                current = current.rightNode;
            } else {
                //search item by traversing left nodes of bst, if found set boolean found to true and return number of instances.
                if (current.i.equals(item)) {
                    searchedInstance = current.instance;
                    found = true;
                    break;
                }
                current = current.leftNode;
            }
        }

        if (found)
            return searchedInstance;
        else
            return -1;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
        elementList.clear();
        return traversalSearchByInstance(rootNode, instanceCount);
    } // end of searchByInstance

    /**
     * Inorder traversal of bst to get list of elements with required number of instances.
     *
     * @param node is rootnode of bst and instanceCount to be searched.
     *
     * @return list of string containing elements with required number of instances.
     */
    private List<String> traversalSearchByInstance(BstNode node, int instanceCount) {
        if (node == null)
            return null;
        traversalSearchByInstance(node.leftNode, instanceCount);
        if(node!=null) {
            if(node.instance == instanceCount){
                elementList.add(node.i);
            }
        }
        traversalSearchByInstance(node.rightNode, instanceCount);
        return elementList;
    }

    @Override
	public boolean contains(String item) {
        BstNode current = rootNode;
        while(current!=null) {
            if (item.compareTo(current.i) >= 0) {
                //look for the required element traversing in right side nodes of bst and if found return true
                if (current.i.equals(item)) {
                    return true;
                }
                current = current.rightNode;
            } else {
                //look for the required element traversing in left side nodes of bst and if found return true
                if (current.i.equals(item)) {
                    return true;
                }
                current = current.leftNode;
            }
        }
        return false;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        if (rootNode != null) {
            BstNode firstRootnode = rootNode;
            Stack<BstNode> s1 = new Stack<BstNode>();
            BstMultiset newBstMultiset = new BstMultiset();
            while (true) {
                // push the Nodes of first bst in stack s1
                if (firstRootnode != null) {
                    s1.push(firstRootnode);
                    firstRootnode = firstRootnode.leftNode;
                }

                //  rootnode is NULL here
                else if (!s1.isEmpty()) {
                    firstRootnode = s1.peek();

                    // If the node is not the one to be removed, add it
                    if (!firstRootnode.i.equals(item)) {
                        BstNode newBstNode = new BstNode(firstRootnode.i);
                        newBstNode.instance = firstRootnode.instance;
                        newBstMultiset.add(newBstNode);

                        s1.pop();

                        // move to the inorder successor
                        firstRootnode = firstRootnode.rightNode;
                    } else {
                        //if the node to be removed has instances more than one left after removal
                        // then only add it to the new Bst
                        if (firstRootnode.instance - 1 > 0){
                            BstNode newBstNode = new BstNode(firstRootnode.i);
                            newBstNode.instance = firstRootnode.instance-1;
                            newBstMultiset.add(newBstNode);
                        }
                        s1.pop();
                        // move to the inorder successor
                        firstRootnode = firstRootnode.rightNode;
                    }
                }

                // Both roots and both stacks are empty
                else
                    break;
            }
            //rootNode of new bst (without the node to be removed) is given to rootNode of current bst
            rootNode = newBstMultiset.rootNode;
        }

    } // end of removeOne()


    @Override
	public String print() {
        arrayMultiset.array=null;
        nodes = new StringBuilder();
        //bst nodes are store in ArrayMultiset and print method of ArrayMultiset is called
        arrayMultiset.array=printBST(rootNode);
        return arrayMultiset.print();

    } // end of OrderedPrint

    /**
     * Traverse through all the nodes of BstMultiset and convert it into ArrayMultiset.
     *
     * @param node is Bst rootNode.
     *
     * @return array of ArrayMultiset.
     */
    private ArrayMultiset.ArrayElement[] printBST(BstNode node) {
        //recursion is used to traverse the Bst
        if (node == null)
            return null;
        printBST(node.leftNode);
        if(node!=null) {
            ArrayMultiset.ArrayElement arrayElement = new ArrayMultiset.ArrayElement(node.i);
            arrayElement.instance = node.instance;
            arrayMultiset.add(arrayElement);
        }
        printBST(node.rightNode);
        return arrayMultiset.array;
    }


    @Override
	public String printRange(String lower, String upper) {
        arrayMultiset.array=null;
        nodes = new StringBuilder();

        arrayMultiset.array=printRangeBST(rootNode, lower, upper);
        return arrayMultiset.print();
    } // end of printRange()

    /**
     * Traverse through all the nodes of BstMultiset within lower and upper limit
     * and convert it into ArrayMultiset.
     *
     * @param node is Bst rootNode, upper and lower limit of required element values.
     *
     * @return array of ArrayMultiset.
     */
    private ArrayMultiset.ArrayElement[] printRangeBST(BstNode node, String lower, String upper) {
        if (node == null)
            return null;
        printRangeBST(node.leftNode, lower, upper);
        if(node!=null) {
            //if node value is within the range of lower and upper (including) then only add it to ArrayMultiset
            if (lower.compareTo(node.i) <= 0 && upper.compareTo(node.i) >= 0) {
                ArrayMultiset.ArrayElement arrayElement = new ArrayMultiset.ArrayElement(node.i);
                arrayElement.instance = node.instance;
                arrayMultiset.add(arrayElement);
            }
        }
        printRangeBST(node.rightNode, lower, upper);
        return arrayMultiset.array;
    }

    @Override
	public RmitMultiset union(RmitMultiset other) {
        arrayMultiset.array=null;
        otherArrayMultiset.array=null;

        //both the BstMultisets are converted to ArrayMultiset and then pass to union method of array

        nodes = new StringBuilder();
        arrayMultiset.array=bstToArray(rootNode, arrayMultiset);

        nodes = new StringBuilder();
        BstMultiset otherBstMultiset = (BstMultiset) other;
        otherArrayMultiset.array=bstToArray(otherBstMultiset.rootNode, otherArrayMultiset);

        return arrayMultiset.union(otherArrayMultiset);
    } // end of union()



    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        arrayMultiset.array=null;
        otherArrayMultiset.array=null;

        //both the BstMultisets are converted to ArrayMultiset and then pass to union method of array

        nodes = new StringBuilder();
        arrayMultiset.array=bstToArray(rootNode, arrayMultiset);

        nodes = new StringBuilder();
        BstMultiset otherBstMultiset = (BstMultiset) other;
        otherArrayMultiset.array=bstToArray(otherBstMultiset.rootNode, otherArrayMultiset);

        return arrayMultiset.intersect(otherArrayMultiset);
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {arrayMultiset.array=null;
        otherArrayMultiset.array=null;

        //both the BstMultisets are converted to ArrayMultiset and then pass to union method of array

        nodes = new StringBuilder();
        arrayMultiset.array=bstToArray(rootNode, arrayMultiset);

        nodes = new StringBuilder();
        BstMultiset otherBstMultiset = (BstMultiset) other;
        otherArrayMultiset.array=bstToArray(otherBstMultiset.rootNode, otherArrayMultiset);

        return arrayMultiset.difference(otherArrayMultiset);
    } // end of difference()

    private ArrayMultiset.ArrayElement[] bstToArray(BstNode node, ArrayMultiset arrayMultiset) {
        if (node == null)
            return null;
        bstToArray(node.leftNode, arrayMultiset);
        if(node!=null) {
            ArrayMultiset.ArrayElement arrayElement = new ArrayMultiset.ArrayElement(node.i);
            arrayElement.instance = node.instance;
            arrayMultiset.add(arrayElement);
        }
        bstToArray(node.rightNode, arrayMultiset);
        return arrayMultiset.array;
    }

    //method is not required in this Multiset as Node class is different
    @Override
    public void add(Node node) {

    }

} // end of class BstMultiset
