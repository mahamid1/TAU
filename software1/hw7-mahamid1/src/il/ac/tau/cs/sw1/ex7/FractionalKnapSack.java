package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }
    public int max(List<Item> t1)
    {
    	int max=0;
    	for(int i=0;i<t1.size();i++)
    	{
    		Item current=lst.get(i);
    		if(current.value/current.weight > lst.get(max).value/lst.get(max).weight)
    		{
    			max=i;
    		}
    	}
    	return max;
    }    

    @Override
    public Iterator<Item> selection() {
    	List<Item> tmp=new ArrayList<Item>(lst);
    	List<Item> tmp2=new ArrayList<Item>();
    	int currMax=max(tmp);
    	
    	while(tmp.size()!=0)
    	{
    		
    		tmp2.add(tmp.get(currMax));
    		tmp.remove(currMax);
    		currMax=max(tmp);
    	} 
        return tmp2.iterator();
    }
    public double getSum(List<Item> arr,Item item)
    {
    	double sum=0;
    	for(Iterator<Item> it=arr.iterator(); it.hasNext();)
    	{
    		Item tmp=it.next();
    		sum+=tmp.weight;
    	}
    	return sum+item.weight;
    }
    public int Help(List<Item> arr,Item item)
    {
    	int Index=0;
    	for(int i=(int) Math.floor(item.weight);i>0;i--)
    	{
    		Item tmp=new Item(i,item.value);
    		if(getSum(arr,tmp)<=capacity)
    		{
    			Index=i;
    			break;
    		}
    	}
    	return Index;
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
    	if(getSum(candidates_lst,element)<=capacity)
    	{
    		return true;
    	}
    	else if(Help(candidates_lst,element)!=0)
    		return true;
        return false;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
    	if(getSum(candidates_lst,element)>capacity)
    	{
    		Item curr=new Item(Help(candidates_lst,element),element.value);
    		candidates_lst.add(candidates_lst.size(),curr);
    	}
    	else
    	{
    		candidates_lst.add(candidates_lst.size(),element);
    	}
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
    	Item curr=new Item(0,0);
    	double sum=getSum(candidates_lst,curr);
        return sum==capacity;
    }
}
