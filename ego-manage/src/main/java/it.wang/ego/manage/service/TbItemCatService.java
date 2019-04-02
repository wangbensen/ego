package it.wang.ego.manage.service;



import it.ego.commons.pojo.EasyUiTree;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TbItemCatService {
    /**
     * 根据父菜单 id 显示所有子菜单
     * @param pid
     * @return
     */
    List<EasyUiTree> show(long pid);
}
