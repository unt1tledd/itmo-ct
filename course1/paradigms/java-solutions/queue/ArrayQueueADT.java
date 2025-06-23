package queue;

import java.util.Objects;

// Model: a[1]...a[n]
// I: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public class ArrayQueueADT<T> {
    private static <T> T[] newQueue(int size) {
        @SuppressWarnings("unchecked")
        T[] queue = (T[]) new Object[size];
        return queue;
    }

    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private T[] elements = newQueue(5);

    // Pre: true
    // Post: R.n = 0
    public static <T> ArrayQueueADT<T> create() {
        ArrayQueueADT<T> queue = new ArrayQueueADT<>();
        queue.elements = newQueue(5);
        return queue;
    }

    // P: index < n && n > 0
    // Q: R = a[n] && immutable(n)
    public static <T> T get(ArrayQueueADT<T> queueADT, int index) {
        assert !isEmpty(queueADT) && index <= queueADT.size: "length queue < 0";
        return queueADT.elements[(queueADT.tail - 1 - index + queueADT.elements.length) % queueADT.elements.length];
    }

    // P: index < n && n > 0
    // Q: a[n] = new elem && immutable(n)
    public static <T> void set(ArrayQueueADT<T> queueADT, int index, T element) {
        assert !isEmpty(queueADT) && index <= queueADT.size: "length queue < 0";
        Objects.requireNonNull(element);
        queueADT.elements[(queueADT.tail - 1 - index + queueADT.elements.length) % queueADT.elements.length] = element;
    }

    // P: element != null
    // Q: n' = n + 1 && a'[n'] = element && immutable(n)
    public static <T> void enqueue(ArrayQueueADT<T> queueADT, T element) {
        Objects.requireNonNull(element);
        ensureCapacity(queueADT);
        queueADT.elements[queueADT.tail] = element;
        queueADT.tail = (queueADT.tail + 1) % queueADT.elements.length;
        queueADT.size++;

    }

    // P: n > 0
    // Q: R = a[1] && n' = n - 1 && immutable(n')
    public static <T> T dequeue(ArrayQueueADT<T> queueADT) {
        assert !isEmpty(queueADT) : "length queue < 0";
        T val = queueADT.elements[queueADT.head];
        queueADT.elements[queueADT.head] = null;
        queueADT.head = (queueADT.head + 1) % queueADT.elements.length;
        queueADT.size--;
        return val;
    }


    // P: n > 0
    // Q: R = a[1] && immutable(n)
    public static <T> T element(ArrayQueueADT<T> queueADT) {
        assert !isEmpty(queueADT) : "length queue < 0";
        return queueADT.elements[queueADT.head];
    }

    // P: true
    // Q: R = n && immutable(n)
    public static <T> int size(ArrayQueueADT<T> queueADT) {
        return queueADT.size;
    }

    // P: true
    // Q: R = (n == 0) && immutable(n)
    public static <T> boolean isEmpty(ArrayQueueADT<T> queueADT) {
        return queueADT.size == 0;
    }

    // P: true
    // Q: n' = 0 && a = []
    public static <T> void clear(ArrayQueueADT<T> queueADT) {
        queueADT.size = queueADT.head = queueADT.tail = 0;
        queueADT.elements = newQueue(5);
    }

    // P: true
    // Q: n' = n && immutable(n)
    private static <T> void ensureCapacity(ArrayQueueADT<T> queueADT) {
        if (queueADT.size == queueADT.elements.length) {
            T[] newElements = newQueue(2 * (queueADT.elements.length + 1));

            if (queueADT.head < queueADT.tail) {
                System.arraycopy(queueADT.elements, queueADT.head, newElements, 0, queueADT.size);
            } else {
                System.arraycopy(queueADT.elements, queueADT.head, newElements, 0, queueADT.elements.length - queueADT.head);
                System.arraycopy(queueADT.elements, 0, newElements, queueADT.elements.length - queueADT.head, queueADT.tail);
            }

            queueADT.elements = newElements;
            queueADT.head = 0;
            queueADT.tail = queueADT.size;
        }
    }

    // P: true
    // Q: R = str(forall i in [0, n): a[i]' == a[i]) && n' == n
    public static <T> String toStr(ArrayQueueADT<T> queueADT) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < queueADT.size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(queueADT.elements[(queueADT.head + i) % queueADT.elements.length]);
        }
        sb.append("]");
        return sb.toString();
    }
}