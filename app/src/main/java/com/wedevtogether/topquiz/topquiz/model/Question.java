package com.wedevtogether.topquiz.topquiz.model;

import java.util.List;

public class Question {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex){
        this.setQuestion(question);
        this.setChoiceList(choiceList);
        this.setAnswerIndex(answerIndex);
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }
}
