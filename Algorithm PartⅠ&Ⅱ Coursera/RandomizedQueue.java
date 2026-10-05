import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] RQueue;
    private int size;

    private void resize(int capacity) {
        Item[] tempArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            tempArray[i] = RQueue[i];
        }
        RQueue = tempArray;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        RQueue = (Item[]) new Object[2];
        size = 0;
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
        if (size == RQueue.length) resize(RQueue.length * 2);
        RQueue[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        int index = StdRandom.uniformInt(0, size);
        Item result = RQueue[index];
        RQueue[index] = RQueue[size - 1];
        RQueue[size - 1] = null;
        size--;
        if (size > 0 && size <= RQueue.length / 4) {
            resize(RQueue.length / 2);
        }
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        return RQueue[StdRandom.uniformInt(0, size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] copy;
        private int cur;

        public RandomizedQueueIterator() {
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copy[i] = RQueue[i];
            }
            StdRandom.shuffle(copy);
            cur = 0;
        }

        public boolean hasNext() {
            return cur < copy.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no next item to return");
            }
            else {
                return copy[cur++];
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("the 'remove' is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Test programs written by ai

        // 1. 测试构造函数
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        // 2. 测试 isEmpty() 和 size()
        System.out.println("Initially:");
        System.out.println("isEmpty = " + queue.isEmpty());
        System.out.println("size = " + queue.size());

        // 3. 测试 enqueue()
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);
        queue.enqueue(50);

        System.out.println("\nAfter enqueue:");
        System.out.println("isEmpty = " + queue.isEmpty());
        System.out.println("size = " + queue.size());

        // 4. 测试 sample()
        // sample 不应该改变 size
        System.out.println("\nSample several times:");
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());

        System.out.println("size after sample = " + queue.size());

        // 5. 测试 iterator()
        System.out.println("\nIterator 1:");
        Iterator<Integer> it1 = queue.iterator();

        while (it1.hasNext()) {
            System.out.print(it1.next() + " ");
        }
        System.out.println();

        // 再创建一个 iterator
        // 应该独立，并且通常具有不同随机顺序
        System.out.println("Iterator 2:");
        Iterator<Integer> it2 = queue.iterator();

        while (it2.hasNext()) {
            System.out.print(it2.next() + " ");
        }
        System.out.println();

        // 6. 测试 dequeue()
        System.out.println("\nRandom dequeue:");

        while (!queue.isEmpty()) {
            int value = queue.dequeue();

            System.out.println(
                    "removed = " + value
                            + ", size = " + queue.size()
            );
        }

        System.out.println("isEmpty = " + queue.isEmpty());

        // 7. 测试空队列 dequeue() 异常
        try {
            queue.dequeue();
            System.out.println("ERROR: dequeue should throw exception");
        }
        catch (NoSuchElementException e) {
            System.out.println(
                    "\nCorrectly caught dequeue exception: "
                            + e.getMessage()
            );
        }

        // 8. 测试空队列 sample() 异常
        try {
            queue.sample();
            System.out.println("ERROR: sample should throw exception");
        }
        catch (NoSuchElementException e) {
            System.out.println(
                    "Correctly caught sample exception: "
                            + e.getMessage()
            );
        }

        // 9. 测试 enqueue(null)
        try {
            queue.enqueue(null);
            System.out.println("ERROR: enqueue(null) should throw exception");
        }
        catch (IllegalArgumentException e) {
            System.out.println(
                    "Correctly caught null exception: "
                            + e.getMessage()
            );
        }

        // 10. 测试 iterator.next() 越界
        Iterator<Integer> emptyIterator = queue.iterator();

        try {
            emptyIterator.next();
            System.out.println("ERROR: next() should throw exception");
        }
        catch (NoSuchElementException e) {
            System.out.println(
                    "Correctly caught next exception: "
                            + e.getMessage()
            );
        }

        // 11. 测试 iterator.remove()
        Iterator<Integer> removeIterator = queue.iterator();

        try {
            removeIterator.remove();
            System.out.println("ERROR: remove() should throw exception");
        }
        catch (UnsupportedOperationException e) {
            System.out.println(
                    "Correctly caught remove exception: "
                            + e.getMessage()
            );
        }

        // 12. 大量 enqueue/dequeue，顺便测试扩容和缩容
        System.out.println("\nResize test:");

        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
        }

        System.out.println("size after 100 enqueue = " + queue.size());

        for (int i = 0; i < 95; i++) {
            queue.dequeue();
        }

        System.out.println("size after 95 dequeue = " + queue.size());

        System.out.println("\nRemaining elements:");
        for (Integer x : queue) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

}
