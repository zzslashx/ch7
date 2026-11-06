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

System.out.printf("Biggest drop is: %s %d to %s %d (%.2f%%)%n",
    monthName(months[highestDrop]), years[highestDrop],
    monthName(months[highestDrop + 1]), years[highestDrop + 1], pct);
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
}
