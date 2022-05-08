package aie.plotter.guiplotter.graph;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Class Contains all utils about graph's equation. like compile, set X Values calculate y values
 */
public class Equation {
    private double[] calculatedY;

    public boolean calculate(String equation,int[] xs) {
        calculatedY = new double[xs.length];
        var argument = new Argument("x");
        var eq = new Expression(equation, argument);
        if (!eq.checkSyntax()) return false;
        for (int i = 0; i < xs.length; i++) {
            argument.setArgumentValue(xs[i]);
            calculatedY[i] = eq.calculate();

        }

        return true;
    }

    @Deprecated
    public double[] yAxisValues() {


        var max = Arrays.stream(calculatedY).max().getAsDouble();
        var min = max / 5;
        var mag = Math.pow(10, Math.floor(Math.log10(min)));
        var res = min / mag;
        var tick = 0d;
        if (res > 5)
            tick = 10 * mag;
        else if (res > 2)
            tick = 5 * mag;
        else if (res > 1) {
            tick = 2 * mag;
        } else tick = mag;
        System.out.println(tick);
        return calculatedY;
    }


    public double[] getYValues() {
        return calculatedY;
    }
}
