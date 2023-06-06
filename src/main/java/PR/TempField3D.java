package PR;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.function.Function3D;
import com.orsoncharts.fx.Chart3DViewer;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.RainbowScale;
import com.orsoncharts.renderer.xyz.SurfaceRenderer;
import com.orsoncharts.util.Orientation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Александр Холодов
 * @created 02/2021
 * @project RPA-MPEI
 * @description Построение 3D поверхности
 */
public class TempField3D extends Application {

    private double[][] field;
    private int width, height;
    private double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;


    public static void showField(double[][] field){
        try { new Thread(() -> launch("")).start(); Thread.sleep(100); }
        catch (Exception e) { e.printStackTrace(); }

        TempField3D tf = new TempField3D();
        tf.show(field);
    }

    public void show(double[][] field){
        this.field = field;

        Platform.runLater(() -> {
            Stage stage = new Stage();
            StackPane sp = new StackPane();
            sp.getChildren().add(createChartNode());
            Scene scene = new Scene(sp, 1024, 768);
            stage.setScene(scene);
            stage.setTitle("Temperature field");
            stage.show();
        });
    }


    public Node createChartNode() {

        try {
            if(field == null) throw new Exception("Неправильный формат");
            if(field[0] == null) throw new Exception("Неправильный формат");
        } catch (Exception e) { e.printStackTrace(); System.exit(0); }

        this.width = field[0].length;
        this.height = field.length;

        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                min = Math.min(field[i][j], min);
                max = Math.max(field[i][j], max);
            }
        }

        Function3D function = (x, z) -> {
            int x1 = (int) x, z1 = (int) z;
            if(x1 < height && z1 < width ) return field[x1][z1];
            else return 0.0;
        };

        Chart3D chart = Chart3DFactory.createSurfaceChart(
                "Temperature field",
                "",
                function, "X", "Temperature", "Z");

        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(width, 50, height));

        /* Диапазоны построения */

        ValueAxis3D xAxis = plot.getXAxis();
        xAxis.setRange(0, height);

        ValueAxis3D zAxis = plot.getZAxis();
        zAxis.setRange(0, width);

        SurfaceRenderer renderer = (SurfaceRenderer) plot.getRenderer();
//        renderer.setColorScale(new GradientColorScale(new Range(min, max), Color.BLUE, Color.RED));
        renderer.setColorScale(new RainbowScale(new Range(min, max)));
        renderer.setDrawFaceOutlines(false);
        chart.setLegendPosition(LegendAnchor.BOTTOM_RIGHT, Orientation.VERTICAL);

        /* Масштабирование */
        Chart3DViewer viewer = new Chart3DViewer(chart);
        Platform.runLater(() -> {
            viewer.getCanvas().zoomToFit(viewer.getCanvas().getWidth(), viewer.getCanvas().getHeight());
        });

        return viewer;
    }

    @Override
    public void start(Stage primaryStage) { }

}

