package binance_tarder;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;


public class JanelaGrafico extends JFrame {

  private static final long serialVersionUID = 1L;
  
  public JanelaGrafico(){
      super();
  }
  
  public ChartPanel configJanelaGrafico(XYSeriesCollection dataSet, String nomeMoeda) {
        //Creates a sample dataset
        XYSeriesCollection ds = dataSet;
        
        // based on the dataset we create the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Preço Moeda "+nomeMoeda,     
                "Nº Registro",           
                "Preço",           
                ds
                );

        // Adding chart into a chart panel
        ChartPanel chartPanel = new ChartPanel(chart);
       
        // settind default size
        chartPanel.setPreferredSize(new java.awt.Dimension(630, 270));
             
        chartPanel.repaint();
        
        chartPanel.setDomainZoomable(true);
        
        return chartPanel;
        // add to contentPane 
    }

}