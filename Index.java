
public class Index {
	
	String name;
	String hrefPath;
	String symbol;
	String isin;
	String lastRate;
	String change;
	String turnover;
	String lastTrade;
	String basePrice;
	String openingPrice;
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getHrefPath() {return hrefPath;}
	public void setHrefPath(String hrefPath) {this.hrefPath = hrefPath;}
	public String getSymbol() {return symbol;}
	public void setSymbol(String symbol) {this.symbol = symbol;}
	public String getIsin() {return isin;}
	public void setIsin(String isin) {this.isin = isin;}
	

	public String getLastRate() {return lastRate;}
	public void setLastRate(String lastRate) {this.lastRate = lastRate;}
	public String getChange() {return change;}
	public void setChange(String change) {this.change = change;}
	public String getTurnover() {return turnover;}
	public void setTurnover(String turnover) {this.turnover = turnover;}
	public String getLastTrade() {return lastTrade;}
	public void setLastTrade(String lastTrade) {this.lastTrade = lastTrade;}
	public String getBasePrice() {return basePrice;}
	public void setBasePrice(String basePrice) {this.basePrice = basePrice;}
	public String getOpeningPrice() {return openingPrice;}
	public void setOpeningPrice(String openingPrice) {this.openingPrice = openingPrice;}

	public Index(String name, String hrefPath, String [] data) {
		super();
		this.name = name;
		this.hrefPath = hrefPath;
		this.symbol = data[0];
		this.isin = data[1];
		this.lastRate = data[2];;
		this.change = data[3];
		this.turnover = data[4];
		this.lastTrade = data[5];
		this.basePrice = data[6];
		this.openingPrice = data[7];
	}
	
	public Index(String name, String hrefPath, String symbol, String isin, String lastRate, String change,
			String turnover, String lastTrade, String basePrice, String openingPrice) {
		super();
		this.name = name;
		this.hrefPath = hrefPath;
		this.symbol = symbol;
		this.isin = isin;
		this.lastRate = lastRate;
		this.change = change;
		this.turnover = turnover;
		this.lastTrade = lastTrade;
		this.basePrice = basePrice;
		this.openingPrice = openingPrice;
	}
	public Index() {  }
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name: "+ name +"; Symbol: " +symbol + "; ISIN: " + isin + "; Last Rate: " +lastRate + "; Change: " + change
			+ "; Turnover:" + turnover + "; Last Trade:" + lastTrade + "; Base Price:" + basePrice + "; Opening Price" + openingPrice+ "\n";
	}	

}