package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Comparator;

import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

class RankedWordComparator implements Comparator<RankedWord>{
	private rankType cType;
	public RankedWordComparator(rankType cType) {
		this.cType=cType;
	}
	
	@Override
	public int compare(RankedWord o1, RankedWord o2) {
		Integer x=(Integer)o1.getRankByType(this.cType);
		Integer y=(Integer)o2.getRankByType(this.cType);
		return x.compareTo(y);
	}	
}
