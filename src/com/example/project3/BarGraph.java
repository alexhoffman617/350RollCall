package com.example.project3;





import org.achartengine.ChartFactory;

import org.achartengine.GraphicalView;

import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.CombinedXYChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYSeries;

import org.achartengine.model.XYMultipleSeriesDataset;

import org.achartengine.renderer.XYMultipleSeriesRenderer;

import org.achartengine.renderer.XYSeriesRenderer;



import android.content.Context;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.Paint.Align;



public class BarGraph{

//private XYMultipleSeriesRenderer mRenderer = getDemoRenderer();
public int[] y = {};
public String[] x = {};
public int[] y2 = {80, 20, 55, 20, 85, 22, 18, 53, 33, 42, 76, 22};
public String graphType = "";
GraphicalView gv;

public void setData(int[] yData, String[] xData, String type){
	y = yData;
	x = xData;
	graphType = type;
}

public void setData(int[] yData, String[] xData, String[] y2Data, String type){
	y = yData;
	x = xData;
	y2Data = y2Data;
	graphType = type;
}

public GraphicalView getView(Context context)

{


//CODE FOR A NORMAL GRAPH
if(graphType.equals("Stacked") || graphType.equals("Compare")){
CategorySeries series = new CategorySeries("Demo Bar Graph");

for (int i = 0; i<y.length; i++){

series.add("Bar " + (i+1), y[i]);

}


XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

dataset.addSeries(series.toXYSeries());


XYSeriesRenderer renderer = new XYSeriesRenderer();

renderer.setDisplayChartValues(true);

renderer.setChartValuesSpacing((float) 1);
renderer.setColor(Color.rgb(130, 130, 230));


XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

mRenderer.addSeriesRenderer(renderer);
mRenderer.setZoomButtonsVisible(true);
mRenderer.setChartTitle("Student");
mRenderer.setXTitle("Dates");
mRenderer.setYTitle("Attendance");
mRenderer.setZoomButtonsVisible(true);



for(int i = 0; i<x.length; i++){
	mRenderer.addXTextLabel(i+1, x[i]);
}

mRenderer.setXLabels(0);

GraphicalView gv = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);


return gv;

}


//CODE FOR A COMPARISON GRAPH
else if(y[0]==0){
// Creating an  XYSeries for Income
XYSeries boys = new XYSeries("Boys");
// Creating an  XYSeries for Expense
XYSeries girls = new XYSeries("Grils");
// Adding data to Income and Expense Series
for(int i=0;i<x.length;i++){
    boys.add(i,y[i]);
    girls.add(i,y2[i]);
}

// Creating a dataset to hold each series
XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
// Adding Income Series to the dataset
dataset.addSeries(boys);
// Adding Expense Series to dataset
dataset.addSeries(girls);

// Creating XYSeriesRenderer to customize incomeSeries
XYSeriesRenderer boysRenderer = new XYSeriesRenderer();
boysRenderer.setColor(Color.rgb(130, 130, 230));
boysRenderer.setFillPoints(true);
boysRenderer.setLineWidth(2);
boysRenderer.setDisplayChartValues(true);

// Creating XYSeriesRenderer to customize expenseSeries
XYSeriesRenderer girlsRenderer = new XYSeriesRenderer();
girlsRenderer.setColor(Color.rgb(220, 80, 80));
girlsRenderer.setFillPoints(true);
girlsRenderer.setLineWidth(2);
girlsRenderer.setDisplayChartValues(true);

// Creating a XYMultipleSeriesRenderer to customize the whole chart
XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
multiRenderer.setXLabels(0);
multiRenderer.setChartTitle("Boys vs Girls");
multiRenderer.setXTitle("Dates");
multiRenderer.setYTitle("Attendance");
multiRenderer.setZoomButtonsVisible(true);
for(int i=0; i< x.length;i++){
    multiRenderer.addXTextLabel(i, x[i]);
}

// Adding incomeRenderer and expenseRenderer to multipleRenderer
// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
// should be same
multiRenderer.addSeriesRenderer(boysRenderer);
multiRenderer.addSeriesRenderer(girlsRenderer);

 gv = ChartFactory.getBarChartView(context, dataset, multiRenderer, Type.DEFAULT);

if(graphType.equals("Stacked")){
	gv = ChartFactory.getBarChartView(context, dataset, multiRenderer, Type.STACKED);
}

return gv;
}




//CODE FOR A NORMAL GRAPH
else{
CategorySeries series = new CategorySeries("Demo Bar Graph");

for (int i = 0; i<y.length; i++){

series.add("Bar " + (i+1), y[i]);

}


XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

dataset.addSeries(series.toXYSeries());


XYSeriesRenderer renderer = new XYSeriesRenderer();

renderer.setDisplayChartValues(true);

renderer.setChartValuesSpacing((float) 1);
renderer.setColor(Color.rgb(130, 130, 230));


XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

mRenderer.addSeriesRenderer(renderer);
mRenderer.setZoomButtonsVisible(true);
mRenderer.setChartTitle("Student");
mRenderer.setXTitle("Dates");
mRenderer.setYTitle("Attendance");
mRenderer.setZoomButtonsVisible(true);



for(int i = 0; i<x.length; i++){
	mRenderer.addXTextLabel(i+1, x[i]);
}

mRenderer.setXLabels(0);

 gv = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);


return gv;

}
}



}