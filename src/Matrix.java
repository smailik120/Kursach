
public class Matrix {
	private String[][] array;
	public Matrix(String[][] array) {
		this.array = array;
	}
	
	public int getSeddlePoint() {
		System.out.println(array.length);
		int min = 1000000;
		int max = -min;
		for(int i = 1; i < array.length; i++)
		{
			for(int j = 1; j < array[i].length; j++)
			{
				if(Integer.parseInt(array[i][j]) <= min) {
					min = Integer.parseInt(array[i][j]);
				}
			}
			if(min >= max) {
				max = min;
			}
			min = 1000000;
		}
		return max;
	}
}
