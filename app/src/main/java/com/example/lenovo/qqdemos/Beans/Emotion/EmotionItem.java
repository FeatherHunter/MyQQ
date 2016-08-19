package com.example.lenovo.qqdemos.Beans.Emotion;

/**
 * Created by lenovo on 2016/8/18.
 */
public class EmotionItem  {

    private EmotionId[] emotionIds;

    public EmotionItem(EmotionId[] emotionIds) {
        this.emotionIds = emotionIds;
    }

    public EmotionId getEmotion(int position) {
        return emotionIds[position];
    }

    public void setEmotion(int position, EmotionId emotion) {
        emotionIds[position] = emotion;
    }
}
