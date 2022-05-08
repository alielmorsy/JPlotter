module aie.plotter.guiplotter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.json;
    //requires MathParser.org.mXparser;


    opens aie.plotter.guiplotter to javafx.fxml;
    exports aie.plotter.guiplotter;
}