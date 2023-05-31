package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{
	private ArrayList<Items<T>> sortedItems;
	private Iterator<Items<T>> current;
	public HashMapHistogramIterator(HashMap<T,Integer> currentMap)
	{
		ArrayList<Items<T>> result = new ArrayList<Items<T>>();
		Set<T> items=currentMap.keySet();
		for(T item:items)
		{
			result.add(new Items<T>(item,currentMap.get(item)));
		}
		result.sort(new ItemComparator<T>());
		this.sortedItems=result;
		this.current=this.sortedItems.iterator();
	}

	@Override
	public boolean hasNext() {
		return this.current.hasNext();
	}

	@Override
	public T next() {
		return this.current.next().GetItem();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(); //no need to change this
	}
}
