import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Front-end GUI for the Burger Shop Management System
// Uses BurgerManager and Order to perform operations.

public class BurgerShopFE extends JFrame implements ActionListener {

    private BurgerManager burgerManager;
    private Order currentOrder;

    // Menu components
    private DefaultListModel<Burger> menuListModel;
    private JList<Burger> menuList;
    private JTextField txtBurgerName;
    private JTextField txtBurgerPrice;
    private JTextField txtBurgerDescription;

    // Order components
    private DefaultListModel<String> orderListModel;
    private JList<String> orderList;

    // Customization checkboxes
    private JCheckBox chkExtraCheese;
    private JCheckBox chkBacon;
    private JCheckBox chkNoPickles;

    // Discount and totals
    private JTextField txtDiscountCode;
    private JLabel lblSubtotal;
    private JLabel lblDiscount;
    private JLabel lblTotal;

    public BurgerShopFE() {
        burgerManager = new BurgerManager();
        currentOrder = new Order();

        setTitle("Burger Shop Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        buildUI();
        refreshTotals();
    }

    private void buildUI() {
        setLayout(new GridLayout(1, 2));

        // ===== LEFT SIDE: MENU =====
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Burger Menu"));

        menuListModel = new DefaultListModel<>();
        menuList = new JList<>(menuListModel);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Load initial menu
        for (Burger b : burgerManager.getMenu()) {
            menuListModel.addElement(b);
        }

        menuPanel.add(new JScrollPane(menuList), BorderLayout.CENTER);

        // New burger form
        JPanel newBurgerPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        newBurgerPanel.setBorder(BorderFactory.createTitledBorder("Add New Burger"));

        newBurgerPanel.add(new JLabel("Name:"));
        txtBurgerName = new JTextField();
        newBurgerPanel.add(txtBurgerName);

        newBurgerPanel.add(new JLabel("Price:"));
        txtBurgerPrice = new JTextField();
        newBurgerPanel.add(txtBurgerPrice);

        newBurgerPanel.add(new JLabel("Description:"));
        txtBurgerDescription = new JTextField();
        newBurgerPanel.add(txtBurgerDescription);

        JButton btnAddMenuBurger = new JButton("Add Burger To Menu");
        btnAddMenuBurger.setActionCommand("ADD_MENU");
        btnAddMenuBurger.addActionListener(this);
        newBurgerPanel.add(btnAddMenuBurger);

        // filler to keep grid even
        newBurgerPanel.add(new JLabel(""));

        menuPanel.add(newBurgerPanel, BorderLayout.SOUTH);

        // ===== RIGHT SIDE: ORDER / CUSTOMIZATION / DISCOUNT =====
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Customization + "Add to Order"
        JPanel customPanel = new JPanel();
        customPanel.setLayout(new GridLayout(2, 1));
        customPanel.setBorder(BorderFactory.createTitledBorder("Customizations for Next Burger"));

        JPanel checksPanel = new JPanel();
        chkExtraCheese = new JCheckBox("Extra cheese (+1.00)");
        chkBacon = new JCheckBox("Bacon (+1.50)");
        chkNoPickles = new JCheckBox("No pickles (+0.00)");
        checksPanel.add(chkExtraCheese);
        checksPanel.add(chkBacon);
        checksPanel.add(chkNoPickles);

        JPanel addToOrderPanel = new JPanel();
        JButton btnAddToOrder = new JButton("Add Selected Burger To Order");
        btnAddToOrder.setActionCommand("ADD_TO_ORDER");
        btnAddToOrder.addActionListener(this);
        addToOrderPanel.add(btnAddToOrder);

        customPanel.add(checksPanel);
        customPanel.add(addToOrderPanel);

        rightPanel.add(customPanel, BorderLayout.NORTH);

        // Order list
        orderListModel = new DefaultListModel<>();
        orderList = new JList<>(orderListModel);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        rightPanel.add(new JScrollPane(orderList), BorderLayout.CENTER);

        // Buttons + Discount + Totals at bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Order control buttons
        JPanel orderButtonsPanel = new JPanel();
        JButton btnRemove = new JButton("Remove Selected From Order");
        btnRemove.setActionCommand("REMOVE_ORDER");
        btnRemove.addActionListener(this);
        orderButtonsPanel.add(btnRemove);

        JButton btnClear = new JButton("Clear Order");
        btnClear.setActionCommand("CLEAR_ORDER");
        btnClear.addActionListener(this);
        orderButtonsPanel.add(btnClear);

        bottomPanel.add(orderButtonsPanel, BorderLayout.NORTH);

        // Discount + totals
        JPanel discountPanel = new JPanel();
        discountPanel.add(new JLabel("Discount code:"));
        txtDiscountCode = new JTextField(10);
        discountPanel.add(txtDiscountCode);

        JButton btnApplyDiscount = new JButton("Apply Discount");
        btnApplyDiscount.setActionCommand("APPLY_DISCOUNT");
        btnApplyDiscount.addActionListener(this);
        discountPanel.add(btnApplyDiscount);

        JPanel totalsPanel = new JPanel(new GridLayout(3, 1));
        lblSubtotal = new JLabel("Subtotal: $0.00");
        lblDiscount = new JLabel("Discount: (none)");
        lblTotal = new JLabel("Total: $0.00");
        totalsPanel.add(lblSubtotal);
        totalsPanel.add(lblDiscount);
        totalsPanel.add(lblTotal);

        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.add(discountPanel, BorderLayout.NORTH);
        summaryPanel.add(totalsPanel, BorderLayout.SOUTH);

        bottomPanel.add(summaryPanel, BorderLayout.SOUTH);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add left and right panels to frame
        add(menuPanel);
        add(rightPanel);
    }

    // Build customizations list based on which checkboxes are checked
    private List<Customization> getSelectedCustomizations() {
        List<Customization> list = new ArrayList<>();

        if (chkExtraCheese.isSelected()) {
            list.add(new Customization("Extra cheese", 1.00));
        }
        if (chkBacon.isSelected()) {
            list.add(new Customization("Bacon", 1.50));
        }
        if (chkNoPickles.isSelected()) {
            list.add(new Customization("No pickles", 0.00));
        }

        return list;
    }

    private void clearCustomizationSelection() {
        chkExtraCheese.setSelected(false);
        chkBacon.setSelected(false);
        chkNoPickles.setSelected(false);
    }

    // Rebuilds the order list display
    private void refreshOrderList() {
        orderListModel.clear();
        for (int i = 0; i < currentOrder.getBurgers().size(); i++) {
            Burger b = currentOrder.getBurgers().get(i);
            List<Customization> customizations = currentOrder.getCustomizationsForBurger(i);

            StringBuilder sb = new StringBuilder();
            sb.append(b.getName());

            if (!customizations.isEmpty()) {
                sb.append(" [");
                for (int j = 0; j < customizations.size(); j++) {
                    sb.append(customizations.get(j).getName());
                    if (j < customizations.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
            }

            double lineTotal = currentOrder.calculateBurgerTotal(i);
            sb.append(" - $").append(String.format("%.2f", lineTotal));

            orderListModel.addElement(sb.toString());
        }
    }

    // Updates subtotal / discount / total labels
    private void refreshTotals() {
        double subtotal = currentOrder.calculateSubtotal();
        lblSubtotal.setText("Subtotal: $" + String.format("%.2f", subtotal));

        Discount d = currentOrder.getDiscount();
        if (d != null) {
            lblDiscount.setText("Discount: " + d.toString());
            double total = currentOrder.calculateTotal();
            lblTotal.setText("Total: $" + String.format("%.2f", total));
        } else {
            lblDiscount.setText("Discount: (none)");
            lblTotal.setText("Total: $" + String.format("%.2f", subtotal));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("ADD_MENU".equals(cmd)) {
            handleAddMenuBurger();
        } else if ("ADD_TO_ORDER".equals(cmd)) {
            handleAddToOrder();
        } else if ("REMOVE_ORDER".equals(cmd)) {
            handleRemoveFromOrder();
        } else if ("CLEAR_ORDER".equals(cmd)) {
            handleClearOrder();
        } else if ("APPLY_DISCOUNT".equals(cmd)) {
            handleApplyDiscount();
        }
    }

    private void handleAddMenuBurger() {
        String name = txtBurgerName.getText().trim();
        String priceText = txtBurgerPrice.getText().trim();
        String desc = txtBurgerDescription.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter at least a name and price.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Price must be a valid number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Burger newBurger = new Burger(name, price, desc);
        burgerManager.addBurger(newBurger);
        menuListModel.addElement(newBurger);

        txtBurgerName.setText("");
        txtBurgerPrice.setText("");
        txtBurgerDescription.setText("");
    }

    private void handleAddToOrder() {
        int selectedIndex = menuList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a burger from the menu first.",
                    "No Burger Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Burger selectedBurger = burgerManager.getBurger(selectedIndex);
        int orderIndex = currentOrder.addBurger(selectedBurger);

        List<Customization> customizations = getSelectedCustomizations();
        for (Customization c : customizations) {
            currentOrder.addCustomizationToBurger(orderIndex, c);
        }

        clearCustomizationSelection();
        refreshOrderList();
        refreshTotals();
    }

    private void handleRemoveFromOrder() {
        int selectedIndex = orderList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an item in the order to remove.",
                    "No Item Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentOrder.removeBurger(selectedIndex);
        refreshOrderList();
        refreshTotals();
    }

    private void handleClearOrder() {
        currentOrder.clear();
        refreshOrderList();
        refreshTotals();
    }

    private void handleApplyDiscount() {
        String code = txtDiscountCode.getText().trim();

        if (code.equalsIgnoreCase("SAVE10")) {
            // simple fixed 10% discount code
            currentOrder.setDiscount(new Discount("SAVE10", 10.0));
            JOptionPane.showMessageDialog(this,
                    "10% discount applied!",
                    "Discount",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (code.isEmpty()) {
            currentOrder.setDiscount(null);
            JOptionPane.showMessageDialog(this,
                    "No discount code entered. Discount cleared.",
                    "Discount",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            currentOrder.setDiscount(null);
            JOptionPane.showMessageDialog(this,
                    "Invalid discount code.",
                    "Discount Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        refreshTotals();
    }
}
