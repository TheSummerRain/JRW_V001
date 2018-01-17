package com.jryz.web.view;

import org.springframework.web.servlet.view.InternalResourceView;

import java.io.File;
import java.util.Locale;

/**
 * @author caixl
 * @ClassName: HtmlResourceView
 * @date 2016-6-8 上午11:01:41
 */
public class HtmlResourceView extends InternalResourceView {

    /**
     * 同一个路径不会重复调用该方法
     * @param locale
     * @return
     */
    @Override
    public boolean checkResource(Locale locale) {
        File file = new File(this.getServletContext().getRealPath("/") + getUrl());
        return file.exists();// 判断该页面是否存在
    }
}  