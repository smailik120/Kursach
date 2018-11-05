import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.Comparator;
public class StringWorker implements Work{
	public StringWorker() {
		
	}
	int[] getSubset(int[] input, int[] subset) {
	    int[] result = new int[subset.length]; 
	    for (int i = 0; i < subset.length; i++) 
	        result[i] = input[subset[i]];
	    return result;
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
		int[] input = new int[array.size()];//input array
		for(int i = 0; i < array.size(); i++)
		{
			input[i] = i + 1;
		}
		TreeSet<ArrayList<String>> result = new TreeSet<ArrayList<String>>(new Comparator<ArrayList<String>>() {
		    @Override
		    public int compare(ArrayList<String> s1, ArrayList<String> s2) {
		    	int cmp = Integer.compare(s1.toString().length(), s2.toString().length());
	            return cmp != 0 ? cmp : s1.toString().compareTo(s2.toString());
		    }
		});
		int k = 1;
		while(k != array.size() + 1) {                             // sequence length   
		List<int[]> subsets = new ArrayList<>();

		int[] s = new int[k];                  // here we'll keep indices 
		                                       // pointing to elements in input array
			
		if (k <= input.length) {
		    // first index sequence: 0, 1, 2, ...
		    for (int i = 0; (s[i] = i) < k - 1; i++);  
		    subsets.add(getSubset(input, s));
		    for(;;) {
		        int i;
		        // find position of item that can be incremented
		        for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--); 
		        if (i < 0) {
		            break;
		        }
		        s[i]++;                    // increment this item
		        for (++i; i < k; i++) {    // fill up remaining items
		            s[i] = s[i - 1] + 1; 
		        }
		        subsets.add(getSubset(input, s));
		    }
		}
		for(int[] l: subsets) {
			ArrayList<String> out = new ArrayList<String>();
			String p = "";
			for(int j = 0; j < l.length; j++) {
				p = p + Integer.toString(l[j]) + " ";
			}
			out.add(p);
			result.add(out);
		}
		k++;
		}

		/*
		for(int i = 1; i <= array.size(); i++) {
			for(int j = 0; j <= array.size(); j++) {
				for(int p = 0; p <= array.size() - i -j ; p++) {
					ArrayList<String> temp = new ArrayList<>();
					String t = k.get(j) + " ";
					String t1 = "";
					for(int m = 0; m < k.size(); m++)
					{
						if(m>=j+p+1  && m <= j+i+p) {
							t1 = t1 + k.get(m) + " ";
						}
					}
					t = t + t1;
					temp.add(t);
					result.add(temp);
				}
			}
		}
		*/
		System.out.println(result.toString());
		return result;
	}
}

