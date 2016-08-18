package com.example.lenovo.qqdemos.chat.bean;

/**
 * Created by lenovo on 2016/8/18.
 */
public class EmotionId {
    private int emotioId;
    private int emotion;

    public EmotionId(int id, int emotion) {
        this.emotioId = id;
        this.emotion = emotion;
    }

    public int getEmotion() {
        return emotion;
    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }

    public int getEmotioId() {
        return emotioId;
    }

    public void setEmotioId(int emotioId) {
        this.emotioId = emotioId;
    }
}
