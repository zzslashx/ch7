//For msft_2000_2010.txt, the highest price comes before the lowest price. What does that mean for someone who bought Microsoft stock in 2000?
// -> It means that they lost money

//Look up what happened in the world around the lowest month for sbux_2007_2016.txt and sp500_1925_1945.txt. Does the timing make sense?
//Great depression and Global Financial Crisis, yes it does make sense. 



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("rising.txt"));
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
    return movingAverage(history, holding, boughtAt);  
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
}
