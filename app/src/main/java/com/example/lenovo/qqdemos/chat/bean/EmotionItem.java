package com.example.lenovo.qqdemos.chat.bean;

/**
 * Created by lenovo on 2016/8/18.
 */
public class EmotionItem  {

    private EmotionId emotion1;
    private EmotionId emotion2;
    private EmotionId emotion3;
    private EmotionId emotion4;
    private EmotionId emotion5;
    private EmotionId emotion6;
    private EmotionId emotion7;

    public EmotionItem(EmotionId emotion1, EmotionId emotion2, EmotionId emotion3, EmotionId emotion4, EmotionId emotion5, EmotionId emotion6, EmotionId emotion7) {
        this.emotion1 = emotion1;
        this.emotion2 = emotion2;
        this.emotion3 = emotion3;
        this.emotion4 = emotion4;
        this.emotion5 = emotion5;
        this.emotion6 = emotion6;
        this.emotion7 = emotion7;
    }

    public EmotionId getEmotion1() {
        return emotion1;
    }

    public void setEmotion1(EmotionId emotion1) {
        this.emotion1 = emotion1;
    }

    public EmotionId getEmotion2() {
        return emotion2;
    }

    public void setEmotion2(EmotionId emotion2) {
        this.emotion2 = emotion2;
    }

    public EmotionId getEmotion3() {
        return emotion3;
    }

    public void setEmotion3(EmotionId emotion3) {
        this.emotion3 = emotion3;
    }

    public EmotionId getEmotion4() {
        return emotion4;
    }

    public void setEmotion4(EmotionId emotion4) {
        this.emotion4 = emotion4;
    }

    public EmotionId getEmotion5() {
        return emotion5;
    }

    public void setEmotion5(EmotionId emotion5) {
        this.emotion5 = emotion5;
    }

    public EmotionId getEmotion6() {
        return emotion6;
    }

    public void setEmotion6(EmotionId emotion6) {
        this.emotion6 = emotion6;
    }

    public EmotionId getEmotion7() {
        return emotion7;
    }

    public void setEmotion7(EmotionId emotion7) {
        this.emotion7 = emotion7;
    }
}
