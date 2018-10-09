package jit.wxs.dv.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import jit.wxs.dv.domain.entity.DvUserLookLater;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 稍后再看表 Mapper 接口
 * </p>
 *
 * @author jitwxs
 * @since 2018-10-07
 */
public interface DvUserLookLaterMapper extends BaseMapper<DvUserLookLater> {

    boolean hasExist(@Param("contentId") String contentId, @Param("username") String username);
}
