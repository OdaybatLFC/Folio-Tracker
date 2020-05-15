import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddFolioModal extends JDialog {

    private JFrame parent;

    private JLabel errorLabel;

    public JTextField nameInput;

    public JButton addBtn;
    public JButton cancelBtn;

    public AddFolioModal(JFrame parent) {

        super(parent, true);
        this.parent = parent;

        setTitle("Add Folio");
        setLayout(new BorderLayout());
        setSize(300,180);
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

        errorLabel = new JLabel();
        JPanel errorRow = new JPanel(new FlowLayout());
        errorRow.add(errorLabel);
        formPanel.add(errorRow);

        add(formPanel, BorderLayout.CENTER);
    }

    private void setUpButtons() {

        addBtn = new JButton("Add Folio");
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                resetForm();
                super.windowClosed(e);
            }
        });
    }
}
