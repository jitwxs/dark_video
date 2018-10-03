package jit.wxs.dv.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jit.wxs.dv.config.SettingConfig;
import jit.wxs.dv.domain.entity.DvContent;
import jit.wxs.dv.domain.entity.SysSetting;
import jit.wxs.dv.domain.vo.ResultVO;
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
    private SettingConfig settingConfig;

    @Override
    public ResultVO setResInfo(String resRoot, String resIP) {
        insertOrUpdate(settingConfig.getResRootKey(), resRoot);
        insertOrUpdate(settingConfig.getResIpKey(), resIP);

        return ResultVOUtils.success();
    }

    @Override
    public ResultVO getResInfo() {
        Map<String, String> map = new HashMap<>(16);
        map.put("resRoot", getResRoot());
        map.put("resIp", getResIp());

        return ResultVOUtils.success(map);
    }

    @Override
    public ResultVO getVideoAndPictureNum() {
        return null;
    }

    @Override
    public String getResRoot() {
        SysSetting setting = settingMapper.selectById(settingConfig.getResRootKey());
        return setting != null ? setting.getV() : null;
    }

    @Override
    public String getResIp() {
        SysSetting setting = settingMapper.selectById(settingConfig.getResIpKey());
        return setting != null ? setting.getV() : null;
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
    public void updateVideoAndPictureNum() {
        int videoNum = contentMapper.selectCount(new EntityWrapper<DvContent>().eq("type", "video"));
        int pictureNum = contentMapper.selectCount(new EntityWrapper<DvContent>().eq("type", "picture"));
        insertOrUpdate(settingConfig.getVideoNum(), String.valueOf(videoNum));
        insertOrUpdate(settingConfig.getPictureNum(),  String.valueOf(pictureNum));
    }
}