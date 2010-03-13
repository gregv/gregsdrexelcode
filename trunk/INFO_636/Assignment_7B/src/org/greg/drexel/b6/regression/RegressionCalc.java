package org.greg.drexel.b6.regression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose:
 *  This class calculations regression parameters B0 and B1
 * 
 * @version 1.0
 * 
 * Notes:
 * 
 * The file contents used in this class should be in this form:
 *      0                 1                  2             3               4           
 * Program Number   Estimated(N) LOC   Actual(N) LOC  Estimated Time  Actual Time
 *    1.2 (1B)        200                 157             300             186
 *    2.1 (2A)        60                  92              221             406
 *   2.2 (2B)        70                  81              313             560
 *   3.2 (3B)        5                   4               116             132
 *   4.2 (4B)        53                  43              175             195
 *   5.2 (5B)        39                  57              105             179
 */


public class RegressionCalc
{
       private ArrayList<ArrayList<String>> stringData = null;
       private ArrayList<ArrayList<Double>> data = null;
       private HashMap<String, String> supportingRegressionValues = null;
       public static final String XSUM = "XSUM";
       public static final String YSUM = "YSUM";
       public static final String XIYISUM = "XIYISUM";
       public static final String XI2SUM = "XI2SUM";
       public static final String YI2SUM = "YI2SUM";
       public static final String XIAVG = "XIAVG";
       public static final String YIAVG = "YIAVG";
       public static final String RSQUARED = "RSQUARED";
       public static final String N = "N";
       public static final String BETA1 = "BETA1";
       public static final String BETA0 = "BETA0";
       private static final double[] tableA2_70percentConf =
       {1.963, 1.386, 1.250, 1.190, 1.156, 1.134, 1.119, 1.108, 1.100, 1.093, 1.074, 1.064, 1.055, 1.036 };
       
       
       /**
        * Method: RegressionCalc<br/>
        * Initialize the regression data
        * 
        * @param input - data assembled in rows and columns to be calculated
        */
       public RegressionCalc( ArrayList<ArrayList<String>> input )
       {
           supportingRegressionValues = new HashMap<String,String>();
           
           this.stringData = input;
           convertStringDataToDoubleData();
       }
       
       
       /**
        * Method: convertStringDataToDoubleData<br/>
        * Converts the ArrayList<String> to be ArrayList<Double>
        * 
        */
       private void convertStringDataToDoubleData()
       {
           ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
           
           // Convert from stringData to data
           for( ArrayList<String> arr : stringData )
           {
               ArrayList<Double> row = new ArrayList<Double>();
               for( String s : arr )
               {
                   row.add( Double.parseDouble(s) );
               }
               result.add( row );
           }
           
           this.data = result;
       }
       
       
       /**
        * Method: calculateTimeEstimateRegression<br/>
        * Helper function to get time regression estimates
        * 
        */
       public void calculateTimeEstimateRegression()
       {
           calculateEstimateRegression(1, 4);
       }
       
       /**
        * Method: calculateTimeEstimateRegression<br/>
        * Helper function to get size regression estimates
        * 
        */
       public void calculateSizeEstimateRegression()
       {
           calculateEstimateRegression(1, 2);
       }
       
       
       /**
        * Method: calculateEstimateRegression<br/>
        * Calculate all of the regression parameters
        * 
        * @param firstColumn - the X column to calculate regression
        * @param secondColumn - the Y column to calculate regression
        */
       public void calculateEstimateRegression(int firstColumn, int secondColumn )
       {
           // Estimated Object LOC Xi, Actual New and Changed LOC Yi
           
           // Determine n ( = the number of rows in data)
           int n = data.size();
           
          // Get the sum of columns 1 and 2
          double Xsum = 0;
          double Ysum = 0;
          double XiYiSum = 0;
         
          double Xi2Sum = 0;
          double Yi2Sum = 0;
          
          double XiAvg = 0;
          double YiAvg = 0;
          
          // Store and calculate regression estimates
          for( int y=0; y<n;y++ )
          {
              double Xi2 = 0;
              double Yi2 = 0;
              ArrayList<Double> row = data.get(y);
              
              for( int x=0; x<row.size(); x++ )
              {
                  if( x == firstColumn )
                  {
                      Xsum += row.get(x);
                      Xi2 = Math.pow( row.get(x), 2 );
                  }
                  else if( x == secondColumn )
                  {
                      Ysum += row.get(x);
                      Yi2 = Math.pow( row.get(x), 2 );
                  }
                  
              } // end for x
              
              XiYiSum += row.get(firstColumn) * row.get(secondColumn);
              Xi2Sum += Xi2;
              Yi2Sum += Yi2;
              
          } // end for y
          
          XiAvg = Xsum / (n * 1.0);
          YiAvg = Ysum / (n * 1.0);
          
          // Calculate rSquared
          double rSquared     = 0;
          double rSquared_top = 0;
          double rSqured_bot  = 0;
          rSquared_top = (n * XiYiSum) - (Xsum * Ysum);
          rSqured_bot =  Math.sqrt( ( n * Xi2Sum - Math.pow(Xsum,2) ) * ( n * Yi2Sum - Math.pow(Ysum,2) ) );
          rSquared = Math.pow( rSquared_top / rSqured_bot, 2 );
           
          double beta0 = 0.0;
          double beta1 = 0.0;
          
          // Determine how to calculate Beta1 and Beta0
          if( rSquared < 0.5 )
          {
              beta1 =  Round(Ysum / Xsum,5);
              beta0 =  0.0;
          }
          else
          {
              double tmp = (XiYiSum-n * XiAvg * YiAvg) / (Xi2Sum - n * XiAvg * XiAvg);
              beta1 =  Round(tmp,5) ;
              beta0 =  Round(YiAvg - tmp*XiAvg,5);
          }
        
          double sumYiB0B1XiSquared = 0.0;
          double Yi = 0.0;
          double Xi = 0.0;
          
          for( int y=0; y<n;y++ )
          {
              ArrayList<Double> row = data.get(y);
              
              Xi = row.get(firstColumn);
              Yi = row.get(secondColumn);
              
              sumYiB0B1XiSquared += Math.pow( Yi-beta0 - (beta1 * Xi), 2 );
          } // end for y
          
          
          System.out.println( "Stuff = " + sumYiB0B1XiSquared );
          
          double variance = (1 / (n-2)) * sumYiB0B1XiSquared;
          double stddev   = Math.pow( variance, 0.5 );
          
          System.out.println( "variance = " + variance );
          System.out.println( "stddev = " + stddev );
          
          // Store all supporting values
          rSquared = Round( rSquared, 5 );
          supportingRegressionValues.put(XSUM       , Xsum + "" );
          supportingRegressionValues.put(YSUM       , Ysum + "" );
          supportingRegressionValues.put(XIYISUM    , XiYiSum + "" );
          supportingRegressionValues.put(XI2SUM     , Xi2Sum + ""  );
          supportingRegressionValues.put(YI2SUM     , Yi2Sum + "" );
          supportingRegressionValues.put(XIAVG      , XiAvg + "" );
          supportingRegressionValues.put(YIAVG      , YiAvg + "" );
          supportingRegressionValues.put(RSQUARED   , rSquared + "" );
          supportingRegressionValues.put(N          , n + "" );
          supportingRegressionValues.put(BETA0      , beta0 + "" );
          supportingRegressionValues.put(BETA1      , beta1 + "" );
       }
       
       
       /**
        * Method: Round<br/>
        *   Round an input double to a specified number of decimal places
        * 
        * @param value - the input double
        * @param place - the decimal place to round to
        */
       public double Round( double value, int place )
       {
           // see the Javadoc about why we use a String in the constructor
           // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
           BigDecimal bd = null;
           try
           {
               bd = new BigDecimal(Double.toString(value));
           }
           catch( NumberFormatException nfe )
           {
               // Return the same value if it is NaN
               return value;
           }
           
           bd = bd.setScale(place,BigDecimal.ROUND_HALF_UP);
           return bd.doubleValue();
       }
       
       
       public HashMap<String,String> getSupportingRegressionValues()
       {
           return supportingRegressionValues;
       }
       
       
       // A tester main method - not to be used in production
       public static void main( String args[] )
       {
           ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
           ArrayList<String> row = new ArrayList<String>();
        
           
           row.add("2.1");
           row.add("60");
           row.add("92");
           row.add("221");
           row.add("406");
           input.add( row );
           
           row = new ArrayList<String>();
           row.add("2.2");
           row.add("70");
           row.add("81");
           row.add("313");
           row.add("560");
           input.add( row );
           
           
           row = new ArrayList<String>();
           row.add("3.2");
           row.add("5");
           row.add("4");
           row.add("116");
           row.add("132");
           input.add( row );
           
           RegressionCalc r = new RegressionCalc( input );
           
           r.calculateSizeEstimateRegression();
           System.out.println("Regression Values = " + r.getSupportingRegressionValues() );
           
           
           r.calculateTimeEstimateRegression();
           System.out.println("Regression Values = " + r.getSupportingRegressionValues() );
           
       }
       
}
