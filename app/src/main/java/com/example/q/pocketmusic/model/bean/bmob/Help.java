package com.example.q.pocketmusic.model.bean.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/2/15.
 */

public class Help extends BmobObject{
    private String question;
    private String answer;

    public Help(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
