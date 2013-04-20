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
	y2 = new ArrayList<Integer>();
	graphType = type;
}

public void setData(ArrayList<Integer> yData, ArrayList<String> xData, ArrayList<Integer> y2Data, String type){
	y = yData;
	x = xData;
	y2 = y2Data;//y2Data;
	graphType = type;
}

public GraphicalView getView(Context context)

{


if(graphType.equals("Stacked") || graphType.equals("Compare")){


	
	
	
	

//CODE FOR A COMPARISON GRAPH
// Creating an  XYSeries for Income
XYSeries boys = new XYSeries("Boys");
// Creating an  XYSeries for Expense
XYSeries girls = new XYSeries("Grils");
// Adding data to Income and Expense Series
for(int i=0;i<y.size();i++){
    boys.add((i+1), y2.get(i));
    girls.add((i+1),y.get(i));
}

// Creating a dataset to hold each series
XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
// Adding Income Series to the dataset
dataset.addSeries(boys);
// Adding Expense Series to dataset
dataset.addSeries(girls);

// Creating XYSeriesRenderer to customize incomeSeries
XYSeriesRenderer boysRenderer = new XYSeriesRenderer();
boysRenderer.setColor(Color.rgb(0, 153, 204));
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
multiRenderer.setXTitle("Dates");
multiRenderer.setYTitle("Attendance");
for(int i=0; i< x.size();i++){
    multiRenderer.addXTextLabel(i+1, x.get(i));
}

// Adding incomeRenderer and expenseRenderer to multipleRenderer
// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
// should be same
multiRenderer.addSeriesRenderer(boysRenderer);
multiRenderer.addSeriesRenderer(girlsRenderer);


multiRenderer.setBarSpacing(1);

int max=1;
for(int i=0; i< y.size(); i++){
	if(y.get(i)>max){
		max = y.get(i);
	}
	if(y2.get(i)>max){
		max = y2.get(i);
	}
}


multiRenderer.setYAxisMin(0);
if(max > 50){
	multiRenderer.setYAxisMax(max+10);
}
else{
multiRenderer.setYAxisMax(max+1);
}
multiRenderer.setXAxisMin(.5);
multiRenderer.setXAxisMax(y.size()+.5);
multiRenderer.setAxisTitleTextSize(25);
multiRenderer.setChartValuesTextSize(20);
multiRenderer.setLabelsTextSize(20);
multiRenderer.setChartTitleTextSize(30);
multiRenderer.setBackgroundColor(Color.WHITE);
multiRenderer.setYLabelsAlign(Align.LEFT);
multiRenderer.setYLabelsColor(0, Color.BLACK);
 gv = ChartFactory.getBarChartView(context, dataset, multiRenderer, Type.DEFAULT);

if(graphType.equals("Stacked")){
	gv = ChartFactory.getBarChartView(context, dataset, multiRenderer, Type.STACKED);
}

return gv;


}


//CODE FOR A NORMAL GRAPH
else{
CategorySeries series = new CategorySeries("");

for (int i = 0; i<y.size(); i++){

series.add("Bar " + (i+1), y.get(i));

}


XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

dataset.addSeries(series.toXYSeries());


XYSeriesRenderer renderer = new XYSeriesRenderer();

renderer.setDisplayChartValues(true);

renderer.setChartValuesSpacing((float) 5);
renderer.setColor(Color.rgb(0, 153, 204));



XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

mRenderer.addSeriesRenderer(renderer);
mRenderer.setXTitle("Dates");
mRenderer.setYTitle("Attendance");
mRenderer.setBarSpacing(1);

int max=1;
for(int i=0; i< y.size(); i++){
	if(y.get(i)>max){
		max = y.get(i);
	}
}



mRenderer.setYAxisMin(0);
if(max > 50){
	mRenderer.setYAxisMax(max+10);
}
else{
mRenderer.setYAxisMax(max+1);
}
mRenderer.setXAxisMin(.5);
mRenderer.setXAxisMax(y.size()+.5);
mRenderer.setAxisTitleTextSize(25);
mRenderer.setChartValuesTextSize(20);
mRenderer.setLabelsTextSize(20);
mRenderer.setChartTitleTextSize(30);
mRenderer.setBackgroundColor(Color.WHITE);
mRenderer.setYLabelsAlign(Align.LEFT);
mRenderer.setYLabelsColor(0, Color.BLACK);


for(int i = 0; i<x.size(); i++){
	mRenderer.addXTextLabel(i+1, x.get(i));
}

mRenderer.setXLabels(0);

 gv = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);


return gv;

}
}



}