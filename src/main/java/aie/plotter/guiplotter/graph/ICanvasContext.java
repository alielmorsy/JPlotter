package aie.plotter.guiplotter.graph;

import aie.plotter.guiplotter.excepations.EquationFormatException;

/**
 * CanvasContext Interface. it should contain the main functions for the canvas, and be connection wire between drawer and GUI.
 */
public interface ICanvasContext {
    /**
     * to draw  a dumb axises at the start of the application
     * X-axis, and y-axis will be dummy values from 0 to 9
     */
    void drawDumbAxis();

    /**
     * To Draw the given inputs on graph based on x value limits from x1 to x2
     * @param equation the user input equation to be drawn
     * @param x1 the start point on x-axis
     * @param x2 the end point on x-axis
     * @return true if equation is compiled otherwise false
     */


    boolean draw(String equation, int x1, int x2);
}
