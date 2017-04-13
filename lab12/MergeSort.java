import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
        makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> sing = new Queue<>();
        int sized = items.size();
        for (int x = 0; x < sized; x++) {
            Item add = items.dequeue();
            Queue<Item> nqueue = new Queue<Item>();
            nqueue.enqueue(add);
            sing.enqueue(nqueue);
            items.enqueue(add);
        }
        return sing;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> comb = new Queue<>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            Item curr1 = q1.peek();
            Item curr2 = q2.peek();
            if (curr1.compareTo(curr2) <= 0) {
                q1.dequeue();
                comb.enqueue(curr1);
            } else {
                q2.dequeue();
                comb.enqueue(curr2);
            }
        }
        if (q1.isEmpty()) {
            while (!q2.isEmpty()) {
                Item add = q2.dequeue();
                comb.enqueue(add);
            }
        } else {
            while (!q1.isEmpty()) {
                Item add = q1.dequeue();
                comb.enqueue(add);
            }
        }
        return comb;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> sing = MergeSort.makeSingleItemQueues(items);
        Queue<Item> curr = sing.dequeue();
        while (!sing.isEmpty()) {
            Queue<Item> next = sing.dequeue();
            curr = MergeSort.mergeSortedQueues(next, curr);
        }
        return curr;
    }


    private static <Item extends Comparable> Queue<Item> makecopy(Queue<Item> item) {
        Queue<Item> copy = new Queue<>();
        Iterator<Item> iter = item.iterator();
        while (iter.hasNext()) {
            copy.enqueue(iter.next());
        }
        return copy;
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        System.out.println(students);
        Queue<String> nstudents = MergeSort.mergeSort(students);
        System.out.println(nstudents);

    }
}
