import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException(
                    "item cannot be null"
            );
        }

        Node<Item> newNode = new Node<>();
        newNode.item = item;

        if (isEmpty()) {

            first = newNode;
            last = newNode;
        }
        else {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }

        size++;
    }

    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException(
                    "item cannot be null"
            );
        }

        Node<Item> newNode = new Node<>();
        newNode.item = item;

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }

        size++;
    }

    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException(
                    "deque is empty"
            );
        }

        Item result = first.item;

        first = first.next;

        if (first == null) {

            last = null;
        }
        else {
            first.prev = null;
        }

        size--;

        return result;
    }

    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException(
                    "deque is empty"
            );
        }

        Item result = last.item;

        last = last.prev;

        if (last == null) {

            first = null;
        }
        else {
            last.next = null;
        }

        size--;

        return result;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current;

        public DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {

            if (current == null) {
                throw new NoSuchElementException(
                        "no more items"
                );
            }

            Item result = current.item;
            current = current.next;

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "remove() is not supported"
            );
        }
    }

    public static void main(String[] args) {


        Deque<Integer> deque = new Deque<>();

        System.out.println("Initially:");
        System.out.println("isEmpty = " + deque.isEmpty());
        System.out.println("size = " + deque.size());


        deque.addFirst(2);
        deque.addFirst(1);


        deque.addLast(3);
        deque.addLast(4);
        System.out.println();
        System.out.println("After adding 1, 2, 3, 4:");

        System.out.println("isEmpty = " + deque.isEmpty());
        System.out.println("size = " + deque.size());


        System.out.print("Iterator: ");

        Iterator<Integer> iterator = deque.iterator();

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        System.out.println();


        System.out.println();
        System.out.println(
                "removeFirst = " + deque.removeFirst()
        );


        System.out.println(
                "removeLast = " + deque.removeLast()
        );
        System.out.println("size = " + deque.size());
        System.out.print("Remaining: ");

        for (Integer value : deque) {
            System.out.print(value + " ");
        }

        System.out.println();


        System.out.println(
                "removeFirst = " + deque.removeFirst()
        );

        System.out.println(
                "removeLast = " + deque.removeLast()
        );

        System.out.println(
                "isEmpty = " + deque.isEmpty()
        );


        try {
            deque.removeFirst();
        }
        catch (NoSuchElementException e) {
            System.out.println();
            System.out.println(
                    "Caught removeFirst exception: "
                            + e.getMessage()
            );
        }


        try {
            deque.removeLast();
        }
        catch (NoSuchElementException e) {
            System.out.println(
                    "Caught removeLast exception: "
                            + e.getMessage()
            );
        }


        try {
            deque.addFirst(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println(
                    "Caught addFirst null exception: "
                            + e.getMessage()
            );
        }


        try {
            deque.addLast(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println(
                    "Caught addLast null exception: "
                            + e.getMessage()
            );
        }


        Iterator<Integer> emptyIterator = deque.iterator();

        try {
            emptyIterator.next();
        }
        catch (NoSuchElementException e) {
            System.out.println(
                    "Caught iterator next exception: "
                            + e.getMessage()
            );
        }


        Iterator<Integer> removeIterator = deque.iterator();

        try {
            removeIterator.remove();
        }
        catch (UnsupportedOperationException e) {
            System.out.println(
                    "Caught iterator remove exception: "
                            + e.getMessage()
            );
        }
    }
}
