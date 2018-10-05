package jit.wxs.dv.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import jit.wxs.dv.domain.dto.ContentDTO;
import jit.wxs.dv.domain.entity.DvContent;
import jit.wxs.dv.domain.enums.CategoryLevelEnum;
import jit.wxs.dv.domain.vo.ContentVO;
import jit.wxs.dv.domain.vo.ResultVO;

/**
 * <p>
 * 内容表 服务类
 * </p>
 *
 * @author jitwxs
 * @since 2018-10-04
 */
public interface DvContentService extends IService<DvContent> {

    /**
     * 生成内容
     * @author jitwxs
     * @since 2018/10/4 2:42
     */
    void genContent(String path, String url, String firstCategory, String secondCategory, boolean hasSecondCategory);

    /**
     * 获取指定目录下最新内容
     * @param category 目录ID
     * @param count 内容条数
     * @author jitwxs
     * @since 2018/10/4 15:00
     */
    ResultVO listLatestContent(CategoryLevelEnum levelEnum, String category, int count);

    /**
     * 获取指定目录下最热内容
     * @param category 目录ID
     * @param count 内容条数
     * @author jitwxs
     * @since 2018/10/4 15:00
     */
    ResultVO listHotContent(CategoryLevelEnum levelEnum, String category, int count);

    /**
     * 分页查询某一分类下内容
     * @param levelEnum 分类级别
     * @param category 类别ID
     * @author jitwxs
     * @since 2018/10/4 23:38
     */
    Page<ContentVO> pageByCategory(CategoryLevelEnum levelEnum, String category, Page<DvContent> page);

    ContentDTO getContentDTO(String contentId);
}
