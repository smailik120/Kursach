
public class Mathematics {
	public Mathematics() {
		
	}
	
	public int combinations(int n, int k) {
		return factorial(n) / (factorial(n-k) * factorial(k));
	}
	
	public int sumOfCombinations(int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			sum += combinations(n, i);
		}
		return sum;
	}
	
	public int factorial(int n) {
		int temp = 1;
		for(int i = 1; i <= n; i++) {
			temp = temp * i;
		}
		return temp;
	}
}
