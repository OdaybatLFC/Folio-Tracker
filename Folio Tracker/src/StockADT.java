
public interface StockADT {

	String getName();
	String getSymbol();
	float getPrice();
	void setPrice(float updatedPrice);
	int getShares();
	void setShares(int shares);
	float getValue();
	String getTrend();
	float getChange();
	float getMin();
	float getMax();
}
