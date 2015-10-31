package tdt.minh095.ohman.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "T05_BadWords", id = "_id")
public class BadWord extends Model {

    @Column(name = "Word")
    private String word;

    public BadWord() {
        super();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public static List<BadWord> getAllBadWords() {
        return new Select().from(BadWord.class).execute();
    }
}
