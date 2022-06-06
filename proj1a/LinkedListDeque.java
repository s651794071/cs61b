import javax.swing.tree.TreeNode;

public class LinkedListDeque<T> {

    class Node{
        T item;
        Node prev;
        Node next;

        public Node(T item, Node prev, Node next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node sentinel;
    private int size;

    /**
     * Creates an empty linked list deque
     */
    public LinkedListDeque(){
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }


    /**
     * Create an entirely new LinkedListDeque, with the exact same items as other.
     * @param other
     */
    public LinkedListDeque(LinkedListDeque other) {
        size = other.size;

        Node node = other.sentinel.next; // 指着other里面的元素
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        if (node != other.sentinel) {
            Node curr = new Node(node.item, sentinel, sentinel); // 指着我们要构建的新链表
            node = node.next;
            sentinel.next = curr;
            while (node != other.sentinel) {
                Node prev = curr;
                curr = new Node(node.item, curr, sentinel);
                prev.next = curr;
                node = node.next;
            }
            sentinel.prev = curr;
        }

    }

    /**
     * Adds an item of type T to the front of the deque.
     * @param item
     */
    public void addFirst(T item) {
        Node originalFirst = sentinel.next; // 存一下当前的链表
        Node newFirst = new Node(item, sentinel, originalFirst); // 创建新链表，prev指向哨兵节点，next指向原来的链表

        sentinel.next = newFirst; // 更新哨兵节点next指向
        if (originalFirst != null) // 因为有可能原来就是空链表，这样调用.prev会空指针异常
            originalFirst.prev = newFirst; // 更新原来链表prev指向，原来指向哨兵节点，现在指向新链表
        else {
            newFirst.next = sentinel; // 此时这个节点是头节点也是尾节点，所以prev和next都指向哨兵
            sentinel.prev = newFirst;
            // 没报错但是这个bug找了好久 打印那边有问题 要在第一次addFirst的时候把哨兵节点的prev指向这个尾节点
            // 之后不用更新，因为添加的是头，尾节点不变
        }

        size++; // 维护size
    }

    /**
     * Adds an item of type T to the end of the deque.
     * @param item
     */
    public void addLast(T item) {
        Node originalLast = sentinel.prev;
        Node newLast = new Node(item, originalLast, sentinel);

        sentinel.prev = newLast;
        if (originalLast != null)
            originalLast.next = newLast;
        else {
            newLast.prev = sentinel;
            sentinel.next = newLast;
        }
        size++;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space
     */
    public void printDeque() {
        Node curr = sentinel.next;
        while (curr != sentinel) { // 不是null!
            System.out.print(curr.item + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    /**
     *  Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null.
     * @return
     */
    public T removeFirst() {
        if (sentinel.next == null)
            return null;

        Node removedNode = sentinel.next;
        Node node = removedNode.next;

        sentinel.next = node;
        node.prev = sentinel;
        size--;
        return removedNode.item;
    }

    /**
     *  Removes and returns the item at the end of the deque.
     *  If no such item exists, returns null.
     * @returnx
     */
    public T removeLast() {
        if (sentinel.prev == null)
            return null;

        Node removedNode = sentinel.prev;
        Node node = removedNode.prev;

        sentinel.prev = node;
        node.next = sentinel;
        size--;
        return removedNode.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     * @param index
     * @return
     */
    public T get(int index) {
        if (index < 0 || index >= size || sentinel.next == null)
            return null;
        Node curr = sentinel.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }

    /**
     * Same as get, but uses recursive
     * @param index
     * @return
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size || sentinel.next == null)
            return null;
        Node node = sentinel.next;
        return getRecursive(index, node);

    }

    /**
     * helper function of getRecursive
     * @param index
     * @param node
     * @return
     */
    private T getRecursive(int index, Node node) {
        if (index == 0)
            return node.item;
        index--;
        return getRecursive(index, node.next);
    }

}
