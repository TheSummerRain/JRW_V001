package com.core.login.controller;

import com.core.login.bean.UploadResult;
import com.jryz.model.ApiResult;
import com.jryz.web.WebProjectUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping(params = "upload")
    @ResponseBody
    public ApiResult upload(MultipartFile file, HttpServletRequest request) throws IOException {
        ApiResult re = new ApiResult();
        UploadResult uploadResult = new UploadResult();

        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File newFile = new File(WebProjectUtil.getWebrootPath() + "/file",
                UUID.randomUUID().toString().replace("-", "") + fileExt);
        if (!newFile.getParentFile().isDirectory()) {
            newFile.getParentFile().mkdirs();
        }
        newFile.createNewFile();
        file.transferTo(newFile);
        uploadResult.setFileLength(newFile.length());
        uploadResult.setRealPath(newFile.getPath());
        uploadResult.setFilePath(WebProjectUtil.getProjectUrl(request) + "/file/" + newFile.getName());
        re.setData(uploadResult);
        re.setSuccess(true);

        return re;
    }
}
