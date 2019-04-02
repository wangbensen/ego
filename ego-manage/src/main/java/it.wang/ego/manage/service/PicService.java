package it.wang.ego.manage.service;

import it.ego.commons.utils.FtpUtil;
import it.ego.commons.utils.IDUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface PicService {
    /**
     * 文件上传
     * @param file
     * @return
     */
    Map<String,Object> upload(MultipartFile file) throws
            IOException;
}
