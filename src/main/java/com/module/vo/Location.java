package com.module.vo;

import lombok.Data;

/**
 * 经纬度
 * @author chc
 * @create 2017-11-03 14:09
 **/
@Data
public class Location {

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;
}
