package com.xwbing.domain.entity.rest;

import com.xwbing.domain.entity.sys.JpaBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * @author xiangwb
 * @version $Id: FilesUpload.java, v 0.1 2018年06月13日 17:23 wb-xwb402636 Exp $
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "file_upload")
public class FilesUpload extends JpaBaseEntity {
    private static final long serialVersionUID = 3284231281346882055L;
    public static String table = "file_upload";
    private String name;
    private String type;
    private String data;
}
