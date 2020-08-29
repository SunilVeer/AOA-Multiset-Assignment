package implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Array implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class ArrayMultiset extends RmitMultiset
{

    protected ArrayElement array[]= null;

    static class ArrayElement {
        String element;
        int instance;

        ArrayElement(String elem) {
            element = elem;
            this.instance = 1;
        }
    }


    @Override
    public void add(String elem) {
        // Implement me!
        boolean found;
        //int instance = 0;
        if(array==null){
            ArrayElement arrayelem = new ArrayElement(elem);
            array = new ArrayElement[1];
            array[0] = arrayelem;
            for (int i = 0; i < array.length; i++){
                System.out.println("array is: " + array[i].element);
            }
        } else {
            found = false;
            for (ArrayElement arrayElement : array) {
                System.out.println("for ele: " + arrayElement.element);
                if (arrayElement.element.compareTo(elem) == 0) {
                    arrayElement.instance += 1;
                    System.out.println("inst : " + arrayElement.instance);
                    found = true;
                }
                for (int i2 = 0; i2 < array.length; i2++) {
                    System.out.println("array else is: " + array[i2].element);
                }
            }
            if (!found) {
                ArrayElement newArray[] = new ArrayElement[array.length+1];
                for (int i = 0; i < array.length; i++) {
                    newArray[i] = array[i];
                }
                // new entry, add to end of newArray
                newArray[newArray.length-1] = new ArrayElement(elem);
                array = newArray;
                for (ArrayElement arrayElement : array) {
                    System.out.println("array if found is: " + arrayElement.element);
                }
            }
        }
    } // end of add()

    public void add(ArrayElement arrayElement){
        if (array == null) {
            //ArrayElement arrayelem = new ArrayElement(elem);
            array = new ArrayElement[1];
            array[0] = arrayElement;
            for (int i = 0; i < array.length; i++){
                System.out.println("array is: " + array[i].element);
            }
        }
        else {
            ArrayElement newArray[] = new ArrayElement[array.length+1];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            // new entry, add to end of newArray
            newArray[newArray.length-1] = arrayElement;
            array = newArray;
        }
    }

    @Override
    public int search(String elem) {
        // Implement me!
        boolean found = false;
        int instances = 0;

        if(array!=null) {
            for (ArrayElement arrayElement : array) {
                if (arrayElement.element.equals(elem)) {
                    instances = arrayElement.instance;
                    found = true;
                }
            }
        }

        if (found)
            return instances;
        else
            return -1;
    } // end of search()


    @Override
    public List<String> searchByInstance(int instanceCount) {

        List<String> elementList = new ArrayList<String>();

        if(array!=null) {
            for (ArrayElement arrayElement : array) {
                //System.out.println("i: " + i);
                if (arrayElement.instance == instanceCount) {
                    elementList.add(arrayElement.element);
                }
            }
        }
        return elementList;
    } // end of searchByInstance


    @Override
    public boolean contains(String elem) {
        // Implement me!
        if(array!=null) {
            for (ArrayElement arrayElement : array) {
                if (arrayElement.element.equals(elem)) {
                    return true;
                }
            }
        }
        return false;
    } // end of contains()


    @Override
    public void removeOne(String elem) {
        int instance = 0;
        if(array!=null) {
            int i = 0;
            int j = 0;
            boolean found = false;
            for (i = 0; i < array.length; i++, i++) {
                if (array[i].element.equals(elem)){
                    found = true;
                    array[i].instance -=1;
                    instance = array[i].instance;
                    break;
                }
            }
            if (found && instance == 0) {
                System.out.println("i is: " + i);
                ArrayElement newArray[] = new ArrayElement[array.length - 1];
                for (j = 0; j < i; j++) {
                    System.out.println("array[j] is: " + array[j].element);
                    newArray[j] = array[j];
                }
                for (int k = j + 1; k < array.length; k++) {
                    System.out.println("array[k] is: " + array[k].element);
                    newArray[j] = array[k];
                    j++;
                }
                array = newArray;
                for (i = 0; i < array.length; i++) {
                    System.out.println("new array is: " + array[i].element);
                }
            }
        }
    } // end of removeOne()


    @Override
    public String print() {
        StringBuilder arrayOutput = new StringBuilder();
        if (array != null) {
            sortedArray(array);
            for (ArrayElement arrayElement : array) {
                arrayOutput.append(arrayElement.element).append(":").append(arrayElement.instance).append("\n");
            }
        }
        return arrayOutput.toString();
    } // end of OrderedPrint

    private void sortedArray(ArrayElement[] array) {
        int swapCounter = 0;
        ArrayElement temp;
        for (int i = 0; i <= array.length-2; i++) {
            swapCounter = 0;
            for (int j = 0; j <= array.length-2-i; j++) {
                if (array[j+1].instance > array[j].instance){
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    swapCounter++;
                }
            }
            if (swapCounter == 0)
                break;
        }
    }


    @Override
    public String printRange(String lower, String upper) {
        StringBuilder arrayOutput = new StringBuilder();

        if (array != null) {
            for (ArrayElement arrayElement : array) {
                if (lower.compareTo(arrayElement.element) <= 0 && upper.compareTo(arrayElement.element) >= 0) {
                    arrayOutput.append(arrayElement.element).append(":").append(arrayElement.instance).append("\n");
                }
            }
        }

        return arrayOutput.toString();
    } // end of printRange()


    @Override
    public RmitMultiset union(RmitMultiset other) {
        if (other instanceof ArrayMultiset){
            ArrayMultiset newArray = new ArrayMultiset();
            newArray.array = array;

            ArrayElement array1[] = ((ArrayMultiset)other).array;

            for (ArrayElement arrayElement : array1) {
                System.out.println("array1: " + arrayElement.element);
            }
            for (int i = 0; i < array1.length; i++) {
                boolean found = false;
                for (int j = 0; j < newArray.array.length; j++) {
                    if (array1[i].element.equals(newArray.array[j].element)) {
                        found = true;
                        newArray.array[j].instance += array1[i].instance;
                        break;
                    }
                }
                if (!found) {
                    newArray.add(array1[i]);
                }
                for (int j = 0; j < newArray.array.length; j++) {
                    System.out.println("newest array: " + newArray.array[j]);
                }
            }

            return newArray;
        }
        // Placeholder, please update
        return null;
    } // end of union()


    @Override
    public RmitMultiset intersect(RmitMultiset other) {
        if (other instanceof ArrayMultiset) {
            ArrayMultiset newArray = new ArrayMultiset();
            ArrayElement otherArray[] = ((ArrayMultiset) other).array;
            System.out.println("arr1 l: " + otherArray.length);
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < otherArray.length; j++) {
                    if (array[i].element.equals(otherArray[j].element)) {
                        if (array[i].instance < otherArray[j].instance) {
                            newArray.add(array[i]);
                        } else {
                            newArray.add(otherArray[j]);
                        }
                    }
                }
            }
            return newArray;
        }
        return null;
    } // end of intersect()


    @Override
    public RmitMultiset difference(RmitMultiset other) {
        if (other instanceof ArrayMultiset){
            ArrayMultiset newArray = new ArrayMultiset();
            ArrayElement otherArray[] = ((ArrayMultiset)other).array;
            System.out.println("arr1 l: " + otherArray.length);
            int j = 0;
            for (int i = 0; i < array.length; i++) {
                boolean found = false;
                for (j = 0; j < otherArray.length; j++) {
                    System.out.println("other ele: " + otherArray[j]);
                    if (array[i].element.equals(otherArray[j].element)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    if (array[i].instance - otherArray[j].instance > 0) {
                        ArrayElement newArrayElement = new ArrayElement(array[i].element);
                        newArrayElement.instance = array[i].instance - otherArray[j].instance;
                        newArray.add(newArrayElement);
                    }
                }
                else {
                    newArray.add(array[i]);
                }
            }
            for (j = 0; j < newArray.array.length; j++) {
                System.out.println("newest array: " + newArray.array[j]);
            }

            return newArray;
        }
        return null;
    } // end of difference()

    @Override
    protected void add(Node node) {

    }

} // end of class ArrayMultiset
