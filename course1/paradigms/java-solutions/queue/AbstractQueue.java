package queue;

import java.util.Objects;

public abstract class AbstractQueue<T> implements Queue<T> {
    protected int size;
    protected int head = 0;
    protected int tail = 0;

    @Override
    public void enqueue(T element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        tail = (tail + 1) % getLength();
        size++;
    }

    protected abstract void enqueueImpl(T element);

    @Override
    public T dequeue() {
        assert !isEmpty() : "length queue < 0";
        T val = dequeueImpl();
        head = (head + 1) % getLength();
        size--;
        return val;
    }


    protected abstract T dequeueImpl();

    @Override
    public T element() {
        assert !isEmpty() : "length queue < 0";
        return elementImpl();
    }

    protected abstract T elementImpl();

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract int getLength();
}