package com.heng.base.service.impl;/**
 * @author shkstart
 * @create 2023-03-29 14:49
 */

import com.heng.base.service.IFastDfsService;
import com.heng.base.utils.FastdfsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/29日14:49
 *@Description:
 */
@Service
public class FastDfsServiceImpl implements IFastDfsService {

    @Value("${fastDfs.prefixUrl}")
    private String prefixUrl;

    @Override
    public String upload(byte[] bytes, String extension) {
        return prefixUrl + FastdfsUtil.upload(bytes,extension);
    }

    @Override
    public void deleteFile(String path) {
        path = path.substring(this.prefixUrl.length());
        FastdfsUtil.delete(path);
    }
}
