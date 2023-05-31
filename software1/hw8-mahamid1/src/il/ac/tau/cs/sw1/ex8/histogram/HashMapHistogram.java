package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{
	private HashMap<T,Integer> x;
	public HashMapHistogram()
	{
		this.x=new HashMap<T,Integer>();
	}

	@Override
	public Iterator<T> iterator() {
		
		return new HashMapHistogramIterator<T>(this.x);
	}

	@Override
	public void addItem(T item) {
		if(this.x.containsKey(item))
		{
			this.x.put(item,this.x.get(item)+1);
		}
		else 
		{
			this.x.put(item,1);
		}
		
	}

	@Override
	public void removeItem(T item) throws IllegalItemException {
		if(!this.x.containsKey(item))
		{
			throw new IllegalItemException();
		}
		if(this.x.get(item)==1)
		{
			this.x.remove(item);
		}
		else 
		{
			this.x.put(item,this.x.get(item)-1);
		}
		
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValueException {
		if(k<0)
		{
			throw new IllegalKValueException(k);
		}
		else
		{
			if(this.x.containsKey(item))
				this.x.put(item,this.x.get(item)+k);
			else this.x.put(item,k);
				
					
		}
		
	}

	@Override
	public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {
		if(!this.x.containsKey(item))
			throw new IllegalItemException();
		else 
		{
			if(k>this.x.get(item) || k<0)
			{
				throw new IllegalKValueException(k);
			}
			else 
			{
				for(int i=0;i<k;i++)
				{
					this.removeItem(item);
				}
			}
		}
		
	}

	@Override
	public int getCountForItem(T item) {
		if(this.x.containsKey(item))
		{
			return this.x.get(item);
		}
		return 0;
	}

	@Override
	public void addAll(Collection<T> items) {
		for(T item:items)
		{
			this.addItem(item);
		}	
	}

	@Override
	public void clear() {
		this.x.clear();
		
	}

	@Override
	public Set<T> getItemsSet() {
		return this.x.keySet();
	}

	@Override
	public void update(IHistogram<T> anotherHistogram) {
		for(T item:anotherHistogram)	
		{
			if(this.x.containsKey(item))
			{
				this.x.put(item,this.getCountForItem(item)+anotherHistogram.getCountForItem(item));
			}
			else
			{
				this.x.put(item,anotherHistogram.getCountForItem(item));
			}
		}
	}

	
}
