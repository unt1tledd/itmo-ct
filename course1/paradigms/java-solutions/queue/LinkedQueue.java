package queue;

public class LinkedQueue<T> extends AbstractQueue<T> {
    private Node head;
    private Node tailNode;

    @Override
    protected void enqueueImpl(Object element) {
        Node newNode = new Node(element, null);
        if (tailNode == null) {
            head = newNode;
        } else {
            tailNode.next = newNode;
        }
        tailNode = newNode;
    }

    @Override
    protected T dequeueImpl() {
        T value = head.value;
        head = head.next;
        if (head == null) {
            tailNode = null;
        }
        return value;
    }

    @Override
    protected T elementImpl() {
        return head.value;
    }

    @Override
    protected int getLength() {
        return Integer.MAX_VALUE;
    }

    public void clear() {
        head = tailNode = null;
        size = 0;
    }

    private class Node {
        private final T value;
        private Node next;

        @SuppressWarnings("unchecked")
        public Node(Object value, Node next) {
            this.value = (T) value;
            this.next = next;
        }
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }

        sb.append("]");
        return sb.toString();
    }

}
