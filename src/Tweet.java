public class Tweet {
    private int ID;
    private String icerik;
    private String hashtag;

    public Tweet(int ID, String icerik, String hashtag) {
        this.ID = ID;
        this.icerik = icerik;
        this.hashtag = hashtag;
    }

    public String getIcerik() {
        return icerik;
    }

    public String getHashtag() {
        return hashtag;
    }
}
