package PR;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;

/**
 * @author Александр Холодов
 * @created 02/2021
 * @project RPA-MPEI
 * @description График для построения градиента
 */

public class TempField2D {

    private double[][] field;
    private int width, height;
    private double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;

    public static void showField(double[][] field) {
        TempField2D tf = new TempField2D();
        tf.show(field);
    }

    private void show(double[][] field) {

        try {
            if(field == null) throw new Exception("Неправильный формат");
            if(field[0] == null) throw new Exception("Неправильный формат");
        } catch (Exception e) { e.printStackTrace(); System.exit(0); }

        this.field = field;
        this.width = field[0].length;
        this.height = field.length;

        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                min = Math.min(field[i][j], min);
                max = Math.max(field[i][j], max);
            }
        }

        for(int j=0; j<width; j++) {
            for (int i=0; i<height/2; i++) {
                double temp = field[i][j];
                field[i][j] = field[height - 1 - i][j];
                field[height - 1 - i][j] = temp;
            }
        }


        JFrame f = new JFrame("Temperature field");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = field[0].length, height = field.length;
        ChartPanel chartPanel = new ChartPanel(createChart(createDataset())) {
            public Dimension getPreferredSize() { return new Dimension(1024, 1024 * height/width); }
        };
        chartPanel.setMouseZoomable(true, false);
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public TempField2D() { }

    private JFreeChart createChart(XYDataset dataset) {
        NumberAxis xAxis = new NumberAxis("x Axis");
        NumberAxis yAxis = new NumberAxis("y Axis");
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);
        XYBlockRenderer r = new XYBlockRenderer();
        SpectrumPaintScale ps = new SpectrumPaintScale(min * 0.8, max * 1.2);
        r.setPaintScale(ps);
        r.setBlockHeight(2f);
        r.setBlockWidth(2f);
        plot.setRenderer(r);
        JFreeChart chart = new JFreeChart("Temperature field", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        NumberAxis scaleAxis = new NumberAxis("Scale");
        scaleAxis.setAxisLinePaint(Color.white);
        scaleAxis.setTickMarkPaint(Color.white);
        PaintScaleLegend legend = new PaintScaleLegend(ps, scaleAxis);
        legend.setSubdivisionCount(128);
        legend.setAxisLocation(AxisLocation.TOP_OR_RIGHT);
        legend.setPadding(new RectangleInsets(10, 10, 10, 10));
        legend.setStripWidth(20);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(Color.WHITE);
        chart.addSubtitle(legend);
        chart.setBackgroundPaint(Color.white);
        return chart;
    }

    private XYZDataset createDataset() {
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        int width = field[0].length, height = field.length;

        for (int i=0; i<height; i++) {
            double[][] data = new double[3][field[0].length];
            for (int j=0; j<width; j++) {
                data[0][j] = j;
                data[1][j] = i;
                data[2][j] = field[i][j];
            }
            dataset.addSeries("Series" + i, data);
        }
        return dataset;
    }


    private static class SpectrumPaintScale implements PaintScale {

        private static final float H1 = 0f, H2 = 0.5f;
        private final double lowerBound, upperBound;

        public SpectrumPaintScale(double lowerBound, double upperBound) { this.lowerBound = lowerBound; this.upperBound = upperBound; }

        public double getLowerBound() { return lowerBound; }
        public double getUpperBound() { return upperBound; }
        public Paint getPaint(double value) {
            float scaledValue = (float) (value / (getUpperBound() - getLowerBound()));
            float scaledH = H2 + scaledValue * (H2 - H1);
            return Color.getHSBColor(scaledH, 1f, 1f);
        }
    }
}