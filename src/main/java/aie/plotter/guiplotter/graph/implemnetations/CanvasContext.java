package aie.plotter.guiplotter.graph.implemnetations;

import aie.plotter.guiplotter.graph.Equation;
import aie.plotter.guiplotter.graph.ICanvasContext;
import aie.plotter.guiplotter.graph.utils.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class CanvasContext implements ICanvasContext {
    private GraphicsContext graphics;

    private float width, height;

    private double basicXDistance, basicYDistance;
    private Map<Integer, Double> xValues = new HashMap<>();
    private Map<Double, Double> yValues = new HashMap<>();
    private double step;

    public CanvasContext(GraphicsContext graphics) {
        this.graphics = graphics;
        width = (float) graphics.getCanvas().getWidth();
        height = (float) graphics.getCanvas().getHeight();
    }

    @Override
    public void drawDumbAxis() {

        drawAxis(IntStream.range(-5, 10).toArray(), IntStream.rangeClosed(-6, 6).mapToDouble(x -> x).toArray());
    }


    @Override
    public boolean draw(String eq, int x1, int x2) {
        xValues.clear();
        yValues.clear();
        graphics.setFill(Color.RED);
        graphics.clearRect(0, 0, 800, 800);

        resetPlot();
        Equation equation = new Equation();
        var xs = IntStream.rangeClosed(x1, x2).toArray();
        if (!equation.calculate(eq, xs)) {
            return false;
        }

        double[] ys = equation.getYValues();

        double[] axisY = Utils.normalizeAxis(ys, 10);
        drawAxis(xs, axisY);
        addPointsToPlot(xs, ys);
        return true;
    }

    private void addPointsToPlot(int[] xs, double[] ys) {
        setupYValues(ys);
        setupPlots();
        var x = xValues.get(xs[0]);
        var y = yValues.getOrDefault(ys[0], (double) -1);
        graphics.fillOval(x - 3, y - 3, 6, 6);

        for (int i = 1; i < xs.length; i++) {
            double x1 = xValues.get(xs[i]);
            var y1 = yValues.getOrDefault(ys[i], (double) -1);
            var x2 = xValues.get(xs[i - 1]);
            var y2 = yValues.getOrDefault(ys[i - 1], -1d);

            graphics.strokeLine(x1, y1, x2, y2);
            graphics.fillOval(x1 - 3, y1 - 3, 6, 6);
        }
        x = xValues.get(xs[xs.length - 1]);
        y = yValues.getOrDefault(ys[xs.length - 1], (double) -1);
        graphics.fillOval(x - 3, y - 3, 6, 6);
    }

    private void setupYValues(double[] ys) {
        for (double y : ys) {

            var yValue = yValues.getOrDefault(y, (double) -1);
            if (yValue == -1) {

                var mod = y % step;
                var originalY = y - mod;

                var newY = yValues.get(originalY);

                newY -= basicYDistance * (mod);

                yValues.put(y, newY);


            }
        }
    }

    private void setupPlots() {

        graphics.setLineCap(StrokeLineCap.ROUND);
        graphics.setLineJoin(StrokeLineJoin.ROUND);
        graphics.setStroke(Color.RED);

        graphics.setLineWidth(2);
    }

    private void resetPlot() {
        graphics.setStroke(Color.BLACK);
        graphics.setLineWidth(1);
    }

    private void drawAxis(int[] xs, double[] ys) {
        System.out.println("Drawing!");
        graphics.setFill(Color.BLACK);
        double[] points;
        if (xs[0] >= 0) {
            points = drawPositiveXAxis(xs, ys);
        } else {
            points = drawNegativeXAxis(xs[xs.length - 1], Math.abs(xs[0]), xs[0], ys);
        }
        drawYAxis(points[0], points[1], ys);

    }

    private double[] drawPositiveXAxis(int[] xs, double[] ys) {
        double ix = 40, iy = calculateFirstPosition(ys);
        graphics.strokeLine(ix, iy, width, iy);
        graphics.strokeLine(ix, iy, ix, 0);

        int x1 = xs[0], x2 = xs[xs.length - 1];
        int n;
        if (x1 == 0) {
            n = x2;
        } else {
            n = Math.abs(x2 - x1 + 1);
        }

        double distance = ((width - ix * 2) / n);
        basicXDistance = distance;
        double intitalDistance = ix;
        for (int i = 0; i <= x2; i++) {
            xValues.put(i, intitalDistance);
            graphics.fillOval(intitalDistance - 3, iy - 3, 6, 6);
            graphics.fillText(String.valueOf(i), intitalDistance, iy + 15);
            intitalDistance += distance;
        }
        return new double[]{ix, iy};
    }

    private double[] drawNegativeXAxis(int countFromZero, int countToZero, int x1, double[] ys) {
        var iy = calculateFirstPosition(ys);
        var init = ((width - 60) / (countFromZero + countToZero));
        var negativeWidth = (init * (countToZero)) + 15;
        graphics.strokeLine(0, iy, width, iy);
        //  graphics.strokeLine(negativeWidth, iy, negativeWidth, 40);
        var distance = 15d;
        //add x-axis points
        for (int i = 0; i <= countFromZero + countToZero; i++) {

            xValues.put(x1, distance);
            float textPadding;
            if (i < countToZero) {
                textPadding = -5;
            } else {
                textPadding = 0;
            }
            graphics.fillOval(distance - 3, iy - 3, 6, 6);
            graphics.fillText(String.valueOf(x1++), distance + textPadding - 2, iy + 15);

            distance += init;
        }
        basicXDistance = init;
        return new double[]{negativeWidth, iy};
    }

    private float drawYAxis(double ix, double iy, double[] ys) {
        var y1 = ys[0];
        var y2 = ys[ys.length - 1];
        step = ys[1] - y1;
        y2 = Math.max(y2, 0);
        y1 = Math.min(y1, 0);
        double basicSize = ((height - 40) / (Math.abs(y1) + Math.abs(y2)));
        double distance = (height - 40);
        graphics.strokeLine(ix, 0, ix, height);
        for (double y : ys) {

            yValues.put(y1, distance);
            graphics.fillOval(ix - 3, distance - 3, 6, 6);
            graphics.fillText(String.format("%.2f", y1), ix - 25, distance + 9f);
            distance -= basicSize * (step);
            y1 += step;
        }
        basicYDistance = basicSize;
        return -1;
    }

    private double calculateFirstPosition(double[] ys) {

        var y1 = Arrays.stream(ys).min().getAsDouble();

        if (y1 >= 0) {
            return height - 40;
        }
        var y2 = Arrays.stream(ys).max().getAsDouble();
        var basicSize = (height - 40) / (Math.abs(y1) + Math.abs(y2));

        return basicSize * Math.abs(y2);
    }
}