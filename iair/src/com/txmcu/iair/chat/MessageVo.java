/**
 * @Title: MessageVo.java 
 * @Package com.test.testim 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author Alex.Z   
 * @date 2013-2-27 上午11:23:17 
 * @version V1.0
 */
package com.txmcu.iair.chat;

public class MessageVo {
    public static final int MESSAGE_FROM = 0;
    public static final int MESSAGE_TO = 1;
    
    private int direction;    //该变量为消息是收到(MESSAGE_FROM)的还是发送(MESSAGE_TO)的
    private String content;   //内容
    private String time;       //时间
    public MessageVo(int direction, String content, String time) {
        super();
        this.direction = direction;
        this.content = content;
        this.time = time;
    }
    public int getDirection() {
        return direction;
    }
    public String getContent() {
        return content;
    }
    public String getTime() {
        return time;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setTime(String time) {
        this.time = time;
    }
}