package queue;

import java.util.Objects;


// Model: a[1]...a[n]
// I: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public class ArrayQueue<T> extends AbstractQueue<T> {
    private static <T> T[] newQueue(int size) {
        @SuppressWarnings("unchecked")
        T[] queue = (T[]) new Object[size];
        return queue;
    }

    private T[] queue = newQueue(5);

    @Override
    protected void enqueueImpl(T element) {
        ensureCapacity();
        queue[tail] = element;
    }

    @Override
    protected T dequeueImpl() {
        T val = queue[head];
        queue[head] = null;
        return val;
    }

    @Override
    protected T elementImpl() {
        return queue[head];
    }

    @Override
    protected int getLength() {
        return queue.length;
    }

    // P: index < n && n > 0
    // Q: R = a[n] && immutable(n)
    public T get(int index) {
        assert !isEmpty() && index <= size: "length queue < 0";
        return queue[(tail - 1 - index + queue.length) % queue.length];
    }

    // P: index < n && n > 0
    // Q: a[n] = new elem && immutable(n)
    public void set(int index, T element) {
        assert !isEmpty() && index <= size: "length queue < 0";
        Objects.requireNonNull(element);
        queue[(tail - 1 - index + queue.length) % queue.length] = element;
    }

    // P: true
    // Q: n' = 0 && a = []
    public void clear() {
        size = head = tail = 0;
        queue = newQueue(5);
    }

    // P: true
    // Q: n' = n && immutable(n)
    private void ensureCapacity() {
        if (size == queue.length) {
            T[] newElements = newQueue(2 * (queue.length + 1));

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
    public String toStr() {
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