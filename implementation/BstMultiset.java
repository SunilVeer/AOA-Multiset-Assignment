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
    List<String> elementList = new ArrayList<String>();

    int arraySize;
    BstNode smallestNode= null, largestNode = null;
    StringBuilder nodes = new StringBuilder();
    protected BstNode rootNode;
    public BstMultiset() {
        rootNode = null;

    }
    // Class for nodes
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
    }

    @Override
	public void add(String item) {
        // Implement me!
        boolean found = false;
        BstNode previous = null;
        BstNode newNode = new BstNode(item);
        BstNode current = null;
        if (rootNode == null) {
            rootNode = newNode;
            System.out.println("root node is: " + rootNode.i);
        }
        else {
            current = rootNode;
            while (current != null) {
                //if the item is greater than root node then it should become the right node
                if(item.compareTo(current.i)==0) {
                    System.out.println("instance: "+current.i);
                    current.instance++;
                    found=true;
                    break;
                }
                else if (item.compareTo(current.i) > 0) {
                    previous = current;
                    current = current.rightNode;
                    System.out.println("right node is: " + previous.i);
                } else {
                    previous = current;
                    current = current.leftNode;
                    System.out.println("left node is: " + previous.i);
                }
            }
            if(previous!=null && !found) {
                System.out.println("in if");
                if (item.compareTo(previous.i) > 0) {

                    previous.rightNode = newNode;
                    if (previous.rightNode != null)
                    System.out.println("previous.rightNode: " + previous.rightNode.i);
                    newNode.parentNode = previous;
                    if (newNode.parentNode != null)
                        System.out.println("newNode.parentNode: " + newNode.parentNode.i);
                } else {

                    previous.leftNode = newNode;
                    if (previous.leftNode != null)
                        System.out.println("previous.leftNode: " + previous.leftNode.i);
                    newNode.parentNode = previous;
                    if (newNode.parentNode != null)
                        System.out.println("newNode.parentNode: " + newNode.parentNode.i);
                }
            }
        }
    } // end of add()


    public void add(BstNode node) {
        // Implement me!
        boolean found = false;
        BstNode previous = null;
        BstNode current = null;
        if (rootNode == null) {
            rootNode = node;
            System.out.println("root node is: " + rootNode.i);
        }
        else {
            current = rootNode;
            while (current != null) {
                System.out.println("while current : " + current.i);
                //if the item is greater than root node then it should become the right node
                if(node.i.compareTo(current.i)==0) {
                    System.out.println("instance: "+current.i);
                    current.instance++;
                    found=true;
                    break;
                }
                else if (node.i.compareTo(current.i) > 0) {
                    previous = current;
                    current = current.rightNode;
                    System.out.println("right node is: " + previous.i);
                } else {
                    previous = current;
                    current = current.leftNode;
                    System.out.println("left node is: " + previous.i);
                }
                if (found)
                    break;
            }
            if(previous!=null && !found) {
                System.out.println("in if");
                if (node.i.compareTo(previous.i) > 0) {

                    previous.rightNode = node;
                    if (previous.rightNode != null)
                        System.out.println("previous.rightNode: " + previous.rightNode.i);
                    node.parentNode = previous;
                    if (node.parentNode != null)
                        System.out.println("newNode.parentNode: " + node.parentNode.i);
                } else {

                    previous.leftNode = node;
                    if (previous.leftNode != null)
                        System.out.println("previous.leftNode: " + previous.leftNode.i);
                    node.parentNode = previous;
                    if (node.parentNode != null)
                        System.out.println("newNode.parentNode: " + node.parentNode.i);
                }
            }
        }
    } // end of add()


    @Override
	public int search(String item) {
        // Implement me!
        BstNode current = rootNode;
        int searchedInstance = 0;
        boolean found = false;
        while(current!=null) {
            if (item.compareTo(current.i) >= 0) {
                if (current.i.equals(item)) {
                    searchedInstance = current.instance;
                    found = true;
                    break;
                }
                current = current.rightNode;
            }
            else {
                if (current.i.equals(item)) {
                    searchedInstance = current.instance;
                    found = true;
                    break;
                }
                current = current.leftNode;
            }
        }
        // Placeholder, please update.
        if (found)
            return searchedInstance;
        else
            return -1;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
        //BstNode current = rootNode;
        elementList.clear();
        //BstNode current = rootNode;
        return traversalSearchByInstance(rootNode, instanceCount);
    } // end of searchByInstance

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
        // Implement me!
        BstNode current = rootNode;
        while(current!=null) {
            if (item.compareTo(current.i) >= 0) {
                if (current.i.equals(item)) {
                    return true;
                }
                current = current.rightNode;
            } else {
                if (current.i.equals(item)) {
                    return true;
                }
                current = current.leftNode;
            }
        }
        // Placeholder, please update.
        return false;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        // Implement me!
        if (rootNode != null) {
            BstNode firstRootnode = rootNode;
            Stack<BstNode> s1 = new Stack<BstNode>();
            BstMultiset newBstMultiset = new BstMultiset();
            while (true) {
                // push the Nodes of first tree in stack s1
                if (firstRootnode != null) {
                    s1.push(firstRootnode);
                    firstRootnode = firstRootnode.leftNode;
                }

                // Both root1 and root2 are NULL here
                else if (!s1.isEmpty()) {
                    firstRootnode = s1.peek();

                    // If current keys in two trees are same
                    if (!firstRootnode.i.equals(item)) {
                        System.out.println(firstRootnode.i + " ");
                        BstNode newBstNode = new BstNode(firstRootnode.i);
                        newBstNode.instance = firstRootnode.instance;
                        newBstMultiset.add(newBstNode);

                        s1.pop();

                        // move to the inorder successor
                        firstRootnode = firstRootnode.rightNode;
                    } else {
                        if (firstRootnode.instance - 1 > 0){
                            BstNode newBstNode = new BstNode(firstRootnode.i);
                            newBstNode.instance = firstRootnode.instance-1;
                            System.out.println("newbst inst: " + newBstNode.instance);
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
            rootNode = newBstMultiset.rootNode;
        }

    } // end of removeOne()

    private BstNode deriveSmallestNode (BstNode node, String i) {
        if (node == null)
            return null;
        //deriveSmallestNode(node.leftNode, i);
        if(node!=null) {
            System.out.println("nodes in smallestNode: " + node.i);
            System.out.println("i : " + i);
            if (node.i.compareTo(i) <= 0){
                System.out.println("nodes in smallestNode if: " + node.i);
                smallestNode = node;
                i = smallestNode.i;
            }
        }
        deriveSmallestNode(node.leftNode, i);
        //deriveSmallestNode(node.rightNode);
        return smallestNode;
    }

    @Override
	public String print() {

        //ArrayMultiset arrayMultiset = new ArrayMultiset();
        arrayMultiset.array=null;

        //arraySize=0;
         nodes = new StringBuilder();

        arrayMultiset.array=printBST(rootNode);
        return arrayMultiset.print();

    } // end of OrderedPrint


    private ArrayMultiset.ArrayElement[] printBST(BstNode node) {
        if (node == null)
            return null;
        printBST(node.leftNode);
        if(node!=null) {
            ArrayMultiset.ArrayElement arrayElement = new ArrayMultiset.ArrayElement(node.i);
            System.out.println("Array node:"+arrayElement.element + " " + arrayElement.instance);
            arrayElement.instance = node.instance;
            arrayMultiset.add(arrayElement);
        }
        printBST(node.rightNode);
        return arrayMultiset.array;
    }


    @Override
	public String printRange(String lower, String upper) {
        arrayMultiset.array=null;

        //arraySize=0;
        nodes = new StringBuilder();

        arrayMultiset.array=printRangeBST(rootNode, lower, upper);
        return arrayMultiset.print();
    } // end of printRange()

    private ArrayMultiset.ArrayElement[] printRangeBST(BstNode node, String lower, String upper) {
        if (node == null)
            return null;
        printRangeBST(node.leftNode, lower, upper);
        if(node!=null) {
            if (lower.compareTo(node.i) <= 0 && upper.compareTo(node.i) >= 0) {
                ArrayMultiset.ArrayElement arrayElement = new ArrayMultiset.ArrayElement(node.i);
                System.out.println("Array node:" + arrayElement.element + " " + arrayElement.instance);
                arrayElement.instance = node.instance;
                arrayMultiset.add(arrayElement);
            }
        }
        printRangeBST(node.rightNode, lower, upper);
        return arrayMultiset.array;
    }

    @Override
	public RmitMultiset union(RmitMultiset other) {
        if (other instanceof BstMultiset) {
            BstMultiset firstBstMultiset = (BstMultiset) other;
            BstNode firstRootnode = rootNode;
            BstMultiset otherBstMultiset = (BstMultiset) other;
            BstNode otherRootnode = otherBstMultiset.rootNode;
            Stack<BstNode> s1 = new Stack<BstNode> ();
            Stack<BstNode> s2 = new Stack<BstNode> ();
            BstMultiset newBstMultiset = new BstMultiset();
            while (true)
            {
                // push the Nodes of first tree in stack s1
                if (firstRootnode != null)
                {
                    s1.push(firstRootnode);
                    firstRootnode = firstRootnode.leftNode;
                }

                // push the Nodes of second tree in stack s2
                else if (otherRootnode != null)
                {
                    s2.push(otherRootnode);
                    otherRootnode = otherRootnode.leftNode;
                }

                // Both root1 and root2 are NULL here
                else if (!s1.isEmpty() && !s2.isEmpty())
                {
                    firstRootnode = s1.peek();
                    otherRootnode = s2.peek();

                    // If current keys in two trees are same
                    if (firstRootnode.i.equals(otherRootnode.i)) {
                        System.out.println(firstRootnode.i + " ");
                            BstNode newBstNode = new BstNode(firstRootnode.i);
                            newBstNode.instance = firstRootnode.instance + otherRootnode.instance;
                            newBstMultiset.add(newBstNode);

                        s1.pop();
                        s2.pop();

                        // move to the inorder successor
                        firstRootnode = firstRootnode.rightNode;
                        otherRootnode = otherRootnode.rightNode;
                    } else {
                        // If Node of first tree is smaller, than that of
                        // second tree, then its obvious that the inorder
                        // successors of current Node can have same value
                        // as that of the second tree Node. Thus, we pop
                        // from s2
                        System.out.println("else : " + firstRootnode.i + " " + otherRootnode.i);

                        BstNode newBstNode = new BstNode(firstRootnode.i);
                        newBstNode.instance = firstRootnode.instance;
                        newBstMultiset.add(newBstNode);

                        newBstNode = new BstNode(otherRootnode.i);
                        newBstNode.instance = otherRootnode.instance;
                        newBstMultiset.add(newBstNode);

                        s1.pop();
                        firstRootnode = firstRootnode.rightNode;

                        // root2 is set to NULL, because we need
                        // new Nodes of tree 1
                        otherRootnode = null;
                    }
                }

                // Both roots and both stacks are empty
                else break;
            }
            // Placeholder, please update.
            return newBstMultiset;
        }
        // Placeholder, please update.
        return null;
    } // end of union()



    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        if (other instanceof  BstMultiset) {
            BstMultiset firstBstMultiset = (BstMultiset) other;
            BstNode firstRootnode = rootNode;
            BstMultiset otherBstMultiset = (BstMultiset) other;
            BstNode otherRootnode = otherBstMultiset.rootNode;
            Stack<BstNode> s1 = new Stack<BstNode>();
            Stack<BstNode> s2 = new Stack<BstNode>();
            BstMultiset newBstMultiset = new BstMultiset();
            while (true) {
                // push the Nodes of first tree in stack s1
                if (firstRootnode != null) {
                    s1.push(firstRootnode);
                    firstRootnode = firstRootnode.leftNode;
                }

                // push the Nodes of second tree in stack s2
                else if (otherRootnode != null) {
                    s2.push(otherRootnode);
                    otherRootnode = otherRootnode.leftNode;
                }

                // Both root1 and root2 are NULL here
                else if (!s1.isEmpty() && !s2.isEmpty()) {
                    firstRootnode = s1.peek();
                    otherRootnode = s2.peek();

                    // If current keys in two trees are same
                    if (firstRootnode.i.equals(otherRootnode.i)) {
                        System.out.println(firstRootnode.i + " ");
                        if (firstRootnode.instance < otherRootnode.instance) {
                            BstNode newBstNode = new BstNode(firstRootnode.i);
                            newBstNode.instance = firstRootnode.instance;
                            newBstMultiset.add(newBstNode);
                        } else {
                            BstNode newBstNode = new BstNode(otherRootnode.i);
                            newBstNode.instance = otherRootnode.instance;
                            newBstMultiset.add(newBstNode);
                        }
                        s1.pop();
                        s2.pop();

                        // move to the inorder successor
                        firstRootnode = firstRootnode.rightNode;
                        otherRootnode = otherRootnode.rightNode;
                    } else if (firstRootnode.i.compareTo(otherRootnode.i) <= 0) {
                        // If Node of first tree is smaller, than that of
                        // second tree, then its obvious that the inorder
                        // successors of current Node can have same value
                        // as that of the second tree Node. Thus, we pop
                        // from s2
                        System.out.println("first else if : " + firstRootnode.i + " " + otherRootnode.i);
                        s1.pop();
                        firstRootnode = firstRootnode.rightNode;

                        // root2 is set to NULL, because we need
                        // new Nodes of tree 1
                        otherRootnode = null;
                    } else if (firstRootnode.i.compareTo(otherRootnode.i) >= 0) {
                        System.out.println("second else if : " + firstRootnode.i + " " + otherRootnode.i);
                        s2.pop();
                        otherRootnode = otherRootnode.rightNode;
                        firstRootnode = null;
                    }
                }

                // Both roots and both stacks are empty
                else break;
            }
            // Placeholder, please update.
            return newBstMultiset;
        }
        return null;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        if (other instanceof  BstMultiset) {
            BstMultiset firstBstMultiset = (BstMultiset) other;
            BstNode firstRootnode = rootNode;
            BstMultiset otherBstMultiset = (BstMultiset) other;
            BstNode otherRootnode = otherBstMultiset.rootNode;
            Stack<BstNode> s1 = new Stack<BstNode>();
            Stack<BstNode> s2 = new Stack<BstNode>();
            BstMultiset newBstMultiset = new BstMultiset();
            while (true) {
                // push the Nodes of first tree in stack s1
                if (firstRootnode != null) {
                    s1.push(firstRootnode);
                    firstRootnode = firstRootnode.leftNode;
                }

                // push the Nodes of second tree in stack s2
                else if (otherRootnode != null) {
                    s2.push(otherRootnode);
                    otherRootnode = otherRootnode.leftNode;
                }

                // Both root1 and root2 are NULL here
                else if (!s1.isEmpty() && !s2.isEmpty()) {
                    firstRootnode = s1.peek();
                    otherRootnode = s2.peek();

                    // If current keys in two trees are same
                    if (firstRootnode.i.equals(otherRootnode.i)) {
                        System.out.println(firstRootnode.i + " ");
                        if (firstRootnode.instance - otherRootnode.instance > 0) {
                            BstNode newBstNode = new BstNode(firstRootnode.i);
                            newBstNode.instance = firstRootnode.instance - otherRootnode.instance;
                            newBstMultiset.add(newBstNode);
                        }
                        s1.pop();
                        s2.pop();

                        // move to the inorder successor
                        firstRootnode = firstRootnode.rightNode;
                        otherRootnode = otherRootnode.rightNode;
                    } else {
                        // If Node of first tree is smaller, than that of
                        // second tree, then its obvious that the inorder
                        // successors of current Node can have same value
                        // as that of the second tree Node. Thus, we pop
                        // from s2
                        System.out.println("first else if : " + firstRootnode.i + " " + otherRootnode.i);
                        BstNode newBstNode = new BstNode(firstRootnode.i);
                        newBstNode.instance = firstRootnode.instance;
                        newBstMultiset.add(newBstNode);
                        s1.pop();
                        firstRootnode = firstRootnode.rightNode;

                        // root2 is set to NULL, because we need
                        // new Nodes of tree 1
                        otherRootnode = null;
                    }
                }

                // Both roots and both stacks are empty
                else break;
            }
            // Placeholder, please update.
            return newBstMultiset;
        }
        // Placeholder, please update.
        return null;
    } // end of difference()

    @Override
    protected void add(Node node) {

    }

} // end of class BstMultiset
