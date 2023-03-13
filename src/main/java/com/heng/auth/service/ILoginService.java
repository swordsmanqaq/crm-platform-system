package com.heng.auth.service;

import com.heng.auth.dto.LoginDTO;

import java.util.Map;
import java.util.Objects;

/**
 * @author shkstart
 * @create 2023-03-07 17:23
 */
public interface ILoginService {
    Map<String, Object> loginIn(LoginDTO dto);
}
