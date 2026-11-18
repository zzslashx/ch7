//For msft_2000_2010.txt, the highest price comes before the lowest price. What does that mean for someone who bought Microsoft stock in 2000?
// -> It means that they lost money

//Look up what happened in the world around the lowest month for sbux_2007_2016.txt and sp500_1925_1945.txt. Does the timing make sense?
//Great depression and Global Financial Crisis, yes it does make sense. 

//Overall, it seems like moving average is the best method strategy, but on some files, my method beat it. There wasn't a strategy that was best on every file, eeach method has it's own advantages
//The dip buyer did bad on rising.txt because the data was always rising and never dipped, so the dip buyer never bought or sold anything.
//Moving average beat buy and hold on aapl_2000_2010.txt. because the dot com crash occured in that time period
//My strategy on average beat buy and hold 7/12 times.
//I did change my rules to do better on one file. Making the strategy buy sooner and sell less often brought the AAPL average up to about the buy and hold level, but on AMZN and MSFT decreased a lot.


//                      Lowest    When      Highest    When
// tiny.txt             $30.00    Mar 2020  $70.00     Jul 2020
// rising.txt           $20.00    Jan 2010  $48.14     Dec 2014
// falling.txt          $30.36    Dec 2014  $100.00    Jan 2010
// zigzag.txt           $35.00    Jul 2010  $65.00     Mar 2010
// aapl_2000_2010.txt   $7.07     Mar 2003  $223.02    Mar 2010
// amzn_2000_2010.txt   $5.97     Sep 2001  $135.91    Nov 2009
// msft_2000_2010.txt   $15.81    Feb 2009  $43.22     Mar 2000
// ibm_2000_2010.txt    $53.01    Sep 2002  $130.32    Dec 2009
// goog_2004_2010.txt   $102.37   Aug 2004  $707.00    Oct 2007
// sbux_2007_2016.txt   $4.09     Nov 2008  $62.16     Oct 2015
// sp500_1925_1945.txt  $4.77     Jun 1932  $31.30     Sep 1929
// sp500_1990_2025.txt  $307.12   Oct 1990  $6,853.03  Dec 2025


// File                                Buy & hold         Dip buyer           Moving avg            My strategy
// tiny.txt                         $12,975.00     $14,975.00   $10,000.00                     $21,645.00
// rising.txt                      $24,031.86     $10,000.00   $20,411.92                    $22,940.60
// falling.txt                     $3,095.64      $3,159.28    $10,000.00  	                   $3,676.52
// zigzag.txt                     $12,101.39   $38,071.99   $9,848.78                       $ 10,680.20
// aapl_2000_2010.txt     $85,865.80   $36,610.49   $114,231.31                    $59,110.93
// amzn_2000_2010.txt   $19,886.04   $14,192.52   $38,170.87                  	  $27,728.76
// msft_2000_2010.txt     $7,237.50    $49,273.15   $7,208.23                  		 $8,875.66
// ibm_2000_2010.txt      $12,467.97   $27,998.86   $7,549.75                 		 $11,206.13
// goog_2004_2010.txt    $54,398.54   $13,655.41   $18,706.20                   	$ 69,781.10
// sbux_2007_2016.txt    $36,280.60   $15,759.50   $67,500.23                  	 $50,936.92
// sp500_1925_1945.txt  $16,362.00   $11,483.45   $37,195.81   					 $27038.55
// sp500_1990_2025.txt  $198,868.74  $40,836.68   $199,113.67     			$145,719.12

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("sp500_1990_2025.txt"));
        String symbol = in.next();
        int n = in.nextInt();

        int[] years = new int[n];
        int[] months = new int[n];
        double[] prices = new double[n];

        loadData(in, years, months, prices);
        printData(years, months, prices);
        int lowest = indexOfLowest(prices);

        System.out.println("Lowest: " + prices[lowest] + " in " + monthName(months[lowest])
            + " " + years[lowest]);

        int highest = indexOfHighest(prices);
        int highestDrop = indexOfBiggestDrop(prices);
        System.out.println("Highest: " + prices[highest] + " in " + monthName(months[highest])
            + " " + years[highest]);

double pct = (prices[highestDrop] - prices[highestDrop + 1]) / prices[highestDrop] * 100;

System.out.print("Biggest drop is: " + monthName(months[highestDrop]) + " " + years[highestDrop]
    + " to " + monthName(months[highestDrop + 1]) + " " + years[highestDrop + 1] + " (");
System.out.printf("%.2f%%", pct);
System.out.println(")");


double result = simulate(years, months, prices);
System.out.printf("Final value: $%,.2f%n", result);

}
    public static void loadData(Scanner in, int[] years, int[] months, double[] prices) {
        for (int i = 0; i < years.length; i++) {
            years[i] = in.nextInt();
            months[i] = in.nextInt();
            prices[i] = in.nextDouble();
        }
    }

    public static String monthName(int month) {
        String[] names = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return names[month - 1];
    }

    public static void printData(int[] years, int[] months, double[] prices) {
        for (int i = 0; i < months.length; i++) {
            System.out.printf("%s %d: $%.2f%n", monthName(months[i]), years[i], prices[i]);
        }
    }
    public static int indexOfLowest(double[] prices) {
        int lowestIndex = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < prices[lowestIndex])
                lowestIndex = i;
        }
        return lowestIndex;
    }
    public static int indexOfHighest(double[] prices) {
        int highestIndex = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > prices[highestIndex])
                highestIndex = i;
        }
        return highestIndex;
    }
public static int indexOfBiggestDrop(double[] prices) {
    int biggestDrop = 0;
    for (int i = 0; i < prices.length - 1; i++) {
        double drop = (prices[i] - prices[i + 1]) / prices[i];
        double best = (prices[biggestDrop] - prices[biggestDrop + 1]) / prices[biggestDrop];
        if (drop > best)
            biggestDrop = i;
    }
    return biggestDrop;
}
public static final double START_CASH = 10000.0;
public static final double FEE = 10.0;

public static double simulate(int[] years, int[] months, double[] prices) {
    double cash = START_CASH;
    int shares = 0;
    double boughtAt = 0.0;

    for (int i = 0; i < prices.length; i++) {
        double[] history = Arrays.copyOf(prices, i + 1);
        String action = decide(history, shares > 0, boughtAt);

        if (action.equals("BUY") && shares == 0) {
            int canBuy = (int) ((cash - FEE) / prices[i]);
            if (canBuy > 0) {
                shares = canBuy;
                cash = cash - shares * prices[i] - FEE;
                boughtAt = prices[i];
                System.out.printf("%s %d: BUY %d shares at $%.2f%n",
                        monthName(months[i]), years[i], shares, prices[i]);
            }
        } else if (action.equals("SELL") && shares > 0) {
            cash = cash + shares * prices[i] - FEE;
            System.out.printf("%s %d: SELL %d shares at $%.2f%n",
                    monthName(months[i]), years[i], shares, prices[i]);
            shares = 0;
            boughtAt = 0.0;
        }
    }
    return cash + shares * prices[prices.length - 1];
}
public static String decide(double[] history, boolean holding, double boughtAt) {
   //return buyAndHold(history, holding, boughtAt);  
    //return dipBuyer(history, holding, boughtAt);
    //return movingAverage(history, holding, boughtAt);
  return myStrategy(history, holding, boughtAt);  
}
public static String buyAndHold(double[] history, boolean holding, double boughtAt) {
    return "BUY";
}
public static String dipBuyer(double[] history, boolean holding, double boughtAt) {
    if (!holding) {
        if (history.length >= 3 && history[history.length - 1] < history[history.length - 2] && history[history.length - 2] < history[history.length - 3]) {
            return "BUY";
        }
    } else {
        if (history[history.length - 1] >= boughtAt * 1.10) {
            return "SELL";
        }
    }
    return "HOLD";
}

public static String movingAverage(double[] history, boolean holding, double boughtAt) {
	double lastTotal = 0;
	double average =0;
if(history.length>=12){
 
for (int i=history.length-1; i>=history.length-12;i--){
	lastTotal+=history[i];
	}
	average = lastTotal / 12;
}
if(history.length<12) return "HOLD";
else if(!holding && history[history.length-1] > average) return "BUY";
else if(holding && history[history.length-1] < average) return "SELL";
else return "HOLD";
}
// My strategy: luck decides if you buy, sell, or hold, but there are restraints 
// If I own nothing, I buy depending on luck, because prices tend to go up over time.
// If I own it, I almost never sell, because each trade costs $10.
// But if the price falls a lot below what I paid, I sell to avoid big losses.
// I use random chances so every time is different and there is a chance of getting lucky and getting a high amount.
public static String myStrategy(double[] history, boolean holding, double boughtAt) {
    double r = Math.random();

    if (!holding) {
        if (r < 0.6) return "BUY";
        return "HOLD";
    }
    if (history[history.length - 1] <= boughtAt * 0.85) {
        if (r < 0.5) return "SELL";
        return "HOLD";
    }
    if (r < 0.02) return "SELL";
    return "HOLD";
}
}
