import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class EditStockModal extends JDialog {

    private JFrame parent;

    private JLabel errorLabel;
    private JLabel nameLabel;
    private JLabel tickerLabel;
    private JLabel priceLabel;

    public JTextField sharesInput;

    public StockADT stock;

    public JButton cancelBtn;
    public JButton saveBtn;


    public EditStockModal(JFrame parent) {

        super(parent, true);
        this.parent = parent;

        setLayout(new BorderLayout());
        setSize(300, 200);
        setVisible(false);

        setUpForm();
        setUpButtons();
        addResetOnClose();
    }

    public void showModal() {
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public void updateStock(StockADT currentStock) {
        stock = currentStock;
        setTitle("Edit Stock: " + stock.getName());
        nameLabel.setText(stock.getName());
        tickerLabel.setText(stock.getSymbol());
        priceLabel.setText(String.valueOf(stock.getPrice()));
    }

    public void updateErrorMsg(String msg) {
        errorLabel.setText(msg);
        errorLabel.setForeground(Color.red);
        errorLabel.setVisible(true);
    }

    public void resetForm() {
        sharesInput.setText("");
        errorLabel.setText("");
    }

    private void setUpForm() {

        JPanel formPanel = new JPanel();

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Name: "));
        nameLabel = new JLabel();
        namePanel.add(nameLabel);
        formPanel.add(namePanel);

        JPanel tickerPanel = new JPanel();
        tickerPanel.add(new JLabel("Ticker: "));
        tickerLabel = new JLabel();
        tickerPanel.add(tickerLabel);
        formPanel.add(tickerPanel);

        JPanel pricePanel = new JPanel();
        pricePanel.add(new JLabel("Price per Share: "));
        priceLabel = new JLabel();
        pricePanel.add(priceLabel);
        formPanel.add(pricePanel);

        JPanel sharesRow = new JPanel(new FlowLayout());
        sharesRow.add(new JLabel("Shares: "));
        sharesInput = new JTextField(10);
        sharesRow.add(sharesInput);
        formPanel.add(sharesRow);

        errorLabel = new JLabel();
        JPanel errorRow = new JPanel(new FlowLayout());
        errorRow.add(errorLabel);
        formPanel.add(errorRow);

        add(formPanel, BorderLayout.CENTER);
    }

    private void setUpButtons() {

        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(cancelBtn, BorderLayout.WEST);
        buttonPanel.add(saveBtn, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(saveBtn);

        cancelBtn.addActionListener(e -> {
            resetForm();
            setVisible(false);
        });
    }

    private void addResetOnClose() {
        addWindowStateListener(e -> {
            if (e.getNewState() == WindowEvent.WINDOW_CLOSING) {
                resetForm();
            }
        });
    }
}
