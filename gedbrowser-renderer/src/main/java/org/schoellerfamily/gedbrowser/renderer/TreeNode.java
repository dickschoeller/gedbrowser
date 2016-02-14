package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 *
 * @param <N> an object to contain in the tree.
 */
public final class TreeNode<N> {
    /** */
    private final transient N node;
    /** */
    private transient TreeNode<N> left;
    /** */
    private transient TreeNode<N> right;
    /** */
    private int row;
    /** */
    private int column;

    /**
     * @param node what ever we are holding in the tree
     * @param seedRow the original row, this will likely get overwritten.
     * @param column the column
     */
    public TreeNode(final N node, final int seedRow, final int column) {
        this.node = node;
        this.row = seedRow;
        this.column = column;
    }

    /**
     * @param rightNode the one on the right.
     */
    public void addRight(final TreeNode<N> rightNode) {
        this.right = rightNode;
    }

    /**
     * @param leftNode the one on the left.
     */
    public void addLeft(final TreeNode<N> leftNode) {
        this.left = leftNode;
    }

    /**
     * @param row the row.
     */
    public void setRow(final int row) {
        this.row = row;
    }

    /**
     * @return the row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return return the highest row number in the underlying tree.
     */
    public int getHighestRowInTree() {
        if (right == null) {
            return row;
        } else {
            return right.getHighestRowInTree();
        }
    }

    /**
     * @param column the column.
     */
    public void setColumn(final int column) {
        this.column = column;
    }

    /**
     * @return the column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return the contained node.
     */
    public N getNode() {
        return node;
    }

    /**
     * @return the node to the left.
     */
    public TreeNode<N> getLeft() {
        return left;
    }

    /**
     * @return the node to the right.
     */
    public TreeNode<N> getRight() {
        return right;
    }
}
