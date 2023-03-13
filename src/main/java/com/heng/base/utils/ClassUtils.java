package com.heng.base.utils;/**
 * @author shkstart
 * @create 2023-03-05 20:03
 */

import org.assertj.core.api.UrlAssert;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/5日20:03
 *@Description:
 */
public class ClassUtils {

    public static List<Class> getClassName(String packageNames){
        String[] split = packageNames.split(",");
        ArrayList<Class> result = new ArrayList<>();
        for (String packageName : split) {
            //获取项目包的相对路径
            String packagePath = packageName.replace(".", "/");
            URL url = ClassLoader.getSystemResource("");
            //url.getPath() + packagePath 获取完整的路径
            File[] files = new File(url.getPath() + packagePath).listFiles(file -> file.getName().endsWith(".class"));
            if (files == null || files.length < 1){
                continue;
            }
            for (File file : files){
                //获取类名
                String fileName = file.getName();
                fileName = fileName.substring(0,fileName.lastIndexOf("."));
                //全限定类名
                String allNames = packageName + "." +fileName;
                try {
                    result.add(Class.forName(allNames));
                }catch(ClassNotFoundException e){
                    e.printStackTrace();
                }

            }
        }

        return result;

    }
}
