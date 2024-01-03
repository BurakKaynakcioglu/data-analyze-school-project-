import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class WordAnalyzeGUI extends JFrame{
    private JTextField textField1;
    private JPanel panel1;
    private JButton bulButton;
    private JLabel label;

    public WordAnalyzeGUI(MyGraph ourGraph) {
        add(panel1);
        setTitle("Kelime ile Analiz");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        bulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField1.getText();
                Node startNode = ourGraph.getNodes().get(0);
                ourGraph.findCommonTweets(startNode, input, "word");
                JOptionPane.showMessageDialog(null, "JSON dosyası oluşturuldu.\n" +
                        "Dosyayı şimdi görüntüleyebilirsiniz.");
            }
        });
    }
}
