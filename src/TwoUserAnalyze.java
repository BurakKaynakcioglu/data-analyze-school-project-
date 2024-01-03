import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TwoUserAnalyze extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton analizEtButton;

    public TwoUserAnalyze(MyGraph ourGraph, MyHashMap<String, User> hashMap) {
        add(panel1);
        setTitle("2 Kullanıcı ile Ananliz");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        analizEtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput1 = textField1.getText();
                User user1 = hashMap.get(userInput1);
                String userInput2 = textField2.getText();
                User user2 = hashMap.get(userInput2);

                if ((user1 == null && user2 == null)) {
                    JOptionPane.showMessageDialog(null, "1. Boşluk -> Geçersiz kullanıcı adı!"
                    + "2. Boşluk -> Geçersiz kullanıcı adı!");
                } else if (user1 == null) {
                    JOptionPane.showMessageDialog(null, "1. Boşluk -> Geçersiz kullanıcı adı!");
                } else if (user2 == null) {
                    JOptionPane.showMessageDialog(null, "2. Boşluk -> Geçersiz kullanıcı adı!");
                } else {
                    MyArrayList<String> user1List = user1.getTakipciler();
                    MyArrayList<String> user2List = user2.getTakipciler();
                    MyHashMap<String, MyArrayList<String>> user1Map = new MyHashMap<>();
                    MyHashMap<String, MyArrayList<String>> user2Map = new MyHashMap<>();

                    MyArrayList<String> interests = new MyArrayList<>();

                    for (int i = 0; i < user1List.size(); i++) {
                        User user = hashMap.get(user1List.get(i));

                        if (user1Map.get(user.getIlgiAlani()) == null) {
                            MyArrayList<String> a = new MyArrayList<>();
                            a.add(user1List.get(i));
                            user1Map.put(user.getIlgiAlani(), a);
                            interests.add(user.getIlgiAlani());
                        } else {
                            MyArrayList<String> a = user1Map.get(user.getIlgiAlani());
                            a.add(user1List.get(i));
                            user1Map.put(user.getIlgiAlani(), a);
                        }
                    }
                    for (int i = 0; i < user2List.size(); i++) {
                        User user = hashMap.get(user2List.get(i));

                        if (user2Map.get(user.getIlgiAlani()) == null) {
                            MyArrayList<String> a = new MyArrayList<>();
                            a.add(user2List.get(i));
                            user2Map.put(user.getIlgiAlani(), a);
                        } else {
                            MyArrayList<String> a = user2Map.get(user.getIlgiAlani());
                            a.add(user2List.get(i));
                            user2Map.put(user.getIlgiAlani(), a);
                        }
                    }


                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("two_user_analyze.json", true))) {
                        Gson gson = new Gson();

                        writer.write("[");
                        writer.newLine();
                        for (int i = 0; i < interests.size(); i++) {
                            String interest = interests.get(i);

                            if (user2Map.get(interest) != null) {
                                MyArrayList<String> a1 = user1Map.get(interest);
                                MyArrayList<String> a2 = user2Map.get(interest);

                                writer.write("{");
                                writer.write(gson.toJson("ilgi_alani"));
                                writer.write(":");
                                writer.write(gson.toJson(interest));
                                writer.write(",");
                                writer.write(gson.toJson("kullanici_adi"));
                                writer.write(":");

                                MyArrayList<String> users = new MyArrayList<>();
                                for (int x = 0; x < a1.size(); x++) {
                                    Node nodeA1 = new Node(hashMap.get(a1.get(x)));
                                    users.add(a1.get(x));

                                    for (int y = 0; y < a2.size(); y++) {
                                        Node nodeA2 = new Node(hashMap.get(a2.get(y)));
                                        users.add(a2.get(y));
                                    }
                                }

                                writer.write("[");
                                for (int x = 0; x < users.size(); x++) {
                                    if (x == users.size() - 1) {
                                        writer.write(gson.toJson(users.get(x)));
                                        writer.write("]");
                                        writer.write("},");
                                    } else {
                                        writer.write(gson.toJson(users.get(x)));
                                        writer.write(",");
                                    }
                                }
                                writer.newLine();
                            }
                        }
                        writer.write("]");
                    } catch (IOException v) {
                        System.exit(1);
                    }
                    finalJSONForm();
                }

                JOptionPane.showMessageDialog(null, "JSON dosyası oluşturuldu.\n" +
                        "Dosyayı şimdi görüntüleyebilirsiniz.");
            }
        });
    }

    private void finalJSONForm() {
        String dosyaYolu = "two_user_analyze.json";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

            String json = sb.toString();

            if (json.length() > 4) {
                json = json.substring(0, json.length() - 4);
                json = json + "\n" + "]";
            } else {
                System.out.println("----DOSYA BOŞ----");
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
