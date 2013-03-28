package com.example.project3;





import java.util.ArrayList;
import java.util.List;

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
public ArrayList<Integer> y;
public ArrayList<String> x;
public ArrayList<Integer> y2;
public String graphType = "";
GraphicalView gv;

public void setData(ArrayList<Integer> yData, ArrayList<String> xData, String type){
	y = yData;
	x = xData;
	graphType = type;
}

public void setData(ArrayList<Integer> yData, ArrayList<String> xData, ArrayList<Integer> y2Data, String type){
	y = yData;
	x = xData;
	y2 = y2Data;
	graphType = type;
}

public GraphicalView getView(Context context)

{


//CODE FOR A NORMAL GRAPH
if(graphType.equals("Stacked") || graphType.equals("Compare")){
CategorySeries series = new CategorySeries("Demo Bar Graph");

for (int i = 0; i<y.size(); i++){

series.add("Bar " + (i+1), y.get(i));

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



for(int i = 0; i<x.size(); i++){
	mRenderer.addXTextLabel(i+1, x.get(i));
}

mRenderer.setXLabels(0);

GraphicalView gv = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);


return gv;

}


//CODE FOR A COMPARISON GRAPH
else if(y.get(0)==0){
// Creating an  XYSeries for Income
XYSeries boys = new XYSeries("Boys");
// Creating an  XYSeries for Expense
XYSeries girls = new XYSeries("Grils");
// Adding data to Income and Expense Series
for(int i=0;i<x.size();i++){
    boys.add(i,y.get(i));
    girls.add(i,y2.get(i));
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
for(int i=0; i< x.size();i++){
    multiRenderer.addXTextLabel(i, x.get(i));
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

for (int i = 0; i<y.size(); i++){

series.add("Bar " + (i+1), y.get(i));

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



for(int i = 0; i<x.size(); i++){
	mRenderer.addXTextLabel(i+1, x.get(i));
}

mRenderer.setXLabels(0);

 gv = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);


return gv;

}
}



}