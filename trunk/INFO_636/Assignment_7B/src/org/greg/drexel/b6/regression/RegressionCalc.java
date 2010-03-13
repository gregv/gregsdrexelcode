package org.greg.drexel.b6.regression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose:
 *  This class calculations regression parameters B0 and B1
 * 
 *  Updated in Program 7B to perform projections given an estimated value
 * 
 * @version 2.0
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
 *   
 *  This program only uses a 70% prediction interval, if you want to use something else
 *  modify the array (tableA2_70percentConf) per Table A2 in the text.
 *  
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
       public static final String STDDEV = "STDDEV";
       public static final String T = "T";
       public static final String RANGE = "RANGE";
       public static final String UPI = "UPI";
       public static final String LPI = "LPI";
       public static final String PREDICTED_VALUE = "PREDICTED VALUE";
       public static final String ESTIMATED_VALUE = "ESTIMATED VALUE";
       
       private static final double[] tableA2_70percentConf =
       {
           // Degrees of freedom
           // 1     2     3      4       5     6       7      8      9      10     15    20      30     inf
           1.963, 1.386, 1.250, 1.190, 1.156, 1.134, 1.119, 1.108, 1.100, 1.093, 1.074, 1.064, 1.055, 1.036 
       };
       
       
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
        * Method: getTvalue<br/>
        * Get the t value with 70% confidence interval from Table A2 in the text
        * 
        * @param n - The size of the dataset
        */
       public double getTvalue( int n )
       {
           int degreesOfFreedom = n - 2;
           
           if( degreesOfFreedom < 1 )
           {
               return tableA2_70percentConf[0];
           }
           if( degreesOfFreedom <= 10 )
           {
               return tableA2_70percentConf[ degreesOfFreedom-1 ];
           }
           else if( degreesOfFreedom > 10 && degreesOfFreedom <= 15 )
           {
               return tableA2_70percentConf[10];
           }
           else if( degreesOfFreedom > 15 && degreesOfFreedom <= 20 )
           {
               return tableA2_70percentConf[11];
           }
           else if( degreesOfFreedom > 20 && degreesOfFreedom <= 30 )
           {
               return tableA2_70percentConf[12];
           }
           else
           {
               return tableA2_70percentConf[13];
           }
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
        * @param estimatedValue - the estimated regression value
        * 
        */
       public void calculateTimeEstimateRegression( double estimatedValue )
       {
           calculateEstimateRegression(1, 4, estimatedValue);
       }
       
       /**
        * Method: calculateTimeEstimateRegression<br/>
        * Helper function to get size regression estimates
        * 
        * @param estimatedValue - the estimated regression value
        * 
        */
       public void calculateSizeEstimateRegression( double estimatedValue )
       {
           calculateEstimateRegression(1, 2, estimatedValue);
       }
       
       
       /**
        * Method: calculateEstimateRegression<br/>
        * Calculate all of the regression parameters
        * 
        * @param firstColumn - the X column to calculate regression
        * @param secondColumn - the Y column to calculate regression
        * @param estimatedValue - the estimated regression value
        */
       public void calculateEstimateRegression(int firstColumn, int secondColumn, double estimatedValue )
       {
           // Estimated Object LOC Xi, Actual New and Changed LOC Yi
           
           // Determine n ( = the number of rows in data )
           int n = data.size();
           
          double Xsum = 0; // Sum of column X
          double Ysum = 0; // Sum of column Y
          
          double XiYiSum = 0;  // Sum of Xi * Yi for each row
         
          double Xi2Sum = 0; // The sum of all of the X columns values squared
          double Yi2Sum = 0; // The sum of all of the Y columns values squared
          
          double XiAvg = 0;  // Average of X column
          double YiAvg = 0;  // Average of Y column
          
          double rSquared     = 0;
          double rSquared_top = 0;
          double rSqured_bot  = 0;
          
          double beta0 = 0.0; // Beta values
          double beta1 = 0.0;
          
          double sumYiB0B1XiSquared = 0.0; // Used for prediction calculations
          double sumXiXavgSquared = 0.0;
          
          double Yi = 0.0; // Used for each element
          double Xi = 0.0;
          
          
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
          rSquared_top = (n * XiYiSum) - (Xsum * Ysum);
          rSqured_bot =  Math.sqrt( ( n * Xi2Sum - Math.pow(Xsum,2) ) * ( n * Yi2Sum - Math.pow(Ysum,2) ) );
          rSquared = Math.pow( rSquared_top / rSqured_bot, 2 );
           
          
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
        
          
          
          //
          // Calculate data needed for regression estimations
          //
          for( int y=0; y<n;y++ )
          {
              ArrayList<Double> row = data.get(y);
              
              Xi = row.get(firstColumn);
              Yi = row.get(secondColumn);
              
              sumYiB0B1XiSquared += Math.pow( Yi-beta0 - (beta1 * Xi), 2 );
              sumXiXavgSquared += Math.pow( Xi - XiAvg , 2 );
          } // end for y
          
          
          double predictedValue = estimatedValue * beta1 + beta0;
          double variance = 0.0;
          
          if( n <= 2 )
          {
              variance = 0.0;
          }
          else
          {
              variance = (1 / (n-2)) * sumYiB0B1XiSquared;
          }
              
          
          double stddev   = Math.pow( variance, 0.5 );
          double t = getTvalue(n);
          
          
          // Break up large range calculation into smaller, manageable equations
          double range1 = 1 + ( 1.0/n );
          double range2 = Math.pow(estimatedValue - XiAvg, 2 );
          double range3 = range2 / sumXiXavgSquared;
          double range4 = Math.pow ( range1 + range3, 0.5 );
          double range = range4 * stddev * t;
          
          // Determine UPI and LPI
          double upperPredictionInterval = predictedValue + range;
          double lowerPredictionInterval = predictedValue - range;
          
          
          // Store all supporting values
          supportingRegressionValues.put(XSUM       ,       Round(Xsum,1)       + "" );
          supportingRegressionValues.put(YSUM       ,       Round(Ysum,1)       + "" );
          supportingRegressionValues.put(XIYISUM    ,       Round(XiYiSum,1)    + "" );
          supportingRegressionValues.put(XI2SUM     ,       Round(Xi2Sum,1)     + ""  );
          supportingRegressionValues.put(YI2SUM     ,       Round(Yi2Sum,1)     + "" );
          supportingRegressionValues.put(XIAVG      ,       Round(XiAvg,1)      + "" );
          supportingRegressionValues.put(YIAVG      ,       Round(YiAvg,1)      + "" );
          supportingRegressionValues.put(RSQUARED   ,       Round(rSquared,3)   + "" );
          supportingRegressionValues.put(N          ,       n                   + "" );
          supportingRegressionValues.put(BETA0      ,       Round(beta0,3)      + "" );
          supportingRegressionValues.put(BETA1      ,       Round(beta1,3)      + "" );
          supportingRegressionValues.put(PREDICTED_VALUE,   Round(predictedValue,0) + "" );
          supportingRegressionValues.put(ESTIMATED_VALUE,   Round(estimatedValue,0) + "" );
          supportingRegressionValues.put(RANGE,             Round(range,0)      + "" );
          supportingRegressionValues.put(UPI,               Round(upperPredictionInterval,0) + "" );
          supportingRegressionValues.put(LPI,               Round(lowerPredictionInterval,0) + "" );
          supportingRegressionValues.put(STDDEV,            Round(stddev,3)     + "" );
          supportingRegressionValues.put(T,                 t                   + "" );
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
           
           r.calculateSizeEstimateRegression(36);
           System.out.println("Regression Values = " + r.getSupportingRegressionValues() );
           
           
           r.calculateTimeEstimateRegression(36);
           System.out.println("Regression Values = " + r.getSupportingRegressionValues() );
           
       }
       
}
