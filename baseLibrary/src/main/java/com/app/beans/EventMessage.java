package com.app.beans;


public class EventMessage {

    public Object mObject;
    public Object mObject2;

    private int messageType;

    private int Num;

    public EventMessage(int type, Object object, Object object2) {

        messageType = type;
        mObject = object;
        mObject2 = object2;
    }

    public EventMessage(int type) {
        messageType = type;
    }

    public EventMessage(int type, Object object) {
        mObject = object;
        messageType = type;
    }

    public EventMessage(int type, Object object, int num) {
        mObject = object;
        messageType = type;
        Num = num;
    }

    public Object getmObject() {
        return mObject;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getNum() {
        return Num;
    }

    //消息类型
    public static final int LOGIN = 1;//登陆
    public static final int LOGOUT = 2;//
    public static final int add = 3;//
    public static final int addtalk = 4;//



    public static final int notice = 5;
    public static final int Click = 6;
    public static final int REFRESH = 7;
    public static final int DEL = 8;
    public static final int CHOICE_PIC = 9;
    public static final int REFRESH2 = 10;
    public static final int REFRESH3 = 11;
    public static final int REFRESH4 = 14;
    public static final int REFRESH5 = 15;

    public static final int REFRESH6 = 16;
    public static final int REFRESH7 = 17;
}
