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

    //objects of class ArrayElement will be used to store attributes of ArrayMultiset
    static class ArrayElement {
        String element;
        int instance;

        //constructor
        ArrayElement(String elem) {
            element = elem;
            this.instance = 1;
        }
    } // end of class ArrayElement


    @Override
    public void add(String elem) {
        boolean found;
        //if array is null, add element to its 0th position
        if(array==null){
            ArrayElement arrayelem = new ArrayElement(elem);
            array = new ArrayElement[1];
            array[0] = arrayelem;
        } else { //array is not null
            found = false;
            //if element is already present in array, increment its instance count only
            for (ArrayElement arrayElement : array) {
                if (arrayElement.element.compareTo(elem) == 0) {
                    arrayElement.instance += 1;
                    found = true;
                }
            }
            //add element to array, if it is not already present
            if (!found) {
                ArrayElement newArray[] = new ArrayElement[array.length+1];
                for (int i = 0; i < array.length; i++) {
                    newArray[i] = array[i];
                }
                // new entry, add to end of newArray
                newArray[newArray.length-1] = new ArrayElement(elem);
                array = newArray;
            }
        }
    } // end of add()

    /**
     * Method to add object of ArrayElement to array
     */
    public void add(ArrayElement arrayElement){
        if (array == null) {
            array = new ArrayElement[1];
            array[0] = arrayElement;
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
        boolean found = false;
        int instances = 0;

        //search elem in whole array, if found set boolean found to true and return number of instances.
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

        //search all elements in array with required number of instances
        // and if is found then add it to list and return
        if(array!=null) {
            for (ArrayElement arrayElement : array) {
                if (arrayElement.instance == instanceCount) {
                    elementList.add(arrayElement.element);
                }
            }
        }
        return elementList;
    } // end of searchByInstance


    @Override
    public boolean contains(String elem) {
        if(array!=null) {
            //look for the required element in whole array and if found return true
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
                //if element is present decrement its number of instance by one
                if (array[i].element.equals(elem)){
                    found = true;
                    array[i].instance -=1;
                    instance = array[i].instance;
                    break;
                }
            }

            //if the element is found and after above decrement operation its instance became zero
            //then remove that element from array
            if (found && instance == 0) {
                ArrayElement newArray[] = new ArrayElement[array.length - 1];
                for (j = 0; j < i; j++) {
                    newArray[j] = array[j];
                }
                for (int k = j + 1; k < array.length; k++) {
                    newArray[j] = array[k];
                    j++;
                }
                array = newArray;
            }
        }
    } // end of removeOne()


    @Override
    public String print() {
        StringBuilder arrayOutput = new StringBuilder();
        if (array != null) {
            //sorting array in descending order of their number of instances
            sortedArray(array);
            //store all the element in required pattern into string builder and return
            for (ArrayElement arrayElement : array) {
                arrayOutput.append(arrayElement.element).append(":").append(arrayElement.instance).append("\n");
            }
        }
        return arrayOutput.toString();
    } // end of OrderedPrint

    /**
     * Method to sort array in descending order of their number of instances
     * Early-termination bubble sorting is used.
     */
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
                //element within the given range including lower and upper are stored in string builder and returned
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
            //newArray is created to store union of both the arrays
            ArrayMultiset newArray = new ArrayMultiset();

            //all elements of first array are store in third array
            for (ArrayElement arrayElement : array) {
                ArrayElement newArrayElement = new ArrayElement(arrayElement.element);
                newArrayElement.instance = arrayElement.instance;
                newArray.add(newArrayElement);
            }

            //array of 'other' i.e. second Multiset is stored in otherArray
            ArrayElement otherArray[] = ((ArrayMultiset)other).array;

            //if element in otherArray is already in newArray then update only number of instances in newArray
            //otherwise add that element in newArray
            for (int i = 0; i < otherArray.length; i++) {
                boolean found = false;
                for (int j = 0; j < newArray.array.length; j++) {
                    if (otherArray[i].element.equals(newArray.array[j].element)) {
                        found = true;
                        newArray.array[j].instance += otherArray[i].instance;
                        break;
                    }
                }
                if (!found) {
                    newArray.add(otherArray[i]);
                }
            }

            return newArray;
        }
        return null;
    } // end of union()


    @Override
    public RmitMultiset intersect(RmitMultiset other) {
        if (other instanceof ArrayMultiset) {
            ArrayMultiset newArray = new ArrayMultiset();
            ArrayElement otherArray[] = ((ArrayMultiset) other).array;
            for (ArrayElement arrayElement : array) {
                for (ArrayElement element : otherArray) {
                    if (arrayElement.element.equals(element.element)) {
                        //if element is present in both arrays, then add that element in newArray
                        //with its lowest number of instances
                        if (arrayElement.instance < element.instance) {
                            newArray.add(arrayElement);
                        } else {
                            newArray.add(element);
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
            int j = 0;
            for (int i = 0; i < array.length; i++) {
                boolean found = false;
                for (j = 0; j < otherArray.length; j++) {
                    if (array[i].element.equals(otherArray[j].element)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    //if element is present in both arrays and number of instances of that element in first array
                    // is greater than that of second array, then store the element in newArray with difference
                    if (array[i].instance - otherArray[j].instance > 0) {
                        ArrayElement newArrayElement = new ArrayElement(array[i].element);
                        newArrayElement.instance = array[i].instance - otherArray[j].instance;
                        newArray.add(newArrayElement);
                    }
                }
                else {
                    //if array element from first array is not present in second array, add the element as it is
                    //in newArray
                    newArray.add(array[i]);
                }
            }
            return newArray;
        }
        return null;
    } // end of difference()

    //method is not required in this Multiset
    @Override
    public void add(Node node) {

    }

} // end of class ArrayMultiset
