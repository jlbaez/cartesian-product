/**
 * Cartesian
 * Created by jose on 4/17/16.
 * Last edited on 4/17/16
 */
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
public class CartesianProductTest
{
    private CartesianProduct c;

    @Before
    public void setup() {
        c = new CartesianProduct();
    }
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void assertProductCorrect1() throws UnclosedBracket {

        assertEquals("{b,c} test", "b c", c.getProduct("{b,c}"));
    }

    @Test
    public void assertProductCorrect2() throws UnclosedBracket {
        assertEquals("a{b,c}d{e,f,g}hi test", "abdehi acdehi abdfhi acdfhi abdghi acdghi", c.getProduct("a{b,c}d{e,f,g}hi"));
    }

    @Test
    public void assertProductCorrect3() throws UnclosedBracket {
        assertEquals("a{b,c{d,e,f}g,h}ij{k,l} test", "abijk acdgijk acegijk acfgijk ahijk abijl acdgijl acegijl acfgijl ahijl", c.getProduct("a{b,c{d,e,f}g,h}ij{k,l}"));
    }

    @Test
    public void assertProductCorrect4() throws UnclosedBracket {
        assertEquals("c{d,e,f}g test", "cdg ceg cfg", c.getProduct("c{d,e,f}g"));
    }

    @Test
    public void assertThrowsBracketError() throws Throwable{
        exception.expect(UnclosedBracket.class);
        c.getProduct("c{d,e,f");
    }
}
