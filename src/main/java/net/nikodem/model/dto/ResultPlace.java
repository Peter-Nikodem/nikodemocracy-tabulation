package net.nikodem.model.dto;

public class ResultPlace {
    private String answer;
    private int votes;

    public ResultPlace(String answer, int votes) {
        this.answer = answer;
        this.votes = votes;
    }

    public ResultPlace() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "ResultPlace{" +
                "answer='" + answer + '\'' +
                ", votes=" + votes +
                '}';
    }
}
