package com.example.project3;





import org.achartengine.ChartFactory;

import org.achartengine.GraphicalView;

import org.achartengine.chart.BarChart.Type;

import org.achartengine.model.CategorySeries;

import org.achartengine.model.XYMultipleSeriesDataset;

import org.achartengine.renderer.XYMultipleSeriesRenderer;

import org.achartengine.renderer.XYSeriesRenderer;



import android.content.Context;

import android.content.Intent;

import android.graphics.Color;



public class BarGraph{

//private XYMultipleSeriesRenderer mRenderer = getDemoRenderer();



public GraphicalView getView(Context context)

{

int[] y = {124, 135 , 413, 356, 234, 123, 342, 134, 123, 643, 234, 274};


CategorySeries series = new CategorySeries("Demo Bar Graph");

for (int i = 0; i<y.length; i++){

series.add("Bar" + (i+1), y[i]);

}


XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

dataset.addSeries(series.toXYSeries());


XYSeriesRenderer renderer = new XYSeriesRenderer();

renderer.setDisplayChartValues(true);

renderer.setChartValuesSpacing((float) 0.5);


XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

mRenderer.addSeriesRenderer(renderer);

mRenderer.setChartTitle("Student");

mRenderer.setXTitle("Dates");

mRenderer.setYTitle("Attendance");

mRenderer.setBackgroundColor(Color.WHITE);

mRenderer.setMarginsColor(Color.WHITE);


GraphicalView gv = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);


return gv;

}



}