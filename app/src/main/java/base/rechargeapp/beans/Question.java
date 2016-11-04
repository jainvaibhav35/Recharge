package base.rechargeapp.beans;

/**
 * Created by lin on 2/8/16.
 */

public class Question {

    String title;
    String link;

    @Override
    public String toString() {
        return (title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
