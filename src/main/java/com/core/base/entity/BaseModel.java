package com.core.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * Model基类
 *
 * @author 何健锋
 */
@Getter
@Setter
public abstract class BaseModel implements IBaseModel {
    private static final long serialVersionUID = -97795143438724193L;
    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

//    @TableField(value = "create_time")
//    private Date createTime;
//
//    @TableField(value = "creater")
//    private String creater;


}
