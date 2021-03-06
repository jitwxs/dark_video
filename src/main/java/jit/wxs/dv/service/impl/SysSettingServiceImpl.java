package jit.wxs.dv.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.dv.config.SettingConfig;
import jit.wxs.dv.domain.entity.DvContent;
import jit.wxs.dv.domain.entity.DvContentAffix;
import jit.wxs.dv.domain.entity.SysSetting;
import jit.wxs.dv.domain.enums.ResultEnum;
import jit.wxs.dv.domain.vo.ResultVO;
import jit.wxs.dv.mapper.DvContentAffixMapper;
import jit.wxs.dv.mapper.DvContentMapper;
import jit.wxs.dv.mapper.SysSettingMapper;
import jit.wxs.dv.service.SysSettingService;
import jit.wxs.dv.util.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jitwxs
 * @since 2018-10-04
 */
@Service
public class SysSettingServiceImpl extends ServiceImpl<SysSettingMapper, SysSetting> implements SysSettingService {
    @Autowired
    private SysSettingMapper settingMapper;
    @Autowired
    private DvContentMapper contentMapper;
    @Autowired
    private DvContentAffixMapper contentAffixMapper;
    @Autowired
    private SettingConfig settingConfig;


    @Override
    public ResultVO setResInfo(String resContent, String resThumbnail, String contentIp, String thumbnailIp) {
        insertOrUpdate(settingConfig.getResContent(), resContent);
        insertOrUpdate(settingConfig.getResThumbnail(), resThumbnail);
        insertOrUpdate(settingConfig.getContentIp(), contentIp);
        insertOrUpdate(settingConfig.getThumbnailIp(), thumbnailIp);

        return ResultVOUtils.success();
    }

    @Override
    public ResultVO<Map> getResInfo() {
        Map<String, String> map = new HashMap<>(16);
        map.put("resContent", getResContent());
        map.put("resThumbnail", getResThumbnail());
        map.put("contentIp", getContentIp());
        map.put("thumbnailIp", getThumbnailIp());

        return ResultVOUtils.success(map);
    }

    @Override
    public Map<String, String> getVideoAndPictureNum() {
        Map<String, String> map = new HashMap<>(16);
        SysSetting setting1 = settingMapper.selectById(settingConfig.getVideoNum());
        SysSetting setting2 = settingMapper.selectById(settingConfig.getPictureNum());

        map.put("vN", setting1 != null ? setting1.getV() : "0");
        map.put("pN", setting2 != null ? setting2.getV() : "0");
        return map;
    }

    @Override
    public void insertOrUpdate(String key, String value) {
        SysSetting setting = settingMapper.selectById(key);
        if(setting == null) {
            setting = new SysSetting(key, value);
            settingMapper.insert(setting);
        } else {
            setting.setV(value);
            settingMapper.updateById(setting);
        }
    }

    @Override
    public ResultVO getCarouselInfo() {
        SysSetting setting = settingMapper.selectById(settingConfig.getCarousel());
        if(setting != null) {
            return ResultVOUtils.success(setting.getV());
        } else {
            return ResultVOUtils.success(ResultVOUtils.error(ResultEnum.INDEX_CAROUSEL_NULL));
        }
    }

    @Override
    public ResultVO setCarouselInfo(String json) {
        insertOrUpdate(settingConfig.getCarousel(), json);
        return ResultVOUtils.success();
    }

    @Override
    public void updateVideoAndPictureNum() {
        int contentVideoNum = contentMapper.selectCount(new EntityWrapper<DvContent>().eq("type", "video"));
        int contentPictureNum = contentMapper.selectCount(new EntityWrapper<DvContent>().eq("type", "picture"));
        int affixVideoNum = contentAffixMapper.selectCount(new EntityWrapper<DvContentAffix>().eq("type", "video"));
        int affixPictureNum = contentAffixMapper.selectCount(new EntityWrapper<DvContentAffix>().eq("type", "picture"));

        insertOrUpdate(settingConfig.getVideoNum(), String.valueOf(contentVideoNum+affixVideoNum));
        insertOrUpdate(settingConfig.getPictureNum(),  String.valueOf(contentPictureNum+affixPictureNum));
    }

    @Override
    public String getResContent() {
        SysSetting setting = settingMapper.selectById(settingConfig.getResContent());
        return setting != null ? setting.getV() : null;
    }

    @Override
    public String getResThumbnail() {
        SysSetting setting = settingMapper.selectById(settingConfig.getResThumbnail());
        return setting != null ? setting.getV() : null;
    }

    @Override
    public String getContentIp() {
        SysSetting setting = settingMapper.selectById(settingConfig.getContentIp());
        return setting != null ? setting.getV() : null;
    }

    @Override
    public String getThumbnailIp() {
        SysSetting setting = settingMapper.selectById(settingConfig.getThumbnailIp());
        return setting != null ? setting.getV() : null;
    }
}
