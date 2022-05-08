package aie.plotter.guiplotter;

import aie.plotter.guiplotter.graph.Equation;
import aie.plotter.guiplotter.graph.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;


public class PlotterTest {
    @Test()
    public void testEquation() {
        Assertions.assertTrue(new Equation().calculate("x", IntStream.range(1, 4).toArray()));
    }

    @Test
    public void testCalculatedY() {
        var xs = IntStream.rangeClosed(1, 4).toArray();
        Equation eq = new Equation();
        eq.calculate("x", xs);
        var ys = eq.getYValues();
        Assertions.assertEquals(xs.length, ys.length);
        for (int i = 0; i < xs.length; i++) {
            Assertions.assertEquals(Math.round(ys[i]), xs[i]);
        }
    }

    @Test
    public void testNormalizedAxis() {
        var xs = IntStream.rangeClosed(0, 4).toArray();
        var axis = Utils.normalizeAxis(Arrays.stream(xs).mapToDouble(x -> x).toArray(), 5);
        Assertions.assertEquals(xs.length, axis.length);

        for (int i = 0; i < xs.length; i++) {
            Assertions.assertEquals(Math.round(axis[i]), xs[i]);
        }
    }

    @Test
    public void testNumberVerificationTrue() {
        Assertions.assertTrue(MainController.isNumeric("4"));
    }
    @Test
    public void testNumberVerificationFalse() {
        Assertions.assertFalse(MainController.isNumeric("x"));
    }
}
