package com.ruoyi.system.service;

import com.itextpdf.text.DocumentException;
import com.ruoyi.system.domain.HeContract;
import com.ruoyi.system.domain.vo.HeContractVo;
import com.ruoyi.system.domain.bo.HeContractBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 电子合同管理Service接口
 *
 * @author henriport
 * @date 2021-10-18
 */
public interface IHeContractService extends IServicePlus<HeContract, HeContractVo> {
    /**
     * 查询单个
     *
     * @return
     */
    HeContractVo queryById(Long id);

    /**
     * 查询列表
     */
    TableDataInfo<HeContractVo> queryPageList(HeContractBo bo);

    /**
     * 查询列表
     */
    List<HeContractVo> queryList(HeContractBo bo);


    /**
     * 根据用户id获取对应的列表
     * @param userId
     * @return
     */
    List<HeContract> queryListByUserId(Long userId);


    /**
     * 根据新增业务对象插入电子合同管理
     *
     * @param bo 电子合同管理新增业务对象
     * @return
     */
    Boolean insertByBo(HeContractBo bo);

    /**
     * 根据编辑业务对象修改电子合同管理
     *
     * @param bo 电子合同管理编辑业务对象
     * @return
     */
    Boolean updateByBo(HeContractBo bo);

    /**
     * 校验并删除数据
     *
     * @param ids     主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    /**
     * 根据id设置审核状态，并自动盖章，入链
     *
     * @param id
     * @param type
     * @param pdfUrl
     * @param sealUrl
     * @return
     */
    Boolean setStateById(Long id, Boolean type, String pdfUrl, String sealUrl);

}
