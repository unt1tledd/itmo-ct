package queue;

public class MyArrayQueueTest {
    public static void fill(ArrayQueue<String> queue, String prefix) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(prefix + i);
        }
    }

    public static void dump(ArrayQueue<String> queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.dequeue());
        }
    }

    public static void main(String[] args) {
        ArrayQueue<String> queue1 = new ArrayQueue<>();
        ArrayQueue<String> queue2 = new ArrayQueue<>();
        fill(queue1, "q1_");
        fill(queue2, "q2_");
        dump(queue1);
        dump(queue2);
    }
}
