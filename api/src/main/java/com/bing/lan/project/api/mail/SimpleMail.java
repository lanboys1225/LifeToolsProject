package com.bing.lan.project.api.mail;

public class SimpleMail {

    private String mSubject;
    private Object mContent;

    String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    Object getContent() {
        return mContent;
    }

    public void setContent(Object content) {
        mContent = content;
    }
}
