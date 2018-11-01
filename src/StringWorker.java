import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Comparator;
public class StringWorker implements Work{
	public StringWorker() {
		
	}
	@Override
	public TreeSet<String> work(int sizeY) {
		StringBuilder k = new StringBuilder("");
		TreeSet<String> result = new TreeSet<>(new Comparator<String>(){
		    @Override
		    public int compare(String s1, String s2) {
		    	int cmp = Integer.compare(s1.length(), s2.length());
	            return cmp != 0 ? cmp : s1.compareTo(s2);
		    }
		});
		for(int i = 1; i <= sizeY; i++) {
			k.append(Integer.toString(i));
		}
		for(int i = 1; i <= sizeY; i++) {
			for(int j = 0; j <= sizeY; j++) {
				for(int p = 0; p <= sizeY - i -j ; p++) {
					String t = k.substring(j, j + 1) + k.substring(j+p+1, j+i+p);
					result.add(t);
				}
			}
		}
		return result;
	}
	
	
	
}
