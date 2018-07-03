package com.module.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chc
 * @create 2017-11-17 17:29
 **/
@Service("fileService")
public class FileService {


    /**
     * 回显图片
     * @author chc
     * @date 2017-08-17
     * @param params
     * @return
     */
    public void showImage(String params, HttpServletRequest request, HttpServletResponse response) {
//        Document parse = Document.parse(params);
//        ArrayList<FileUpload> findByBson = daoFactory.getFileDao().findByBson(parse);
//        if(findByBson!=null && findByBson.size()>0){
//            FileUpload fileUpload = findByBson.get(0);
//            try {
//                FileUtil.showImage(fileUpload.getAddress(), fileUpload.getNewFileName(), request, response);
//            } catch (Exception e) {
//                throwBaseException(ErrorCode.File_103, "展示图片失败:"+e.getMessage());
//            }
//        }
    }
}
