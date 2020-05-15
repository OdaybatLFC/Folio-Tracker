import java.util.List;

public interface FolioTrackerADT {

    void addFolio(PortfolioADT folio) throws DuplicateDataException;
    List<PortfolioADT> getFolios();
    void delFolio(PortfolioADT folio) throws NoSuchFolioException;
    void saveFolios();
    void readFolios();
    float getStockPrice(String ticker) throws WebsiteDataException, NoSuchTickerException;
    void refresh() throws NoSuchTickerException, WebsiteDataException;
}
