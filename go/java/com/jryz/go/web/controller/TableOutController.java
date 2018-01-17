package com.jryz.go.web.controller;


import com.jryz.go.web.bean.TableBean;
import com.jryz.go.web.service.ConfigService;
import com.jryz.go.web.service.FieldService;
import com.jryz.go.web.service.TableCoreService;
import com.jryz.go.web.service.TableService;
import com.jryz.model.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 表导入相关接口
 * 读取表信息部分 使用jdbc 链接。 与架构脱离
 * @author
*/
@RequestMapping("/o/tableOut")
@Controller
public class TableOutController {


    @Autowired
    private FieldService fieldService;
    @Autowired
    private TableService tableService;
    @Autowired
    private TableCoreService tableInputService;
    @Autowired
    private ConfigService configService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取table列表
     * @param tableId
     * @return
     */
    @RequestMapping("selectTable/{tableId}")
    @ResponseBody
    public ApiResult selectTable(@PathVariable String tableId){
        ApiResult re = new ApiResult();
        try {
            TableBean tableBean= tableService.get(tableId);
            Assert.notNull(tableBean, "表单信息不存在");

        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }
}
