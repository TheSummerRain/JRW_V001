package com.jryz.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 文件加载器
 */
@Controller
@RequestMapping("/")
public class WebController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/{str}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String to(@PathVariable String str){
        logger.info("str : {}", str);
        logger.info("str : {}", str = str.replace("&", "/"));
        return str;
    }
}
