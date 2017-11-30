package com.clara.helloandroidgraphs;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends Activity {

	protected BarChart mChart;

	// Holds data points for the graph. Here we have two series drawn on the same graph.
	ArrayList<BarEntry> seriesOfProcedurallyGeneratedData;
	ArrayList<BarEntry> seriesOfRandomlyGeneratedData;

	float barWidth = 0.2f;
	float startX = 0;
	float groupSpace = 0.2f;
	float barSpace = 0.1f;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get reference to the view component
		mChart = (BarChart) findViewById(R.id.barchart);

		configureChart();
		getData();

		// Start x value, group space, bar space (within a group).
		mChart.groupBars(startX, groupSpace, barSpace);

		// Tell the chart to update
		mChart.getData().notifyDataChanged();
		mChart.notifyDataSetChanged();




	}

	private void configureChart() {

		// A BarEntry is one data point for one series of the graph
		// Contains all the BarEntries in an ArrayList

		seriesOfProcedurallyGeneratedData = new ArrayList<BarEntry>();
		seriesOfRandomlyGeneratedData = new ArrayList<BarEntry>();

		// BarDataSet is a list of BarEntry objects and a label for the data
		BarDataSet procDataSet = new BarDataSet(seriesOfProcedurallyGeneratedData, "Procedurally Generated");
		BarDataSet randomDataSet = new BarDataSet(seriesOfProcedurallyGeneratedData, "Randomly Generated");

		// Can configure how each series looks.
		procDataSet.setDrawIcons(false);
		procDataSet.setColor(Color.CYAN);  // Can use the same color for each bar in the series

		procDataSet.setDrawIcons(false);
		randomDataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Or use a color scheme, bars will all be different colors.  Several color schemes built in.

		// We may have many data sets for a graph.
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();

		dataSets.add(procDataSet);
		dataSets.add(randomDataSet);

		// A BarData is generated from the list of BarDataSets
		BarData data = new BarData(dataSets);

		// Configure as needed, many other options, see documentation
		data.setValueTextSize(10f);
		data.setBarWidth(barWidth);

		Description description = new Description();
		description.setText("An example chart");

		mChart.setDescription(description);

		// Set this BarData as the data source for the chart
		mChart.setData(data);


	}

	public void getData() {


		// Some procedurally generated data

		float a = 4;

		for (int i = 0; i < 5; i++) {

			BarEntry entry = new BarEntry(i, a *= 1.5);
			System.out.println(a);
			seriesOfProcedurallyGeneratedData.add(entry);
		}

		BarDataSet procDataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
		procDataSet.setValues(seriesOfProcedurallyGeneratedData);


		// Add some randomly generated data to the random data series

		Random rnd = new Random();

		for (int i = 0 ; i < 5; i++) {

			BarEntry entry = new BarEntry(i, rnd.nextInt(50));
			seriesOfRandomlyGeneratedData.add(entry);

		}

		BarDataSet randomDataSet = (BarDataSet) mChart.getData().getDataSetByIndex(1);
		randomDataSet.setValues(seriesOfRandomlyGeneratedData);


	}

}


