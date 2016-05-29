package lazariciu.com.medicalhouse.domain;

import java.io.Serializable;

public class Answer implements Serializable{
    private String text;

    private boolean correct;

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
