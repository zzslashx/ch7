import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("goog_2004_2010.txt"));
        String symbol = in.next();
        int n = in.nextInt();

        int[] years = new int[n];
        int[] months = new int[n];
        double[] prices = new double[n];

        loadData(in, years, months, prices);
        printData(years, months, prices);
        int lowest = indexOfLowest(prices);

        System.out.println("TINY Lowest: " + prices[lowest] + " in " + monthName(months[lowest])
            + " " + years[lowest]);

        int highest = indexOfHighest(prices);

        System.out.println("TINY Highest: " + prices[highest] + " in " + monthName(months[highest])
            + " " + years[highest]);
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
}
