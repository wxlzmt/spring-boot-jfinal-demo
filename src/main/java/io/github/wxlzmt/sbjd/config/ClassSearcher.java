/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package io.github.wxlzmt.sbjd.config;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 本类代码借鉴自 https://github.com/jflyfox/jfinal_cms/tree/master/src/main/java/com/jflyfox/util/annotation/ClassSearcher.java
 * 
 * 类扫描：借鉴Jfinal ext
 * 
 * 2014年6月9日 下午12:39:43 flyfox 330627517@qq.com
 */
public class ClassSearcher {

    static URL classPathUrl = ClassSearcher.class.getResource("/");

    public static <T> List<Class<? extends T>> findInClasspath(Class<T> clazz) {
        List<String> classFileList = findFiles();
        return extraction(clazz, classFileList);
    }

    /**
     * 递归查找文件
     */
    private static List<String> findFiles() {
        List<String> classNameList = new ArrayList<String>();
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource baseRes = resolver.getResource("classpath:/");
            System.out.println("baseRes=" + baseRes);
            Resource[] resArr = resolver.getResources("classpath:/io/github/wxlzmt/**/*.class");
            for (int i = 0; i < resArr.length; i++) {
                Resource res = resArr[i];
                if (res instanceof ClassPathResource) {
                    //System.out.println("获取到的resource是ClassPathResource");
                    ClassPathResource cpRes = (ClassPathResource) res;
                    String className = cpRes.getPath();
                    classNameList.add(trimClassName(className));
                } else if (res instanceof FileSystemResource) {
                    //System.out.println("获取到的resource是FileSystemResource");
                    String baseResAbsPath = baseRes.getFile().getAbsolutePath();
                    String resAbsPath = res.getFile().getAbsolutePath();
                    if(resAbsPath.startsWith(baseResAbsPath)) {
                        String className = resAbsPath.substring(baseResAbsPath.length());
                        classNameList.add(trimClassName(className));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n------由"+ClassSearcher.class.getName()+".findFiles()"+"扫描到的class ---- start print ==>----");
        
        for(String name : classNameList) {
            System.out.println(name);
        }
        System.out.println("-------<== end print !!!-----------\n");
        
        return classNameList;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<Class<? extends T>> extraction(Class<T> clazz, List<String> classFileList) {
        List<Class<? extends T>> classList = new ArrayList<Class<? extends T>>();
        for (String classFile : classFileList) {
            Class<?> classInFile = null;
            try {
                classInFile = Class.forName(classFile);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz.isAssignableFrom(classInFile) && clazz != classInFile) {
                classList.add((Class<? extends T>) classInFile);
            }
        }

        return classList;
    }

    /**
     * 掐头去尾去掉多余字符
     * @param name 入参可能是 "\com\xxx\xxxx\JfcmsApplication.class" 或者 "com/xxx/xxxx/AppInfo.class"
     * @return com/xxx/xxxx/AppInfo.class
     */
    private static String trimClassName(String name) {
        String str = name.replace("/", ".").replace("\\", ".");
        if(str.startsWith(".")) {
            str = str.substring(1);
        }
        if(str.endsWith(".class")) {
            str = str.substring(0, str.length()-".class".length());
        }
        return str;
    }
}
