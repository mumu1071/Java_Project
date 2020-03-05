package arithmetic;

import java.util.Stack;

/**
 * 二叉树
 */
public class BinaryTree {

    /**
     * 叶子节点
     */
    class TreeNode {
        public String data = null; //数据域
        public TreeNode leftChild = null;
        public TreeNode rightChild = null;
        public int key = 0;//层级

        public TreeNode(int key, String data) {
            this.data = data;
            this.key = key;
        }
    }


    private TreeNode root = null;


    public BinaryTree() {
        this.root = new TreeNode(1, "A");
    }


    /**
     * A
     * B       C
     * D     E        F
     *
     * @param root
     */

    public void createBinaryTree(TreeNode root) {
        TreeNode B = new TreeNode(2, "B");
        TreeNode C = new TreeNode(2, "C");
        TreeNode D = new TreeNode(3, "D");
        TreeNode E = new TreeNode(3, "E");
        TreeNode F = new TreeNode(3, "F");
        root.leftChild = B;
        root.rightChild = C;
        B.leftChild = D;
        B.rightChild = E;
        C.rightChild = F;
    }

    public void visit(TreeNode node) {
        if (node != null) {
            System.out.print(node.data);
        }
    }

    public int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int leftHeight = getHeight(node.leftChild);
            int rightHeight = getHeight(node.rightChild);
            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            }
            return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
        }
    }

    //前序遍历
    public void preOrder(TreeNode node) {
        if (node != null) {
            visit(node);
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
    }

    //中序遍历
    public void inOrder(TreeNode node) {
        if (node != null) {
            inOrder(node.leftChild);
            visit(node);
            inOrder(node.rightChild);
        }
    }


    //后续遍历
    public void postOrder(TreeNode node) {
        if (node != null) {
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            visit(node);
        }
    }

    //先序遍历-非递归实现

    public void nonRecPreOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            visit(node);
            //注意是right元素先进栈
            if (node.rightChild != null) {
                stack.add(node.rightChild);
            }
            if (node.leftChild != null) {
                stack.add(node.leftChild);
            }
        }
    }


    //中序遍历-非递归实现

    public void nonRecInOrder(TreeNode node) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (node != null || stack.size() > 0) {
            //存在左子树
            while (node != null) {
                stack.push(node);
                node = node.leftChild;
            }
            //栈非空
            if (stack.size() > 0) {
                node = stack.pop();
                visit(node);
                node = node.rightChild;
            }
        }
    }

    //后序遍历的非递归实现

    public void noRecPostOrder(TreeNode p) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode node = p;
        while (p != null) {
            //左子树入栈
            for (; p.leftChild != null; p = p.leftChild) {
                stack.push(p);
            }
            //当前结点无右子树或右子树已经输出
            while (p != null && (p.rightChild == null || p.rightChild == node)) {
                visit(p);
                //纪录上一个已输出结点
                node = p;
                if (stack.empty()) {
                    return;
                }
                p = stack.pop();
            }
            //处理右子树
            stack.push(p);
            p = p.rightChild;
        }
    }


    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.createBinaryTree(binaryTree.root);

        System.out.println("*******(前序遍历)[ABDECF]遍历*****************");
        binaryTree.preOrder(binaryTree.root);
        System.out.println("");

        System.out.println("*******(中序遍历)[DBEACF]遍历*****************");
        binaryTree.inOrder(binaryTree.root);
        System.out.println("");

        System.out.println("*******(后序遍历)[DEBFCA]遍历*****************");
        binaryTree.postOrder(binaryTree.root);
        System.out.println("");

        System.out.println("***非递归实现****(前序遍历)[ABDECF]遍历*****************");
        binaryTree.nonRecPreOrder(binaryTree.root);
        System.out.println("");

        System.out.println("***非递归实现****(中序遍历)[DBEACF]遍历*****************");
        binaryTree.nonRecInOrder(binaryTree.root);
        System.out.println("");

        System.out.println("***非递归实现****(后序遍历)[DEBFCA]遍历*****************");
        binaryTree.noRecPostOrder(binaryTree.root);
        System.out.println("");
    }


}
