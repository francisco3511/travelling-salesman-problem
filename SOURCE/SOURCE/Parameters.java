package project;

public class Parameters {
	//MoveParameters moveParam;	
	private double alpha;
	private double beta;
	private double delta;
	//EvaporationParameters evapParam;
	private double eta;
	private double rho;
	private double pLevel;
	
	public Parameters(double _alpha, double _beta, double _delta, double _eta, double _rho, double _pLevel)
	{
		alpha = _alpha;
		beta = _beta;
		delta = _delta;
		eta = _eta;
		rho = _rho;
		pLevel = _pLevel;
	}
	
	public double getDelta()
	{
		return delta;
	}
	
	public double getEta()
	{
		return eta;
	}
	
	public double getAlpha()
	{
		return alpha;
	}
	
	public double getBeta()
	{
		return beta;
	}
	
	public double getRho()
	{
		return rho;
	}
	
	public double getPLevel()
	{
		return pLevel;
	}

	@Override
	public String toString() {
		return "Parameters [alpha=" + alpha + ", beta=" + beta + ", delta=" + delta + ", eta=" + eta + ", rho=" + rho
				+ ", pLevel=" + pLevel + "]";
	}
}
