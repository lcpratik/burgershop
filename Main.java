import javax.swing.SwingUtilities;

// Program entry point

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BurgerShopFE frame = new BurgerShopFE();
                frame.setVisible(true);
            }
        });
    }
}
