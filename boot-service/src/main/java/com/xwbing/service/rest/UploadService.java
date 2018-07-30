package com.xwbing.service.rest;

import com.xwbing.domain.entity.rest.FilesUpload;
import com.xwbing.domain.mapper.rest.FilesUploadMapper;
import com.xwbing.service.BaseService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xiangwb
 * @date: 2018/7/30 21:54
 * @description:
 */
public class UploadService extends BaseService<FilesUploadMapper, FilesUpload> {
    @Resource
    private FilesUploadMapper uploadMapper;

    @Override
    protected FilesUploadMapper getMapper() {
        return null;
    }

    public List<FilesUpload> findByName(String name, String type) {
        return uploadMapper.findByName(name, type);
    }

}
