import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;
import java.util.Comparator;
public class StringWorker implements Work{
	public StringWorker() {
		
	}
	@Override
	public TreeSet<ArrayList<String>> work(ArrayList<Strategy> array) {
		/*
		StringBuilder k = new StringBuilder("");
		TreeSet<String> result = new TreeSet<>(new Comparator<String>(){
		    @Override
		    public int compare(String s1, String s2) {
		    	int cmp = Integer.compare(s1.length(), s2.length());
	            return cmp != 0 ? cmp : s1.compareTo(s2);
		    }
		});
		for(int i = 1; i <= array.size(); i++) {
			k.append(Integer.toString(i));
		}
		for(int i = 1; i <= array.size(); i++) {
			for(int j = 0; j <= array.size(); j++) {
				for(int p = 0; p <= array.size() - i -j ; p++) {
					String t = k.substring(j, j + 1) + k.substring(j+p+1, j+i+p);
					result.add(t);
				}
			}
		}
		return result;
		*/
		ArrayList<String> k = new ArrayList<String>();
		TreeSet<ArrayList<String>> result = new TreeSet<ArrayList<String>>(new Comparator<ArrayList<String>>() {
		    @Override
		    public int compare(ArrayList<String> s1, ArrayList<String> s2) {
		    	int cmp = Integer.compare(s1.toString().length(), s2.toString().length());
	            return cmp != 0 ? cmp : s1.toString().compareTo(s2.toString());
		    }
		});
		for(int i = 1; i <= array.size(); i++) {
			k.add(Integer.toString(i));
		}
		for(int i = 1; i <= array.size(); i++) {
			for(int j = 0; j <= array.size(); j++) {
				for(int p = 0; p <= array.size() - i -j ; p++) {
					ArrayList<String> temp = new ArrayList<>();
					String t = k.get(j);
					String t1 = "";
					for(int m = 0; m < k.size(); m++)
					{
						if(m>=j+p+1  && m <= j+i+p) {
							t1 = t1 + k.get(m);
						}
					}
					t = t + t1;
					temp.add(t);
					result.add(temp);
				}
			}
		}
		System.out.println(result.toString());
		return result;
	}
}

