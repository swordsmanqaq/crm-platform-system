package com.heng.user.service;

import com.heng.user.dto.BindParamDTO;
import com.heng.user.dto.MessageCodeDTO;

/**
 * @author shkstart
 * @create 2023-04-06 18:44
 */
public interface IVerificationCodeService {
    String getImg(String imageCodeKey);

    void sendMessageCode(MessageCodeDTO dto);

    void sendPhoneMessage(MessageCodeDTO dto);

    void sendPhoneMessageChangePassword(MessageCodeDTO dto);

    void sendPhoneMessageToBind(MessageCodeDTO dto);

}
