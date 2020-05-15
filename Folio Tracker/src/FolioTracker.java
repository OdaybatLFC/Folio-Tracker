import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FolioTracker implements FolioTrackerADT {
	
	private List<PortfolioADT> folios;
	private final String dataPath;

	public FolioTracker() {
		folios = new ArrayList<>();
		dataPath = "folios.ser";
		readFolios();
	}

	public void addFolio(PortfolioADT folio) throws DuplicateDataException {
		if (folios.stream().anyMatch(x -> x.getName().equals(folio.getName()))) {
			throw new DuplicateDataException();
		}
		folios.add(folio);
	}

	public PortfolioADT getFolio(String name) {
		PortfolioADT f = folios.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
		return f;
	}

	public List<PortfolioADT> getFolios() {
		return folios;
	}

	public void delFolio(PortfolioADT folio) throws NoSuchFolioException {

		for(PortfolioADT f : folios) {
			if(f.getName().equals(folio.getName())) {
				folios.remove(f);
				return;
			}
		}

		throw new NoSuchFolioException();
	}
	
	public void saveFolios() {
		try {
			FileOutputStream fOut = new FileOutputStream(dataPath);
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			objOut.writeObject(folios);
			objOut.close();
			fOut.close();
		} catch (IOException e) {
			System.out.println("File not found on shut down.");
		}
	}

	public void readFolios() {
		try {
			FileInputStream fIn = new FileInputStream(dataPath);
			ObjectInputStream objIn = new ObjectInputStream(fIn);
			folios = (List<PortfolioADT>) objIn.readObject();
			fIn.close();
			objIn.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("File not found on startup.");
		}
	}

	public float getStockPrice(String ticker) throws NoSuchTickerException, WebsiteDataException {

		String pricePerShare = "0";
		pricePerShare = StrathQuoteServer.getLastValue(ticker);

		float price = Float.parseFloat(pricePerShare);

		return price;
	}
	
	public void refresh() throws NoSuchTickerException, WebsiteDataException {

		float pricePerShare;
		for (PortfolioADT folio : folios) {
			for (StockADT stock : folio.getStocks()) {

				pricePerShare = getStockPrice(stock.getSymbol());
				stock.setPrice(pricePerShare);
			}
		}
	}
}
