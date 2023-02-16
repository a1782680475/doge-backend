package com.doge.controller.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.doge.entity.vo.response.UploadResultVO;
import com.doge.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 通用上传
 *
 * @author shixinyu
 * @date 2021-09-30 15:56
 */
@RestController
@Api(value = "UploadController", tags = "文件上传")
@RequestMapping("/common/upload/")
public class UploadController {
    @Value("${file.uploadFolder}")
    private String uploadPath;
    @Value("${file.urlPrefix}")
    private String urlPrefix;

    @ApiOperation(value = "普通上传")
    @PostMapping("/file")
    public UploadResultVO uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(name = "autoName", defaultValue = "true", required = false) Boolean autoName) {
        if (null == file) {
            throw new BusinessException("上传失败，无法找到文件！");
        }
        String fileName;
        if (autoName) {
            fileName = IdUtil.randomUUID() + "." + FileUtil.getSuffix(file.getOriginalFilename());
        } else {
            fileName = file.getOriginalFilename();
        }
        String dateStr = DateUtil.format(DateUtil.date(), "yyyyMMdd");
        UploadResultVO uploadResultVO = fileUploadQuietly(file, fileName, dateStr);
        return uploadResultVO;
    }

    private UploadResultVO fileUploadQuietly(MultipartFile file, String uploadFileName, String path) {
        try {
            String directoryPath = uploadPath + "/" + path;
            if (!FileUtil.exist(directoryPath)) {
                File directory = new File(directoryPath);
                FileUtils.forceMkdir(directory);
            }
            String fileFullPath = directoryPath + "/" + uploadFileName;
            FileOutputStream out = new FileOutputStream(fileFullPath);
            BufferedOutputStream bufferedStream = new BufferedOutputStream(out);
            bufferedStream.write(file.getBytes());
            IOUtils.closeQuietly(bufferedStream);
            IOUtils.closeQuietly(out);
            return new UploadResultVO(uploadFileName, urlPrefix + "/" + path + "/" + uploadFileName);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
