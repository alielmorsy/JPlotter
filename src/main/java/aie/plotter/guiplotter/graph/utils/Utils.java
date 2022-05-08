package aie.plotter.guiplotter.graph.utils;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Utils {
    public static double[] normalizeAxis(double[] values, int stepCount) {
        var max = Arrays.stream(values).max().getAsDouble();
        var min = Arrays.stream(values).min().getAsDouble();
        min = Math.min(min, 0);

//        var epsilon = (max - min) / 1e6;
//        max += epsilon;
//        min -= epsilon;
//        System.out.println("Min: "+min);
        var range = max - min;
        if (range < 4) {
            return IntStream.rangeClosed(-2, 10).mapToDouble(x -> x).toArray();
        }
        double roughStep = range / (stepCount - 1);
        double[] goodNormalizedSteps = {1, 2, 4, 10, 20, 40};
        double stepPower = Math.pow(10, -Math.floor(Math.log10(Math.abs(roughStep))));

        var normalizedStep = roughStep * stepPower;

        var goodNormalizedStep = matchStep(goodNormalizedSteps, normalizedStep);
        var step = Math.round(goodNormalizedStep / stepPower);

        double scaleMin = min >= 0 ? 0 : Math.floor(min / step) * step;


        var data = new double[stepCount];
        data[0] = scaleMin;
        for (int i = 1; i < stepCount; i++) {
            data[i] = data[i - 1] + step;

        }
        return data;
    }

    private static double matchStep(double[] y, double n) {
        for (double yy : y) {
            if (yy >= n) {
                return yy;
            }
        }
        return -1;
    }
}
