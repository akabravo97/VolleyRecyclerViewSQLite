package abhijeet.volleytweet;

/**
 * Created by MAHE on 10-Apr-18.
 */
public class Info {
    String coin_name;
    String coin_symbol;
    String coin_handle;
    String tweet;
    public Info(String coin_name, String coin_symbol, String coin_handle, String tweet) {
        this.coin_name = coin_name;
        this.coin_symbol = coin_symbol;
        this.coin_handle = coin_handle;
        this.tweet = tweet;
    }

    public String getCoin_name() {
        return coin_name;
    }

    public void setCoin_name(String coin_name) {
        this.coin_name = coin_name;
    }

    public String getCoin_symbol() {
        return coin_symbol;
    }

    public void setCoin_symbol(String coin_symbol) {
        this.coin_symbol = coin_symbol;
    }

    public String getCoin_handle() {
        return coin_handle;
    }

    public void setCoin_handle(String coin_handle) {
        this.coin_handle = coin_handle;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
}

