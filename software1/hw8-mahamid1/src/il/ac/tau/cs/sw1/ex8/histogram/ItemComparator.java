package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Comparator;

public class ItemComparator<T extends Comparable<T>> implements Comparator< Items<T>> {

	@Override
	public int compare(Items<T> arg0, Items<T> arg1) {
		int x =arg1.GetValue().compareTo(arg0.GetValue());
		if(x==0)
			return arg0.GetItem().compareTo(arg1.GetItem());
		return x;
	}
	

}
