package com.module.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.module.entity.LegalMechanism;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 用户Mapper
 */
@Repository
public interface LegalMechanismMapper extends BaseMapper<LegalMechanism> {

    Map findFristNearLegalMechanism(@Param("lat") Double lat,@Param("lng") Double lng);

}
