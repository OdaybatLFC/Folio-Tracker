import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AddStockModal extends JDialog {
    public JFrame parent;

    public JTextField nameInput;
    public JTextField symbolInput;
    public JTextField sharesInput;

    public JButton addBtn;
    public JButton cancelBtn;
	
	private JLabel errorLabel;

    public AddStockModal(JFrame parent) {

        super(parent, true);
        this.parent = parent;

        setTitle("Add Stock");
        setLayout(new BorderLayout());
        setSize(300,230);
        setVisible(false);

        setUpForm();
        setUpButtons();
        addResetOnClose();
    }

    public void showModal() {
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    public void updateErrorMsg(String msg) {
        errorLabel.setText(msg);
        errorLabel.setForeground(Color.red);
        errorLabel.setVisible(true);
    }

    public void resetForm() {
        symbolInput.setText("");
        sharesInput.setText("");
        nameInput.setText("");
        errorLabel.setText("");
    }

    private void setUpForm() {

        JPanel formPanel = new JPanel();

        nameInput = new JTextField(10);
        nameInput.setSize(100,30);
        JLabel nameLabel = new JLabel("  Name: ");
        JPanel nameRow = new JPanel(new FlowLayout());
        nameRow.add(nameLabel);
        nameRow.add(nameInput);
        formPanel.add(nameRow);

        symbolInput = new JTextField(10);
        symbolInput.setSize(100,30);
        JLabel symbolLabel = new JLabel("Symbol: ");
        JPanel symbolRow = new JPanel(new FlowLayout());
        symbolRow.add(symbolLabel);
        symbolRow.add(symbolInput);
        formPanel.add(symbolRow);

        sharesInput = new JTextField(10);
        sharesInput.setSize(100,30);
        JLabel sharesLabel = new JLabel("Shares: ");
        JPanel sharesRow = new JPanel(new FlowLayout());
        sharesRow.add(sharesLabel);
        sharesRow.add(sharesInput);
        formPanel.add(sharesRow);

        errorLabel = new JLabel();
        JPanel errorRow = new JPanel(new FlowLayout());
        errorRow.add(errorLabel);
        formPanel.add(errorRow);

        add(formPanel, BorderLayout.CENTER);
    }

    private void setUpButtons() {

        addBtn = new JButton("Add Stock");
        cancelBtn = new JButton("Cancel");

        JPanel buttonRow = new JPanel(new BorderLayout());
        buttonRow.add(addBtn, BorderLayout.EAST);
        buttonRow.add(cancelBtn, BorderLayout.WEST);
        add(buttonRow, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(addBtn);

        cancelBtn.addActionListener(e -> {
            setVisible(false);
            resetForm();
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
