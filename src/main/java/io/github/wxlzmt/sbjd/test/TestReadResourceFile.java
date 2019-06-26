package io.github.wxlzmt.sbjd.test;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class TestReadResourceFile {
    
    public static void main(String[] args) throws Exception {
        TestReadResourceFile t = new TestReadResourceFile();
        Map<String, String> map = t.testTxtractProp();
        List<String> list = t.testTxtractClasses();

        System.out.println("\n----------输出properties结果:-------------");
        for (String key : map.keySet()) {
            String val = map.get(key);
            System.out.println("key=" + key + ",value=" + val);
        }
        System.out.println("---------<<============end------------\n");

        System.out.println("\n----------输出扫描到的class结果:-------------");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("---------<<============end------------\n");

    }

    /**
     * 测试: 抽取 conf/ 目录下的所有properties文件的内容,合并为一个map
     */
    public Map<String, String> testTxtractProp() throws Exception {
        //通配符模式,参考https://www.cnblogs.com/soltex/archive/2013/12/10/3466697.html
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //获取classpath下的conf目录下的所有扩展名为.properties的文件.
        Resource[] resArr = resolver.getResources("classpath:/conf/*.properties");
        if (resArr.length > 0) {
            if (resArr[0] instanceof ClassPathResource) {
                //当把项目打成jar包运行时,走这个if
                System.out.println("获取到的resource是ClassPathResource");
            } else if (resArr[0] instanceof FileSystemResource) {
                //当在eclipse或者idea等集成开发环境下运行项目时,走这个if
                System.out.println("获取到的resource是FileSystemResource");
            }
        }
        System.out.println("获取到Resource[]内容==>" + Arrays.toString(resArr));
        Map<String, String> tmpConfigMap = new HashMap<String, String>();
        for (int i = 0; i < resArr.length; i++) {
            Resource resource = resArr[i];
            InputStream is = resource.getInputStream();
            if (is == null) {
                System.out.println("inputStream is NULL !");
            }
            Properties props = new Properties();
            props.load(is);
            Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
            while (it.hasNext()) {
                Entry<Object, Object> entry = it.next();
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                if (!tmpConfigMap.containsKey(key)) {
                    tmpConfigMap.put(key, value);
                } else {
                    System.err.println("CONFIG EEOR:key(" + key + ") is exist;");
                }
            }
            if (is != null) {
                is.close();
            }
        }
        return tmpConfigMap;
    }

    /**
     * 抽取指定目录下的所有class,返回的内容的格式是: com.xxx.Axx
     */
    public List<String> testTxtractClasses() {
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
                    if (resAbsPath.startsWith(baseResAbsPath)) {
                        String className = resAbsPath.substring(baseResAbsPath.length());
                        classNameList.add(trimClassName(className));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNameList;
    }

    /**
     * 掐头去尾去掉多余字符
     * @param name 入参可能是 "\com\xxx\xxxx\JfcmsApplication.class" 或者 "com/xxx/xxxx/AppInfo.class"
     * @return com/xxx/xxxx/AppInfo.class
     */
    private static String trimClassName(String name) {
        String str = name.replace("/", ".").replace("\\", ".");
        if (str.startsWith(".")) {
            str = str.substring(1);
        }
        if (str.endsWith(".class")) {
            str = str.substring(0, str.length() - ".class".length());
        }
        return str;
    }
}
