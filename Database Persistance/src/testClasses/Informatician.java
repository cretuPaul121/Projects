package testClasses;

public class Informatician {
	private String nume;
	private boolean timp;
	private double bani;
	private boolean hasFemeie;
	
	public Informatician(String nume, boolean timp, double bani, boolean hasFemeie) {
		this.nume = nume;
		this.timp = timp;
		this.bani = bani;
		this.hasFemeie = hasFemeie;
	}
	
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public boolean isTimp() {
		return timp;
	}
	public void setTimp(boolean timp) {
		this.timp = timp;
	}
	public double getBani() {
		return bani;
	}
	public void setBani(double bani) {
		this.bani = bani;
	}
	public boolean isHasFemeie() {
		return hasFemeie;
	}
	public void setHasFemeie(boolean hasFemeie) {
		this.hasFemeie = hasFemeie;
	}
	
	
}
