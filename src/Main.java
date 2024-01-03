import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        MyHashMap<String, User> hashMap = new MyHashMap<>();
        MyGraph ourGraph = new MyGraph();

        //JSON dan verileri okuyup hashmape kaydettik
        try {
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(new FileReader("fake_users_data.json"), JsonArray.class);

            for (JsonElement element : jsonArray) {
                JsonObject userObject = element.getAsJsonObject();

                JsonObject kullaniciBilgileri = userObject.getAsJsonObject("kullanici_bilgileri");
                String kullaniciAdi = kullaniciBilgileri.get("kullanici_adi").getAsString();
                String adSoyad = kullaniciBilgileri.get("ad_soyad").getAsString();
                int takipciSayisi = kullaniciBilgileri.get("takipci_sayisi").getAsInt();
                int takipEdilenSayisi = kullaniciBilgileri.get("takip_edilen_sayisi").getAsInt();
                String dil = kullaniciBilgileri.get("dil").getAsString();
                String bolge =  kullaniciBilgileri.get("bolge").getAsString();

                User kullanici = new User(kullaniciAdi, adSoyad, takipciSayisi, takipEdilenSayisi, dil, bolge);

                JsonArray tweetler = userObject.getAsJsonArray("tweet_icerikleri");
                for (int i = 0; i < tweetler.size(); i++) {
                    JsonObject tweet = tweetler.get(i).getAsJsonObject();
                    int ID = tweet.get("tweet_id").getAsInt();
                    String icerik = tweet.get("icerik").getAsString();
                    String hashtag = tweet.get("hashtag").getAsString();

                    kullanici.addTweetIcerik(ID, icerik, hashtag);
                }

                JsonObject takipciler = userObject.getAsJsonObject("takip_edilen_ve_takipciler");
                JsonArray takipEdilenler = takipciler.getAsJsonArray("takip_edilenler");
                for (int i = 0; i < takipEdilenler.size(); i++) {
                    String takipEdilen = takipEdilenler.get(i).getAsString();

                    kullanici.addTakipEdilen(takipEdilen);
                }

                JsonArray takipcilerListesi = takipciler.getAsJsonArray("takipciler");
                for (int j = 0; j < takipcilerListesi.size(); j++) {
                    String takipci = takipcilerListesi.get(j).getAsString();

                    kullanici.addTakipci(takipci);
                }

                kullanici.bulIlgiAlani();
                Node node = new Node(kullanici);
                ourGraph.AddNode(node);
                hashMap.put(kullaniciAdi, kullanici);
            }
        } catch (Exception e) {
            System.exit(1);
        }
        User.finalJSONForm();

        System.out.println("Kişi sayısı: " + ourGraph.getNodes().size());  //Kişi sayısı

        //Nodelar arasında ilişkiyi kurduk
        for (int i = 0; i < ourGraph.getNodes().size(); i++){
            Node node = ourGraph.getNodes().get(i);
            User user = hashMap.get(node.getName());

            for (int j = 0; j < user.getTakipEdilenler().size(); j++){
                String kullaniciAdi = user.getTakipEdilenler().get(j);
                Node destNode = ourGraph.getNodeByName(kullaniciAdi);
                ourGraph.AddEdge(node, destNode);
            }

            System.out.println("1: " + i); // SİL
        }
        ourGraph.findCommons();
        ourGraph.findTrendingforLanAndArea();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMenuGUI GUI = new MainMenuGUI(hashMap, ourGraph);
                GUI.setVisible(true);
                GUI.setLocationRelativeTo(null);
            }
        });
    }
}