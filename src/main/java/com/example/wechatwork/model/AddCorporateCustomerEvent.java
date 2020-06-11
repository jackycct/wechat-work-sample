package com.example.wechatwork.model;

import lombok.Data;

@Data
public class AddCorporateCustomerEvent {
    private String toUserName;
    private String fromUserName;
    private int createTime;
    private String msgType;
    private String event;
    private String changeType;
    private String userID;
    private String state;
    private String welcomeCode;
}
