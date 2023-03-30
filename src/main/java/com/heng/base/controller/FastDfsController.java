package com.heng.base.controller;/**
 * @author shkstart
 * @create 2023-03-29 14:18
 */

import com.heng.base.service.IFastDfsService;
import com.heng.base.utils.AjaxResult;
import com.heng.base.utils.FastdfsUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/29日14:18
 *@Description:
 */
@RestController
@RequestMapping("/fastDfs")
public class FastDfsController {

    @Autowired
    private IFastDfsService iFastDfsService;

    @PostMapping("/upload" )
    public AjaxResult upload(MultipartFile file) {
        try {

            String originalFilename = file.getOriginalFilename();
            //文件扩展名
            String extension = FilenameUtils.getExtension(originalFilename);
            //String path = FastdfsUtil.upload(file.getBytes(), extension);
            String path = iFastDfsService.upload(file.getBytes(), extension);
            return AjaxResult.me().setResultObj(path);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("文件上传失败！" + e.getMessage());
        }
    }


    @DeleteMapping("/delete" )
    public AjaxResult deleteFile(String path) {
        try {
            iFastDfsService.deleteFile(path);
            return AjaxResult.me().setResultObj(path);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("文件上传失败！" + e.getMessage());
        }
    }


}
