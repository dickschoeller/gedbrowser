package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

import lombok.RequiredArgsConstructor;

/**
 * Renders tree table output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public final class TreeTableRenderer {
    /** */
    private final transient PersonRenderer top;
    /** */
    private final transient int generations;

    /**
     * Gets the tree rows.
     *
     * @return the tree rows
     */
    public CellRow[] getTreeRows() {
        final TreeNode<PersonRenderer> treeNode = buildTree(top, 0, generations - 1, 0);
        final CellRow[] treeCellRenderers = new CellRow[treeNode.getHighestRowInTree() + 1];
        for (int i = 0; i < treeCellRenderers.length; i++) {
            treeCellRenderers[i] = createCellRow();
        }
        addToTable(treeCellRenderers, treeNode);
        return treeCellRenderers;
    }

    private CellRow createCellRow() {
        return new CellRow(generations * 2 - 1);
    }

    /**
     * Builds the tree.
     *
     * @param personRenderer the person renderer
     * @param currentDepth the current depth
     * @param depthLimit the depth limit
     * @param seedRow the seed row
     * @return the resulting tree node
     */
    public static TreeNode<PersonRenderer> buildTree(
            final PersonRenderer personRenderer, final int currentDepth,
            final int depthLimit, final int seedRow) {
        final Person person = personRenderer.getGedObject();
        final PersonNavigator navigator = new PersonNavigator(person);

        final TreeNode<PersonRenderer> treeNode = new TreeNode<PersonRenderer>(
            personRenderer, currentDepth, currentDepth);

        int row = seedRow;
        if (currentDepth < depthLimit) {
            final Person father = navigator.getFather();
            final PersonRenderer fatherRenderer =
                (PersonRenderer) personRenderer.createGedRenderer(father);
            final TreeNode<PersonRenderer> fatherTreeNode = buildTree(
                fatherRenderer, currentDepth + 1, depthLimit, row);
            treeNode.addLeft(fatherTreeNode);
            row = fatherTreeNode.getHighestRowInTree() + 1;
        }

        treeNode.setRow(row++);

        if (currentDepth < depthLimit) {
            final Person mother = navigator.getMother();
            final PersonRenderer motherRenderer =
                (PersonRenderer) personRenderer.createGedRenderer(mother);
            final TreeNode<PersonRenderer> motherTreeNode =
                buildTree(motherRenderer, currentDepth + 1, depthLimit, row);
            treeNode.addRight(motherTreeNode);
        }
        return treeNode;
    }

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

    private LineCellRenderer createVerticalLineCell() {
        return new LineCellRenderer("v");
    }
}
