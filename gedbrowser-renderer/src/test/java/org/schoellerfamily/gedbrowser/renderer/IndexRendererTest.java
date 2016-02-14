package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public final class IndexRendererTest {

    /**
     * Prepare expectation string for letters in the index.
     *
     * @param letter the letter to link
     * @return the formatted string.
     */
    private String letterExpectString(final String letter) {
        return "<a href=\"surnames?db=null&amp;letter="
                + letter + "\" class=\"name\">[" + letter + "]</a>";
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testLetters() throws IOException {
        final String[] expects = {
                letterExpectString("F"),
                letterExpectString("H"),
                letterExpectString("K"),
                letterExpectString("L"),
                letterExpectString("R"),
                letterExpectString("S"),
        };
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S",
                RenderingContext.anonymous());
        final Collection<String> letters = ir.getLetters();
        int i = 0;
        for (final String letter : letters) {
            assertEquals(expects[i++], letter);
        }
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testSurnames() throws IOException {
        final String[] expects = {
                "Sacerdote",
                "Schoeller",
        };
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S",
                RenderingContext.anonymous());
        final Collection<String> surnames = ir.getSurnames();
        int i = 0;
        for (final String surname : surnames) {
            assertEquals(expects[i++], surname);
        }
    }

    /**
     * Prepare the expect string for index html test.
     *
     * @param id person's ID
     * @param surname person's surname
     * @param prefix person's given name
     * @param dateRange date range string
     * @return formatted string
     */
    private String indexExpectString(final String id, final String surname,
            final String prefix, final String dateRange) {
        return "<a href=\"person?db=null&amp;id="
                + id + "\" class=\"name\"> <span class=\"surname\">"
                + surname + "</span>, "
                + prefix + dateRange + " ("
                + id + ")</a>";
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIndexNameHtmlSchoeller() throws IOException {
        final String[] expects = {
                indexExpectString(
                        "I4", "Schoeller", "John Vincent", " (1934-)"),
                indexExpectString(
                        "I1", "Schoeller", "Melissa Robinson", ""),
                indexExpectString(
                        "I2", "Schoeller", "Richard John", " (1958-)"),
                indexExpectString(
                        "I5", "Schoeller", "Vivian Grace", " (1960-)"),
        };
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S",
                RenderingContext.anonymous());
        final Collection<String> indexNames = ir.getIndexNameHtmls("Schoeller");
        int i = 0;
        for (final String indexName : indexNames) {
            assertEquals(expects[i++], indexName);
        }
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIndexBase() throws IOException {
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S",
                RenderingContext.anonymous());
        assertEquals("S", ir.getBase());
    }
}
