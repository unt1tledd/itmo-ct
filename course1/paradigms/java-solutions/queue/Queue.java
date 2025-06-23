package queue;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public interface Queue<T> {

    // P: element != null
    // Q: n' = n + 1 && a'[n'] = element && immutable(n)
    void enqueue(T element);

    // P: n > 0
    // Q: R = a[1] && immutable(n)
    T element();

    // P: n > 0
    // Q: R = a[1] && n' = n - 1 && immutable(n')
    T dequeue();

    // P: true
    // Q: R = n && n' = n && immutable(n)
    int size();

    // P: true
    // Q: n' = 0 && a = []
    boolean isEmpty();

}