



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



//import LinearRegression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


//Simple Linear Regression
class LinearRegression 
{
	//Variable declaration
	String filepath;
	String data;
	float xy,x2,xSum,ySum,a,b;
	public final int MAX1 =36;
	public final int MAX2 =12;
	int[] trainmonths = new int[MAX1] ;
	int[] trainSales = new int [MAX1];
	int[] testmonths = new int[MAX2] ;
	int[] testSales = new int [MAX2];
	int[]PredictedResultY = new int[MAX2];
	int i=0;
	
	//Constructor 
	LinearRegression()
	{
		xy=xSum=ySum=x2=0;
		
	}
	
	//Reading CSV File
	void  ReadingCsv(String filepath) {
	
		this.filepath = filepath;
		File file =new File(this.filepath);
		try {
			Scanner inputScanner = new Scanner(file);
			while(i<MAX1 ) {
				data = inputScanner.next();
				String []ind_value= data.split(",") ;
				int valuesTab1 = Integer.parseInt(ind_value[0]);
				int valuesTab2 =  Integer.parseInt(ind_value[1]);
				
				trainmonths[i]= valuesTab1;
				trainSales[i] = valuesTab2;
				i++;
			}
			i=0;
			while(i<MAX2) {
				data = inputScanner.next();
				String []ind_value= data.split(",") ;
				int valuesTab1 =  Integer.parseInt(ind_value[0]);
				int valuesTab2 =  Integer.parseInt(ind_value[1]);
				
				testmonths[i]= valuesTab1;
				testSales[i] = valuesTab2;
				i++;
			}
			inputScanner.close();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Linear REgression Algorithm Implementation PArt
	void LinearRegressionResult() 
	{
		int [] x= this.trainmonths;
		int [] y =this.trainSales;
		for (int i = 0;i< x.length; i++)
		{
			// Values required to calculate a and b
			xy=x[i]*y[i]+xy;
			x2 =x[i]*x[i]+x2;
			xSum= xSum+x[i];
			ySum =ySum+y[i];	
		}

		//Calculating value of a and b in y = a + bx
		b = (MAX1*xy - xSum*ySum)/(MAX1*x2-(xSum*xSum));
		a = (ySum - b*xSum)/MAX1;
		
		for (int i =0;i<MAX2;i++) {
			PredictedResultY[i] = (int) (a + b *testmonths[i]);
		}
	}
	
	public  float get_ErrorRate()
	{
      float Syx=0,sumYYr=0;
      for(int i=0;i<MAX1;i++)
      {
          sumYYr+=Math.pow(trainSales[i]-(a+b*trainmonths[i]),2);
      }
      Syx = (float) Math.sqrt(sumYYr/MAX1);
      return Syx;
	}
	
	public  float get_MSE()
	{
      float Syx=0,sumYYr=0;
      for(int i=0;i<MAX1;i++)
      {
          sumYYr+=Math.pow(trainSales[i]-(a+b*trainmonths[i]),2);
      }
      Syx = (float)(sumYYr/MAX1);
      return Syx;
	}
	
	public int[] getSales()
	{
		return testSales;
	}
	
	public int[] getMonths()
	{
		return testmonths;
	}
	
	public int[] getPredictedRedult()
	{
		return PredictedResultY;
	}
	public float  get_a()
	{
		return this.a;
	}

	public float get_b()
	{
		return this.b;
	}
	
	public int[] get_result() {
		int[]result = new int[MAX1];
		for(int i = 0;i< trainmonths.length;i++) {
			result[i]=(int)(a + b * trainmonths[i]);
		}
		return result;
	}
}

//Logistic Linear Regression
class LogisticLinearRegression{
	String filepath;
	String data;
	static int MAX = 36;
  Double []months = new Double[MAX];
  Double []Sales = new Double[MAX];
  static double a,b,p;
  int i=0;
  
  //Reading CSV file
  public  void  ReadingCsv(String filepath) {
  	
		this.filepath = filepath;
		File file =new File(this.filepath);
		try {
			Scanner inputScanner = new Scanner(file);
			while(i<MAX ) {
				data = inputScanner.next();
				String []ind_value= data.split(",") ;
				Double valuesTab1 = Double.parseDouble(ind_value[0]);
				Double valuesTab2 = Double.parseDouble(ind_value[2]);
				
				months[i]= valuesTab1;
				Sales[i] = valuesTab2;
				i++;
				//System.out.println(valuesTab);
				
			}
			inputScanner.close();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
  
  
  
  public Double getProbabilty(Double x) {
  	a=-25;
  	b=0.80345;
  	p=(Math.exp(a + b*x))/(1+Math.exp(a + b*x));
		return p;
  }
  
  public void PredictResult() {
  	if(p<0.50) {
  		System.out.println("Not Popular");
  	}
  	else {
  		System.out.println("Popular");
  	}
  		
  }
  
}

//Seasonalized Linear Regression
class SeasonaliseReg{
	String filepath;
	String data;
	public final int MAX1 =36;
	public final int MAX2 =12;
	int[] trainmonths = new int[MAX1] ;
	int[] trainSales = new int [MAX1];
	int[] testmonths = new int[MAX2] ;
	int[] testSales = new int [MAX2];
	int[]PredictedResultY = new int[MAX1];
	float a,b;
	int i=0;
    static String ProductType;
    
  public  void  ReadingCsv(String filepath) {
  	
		this.filepath = filepath;
		File file =new File(this.filepath);
		try {
			Scanner inputScanner = new Scanner(file);
			while(i<MAX1 ) {
				data = inputScanner.next();
				String []ind_value= data.split(",") ;
				int valuesTab1 = (int) Float.parseFloat(ind_value[0]);
				int valuesTab2 = (int) Float.parseFloat(ind_value[1]);
				
				trainmonths[i]= valuesTab1;
				trainSales[i] = valuesTab2;
				i++;
			}
			i=0;
			while(i<MAX2) {
				data = inputScanner.next();
				String []ind_value= data.split(",") ;
				int valuesTab1 = (int) Float.parseFloat(ind_value[0]);
				int valuesTab2 = (int) Float.parseFloat(ind_value[1]);
				
				testmonths[i]= valuesTab1;
				testSales[i] = valuesTab2;
				i++;
			}
			inputScanner.close();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
  


  //Remove noisy components
  public   float[] getSmoothValues()
  {
      float psum = 0;
      
      float[] psumavg = new float[25];
      for(int i=0;i<trainSales.length;i++)
      {
          psum=0;

           if(i<trainSales.length-11)
           {
               for (int j=i;j<12+i;j++)
               {
                   psum+=trainSales[j];
               }
               psumavg[i] = psum/12;
           }
           else {
               break;
           }
      }
      return psumavg;
  }

  //Obtain CMA i.e. Centered Moving Average
  public  float[] getCMA(float[] psumavg)
  {
	  float[] CMAavg = new float[psumavg.length-1];
      for (int i=0;i<psumavg.length-1;i++)
      {
          CMAavg[i] = (psumavg[i]+psumavg[i+1])/2;
      }
      return CMAavg;
  }

  //Obtain seasonal trained and irregular trained
  public  float[] getstit(float[]CMA,float[] y)
  {
      int size = CMA.length;
      float[]stIt = new float[MAX1];
      int j=10;
      for(int i=0;i<10;i++)
      {
          stIt[i]=0;
      }
      for(int i=0;i<size;i++)
      {
          stIt[j] = y[j] / CMA[i];
          j++;
      }
      for(int k =j;k<MAX1;k++)
      {
          stIt[k] = 0;
      }
      return stIt;
  }

  //Obtain monthly seasonal trained
  public  float[] getMonthlySt(float[]stit)
  {
	  float[] St = new float[12];
      for(int i=0;i<12;i++)
      {
          if(i<10)
          {
              St[i] = (stit[i+12]+stit[i+24])/2;
          }
         else
          {
              St[i] = (stit[i]+stit[i+12]+stit[i+24])/3;
          }
      }
      return St;
  }

  //Obtain Deaseasonalize data
  public  float[] getDeseasonalizeData(float[]st,float[]y)
  {
      int count=0;
      float[] DeseasonalizeData = new float[MAX1];
      for(int i=0;i<MAX1;i++)
      {
          DeseasonalizeData[i] = y[i]/st[count];
          count++;

          if(count==st.length)
          {
              count = 0;
          }
      }
      return DeseasonalizeData;
  }

  //Obtain trained dataset
  public  float[] getTrained_data(float[]x,float[]deseason)
  {

      float sumX=0,sumY=0,sumXY=0,sumX2=0,avgX=0,avgY=0;
      float[] trained_data = new float[MAX1];


       for(int j=0; j<MAX1;j++)
       {
           sumXY+=x[j]*deseason[j];
           sumX2+=x[j]*x[j];
           sumX+=x[j];
           sumY+=deseason[j];
       }
       avgX = sumX/MAX1;
       avgY = sumY/MAX1;
       b = (sumXY-MAX1*avgX*avgY)/(sumX2- MAX1*avgX*avgX);
       a = avgY -(b*avgX) ;
       

       for(int i=0;i<MAX1;i++)
       {
           trained_data[i] = a +(b*x[i]) ;
       }

   return trained_data;
  }

  //Obtain predicted result
  public  void getPredictedData(float[]trained_data,float[]st)
  {
       
      int j=0;
      for(int i=0;i<MAX1;i++)
      {
          PredictedResultY[i]=(int) (trained_data[i]*st[j]);
          j++;
          if(j==st.length)
          {
              j=0;
          }
      }
  }
  
  public int[] getPredictedResult() {
	  return PredictedResultY;
  }
  
  // return forecasted data for nest one year
  public int[] getForcastedResult(float[]st) {
	  int[] result = new int[12];
	  int j=0;
	  for (int i = 37;i < 49; i++) {
		  
		  result[j] = (int) ((a + b*i) * st[j]);
		  j++;
		  
	  }
	  return result;
  }
  
  public float get_A() {
	  return a;
  }
  
  public float get_B() {
	  return b;
  }
  
  
  public float get_Error()
  {
	  float Syx=0,sumYYr=0;
  	for(int i =0;i<PredictedResultY.length;i++) {
        sumYYr+=Math.pow(trainSales[i]-PredictedResultY[i],2);
  	}
    Syx = (float) Math.sqrt(sumYYr/(PredictedResultY.length));
    return Syx;
  }
  
  public  float get_MSE()
	{
    float Syx=0,sumYYr=0;
    for(int i=0;i<PredictedResultY.length;i++)
    {
        sumYYr+=Math.pow(trainSales[i]-PredictedResultY[i],2);
    }
    Syx = (float)(sumYYr/MAX1);
    return Syx;
	}
  
  public int[] getX()
  {
  	return trainmonths;
  }
  
  public int[] getY()
  {
  	return trainSales;
  }
  public float[] getXI()
  {
	  float x[] = new float[trainmonths.length];
	  for(i=0;i<trainmonths.length;i++) {
		  x[i]=(int)trainmonths[i];
	  }
  	return x;
  }
  
  public float[] getYI()
  {

	  float y[] = new float[trainSales.length];
	  for(i=0;i<trainSales.length;i++) {
		  y[i]=(int)trainSales[i];
	  }
  	return y;
  }
  
  public int[] getX1() {
	  return testmonths;
  }
  
  public int[] getY1() {
	  return testSales;
  }
  
  
}

//Writing to a csv file
class WriteToFileCsv
{
public void writeTocsv(Double[]x,Double[]y,String s) throws IOException 
{
FileWriter fw = new FileWriter(s);
PrintWriter out = new PrintWriter(fw);
for(int i = 0;i<x.length;i++) {
	  out.print(x[i]);
	  out.print(",");
	  out.flush();
}
   
//Flush the output to the file

    
//Close the Print Writer
out.close();
    
//Close the File Writer
fw.close();       
}
}


//Class to create Graph
class Windows {
	
    public Windows(final String title,JPanel jp,int []x,int[]y,int[]z,int[]x1,int[]y1,int[]z1) {

 
        final XYDataset dataset = createDataset(x,y,z,x1,y1,z1);
        final JFreeChart chart = createChart(dataset,"Sales Forecasting");
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        chartPanel.setBounds(0,4,900, 500);
        jp.add(chartPanel);

    }
    
   

	private  XYDataset createDataset(int []x,int[]y,int[]z,int[]x1,int[]y1,int[]z1) {
        
        final XYSeries series1 = new XYSeries("Actual Sales");
        for(int i=0;i<x.length;i++) {
        	series1.add(x[i], y[i]);
        }
        
        final XYSeries series2 = new XYSeries("Predicted Sales");
        for(int i=0;i<x.length;i++) {
        	 
             series2.add(x[i],z[i]);
        }
        
        final XYSeries series3 = new XYSeries("Forecasted Sales");
        for(int i=0;i<x1.length;i++) {
        	 
             series3.add(x1[i],z1[i]);
        }
        series3.add(x[x.length-1],y[y.length-1]);
       
 
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
                
        return dataset;
        
    }
    
    
    private JFreeChart createChart(final XYDataset dataset,String x) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            x,      // chart title
            "Months",                      // x axis label
            "Sales",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        //final StandardLegend legend = (StandardLegend) chart.getLegend();
        //legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));

        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));        

        
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
       
    }
}


class gui {
	String[] columnNames;
	Object[][] data;
	JPanel ap1,ap2,ap3,ap4,ap5;
  JLabel l;
  JFrame f,f1;
  JTable table1,table2,table3;
  JScrollPane scrollPane,scrollPane1;
  JButton b0,b1,b2,b3,b4;
  JTextArea textarea,textarea1;
  JFileChooser jfc;
  JTextField tf;
  String s;
  
 
  public void newframe1(int[]x,int[]y,int[]z,int[]x1,int[]y1,int[]z1,float a,float b,float rmse)
  {
	  
	  
	    f1= new JFrame("New Frame");
	    ap1= new JPanel();
	   	ap2= new JPanel();
	   	ap3= new JPanel();
	   	ap4= new JPanel();
	   	ap5 = new JPanel();
	   	b4 = new JButton("BACK");
	   	
	   	//JPanel bgpanel= new JPanel();
	   	
	   	// For First Panel (first)
	   	String[] columnNames = {"Months","Sales_Actual", "SalesPredicted"};
	   	
	   	Object[][] data = new Object[x.length][3];
	   	Object[][] data1 = new Object[x1.length][3];
	   	int j=0;
	   
	   	for (int i=0;i<x.length;i++) {
	   		
	   			data[i][j]=x[i];
	   			data[i][j+1]=y[i];
	   			data[i][j+2]=z[i];
	   		
	   	}
	   	for (int i=0;i<x1.length;i++) {
	   		
   			data1[i][j]=x1[i];
   			data1[i][j+1]=y1[i];
   			data1[i][j+2]=z1[i];
   		
	   	}
	   	new Windows("MU",ap3,x,y,z,x1,y1,z1);
	   	table1 = new JTable(data, columnNames);
	    table1.setBounds(3,20,100,150);
	    
	    
	    table3 = new JTable(data1, columnNames);
	     table3.setBounds(300,530,600,195);
	    scrollPane1 = new JScrollPane(table3);
		scrollPane1.setBounds(0, 0, 500, 150);
		ap5.add(scrollPane1);
		ap5.setBounds(30,530,480,300);
	  	table3.setFillsViewportHeight(true);
	   	scrollPane = new JScrollPane(table1);
	   	scrollPane.setBounds(0, 0, 500, 150);
	   	ap1.add(scrollPane);
	 
	   	ap5.add(table3);
	   	ap1.setBounds(30,30,500,500);
	   	
           
	    ap2.setBounds(60,250,500,200);    
	      
	    ap1.add(ap2);
	      
	     
      
	     
	      
		   
		  
		  
		  f1.add(ap5);
	   	//For Second Panel(middle)
		String[] columnNames1 = {"a","b","RMSE"};
	   	Object[][] data2 = new Object[2][3];
	
	   	for(int i=0;i<1;i++)
	   	{
	   		data2[i][j] = columnNames1[0];
	   		data2[i][j+1] = columnNames1[1];
	   		data2[i][j+2] = columnNames1[2];
	   		data2[i+1][j] = a;
	   		data2[i+1][j+1]=b;
	   		data2[i+1][j+2]=rmse;
	   	}
	   	 table2 = new JTable(data2, columnNames1);
	   	 table2.setBounds(10,10,800,200);
	   	 
	   	 
	   	 scrollPane = new JScrollPane(table2);
	   
	   	table2.setFillsViewportHeight(true);
	   	ap2.add(table2);
	   
	   	// For Third Panel(last)
	   	textarea1= new JTextArea(10,20);
	   	textarea1.setBounds(10,30, 200,200);
	   	
	   	ap3.setBounds(600,30,700,450);    
	   	b4.setBounds(900, 600, 100, 30);
	   	
	   	f1.add(ap1);
	   	f1.add(ap3);
	   	f1.setLayout(null);
	   	f1.add(scrollPane);
	   	//f1.add(scrollPane1);
	 //  	f1.add(bgpanel);
		f1.add(b4);
		f1.setSize(1800, 800);
		f1.getContentPane().setBackground(Color.gray);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		
		//button to Go back
		  b4.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
			    	
				 f1.setVisible(false);
			  }
		  });
		  
  }
  
  
  public void myGUI()  {  
  
	  
	   f= new JFrame("Sales Prediction");
	  jfc= new JFileChooser();
	  tf = new JTextField(10);
	  b0= new JButton("Browse File");
	  b1= new JButton("Simple LR");
	  b2= new JButton("Trend Series");
	 // b3= new JButton("Logistic Regression");
	  l= new  JLabel("Click to choose");
	  
	 
	  b0.addActionListener(new ActionListener() {
			     public void actionPerformed(ActionEvent e) {
			    	 int status =jfc.showOpenDialog(null);
			 	    if (status == JFileChooser.APPROVE_OPTION) {
			 	      File selectedFile = jfc.getSelectedFile();
			 	      tf.setText(selectedFile.getParent());
			 	     tf.setText(selectedFile.getName());
			 	     s = selectedFile.getParent() + "\\" + selectedFile.getName();
			 	     	} else if (status == JFileChooser.CANCEL_OPTION) {
			 	      tf.setText("Cancelled");
	
			 	    }
				      }
				    }); 
	  
	  //button to display simple linear regression
	  b1.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
		    	
			  LinearRegression l = new LinearRegression();
			  l.ReadingCsv(s);
			  l.LinearRegressionResult();
			  newframe1(l.trainmonths,l.trainSales,l.get_result(),l.getMonths(),l.getSales(),l.getPredictedRedult(),l.get_a(),l.get_b(),l.get_ErrorRate());
			 
			  System.out.println(s);
			  System.out.println(l.get_ErrorRate());
		 	    }
	  });
	  
	  //button to display Seasonalized Linear Regression
	  b2.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
		    	
			  SeasonaliseReg sr = new SeasonaliseReg();
			  sr.ReadingCsv(s);
			  
			  sr.getPredictedData(sr.getTrained_data(sr.getXI(), sr.getDeseasonalizeData(sr.getMonthlySt(sr.getstit(sr.getCMA(sr.getSmoothValues()), sr.getYI())), sr.getYI())),sr.getMonthlySt(sr.getstit(sr.getCMA(sr.getSmoothValues()), sr.getYI())));
			  newframe1(sr.getX(),sr.getY(),sr.getPredictedResult(),sr.getX1(),sr.getY1(),sr.getForcastedResult(sr.getMonthlySt(sr.getstit(sr.getCMA(sr.getSmoothValues()), sr.getYI()))),sr.get_A(),sr.get_B(),sr.get_Error());
			 
		  }
	  });
	  
	
	  f.add(l);
	  f.add(tf);
	  f.add(b0);
	  f.add(b1);f.add(b2);
	  f.setSize(400,400);  
	  f.setLayout(new FlowLayout());  
	  f.setVisible(true);  
			
		
	}
  		
	
}

public class LinearRegressionImp{
	
		 public static void main(String[] args) 
		 { 
			 gui g = new gui();
			 g.myGUI();
			 
		}

}

