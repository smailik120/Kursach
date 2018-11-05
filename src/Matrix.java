
public class Matrix {
	private String[][] array;
	public Matrix(String[][] array) {
		this.array = array;
	}
	
	public int getSeddlePoint() {
		int min = 1000000;
		int max = -min;
		int result = 0;
		for(int i = 1; i < array[0].length; i++)
		{
			for(int j = 1; j < array.length; j++)
			{
				if(Integer.parseInt(array[j][i]) >= max) {
					max = Integer.parseInt(array[j][i]);
				}
			}
			if(max <= min) {
				min = max;
				result = i;
			}
			max = -1000000;
		}
		return result;
	}
	
	public int getBaes(String[][] probabilties) {
		double min = 1000000;
		int result = 0;
		double sum = 0;
		for(int i = 1; i < array[0].length; i++)
		{
			for(int j = 1; j < array.length; j++)
			{
				sum += Integer.parseInt(array[j][i]) * Double.parseDouble(probabilties[j - 1][0]);
			}
			if(sum <= min) {
				min = sum;
				result = i;
			}
			sum = 0;
		}
		return result;
	}
	
}
