package il.ac.tau.cs.sw1.hw6;

public class Polynomial {
	
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	private double[] coeff;
	public Polynomial()
	{	
		this.coeff=new double[1];
		this.coeff[0]=0;
		
	} 
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		this.coeff=coefficients;
		
	}
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		double[] res;
		if(this.coeff.length>polynomial.coeff.length)
		{
			res=this.coeff.clone();
			for(int i=0;i<polynomial.coeff.length;i++)
			{
				res[i]+=polynomial.getCoefficient(i);
			}
		}
		else
		{
			res=polynomial.coeff.clone();
			for(int i=0;i<this.coeff.length;i++)
			{
				res[i]+=this.getCoefficient(i);
			}
		}
		Polynomial p1 = new Polynomial(res);
		return p1;
		
	}
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		double[] res=this.coeff.clone();
		for(int i=0;i<res.length;i++)
		{
			res[i]=res[i]*a;
		}
		Polynomial p1=new Polynomial(res);
		return p1;
		
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		return this.coeff.length-1;
	}
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		if(n<this.coeff.length)
		{
			return this.coeff[n];
		}
		return 0.0;
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		if(n<this.coeff.length)
		{
			this.coeff[n]=c;
		}
		else 
		{
			double[] res= new double[n+1];
			for(int i=0;i<this.coeff.length;i++)
			{
				res[i]=this.coeff[i];
			}
			res[n]=c;
			this.coeff=res.clone();
			
		}
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		double[] res =new double[this.coeff.length-1];
		for(int i=0;i<res.length;i++)
		{
			res[i]=this.coeff[i+1]*(i+1);
		}
		Polynomial p1= new Polynomial(res);
		return p1;
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		double sum=0;
		for(int i=0;i<this.coeff.length;i++)
		{
			sum+=this.getCoefficient(i)*Math.pow(x,i);
		}
		return sum;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		if(computePolynomial(x)==0)
		{
			return true;
		}
		return false;
	}
	
	
	
	

    
    

}
