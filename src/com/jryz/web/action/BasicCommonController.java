package com.jryz.web.action;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BasicCommonController {

    Logger log = Logger.getLogger(this.getClass());

    public void writeJSON(HttpServletResponse resp, Object obj) {
        String jsonStr = new Gson().toJson(obj);
        resp.setContentType("application/json; charset=utf-8");
        try {
            resp.getWriter().print(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}

