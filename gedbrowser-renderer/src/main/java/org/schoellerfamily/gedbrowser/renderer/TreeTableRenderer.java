package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public final class TreeTableRenderer {
    /** */
    private final transient int generations;

    /** */
    private final transient PersonRenderer top;

    /**
     * @param top the main person renderer that this tree is for.
     * @param generations number of tree generations to render.
     */
    public TreeTableRenderer(final PersonRenderer top, final int generations) {
        this.top = top;
        this.generations = generations;
    }

    /**
     * @return the 2D array of cells.
     */
    public CellRow[] getTreeRows() {
        CellRow[] treeCellRenderers;
        final TreeNode<PersonRenderer> treeNode = buildTree(top, 0,
                generations - 1, 0);
        treeCellRenderers = new CellRow[treeNode.getHighestRowInTree() + 1];
        for (int i = 0; i < treeCellRenderers.length; i++) {
            treeCellRenderers[i] = createCellRow();
        }
        addToTable(treeCellRenderers, treeNode);
        return treeCellRenderers;
    }

    /**
     * @return a new cell row.
     */
    private CellRow createCellRow() {
        return new CellRow(generations * 2 - 1);
    }

    /**
     * @param personRenderer the person renderer associated with the top node.
     * @param currentDepth 0 based depth into the tree.
     * @param depthLimit highest depth allowed.
     * @param seedRow initial row.
     * @return list ordered from top to bottom of the entries in the tree.
     */
    public static TreeNode<PersonRenderer> buildTree(
            final PersonRenderer personRenderer, final int currentDepth,
            final int depthLimit, final int seedRow) {
        final Person person = personRenderer.getGedObject();
        final TreeNode<PersonRenderer> treeNode = new TreeNode<PersonRenderer>(
                personRenderer, currentDepth, currentDepth);

        int row = seedRow;
        if (currentDepth < depthLimit) {
            final Person father = person.getFather();
            final PersonRenderer fatherRenderer =
                    (PersonRenderer) personRenderer.createGedRenderer(father);
            final TreeNode<PersonRenderer> fatherTreeNode = buildTree(
                    fatherRenderer, currentDepth + 1, depthLimit, row);
            treeNode.addLeft(fatherTreeNode);
            row = fatherTreeNode.getHighestRowInTree() + 1;
        }

        treeNode.setRow(row++);

        if (currentDepth < depthLimit) {
            final Person mother = person.getMother();
            final PersonRenderer motherRenderer =
                    (PersonRenderer) personRenderer.createGedRenderer(mother);
            final TreeNode<PersonRenderer> motherTreeNode = buildTree(
                    motherRenderer, currentDepth + 1, depthLimit, row);
            treeNode.addRight(motherTreeNode);
        }
        return treeNode;
    }

    /**
     * @param cellRows the table that we are working with.
     * @param treeNode the tree whose data we are adding.
     */
    private void addToTable(final CellRow[] cellRows,
            final TreeNode<PersonRenderer> treeNode) {
        final int column = treeNode.getColumn() * 2;
        final int row = treeNode.getRow();
        cellRows[row].set(column, new NodeCellRenderer(treeNode.getNode()));

        final TreeNode<PersonRenderer> left = treeNode.getLeft();
        final TreeNode<PersonRenderer> right = treeNode.getRight();

        if (left != null || right != null) {
            cellRows[row].set(column + 1, new LineCellRenderer("t"));
        }

        if (left != null) {
            addToTable(cellRows, left);
            final int leftRow = left.getRow();
            for (int i = leftRow + 1; i < row; i++) {
                cellRows[i].set(column + 1, createVerticalLineCell());
            }
            cellRows[leftRow].set(column + 1, new LineCellRenderer("u"));
        }

        if (right != null) {
            addToTable(cellRows, right);
            final int rightRow = right.getRow();
            for (int i = row + 1; i < rightRow; i++) {
                cellRows[i].set(column + 1, createVerticalLineCell());
            }
            cellRows[rightRow].set(column + 1, new LineCellRenderer("d"));
        }
    }

    /**
     * @return a cell with the class set for a vertical line.
     */
    private LineCellRenderer createVerticalLineCell() {
        return new LineCellRenderer("v");
    }
}
