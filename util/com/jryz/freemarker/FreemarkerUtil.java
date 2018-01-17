
package com.jryz.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Map;

public class FreemarkerUtil {

    private static final String DEFAULT_PATH = "/ftl";

    /**
     * 拿到 template
     * @param name
     * @return
     */
    public static Template getTemplate(String name) {
        return getTemplate(name, DEFAULT_PATH);
    }

    public static Template getTemplate(String name, String path) {
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(FreemarkerUtil.class, path); // 设定去哪里读取相应的ftl模板文件
            return cfg.getTemplate(name); // 加载模板
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 输出到文件
     *
     * @param name
     * @param root
     * @param outFile
     */
    public static void print(String name, Map<String, Object> root, String outFile, String charsetName) {
        Resource re = new FileSystemResource(outFile);
        if (re.isOpen()) {
            throw new IllegalArgumentException("文件占用 : " + outFile);
        }

        OutputStreamWriter out = null;
        try {
            File file = re.getFile();
            if (!re.exists()) {
                file.getParentFile().mkdirs();
            }
            out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            Template temp = FreemarkerUtil.getTemplate(name);
            temp.setEncoding("UTF-8");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输出到文件
     *
     * @param name
     * @param root
     * @param outFile
     */
    public static void print(String name, Map<String, Object> root, String outFile) {
        print(name, root, outFile, "utf-8");
    }
}