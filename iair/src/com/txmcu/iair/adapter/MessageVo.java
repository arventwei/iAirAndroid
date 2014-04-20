/**
 * @Title: MessageVo.java 
 * @Package com.test.testim 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author Alex.Z   
 * @date 2013-2-27 上午11:23:17 
 * @version V1.0
 */
package com.txmcu.iair.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公告信息详情
 * @author Administrator
 *
 */
public class MessageVo {
    public static final int MESSAGE_FROM = 0;
    public static final int MESSAGE_TO = 1;
    
    public int direction;    //该变量为消息是收到(MESSAGE_FROM)的还是发送(MESSAGE_TO)的
    
    public String noticeid;
    public String content;   //内容
    public String time;       //时间
    public String userName;
    public MessageVo(String noticeid,int direction, String content, int	 timestamp,String userName) {
        super();
        this.direction = direction;
        this.content = content;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm MM/dd");
		this.time = df.format(new  java.util.Date(timestamp*1000)).toString();
		this.userName = userName;
       // this.time = time;
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