package com.lytips.ITags.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.lytips.ITags.utils.FileUtils;
import com.lytips.ITags.utils.OssUtils;

@Controller
@RequestMapping(value = "/test")
public class FileUploadController {

    // 文件上传路径
    private String fileuploadPath;
    
    /**
     * 文件上传Action
     * @param req APPLICATION_JSON_VALUE
     * @return UEDITOR 需要的json格式数据
     */
    @RequestMapping(value = "/test.do")
    @ResponseBody
    public Map<String,Object> upload(HttpServletRequest req){
        Map<String,Object> result = new HashMap<String, Object>();
        
        MultipartHttpServletRequest mReq  =  null;
        MultipartFile file = null;
        InputStream is = null ;
        String fileName = "";
        // 原始文件名   UEDITOR创建页面元素时的alt和title属性
        String originalFileName = "";
        try {
            mReq = (MultipartHttpServletRequest)req;
            // 从config.json中取得上传文件的ID
            file = mReq.getFile("upfile");
            // 取得文件的原始文件名称
            fileName = file.getOriginalFilename();

            originalFileName = fileName;
            
            if(!fileName.isEmpty()){
                is = file.getInputStream();
                fileName = FileUtils.reName(fileName);
                fileuploadPath = OssUtils.upLoadInputStream(fileName, is);
            } else {
                throw new IOException("文件名为空!");
            }
            
            result.put("state", "SUCCESS");// UEDITOR的规则:不为SUCCESS则显示state的内容
            result.put("url", fileuploadPath);
            result.put("title", originalFileName);
            result.put("original", originalFileName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("state", "文件上传失败!");
            result.put("url","");
            result.put("title", "");
            result.put("original", "");
            System.out.println("文件 "+fileName+" 上传失败!");
        }
        
        return result;
    }
}