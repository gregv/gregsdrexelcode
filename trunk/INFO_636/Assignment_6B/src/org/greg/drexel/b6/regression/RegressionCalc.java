/**
 * 
 */
package org.greg.drexel.b6.regression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose:
 * @version
 * Notes:
 *      0                 1                  2             3               4           
 * Program Number   Estimated(N) LOC   Actual(N) LOC  Estimated Time  Actual Time
    1.2 (1B)        200                 157             300             186
    2.1 (2A)        60                  92              221             406
    2.2 (2B)        70                  81              313             560
    3.2 (3B)        5                   4               116             132
    4.2 (4B)        53                  43              175             195
    5.2 (5B)        39                  57              105             179
 */

public class RegressionCalc
{
       private List<Double> BETA0 = null;
       private List<Double> BETA1 = null;
       private ArrayList<ArrayList<String>> stringData = null;
       private ArrayList<ArrayList<Double>> data = null;
        
       public RegressionCalc( ArrayList<ArrayList<String>> input )
       {
           BETA0 = new ArrayList<Double>();
           BETA1 = new ArrayList<Double>();
           this.stringData = input;
           convertStringDataToDoubleData();
       }
       
       public void convertStringDataToDoubleData()
       {
           ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
           
           
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
       
       
       public void calculateTimeEstimateRegression()
       {
           calculateEstimateRegression(1, 4);
       }
       
       public void calculateSizeEstimateRegression()
       {
           calculateEstimateRegression(1, 2);
       }
       
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
          
          for( int y=0; y<n;y++ )
          {
              double Xi2 = 0;
              double Yi2 = 0;
              ArrayList<Double> row = data.get(y);
              
              for( int x=0; x<row.size(); x++ )
              {
                  System.out.println( row.get(x) );
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
          
           
          double rSquared     = 0;
          double rSquared_top = 0;
          double rSqured_bot  = 0;
          rSquared_top = (n * XiYiSum) - (Xsum * Ysum);
          rSqured_bot =  Math.sqrt( ( n * Xi2Sum - Math.pow(Xsum,2) ) * ( n * Yi2Sum - Math.pow(Ysum,2) ) );
           
          rSquared = Math.pow( rSquared_top / rSqured_bot, 2 );
           
          
          System.out.println("Xsum         = " + Xsum );
          System.out.println("Ysum         = " + Ysum );
          System.out.println("XiYiSum      = " + XiYiSum );
          System.out.println("Xi2Sum       = " + Xi2Sum );
          System.out.println("Yi2Sum       = " + Yi2Sum );
          System.out.println("XiAvg        = " + XiAvg );
          System.out.println("YiAvg        = " + YiAvg );
          System.out.println( "" );
          System.out.println("rSquaredt    = " +  rSquared_top );
          System.out.println("rSquaredb    = " +  rSqured_bot );
          System.out.printf("rSquared      =  %.5f\n", rSquared );
          
          if( rSquared < 0.5 )
          {
              BETA1.add( Ysum / Xsum );
              BETA0.add( 0.0 );
          }
          else
          {
              double beta1 = (XiYiSum-n * XiAvg * YiAvg) / (Xi2Sum - n * XiAvg * XiAvg);
              BETA1.add( beta1 );
              BETA0.add( YiAvg - beta1*XiAvg );
          }
          
       }
       
       public List<Double> getBeta0Values()
       {
           return BETA0;
       }
       
       public List<Double> getBeta1Values()
       {
           return BETA1;
       }
       
       public static void main( String args[] )
       {
           ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
           ArrayList<String> row = new ArrayList<String>();
           row.add("1.2");
           row.add("200");
           row.add("157");
           row.add("300");
           row.add("186");
           input.add( row );
           
           row = new ArrayList<String>();
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
           
           RegressionCalc r = new RegressionCalc( input );
           
           r.calculateSizeEstimateRegression();
           r.calculateTimeEstimateRegression();
           
           System.out.println("BETA1: " + r.getBeta1Values() );
           System.out.println("BETA0: " + r.getBeta0Values() );
           
       }
       
}
