public class SelectorList<T> extends CircularLinkedList<T> {
    Node currentNode;

    public Node getCurrentNode() {
        return currentNode;
    }

    public Node selectNextNode() {
        if(currentNode != null) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public SelectorList() {
        super();
        currentNode = head;
    }
}
