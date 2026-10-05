import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] RQueue;
    private int size;
    private int current;

    private void autoLargeSmall() {
        if (current >= size / 2) {
            Item[] tempArray = (Item[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                tempArray[i] = RQueue[i];
            }
            RQueue = tempArray;
            size = size * 2;
        }
        else if (current <= size / 4) {
            Item[] tempArray = (Item[]) new Object[size / 2];
            for (int i = 0; i < size / 2; i++) {
                tempArray[i] = RQueue[i];
            }
            RQueue = tempArray;
            size = size / 2;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        Item[] RQueue = (Item[]) new Object[2];
        size = 0;
        current = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        RQueue[current++] = item;
        autoLargeSmall();
    }

    // remove and return a random item
    public Item dequeue() {
        int index = StdRandom.uniformInt(0, current + 1);
        Item result = RQueue[index];
        RQueue[index] = RQueue[current];
        RQueue[current--] = null;
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return RQueue[ StdRandom.uniformInt(0, current + 1) ];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()

    // unit testing (required)
    public static void main(String[] args)

}
