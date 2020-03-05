package arithmetic;


/**
 * 链表找环
 */
public class JudgeCycle {

    class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }


    public Node isLoop(Node root) {
        //定义两个指针，分别是快和慢
        Node fast = root, slow = root;
        //遍历一遍链表，如果最后是null的话就代表没有环
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            //如果俩相遇了，代表有环
            if (fast == slow) {
                return fast;
            }
        }
        return null;
    }


    public Node getIntersectEntry(Node root) {
        Node meet = isLoop(root);
        if (meet == null) {
            return null;
        }
        //相遇的地方 到 环扣，和从开始  到环扣 距离一致
        Node p = meet;
        Node q = root;
        while (p != q) {
            p = p.next;
            q = q.next;
        }
        return p;
    }

    public Node initLinkedList() {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(2);
        Node n4 = new Node(3);
        Node n5 = new Node(6);
        Node n6 = new Node(6);
        Node n7 = new Node(9);
        Node n8 = new Node(10);
        Node n9 = new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;
        n8.next = n9;
        n9.next = n4;
        return n1;
    }


    public static void main(String[] args) {

        JudgeCycle judgeCycle = new JudgeCycle();
        Node root = judgeCycle.initLinkedList();
        Node result = judgeCycle.isLoop(root);
        result = judgeCycle.getIntersectEntry(root);
        System.out.println(result.val);
    }
}