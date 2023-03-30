package com.heng.base.service;

/**
 * @author shkstart
 * @create 2023-03-29 14:46
 */
public interface IFastDfsService {

    String upload(byte[] bytes, String extension);

    void deleteFile(String path);
}
