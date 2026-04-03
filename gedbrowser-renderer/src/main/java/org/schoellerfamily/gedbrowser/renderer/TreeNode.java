package org.schoellerfamily.gedbrowser.renderer;

/**
 * Represents tree node.
 *
 * @author Richard Schoeller
 * @param <N> an object to contain in the tree.
 */
public final class TreeNode<N> {
    /** */
    private final N node;
    /** */
    private TreeNode<N> left;
    /** */
    private TreeNode<N> right;
    /** */
    private int row;
    /** */
    private int column;

    /**
     * Creates a new TreeNode.
     *
     * @param node the node
     * @param seedRow the seed row
     * @param column the column
     */
    public TreeNode(final N node, final int seedRow, final int column) {
        this.node = node;
        this.row = seedRow;
        this.column = column;
    }

    /**
     * Executes add right.
     *
     * @param rightNode the right node
     */
    public void addRight(final TreeNode<N> rightNode) {
        this.right = rightNode;
    }

    /**
     * Executes add left.
     *
     * @param leftNode the left node
     */
    public void addLeft(final TreeNode<N> leftNode) {
        this.left = leftNode;
    }

    /**
     * Sets the row.
     *
     * @param row the row
     */
    public void setRow(final int row) {
        this.row = row;
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the highest row in tree.
     *
     * @return the highest row in tree
     */
    public int getHighestRowInTree() {
        if (right == null) {
            return row;
        }
        return right.getHighestRowInTree();
    }

    /**
     * Sets the column.
     *
     * @param column the column
     */
    public void setColumn(final int column) {
        this.column = column;
    }

    /**
     * Gets the column.
     *
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets the node.
     *
     * @return the node
     */
    public N getNode() {
        return node;
    }

    /**
     * Gets the left.
     *
     * @return the left
     */
    public TreeNode<N> getLeft() {
        return left;
    }

    /**
     * Gets the right.
     *
     * @return the right
     */
    public TreeNode<N> getRight() {
        return right;
    }
}
