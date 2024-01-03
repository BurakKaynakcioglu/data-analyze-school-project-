import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.*;


class Node{
    private User user;
    private MyArrayList<Edge> edgeList;

    public Node(User user){
        this.user = user;
        this.edgeList = new MyArrayList<>();
    }

    public String getName(){
        return user.getKullaniciAdi();
    }

    public MyArrayList<Edge> getEdgeList(){
        return edgeList;
    }

    public User getUser() {
        return user;
    }
}

class Edge{
    private Node destNode;

    public Edge(Node dest){
        this.destNode = dest;
    }

    public Node getDestNode(){
        return destNode;
    }
}

class MyGraph{
    private MyArrayList<Node> nodes;

    public MyGraph(){
        nodes = new MyArrayList<>();
    }

    public void AddEdge(Node node1, Node node2){
         node1.getEdgeList().add(new Edge(node2));
    }

    public void AddNode(Node node){
         nodes.add(node);
    }

    public Node getNodeByName(String kullaniciAdi) {
        for (int i = 0; i < nodes.size(); i++){
            Node node = nodes.get(i);
            if (node.getName().equals(kullaniciAdi)) {
                return node;
            }
        }

        return null;
    }

   public void findCommonTweets(Node startNode, String input, String type) {
       MyArrayList<Node> visited = new MyArrayList<>();
       MyStack<Node> stack = new MyStack<>();

       stack.push(startNode);

       String pathname = "matching_" + type + "_" + input + ".json" ;
       File file = new File(pathname);

       int choice = type.equals("word") ? 1 : 2;

       putSquareBrackets(1, file);
       while (!stack.isEmpty()) {
           Node currentNode = stack.pop();

           if (!visited.isContains(currentNode)) {
               System.out.println("gezilen düğüm: " + currentNode.getName());
               visited.add(currentNode);

               switch (choice) {
                   case 1:
                       wordAnalyze(currentNode, input, file);
                       break;
                   case 2:
                       hashtagAnalyze(currentNode, input, file);
                       break;
               }

               for (int i = 0; i < currentNode.getEdgeList().size(); i++){
                   Edge edge = currentNode.getEdgeList().get(i);
                   Node nextNode = edge.getDestNode();
                   if (!visited.isContains(nextNode)) {
                       stack.push(nextNode);
                   }
               }
           }
       }
       putSquareBrackets(2, file);
       finalJSONForm(pathname);
    }
    
   private void wordAnalyze(Node node, String input, File file) {
       MyArrayList<Tweet> tweets = node.getUser().getTweetIcerikleri();
       Gson gson = new Gson();

       for (int i = 0; i < tweets.size(); i++) {
           String content = tweets.get(i).getIcerik();
           String[] words = content.split(" ");

           if (contains(words, input)) {
               String matchedTweet = gson.toJson(tweets.get(i));

               try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                   writer.write(matchedTweet);
                   writer.write(",");
                   writer.newLine();
               } catch (IOException e) {
                   System.exit(1);
               }
           }
       }
   }

   private void hashtagAnalyze(Node node, String input, File file) {
       MyArrayList<Tweet> tweets = node.getUser().getTweetIcerikleri();
       Gson gson = new Gson();

       for (int i = 0; i < tweets.size(); i++) {
           String hashtag = tweets.get(i).getHashtag();

           if (hashtag.equals("#" + input)) {
               String matchedTweet = gson.toJson(tweets.get(i));

               try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                   writer.write(matchedTweet);
                   writer.write(",");
                   writer.newLine();
               } catch (IOException e) {
                   System.exit(1);
               }
           }
       }
   }
   
   private boolean contains(String[] words, String targetWord) {
        for (String word : words) {
            if (word.equals(targetWord)) {
                return true;
            }
        }
        return false;
   }

   private void putSquareBrackets (int choice, File file) {
       String character = choice == 1 ? "[" : "]";
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
           writer.write(character);
           writer.newLine();
       } catch (IOException e) {
           System.exit(1);
       }
   }

   private void finalJSONForm(String dosyaYolu) {
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

   public void findCommons() {
       Gson gson = new Gson();
       try (BufferedWriter writer = new BufferedWriter(new FileWriter("user-baglantilar_ilgi_alani.json", true))) {
           writer.write("[");
           writer.newLine();

           MyHashMap<String, MyArrayList<String>> commonInterest = new MyHashMap<>();
           MyArrayList<String> temp = new MyArrayList<>();

           for (int i = 0; i < nodes.size(); i++) {
               MyArrayList<String> commons = new MyArrayList<>();
               User user = nodes.get(i).getUser();
               MyArrayList<String> followers = user.getTakipciler();
               MyArrayList<String> following = user.getTakipEdilenler();

               User user1 = nodes.get(i).getUser();
               String interest = user1.getIlgiAlani();
               if (commonInterest.get(interest) == null) {
                   MyArrayList<String> myArrayList = new MyArrayList<>();
                   myArrayList.add(user1.getKullaniciAdi());
                   commonInterest.put(interest, myArrayList);
                   temp.add(interest);
               } else {
                   MyArrayList<String> myArrayList = commonInterest.get(interest);
                   myArrayList.add(user1.getKullaniciAdi());
                   commonInterest.put(interest, myArrayList);
               }

               for (int x = 0; x < followers.size(); x++) {
                   String kullaniciAdi = followers.get(x);
                   User follower = getNodeByName(kullaniciAdi).getUser();

                   if (follower.getIlgiAlani().equals(user.getIlgiAlani())) {
                       commons.add(follower.getKullaniciAdi());
                   }
               }

               for (int y = 0; y < following.size(); y++) {
                   String kullaniciAdi = following.get(y);
                   User followed = getNodeByName(kullaniciAdi).getUser();

                   if (followed.getIlgiAlani().equals(user.getIlgiAlani())) {
                       commons.add(followed.getKullaniciAdi());
                   }
               }

               writer.write("{");
               writer.write(gson.toJson("kullanici_adi"));
               writer.write(":");
               writer.write(gson.toJson(user.getKullaniciAdi()));
               writer.write(",");
               writer.write(gson.toJson("ortak_ilgi_kullanici"));
               writer.write(":");
               writer.write("[");
               for (int a = 0; a < commons.size(); a++) {
                   String common = commons.get(a);
                   writer.write(gson.toJson(common));

                   if (a != commons.size() - 1) {
                       writer.write(", ");
                   }
               }
               writer.write("]");
               writer.write("}");
               if (i == nodes.size() - 1) {
                   writer.newLine();
                   writer.write("]");
               } else {
                   writer.write(",");
                   writer.newLine();
               }

               System.out.println("2: " + i); //SİL
           }
           writeCommonInterests(temp, commonInterest);
       } catch (IOException e) {
           System.exit(1);
       }
    }

    public void writeCommonInterests(MyArrayList<String> temp, MyHashMap<String, MyArrayList<String>> commonInterest) {
        Gson gson = new Gson();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ilgi_alani-kullanicilar.json", true))) {
            writer.write("[");
            writer.newLine();

            for (int i = 0; i < temp.size(); i++) {
                String interest = temp.get(i);

                MyArrayList<String> userList = commonInterest.get(interest);

                writer.write("{");
                writer.write(gson.toJson("ilgi_alani"));
                writer.write(":");
                writer.write(gson.toJson(interest));
                writer.write(",");
                writer.write(gson.toJson("kullanici"));
                writer.write(":");
                writer.write("[");
                for (int a = 0; a < userList.size(); a++) {
                    String common = userList.get(a);
                    writer.write(gson.toJson(common));
                    if (a != userList.size() - 1) {
                        writer.write(", ");
                    }
                }
                writer.write("]");
                writer.write("}");
                if (i == temp.size() - 1) {
                    writer.newLine();
                    writer.write("]");
                } else {
                    writer.write(",");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.exit(1);
        }
    }


   public void findTrendingforLanAndArea() {
       MyHashMap<String, MyHashMap<String, Integer>> countryInterests = new MyHashMap<>();
       MyHashMap<String, MyHashMap<String, Integer>> languageInterests = new MyHashMap<>();
       MyHashMap<String, MyHashMap<String, Integer>> countryHashtags = new MyHashMap<>();
       MyHashMap<String, MyHashMap<String, Integer>> languageHashtags = new MyHashMap<>();

       for (int i = 0; i < nodes.size(); i++) {
           String interest = nodes.get(i).getUser().getIlgiAlani();
           String hashtag = nodes.get(i).getUser().getHashtag();
           String country = nodes.get(i).getUser().getBolge();
           String language = nodes.get(i).getUser().getDil();

           if (countryInterests.get(country) == null) {
               countryInterests.put(country, new MyHashMap<>());
           }
           MyHashMap<String, Integer> countryInterestsMap = countryInterests.get(country);
           if (countryInterestsMap.get(interest) != null) {
               countryInterestsMap.put(interest, countryInterestsMap.get(interest) + 1);
           } else {
               countryInterestsMap.put(interest, 1);
           }

           if (languageInterests.get(language) == null) {
               languageInterests.put(language, new MyHashMap<>());
           }
           MyHashMap<String, Integer> languageInterestsMap = languageInterests.get(language);
           if (languageInterestsMap.get(interest) != null) {
               languageInterestsMap.put(interest, languageInterestsMap.get(interest) + 1);
           } else {
               languageInterestsMap.put(interest, 1);
           }

           if (countryHashtags.get(country) == null) {
               countryHashtags.put(country, new MyHashMap<>());
           }
           MyHashMap<String, Integer> countryHashtagsMap = countryHashtags.get(country);
           if (countryHashtagsMap.get(hashtag) != null) {
               countryHashtagsMap.put(hashtag, countryHashtagsMap.get(hashtag) + 1);
           } else {
               countryHashtagsMap.put(hashtag, 1);
           }

           if (languageHashtags.get(language) == null) {
               languageHashtags.put(language, new MyHashMap<>());
           }
           MyHashMap<String, Integer> languageHashtagsMap = languageHashtags.get(language);
           if (languageHashtagsMap.get(hashtag) != null) {
               languageHashtagsMap.put(hashtag, languageHashtagsMap.get(hashtag) + 1);
           } else {
               languageHashtagsMap.put(hashtag, 1);
           }

           System.out.println("3: " + i);
       }

       Gson gson = new Gson();
       try (BufferedWriter writer = new BufferedWriter(new FileWriter("country_ilgi_alani.json", true))) {
           writer.write("[");
           writer.newLine();

           MyArrayList<String> countryInterestsKeys = countryInterests.keySet();
           for (int i = 0; i < countryInterestsKeys.size(); i++) {
               String country = countryInterestsKeys.get(i);
               MyHashMap<String, Integer> interestsMap = countryInterests.get(country);
               int maxCount = 0;
               String trendingInterest = "";

               MyArrayList<String> interestsMapKeys = interestsMap.keySet();
               for (int j = 0; j < interestsMapKeys.size(); j++) {
                   String interest = interestsMapKeys.get(j);
                   int count = interestsMap.get(interest);
                   if (count > maxCount) {
                       maxCount = count;
                       trendingInterest = interest;
                   }
               }

               writer.write("{");
               writer.write(gson.toJson("Bolge"));
               writer.write(":");
               writer.write(gson.toJson(country));
               writer.write(",");
               writer.write(gson.toJson("ilgi_alani"));
               writer.write(":");
               writer.write(gson.toJson(trendingInterest));
               writer.write("}");

               if (i == countryInterestsKeys.size() - 1) {
                   writer.newLine();
                   writer.write("]");
               } else {
                   writer.write(",");
                   writer.newLine();
               }
           }
       } catch (IOException e) {
           System.exit(1);
       }

       try (BufferedWriter writer = new BufferedWriter(new FileWriter("language_ilgi_alani.json", true))) {
           writer.write("[");
           writer.newLine();

           MyArrayList<String> languageInterestsKeys = languageInterests.keySet();
           for (int i = 0; i < languageInterestsKeys.size(); i++) {
               String language = languageInterestsKeys.get(i);
               MyHashMap<String, Integer> interestsMap = languageInterests.get(language);
               int maxCount = 0;
               String trendingInterest = "";

               MyArrayList<String> interestsMapKeys = interestsMap.keySet();
               for (int j = 0; j < interestsMapKeys.size(); j++) {
                   String interest = interestsMapKeys.get(j);
                   int count = interestsMap.get(interest);
                   if (count > maxCount) {
                       maxCount = count;
                       trendingInterest = interest;
                   }
               }

               writer.write("{");
               writer.write(gson.toJson("Dil"));
               writer.write(":");
               writer.write(gson.toJson(language));
               writer.write(",");
               writer.write(gson.toJson("ilgi_alani"));
               writer.write(":");
               writer.write(gson.toJson(trendingInterest));
               writer.write("}");

               if (i == languageInterestsKeys.size() - 1) {
                   writer.newLine();
                   writer.write("]");
               } else {
                   writer.write(",");
                   writer.newLine();
               }
           }
       } catch (IOException e) {
           System.exit(1);
       }

       try (BufferedWriter writer = new BufferedWriter(new FileWriter("country_hashtag.json", true))) {
           writer.write("[");
           writer.newLine();

           MyArrayList<String> countryHashtagsKeys = countryHashtags.keySet();
           for (int i = 0; i < countryHashtagsKeys.size(); i++) {
               String country = countryHashtagsKeys.get(i);
               MyHashMap<String, Integer> hashtagsMap = countryHashtags.get(country);
               int maxCount = 0;
               String trendingHashtag = "";

               MyArrayList<String> hashtagsMapKeys = hashtagsMap.keySet();
               for (int j = 0; j < hashtagsMapKeys.size(); j++) {
                   String hashtag = hashtagsMapKeys.get(j);
                   int count = hashtagsMap.get(hashtag);
                   if (count > maxCount) {
                       maxCount = count;
                       trendingHashtag = hashtag;
                   }
               }

               writer.write("{");
               writer.write(gson.toJson("Bolge"));
               writer.write(":");
               writer.write(gson.toJson(country));
               writer.write(",");
               writer.write(gson.toJson("hashtag"));
               writer.write(":");
               writer.write(gson.toJson(trendingHashtag));
               writer.write("}");

               if (i == countryHashtagsKeys.size() - 1) {
                   writer.newLine();
                   writer.write("]");
               } else {
                   writer.write(",");
                   writer.newLine();
               }
           }
       } catch (IOException e) {
           System.exit(1);
       }

       try (BufferedWriter writer = new BufferedWriter(new FileWriter("language_hashtag.json", true))) {
           writer.write("[");
           writer.newLine();

           MyArrayList<String> languageHashtagsKeys = languageHashtags.keySet();
           for (int i = 0; i < languageHashtagsKeys.size(); i++) {
               String language = languageHashtagsKeys.get(i);
               MyHashMap<String, Integer> hashtagsMap = languageHashtags.get(language);
               int maxCount = 0;
               String trendingHashtag = "";

               MyArrayList<String> hashtagsMapKeys = hashtagsMap.keySet();
               for (int j = 0; j < hashtagsMapKeys.size(); j++) {
                   String hashtag = hashtagsMapKeys.get(j);
                   int count = hashtagsMap.get(hashtag);
                   if (count > maxCount) {
                       maxCount = count;
                       trendingHashtag = hashtag;
                   }
               }

               writer.write("{");
               writer.write(gson.toJson("Dil"));
               writer.write(":");
               writer.write(gson.toJson(language));
               writer.write(",");
               writer.write(gson.toJson("hashtag"));
               writer.write(":");
               writer.write(gson.toJson(trendingHashtag));
               writer.write("}");

               if (i == languageHashtagsKeys.size() - 1) {
                   writer.newLine();
                   writer.write("]");
               } else {
                   writer.write(",");
                   writer.newLine();
               }
           }
       } catch (IOException e) {
           System.exit(1);
       }
   }

   public void analyzeGraph(Node startNode) {
       MyArrayList<Node> visited = new MyArrayList<>();
       MyQueue<Node> queue = new MyQueue<>();

       visited.add(startNode);
       queue.enqueue(startNode);

       int floorVal = startNode.getUser().getTakipciSayisi();

       String fileName = "graph_analyze_" + startNode.getUser().getKullaniciAdi() + ".json";
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
           writer.write("[");
           writer.newLine();

           while (!queue.bosMu()) {
               Node currentNode = queue.dequeue();
               System.out.println("gezilen düğüm: " + currentNode.getName());

               if (currentNode.getUser().getTakipciSayisi() > floorVal) {
                   User user = currentNode.getUser();
                   formatString(writer, user.getKullaniciAdi(), user.getAdSoyad(), user.getTakipciSayisi(),
                           user.getTakipEdilenSayisi(), user.getDil(), user.getBolge());
                   if (!queue.bosMu()) {
                       writer.write(",");
                   } else {
                       writer.newLine();
                       writer.write("]");
                       break;
                   }
                   writer.newLine();
               }

               MyArrayList<Edge> edges = currentNode.getEdgeList();
               for (int i = 0; i < edges.size(); i++) {
                   Node nextNode = edges.get(i).getDestNode();
                   if (!visited.isContains(nextNode)) {
                       visited.add(nextNode);
                       queue.enqueue(nextNode);
                   }
               }
           }
       } catch (IOException e ) {
           System.exit(1);
       }
   }

    private void formatString(BufferedWriter writer, String kullaniciAdi, String adSoyad, int takipciSayisi,
                                int takipEdilenSayisi, String dil, String bolge) {
        Gson gson = new Gson();
        try {
            writer.write("{");
            writer.write(gson.toJson("kullanici_adi"));
            writer.write(":");
            writer.write(gson.toJson(kullaniciAdi));
            writer.write(",");
            writer.write(gson.toJson("ad_soyad"));
            writer.write(":");
            writer.write(gson.toJson(adSoyad));
            writer.write(",");
            writer.write(gson.toJson("takipci_sayisi"));
            writer.write(":");
            writer.write(String.valueOf(takipciSayisi));
            writer.write(",");
            writer.write(gson.toJson("takip_edilen_sayisi"));
            writer.write(":");
            writer.write(String.valueOf(takipEdilenSayisi));
            writer.write(",");
            writer.write(gson.toJson("dil"));
            writer.write(":");
            writer.write(gson.toJson(dil));
            writer.write(",");
            writer.write(gson.toJson("bolge"));
            writer.write(":");
            writer.write(gson.toJson(bolge));
            writer.write("}");
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public MyArrayList<Node> getNodes() {
       return nodes;
   }
}
