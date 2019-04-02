package it.wang.ego.manage.service.impl;

import it.ego.commons.utils.FtpUtil;
import it.ego.commons.utils.IDUtils;
import it.wang.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PicServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/116:19
 * @Version 1.0
 **/
@Service
public class PicServiceImpl  implements PicService{
    @Value("${ftpclient.host}")
    private String host;
    @Value("${ftpclient.port}")
    private int port;
    @Value("${ftpclient.username}")
    private String username;
    @Value("${ftpclient.password}")
    private String password;
    @Value("${ftpclient.basepath}")
    private String basePath;
    @Value("${ftpclient.filepath}")
    private String filePath;
    @Override
    public Map<String,Object> upload(MultipartFile file) throws IOException {
        String genImageName =
                IDUtils.genImageName()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        boolean result = FtpUtil.uploadFile(host, port,
                username, password, basePath, filePath, genImageName,
                file.getInputStream());
        Map<String,Object> map = new HashMap();
        if(result){
            map.put("error", 0);
            map.put("url","http://"+
                    host+"/"+genImageName);
        }else{
            map.put("error", 1);
            map.put("message", "图片上传失败");
        }
        return map;
    }

}
