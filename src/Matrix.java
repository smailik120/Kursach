import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Matrix {
	private String[][] array;
	private int money;
	public Matrix(String[][] array) throws IOException {
		this.array = array;
		this.money = money;
	}
	
	public int getIndexForMin(Double[] arr) {
		double min = Double.MAX_VALUE;
		int index = 0;
		for(int i = 1; i < arr.length; i++) {
			if(arr[i] <= min) {
				min = arr[i];
				index = i;
			}
		}
		return index;
	}
	
	public int getIndexForMax(Integer[] arr) {
		int max = -10;
		int index = 0;
		for(int i = 1; i < arr.length; i++) {
			if(arr[i] >= max) {
				max = arr[i];
				index = i;
			}
		}
		return index;
	}
	public int getSeddlePoint() {
		int min = 1000000;
		int max = -min;
		int result = 0;
		int counter = 0;
		for(int i = 1; i < array[0].length; i++)
		{
			for(int j = 1; j < array.length; j++)
			{
				if(Integer.parseInt(array[j][i]) >= max) {
					max = Integer.parseInt(array[j][i]);
				}
			}
			System.out.println(max);
			if(max <= min) {
				min = max;
				result = i;
			}
			max = -1000000;
		}
		return result;
	}
	
	public int getGurvic(double c) {
		double result = 1000000;
		int minIndex = 0;
		int maxIndex = 0;
		int min = -100000;
		int max = 100000;
		int resultIndex = 0;
		double s[] = new double[array[0].length];
		for(int i = 1; i < array[0].length; i++)
		{
			for(int j = 1; j < array.length; j++)
			{
				int current = Integer.parseInt(array[j][i]);
				if(current >= min) {
					min = current;
					maxIndex = j;
				}
				if(current <= max) {
					max = current;
					minIndex = j;
				}
			}
			s[i] = c*min + (1-c) * max;
			if(s[i] <= result) {
				result = s[i];
				resultIndex = i;
			}
			min = -100000;
			max = 100000;
		}
		return resultIndex;
	}
	
	public int getBaes(String[][] probabilties) {
		double min = 1000000;
		int counter = 0;
		int result = 0;
		double sum = 0;
		for(int i = 1; i < array[0].length; i++)
		{
			for(int j = 1; j < array.length; j++)
			{
				sum += Integer.parseInt(array[j][i]) * Double.parseDouble(probabilties[j - 1][0]);
				System.out.println("prob" + probabilties[j-1][0]);
			}
			if(sum <= min) {
				min = sum;
				result = i;
			}
			sum = 0;
		}
		return result;
	}
	
	public int getMonte(int counter) {
		int b = 5000;
		double min = 1000000;
		int result = 0;
		Boolean[] prov = new Boolean[array.length];
		for(int i = 1; i < array.length; i++)
		{
			prov[i] = false;
		}
		Integer[] step = new Integer[array[0].length]; 
		Double[] mean = new Double[array[0].length];
		Double[] meanOnStep = new Double[array[0].length];
		for(int i = 1; i < array[0].length; i++)
		{
				meanOnStep[i] = 0.0;
		}
		for(int i = 1; i < array[0].length; i++)
		{
			step[i] = 0;
		}
		int rand = 1 + (int)(Math.random()*array.length - 1);
		for(int i = 1; i < array[0].length; i++)
		{
			rand = 1 + (int)(Math.random()*array.length - 1);
			step[i]++;
			meanOnStep[i] = Double.parseDouble(array[rand][i]);
			mean[i] = meanOnStep[i] - b*Math.sqrt(2*Math.log(i));
		}
		for(int i = 2; i < counter; i++)
		{
			rand = 1 + (int)(Math.random()*array.length - 1);
			for(int j = 1; j < array[0].length; j++)
			{
				mean[j] = (1/step[j])*(meanOnStep[j] + Double.parseDouble(array[rand][j])) - b*Math.sqrt((2*Math.log(i+array[0].length))/(step[j]+1));
			}
			int currentIndex = getIndexForMin(mean);
			step[currentIndex]++;
			meanOnStep[currentIndex] = meanOnStep[currentIndex] + Double.parseDouble(array[rand][currentIndex]);
		}
		for(int i = 0; i < step.length - 1; i++) {
			meanOnStep[i + 1] = meanOnStep[i+1] *  (1.0/step[i + 1]);
		}
		return getIndexForMin(meanOnStep);
	}
	
	public int getTomphson() throws IOException {
		try {
			test();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileReader reader = new FileReader("src//python.txt");
		Scanner scan = new Scanner(reader);
		int res = scan.nextInt();
		System.out.println(res);
		reader.close();
		return res;
	}
	
	public void test() throws IOException, InterruptedException {
		InputStream is = Runtime.getRuntime().exec(new String[] {"python","src//Thompson.py"}).getInputStream();
        int i;
        while((i = is.read()) != -1)
        {
        }
        is.close();
		//Runtime.getRuntime().exec(new String[] {"cmd.exe", "/C","C:/test/start.bat"});
		//ProcessBuilder processBuilder = new ProcessBuilder("C:/test/start.bat");
		//Runtime rt = Runtime.getRuntime();
		//Process p = rt.exec("python C:/test/Thompson.py");
		//Thread.sleep(1000);
        //p.destroy();
		//processBuilder.start();
		
		//processBuilder.redirectErrorStream();
        System.out.println("Process destroyed bat");
       }
}
