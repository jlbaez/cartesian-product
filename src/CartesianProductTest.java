/**
 * Cartesian
 * Created by jose on 4/17/16.
 * Last edited on 4/17/16
 */
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
public class CartesianProductTest
{
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void assertProductCorrect() throws UnclosedBracket {

        CartesianProduct c = new CartesianProduct();
        assertEquals("{b,c} test", "b c", c.getProduct("{b,c}"));
        assertEquals("a{b,c}d{e,f,g}hi test", "abdehi acdehi abdfhi acdfhi abdghi acdghi", c.getProduct("a{b,c}d{e,f,g}hi"));
        assertEquals("a{b,c{d,e,f}g,h}ij{k,l} test", "abijk acdgijk acegijk acfgijk ahijk abijl acdgijl acegijl acfgijl ahijl", c.getProduct("a{b,c{d,e,f}g,h}ij{k,l}"));
        assertEquals("c{d,e,f}g test", "cdg ceg cfg", c.getProduct("c{d,e,f}g"));
    }

    @Test
    public void assertThrowsBracketError() throws Throwable{
        CartesianProduct c = new CartesianProduct();

        exception.expect(UnclosedBracket.class);
        c.getProduct("c{d,e,f");
    }
}
