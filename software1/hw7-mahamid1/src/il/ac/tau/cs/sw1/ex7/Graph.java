package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,...,n-1]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }
    public class EdgeComparator implements Comparator<Edge>
    {
    	public int compare(Edge g1, Edge g2)
    	{
    		if(g1.weight==g2.weight)
    		{
    			if(g1.node1==g2.node1)
    				return Integer.compare(g1.node2, g2.node2);
    			else
    				return Integer.compare(g1.node1,g2.node1);
    		}
    		return Double.compare(g1.weight, g2.weight);	
    	}
    }

    @Override
    public Iterator<Edge> selection() {
    	List<Edge> arr=new ArrayList<Edge>(lst);
    	EdgeComparator c1=new EdgeComparator();
    	Collections.sort(arr,c1);
        return arr.iterator();
    }
    public int find(int[] arr,int i)
    {
    	if(arr[i]==-1)
    	{
    		return i;
    	}
    	return find(arr,arr[i]);
    }
    public boolean HasCycle(List<Edge> graph)
    {
    	int[] arr=new int[n+1];
    	int i;
    	int j;
    	for(int k=0;k<arr.length;k++)
    	{
    		arr[k]=-1;
    	}
    	for(Edge e:graph)
    	{
    		i=find(arr,e.node1);
    		j=find(arr,e.node2);
    		if(i==j)
    			return false;
    		arr[i]=j;
    		
    	}
    	return true;
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
    	List<Edge> tmp=new ArrayList<Edge>(candidates_lst);
    	tmp.add(tmp.size(),element);
        return HasCycle(tmp);
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        int[] arr=new int[n+1];
        for(Edge e:candidates_lst)
        {
        	arr[e.node1]+=1;
        	arr[e.node2]+=1;
        }
        for(int i=0;i<arr.length;i++)
        {
        	if(arr[i]==0 || candidates_lst.size()!=n)
        		return false;
        }
        return true;
    }
}
