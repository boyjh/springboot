package com.xwbing.domain.repository;

import com.xwbing.domain.entity.rest.FilesUpload;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xiangwb
 * @version $Id: FilesUploadRepository.java, v 0.1 2018年06月13日 17:25 wb-xwb402636 Exp $
 */
public interface FilesUploadRepository extends JpaRepository<FilesUpload, String> {
    List<FilesUpload> getByName(String name, Sort sort);

    List<FilesUpload> getByTypeOrderByCreateTime(String type);

    List<FilesUpload> getByIdIn(List<String> ids);
}
