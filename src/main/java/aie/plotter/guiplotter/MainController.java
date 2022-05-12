package aie.plotter.guiplotter;

import aie.plotter.guiplotter.graph.ICanvasContext;
import aie.plotter.guiplotter.graph.implemnetations.CanvasContext;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

public class MainController {

    @FXML
    private Canvas canvas;
    @FXML
    private TextField equationText, x1Field, x2Field;
    private ICanvasContext canvasContext;

    @FXML
    public void initialize() {
        canvasContext = new CanvasContext(canvas.getGraphicsContext2D());
        canvasContext.drawDumbAxis();
    }

    @FXML
    protected void calculate() {

        String equation = equationText.getText();
        String x1String = x1Field.getText();
        String x2String = x2Field.getText();
        if (equation.isBlank() || x1String.isBlank() || x2String.isBlank() || !isNumeric(x1String) || !isNumeric(x2String)) {
            showError("U need to enter all data Correctly");
            return;
        }
        var x1 = Integer.parseInt(x1String);
        var x2 = Integer.parseInt(x2String);
        if (x1 >= x2) {
            showError("X1 should  be smaller than X2");
            return;
        }
        if (!canvasContext.draw(equation, x1, x2))
            showError("Invalid Equation! Please check it and try again");
    }

    public static void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}