package com.lvdun.service.impl;

import com.lvdun.dao.CodeAppRepository;
import com.lvdun.entity.CodeApp;
import com.lvdun.service.CodeAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
@Service
public class CodeAppServiceImpl implements CodeAppService {
    @Autowired
    CodeAppRepository codeAppDao;

    @Override
    public List<CodeApp> getCodeAppByPlatformId(Long platformId) {
        return codeAppDao.getCodeAppByPlatformId(platformId);
    }
}
