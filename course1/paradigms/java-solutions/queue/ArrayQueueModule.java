package queue;

import java.util.Objects;

// Model: a[1]...a[n]
// I: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public class ArrayQueueModule {
    private static int head = 0;
    private static int tail = 0;
    private static int size = 0;
    private static Object[] queue = new Object[5];

    // P: element != null
    // Q: n' = n + 1 && a'[n'] = element && immutable(n)
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity();
        queue[tail] = element;
        tail = (tail + 1) % queue.length;
        size++;
    }

    // P: n > 0
    // Q: R = a[1] && immutable(n)
    public static Object get(int index) {
        assert !isEmpty() && index <= size: "length queue < 0";
        return queue[(tail - 1 - index + queue.length) % queue.length];
    }

    public static void set(int index, Object element) {
        assert !isEmpty() && index <= size: "length queue < 0";
        Objects.requireNonNull(element);
        queue[(tail - 1 - index + queue.length) % queue.length] = element;
    }

    // P: n > 0
    // Q: R = a[1] && n' = n - 1 && immutable(n')
    public static Object dequeue() {
        assert !isEmpty() : "length queue < 0";
        Object val = queue[head];
        queue[head] = null;
        head = (head + 1) % queue.length;
        size--;
        return val;
    }

    // P: n > 0
    // Q: R = a[1] && immutable(n)
    public static Object element() {
        assert !isEmpty() : "length queue < 0";
        return queue[head];
    }

    // P: true
    // Q: R = n && n' = n && immutable(n)
    public static int size() {
        return size;
    }

    // P: true
    // Q: R = (n == 0) && immutable(n)
    public static boolean isEmpty() {
        return size == 0;
    }

    // P: true
    // Q: n' = 0 && a = []
    public static void clear() {
        size = head = tail = 0;
        queue = new Object[5];
    }

    // P: true
    // Q: n' = n && immutable(n)
    private static void ensureCapacity() {
        if (size == queue.length) {
            Object[] newElements = new Object[2 * (queue.length + 1)];

            if (head < tail) {
                System.arraycopy(queue, head, newElements, 0, size);
            } else {
                System.arraycopy(queue, head, newElements, 0, queue.length - head);
                System.arraycopy(queue, 0, newElements, queue.length - head, tail);
            }

            queue = newElements;
            head = 0;
            tail = size;
        }
    }

    // P: true
    // Q: R = str(forall i in [0, n): a[i]' == a[i]) && n' == n
    public static String toStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(queue[(head + i) % queue.length]);
        }
        sb.append("]");
        return sb.toString();
    }
}