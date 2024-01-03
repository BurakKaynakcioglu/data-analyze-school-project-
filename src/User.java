import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;

public class User {
    private String kullaniciAdi;
    private String adSoyad;
    private int takipciSayisi;
    private int takipEdilenSayisi;
    private String dil;
    private String bolge;
    private MyArrayList<Tweet> tweetIcerikleri;
    private MyArrayList<String> takipEdilenler;
    private MyArrayList<String> takipciler;
    private String ilgiAlani;
    private String ilgiAlaniKelimeSayisi;
    private String hashtag;

    public User(String kullaniciAdi, String adSoyad, int takipciSayisi,
                int takipEdilenSayisi, String dil, String bolge) {
        this.kullaniciAdi = kullaniciAdi;
        this.adSoyad = adSoyad;
        this.takipciSayisi = takipciSayisi;
        this.takipEdilenSayisi = takipEdilenSayisi;
        this.dil = dil;
        this.bolge = bolge;
        this.tweetIcerikleri = new MyArrayList<>();
        this.takipEdilenler = new MyArrayList<>();
        this.takipciler = new MyArrayList<>();
    }

    public void addTweetIcerik(int ID, String icerik, String hashtag) {
        Tweet eklenecekTweet = new Tweet(ID, icerik, hashtag);
        this.tweetIcerikleri.add(eklenecekTweet);
    }

    public void bulIlgiAlani() {
        MyHashMap<String, Integer> interestHashMap = new MyHashMap<>();
        MyHashMap<String, Integer> hashtagHashMap = new MyHashMap<>();

        for (int i = 0; i < tweetIcerikleri.size(); i++) {
            String hashtag = tweetIcerikleri.get(i).getHashtag();
            Tweet tweet = tweetIcerikleri.get(i);
            String cumle = tweet.getIcerik();
            String[] kelimeler = cumle.split(" ");

            for (String kelime : kelimeler) {
                if (interestHashMap.get(kelime) == null) {
                    interestHashMap.put(kelime, 1);
                } else {
                    int count = interestHashMap.get(kelime);
                    interestHashMap.put(kelime, ++count);
                }
            }

            if (hashtagHashMap.get(hashtag) == null) {
                hashtagHashMap.put(hashtag, 1);
            } else {
                int count = hashtagHashMap.get(hashtag);
                hashtagHashMap.put(hashtag, ++count);
            }
        }


        MyArrayList<String> values = interestHashMap.findMaxValueForInt();
        this.hashtag = hashtagHashMap.findMostUsedHashtag();
        this.ilgiAlani = values.get(0);
        this.ilgiAlaniKelimeSayisi = values.get(1);

        Gson gson = new Gson();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ilgi_alani.json", true))) {
            writer.write("{");
            writer.write(gson.toJson("kullanici_adi"));
            writer.write(":");
            writer.write(gson.toJson(kullaniciAdi));
            writer.write(",");
            writer.write(gson.toJson("ilgi_alani"));
            writer.write(":");
            writer.write(gson.toJson(ilgiAlani));
            writer.write(",");
            writer.write(gson.toJson("kullanim_sayisi"));
            writer.write(":");
            writer.write(ilgiAlaniKelimeSayisi);
            writer.write("}");
            writer.write(",");
            writer.newLine();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public static void finalJSONForm() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ilgi_alani.json"));
            StringBuilder sb = new StringBuilder().append("[").append("\n");
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

            String json = sb.toString();

            json = json.substring(0, json.length() - 2);
            json = json + "\n" + "]";

            BufferedWriter writer = new BufferedWriter(new FileWriter("ilgi_alani.json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public void addTakipEdilen(String takipEdilen) {
        this.takipEdilenler.add(takipEdilen);
    }

    public void addTakipci(String takipci) {
        this.takipciler.add(takipci);
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public int getTakipciSayisi() {
        return takipciSayisi;
    }

    public int getTakipEdilenSayisi() {
        return takipEdilenSayisi;
    }

    public String getDil() {
        return dil;
    }

    public String getBolge() {
        return bolge;
    }

    public MyArrayList<Tweet> getTweetIcerikleri() {
        return tweetIcerikleri;
    }

    public MyArrayList<String> getTakipEdilenler() {
        return takipEdilenler;
    }

    public MyArrayList<String> getTakipciler() {
        return takipciler;
    }

    public String getIlgiAlani() {
        return ilgiAlani;
    }

    public String getHashtag() {
        return hashtag;
    }
}
