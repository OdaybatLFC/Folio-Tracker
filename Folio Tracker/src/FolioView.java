import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FolioView extends JFrame {

	private PortfolioADT currentFolio;

	public JTable stocksTable;
	public StockTableModel stockTableModel;

	public JTable folioTable;
	public FolioTableModel folioTableModel;

	public JPanel folioInfo;
	public JLabel folioName;
	public JButton backBtn;
	public JButton addStockBtn;
	public JButton addFolioButton;

	public JPanel folioView;
	public JPanel mainView;

	public JMenuBar menuBar;
	public JMenu menu;
	public JMenuItem saveFolios;
	public JMenuItem refresh;
	public Timer refreshTimer;

	public AddStockModal addStockModal;
	public AddFolioModal addFolioModal;
	public EditStockModal editStockModal;

	public JPopupMenu folioPopupMenu;
	public JPopupMenu stockPopupMenu;

	public JMenuItem deleteFolio;
	public JMenuItem editStock;
	public JMenuItem deleteStock;

	public FolioView() {
		super();
		setTitle("Folio Tracker");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 500);
		setVisible(true);
		setLayout(new FlowLayout());

		setupMenuBar();
		setupMainView();
		setupFolioView();
		setupFolioPopup();
		setupStockPopup();

		setContentPane(mainView);
		mainView.setVisible(true);
		
		setIcon();

		addStockModal = new AddStockModal(this);
		addFolioModal = new AddFolioModal(this);
		editStockModal = new EditStockModal(this);
	}

	public void showMainView(List<PortfolioADT> folios) {
		currentFolio = null;
		setContentPane(mainView);
		folioTableModel.updateFolios(folios);
		repaint();
		revalidate();
	}

	public void showFolioView(PortfolioADT folio) {
		setContentPane(folioView);
		folioName.setText(folio.getName());
		currentFolio = folio;
		stockTableModel.updateStocks(folio.getStocks());
		repaint();
		revalidate();
	}

	public int confirmDialog(String message) {
		return JOptionPane.showConfirmDialog(this, message);
	}

	public void errorDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public PortfolioADT getCurrentFolio(){
		return currentFolio;
	}

	private void setupMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("Folio");
		refresh = new JMenuItem("Refresh Stock Prices");
		saveFolios = new JMenuItem("Save Folios");
		menu.add(saveFolios);
		menu.add(refresh);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	private void setupMainView() {
		mainView = new JPanel();
		mainView.setOpaque(true);
		mainView.setLayout(new BorderLayout());

		folioTableModel = new FolioTableModel();

		folioTable = new JTable(folioTableModel);
		folioTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = folioTable.rowAtPoint(e.getPoint());
				if (e.getClickCount() == 2 && folioTable.getSelectedRow() != -1) {
					PortfolioADT folio = folioTableModel.getFolio(row);
					showFolioView(folio);
				}
			}
		});

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Portfolios");
		title.setFont(new Font("Dialog",Font.BOLD, 18));
		titlePanel.add(title);
		mainView.add(titlePanel, BorderLayout.NORTH);
		mainView.add(new JScrollPane(folioTable), BorderLayout.CENTER);

		JPanel actionPanel = new JPanel(new BorderLayout());
		addFolioButton = new JButton("Add New Portfolio");
		actionPanel.add(addFolioButton, BorderLayout.EAST);

		mainView.add(actionPanel, BorderLayout.SOUTH);
	}

	private void setupFolioView() {
		folioView = new JPanel();
		folioView.setOpaque(true);
		folioView.setLayout(new BorderLayout());
		folioView.setVisible(true);

		folioInfo = new JPanel();
		folioInfo.setSize(500,50);
		folioName = new JLabel();
		folioName.setFont(new Font("Dialog",Font.BOLD, 18));
		folioInfo.add(folioName);
		folioView.add(folioInfo, BorderLayout.NORTH);

		stockTableModel = new StockTableModel();
		stocksTable = new JTable(stockTableModel);
		stocksTable.setCellSelectionEnabled(false);
		folioView.add(new JScrollPane(stocksTable), BorderLayout.CENTER);

		JPanel actionPanel = new JPanel(new BorderLayout());
		backBtn = new JButton("Back");
		actionPanel.add(backBtn, BorderLayout.WEST);
		addStockBtn = new JButton("Add Stock");
		actionPanel.add(addStockBtn, BorderLayout.EAST);

		folioView.add(actionPanel, BorderLayout.SOUTH);
	}

	private void setupFolioPopup() {
		folioPopupMenu = new JPopupMenu();
		deleteFolio = new JMenuItem("Delete");
		folioPopupMenu.add(deleteFolio);
	}

	private void setupStockPopup() {
		stockPopupMenu = new JPopupMenu();
		editStock = new JMenuItem("Edit");
		deleteStock = new JMenuItem("Delete");

		stockPopupMenu.add(editStock);
		stockPopupMenu.add(deleteStock);
	}

	private void setIcon() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("cash.png")));
	}
}
