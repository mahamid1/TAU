package il.ac.tau.cs.sw1.ex8.histogram;

public class Items<T>  implements Comparable<Items<T>>{
	private T item;
	private Integer value;
	public Items(T item,Integer x)
	{
		this.item=item;
		this.value=x;
	}
	public T GetItem()
	{
		return this.item;
	}
	public Integer GetValue()
	{
		return this.value;
	}
	@Override
	public int compareTo(Items<T> t1)
	{
		return 0;
	}
}
