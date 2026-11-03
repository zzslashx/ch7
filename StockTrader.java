import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("tiny.txt"));
        String symbol = in.next();
        int n = in.nextInt();
        int[] years = new int[n];
        int[] months = new int[n];
        double[] prices = new double[n];
        // TODO: create the three arrays, each with n elements
        loadData(in, years, months, prices);
        // TODO: call loadData, then printData
    }
    public static void loadData(Scanner in, int[] years, int[] months, double[] prices) {
        for (int i = 0; i < years.length; i++) {
            years[i] = in.nextInt();
            months[i] = in.nextInt();
            prices[i] = in.nextDouble();
        }
    }
  //  public static String monthName(int month){
	//	String[] names = {"Jan","Feb","Mar","Apr","May","June","Aug","Sept", "Oct", "Nov", "Dec"};
	//	}
//	public static void printData(int[] years, int[] months, double[] prices){
	//	for (int i=0;i<months.length;i++){
	//		
	//		}
	//	}	
}

