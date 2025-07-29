package com.telusko.quizapp.model;

public class Response {

    private Integer id;
    private String submittedAns;

    public Response(Integer id, String submittedAns) {
        this.id = id;
        this.submittedAns = submittedAns;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubmittedAns() {
        return submittedAns;
    }

    public void setSubmittedAns(String submittedAns) {
        this.submittedAns = submittedAns;
    }
}
