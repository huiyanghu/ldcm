package com.lvdun.service;

import com.lvdun.entity.CodeApp;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface CodeAppService {
    public List<CodeApp> getCodeAppByPlatformId(Long platformId);
}
