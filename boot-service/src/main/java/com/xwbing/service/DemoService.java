package com.xwbing.service;

import com.xwbing.util.RestMessage;
import org.apache.commons.lang3.StringUtils;

public class DemoService {
    void setRemind(String id, String typeName, String remindDate, String type) {
        RestMessage one = queryOne(id, typeName);
        if (one != null) {
            if (StringUtils.isNotEmpty(remindDate)) {
                update();
            } else {
                delete();
            }
        } else {
            if (StringUtils.isNotEmpty(remindDate)) {
                save();
            }
        }
    }

    private RestMessage queryOne(String id, String typeName) {
        return new RestMessage();
    }

    private void update() {
    }

    private void delete() {
    }

    private void save() {
    }
}
