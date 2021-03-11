package com.hkb.hdms.config.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 创建时间，修改时间自动填充
 *
 * @author huangkebing
 * 2021/03/11
 */
@Component
public class HdmsMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("create", new Date(), metaObject);
        this.setFieldValByName("modify", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modify", new Date(), metaObject);
    }
}
