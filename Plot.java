
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Class which creates a scattered plot representing the elapsed time of the
 * protocol for different drop probabilities of the messages by the network.
 * 
 * @author Andres Monteoliva
 * @since 19-03-2017
 *
 */
public class Plot extends JFrame {

	private static final long serialVersionUID = 1L;
	private List<Double> dropProb;
	private List<Double> ElapsedTime;
	private XYDataset dataset;

	/**
	 * Constructor of the plot class
	 * 
	 * @param title
	 *            Title of the graph
	 * @param dropProb
	 *            X-Data set. Probabilities of dropping a message by the network.
	 * @param ElapsedTime
	 *            Y-Data set. Elapsed time of the protocol.
	 */
	public Plot(String title, List<Double> dropProb, List<Double> ElapsedTime) {

		super(title);

		this.dropProb = dropProb;
		this.ElapsedTime = ElapsedTime;

		// Create dataset
		dataset = createDataset();

		// Create chart
		JFreeChart chart = ChartFactory.createScatterPlot("Time Elapsed versus Probability Drop",
				"Drop Message Probability", "Protocol Time Elapsed (in ms)", dataset);

		// Create Panel
		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);

	}

	/**
	 * Create the dataset for the plot.
	 * 
	 * @return XYDataset
	 */
	public XYDataset createDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();

		XYSeries dataPoints = new XYSeries("PUSH protocol simulation");

		for (int i = 0; i < dropProb.size(); i++) {

			dataPoints.add(dropProb.get(i), ElapsedTime.get(i));
		}

		dataset.addSeries(dataPoints);

		return dataset;
	}

}
