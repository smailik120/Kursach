
public class Mathematics {
	public Mathematics() {
		
	}
	
	public int combinations(long n, long k) {
		return (int)(factorial(n) / (factorial(n-k) * factorial(k)));
	}
	
	public int sumOfCombinations(int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			sum += combinations(n, i);
			System.out.println(sum);
		}
		return sum;
	}
	
	public long  factorial(Long n) {
		long  temp = 1;
		for(int i = 1; i <= n; i++) {
			temp = temp * i;
		}
		return temp;
	}
}
