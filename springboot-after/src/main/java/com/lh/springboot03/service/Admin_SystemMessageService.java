package com.lh.springboot03.service;

import com.lh.springboot03.bean.SystemMessage;

import java.util.List;

public interface Admin_SystemMessageService {
    void releaseCommonSystemMessage(SystemMessage systemMessage);

    List<SystemMessage> getSystemMessageList();
}
