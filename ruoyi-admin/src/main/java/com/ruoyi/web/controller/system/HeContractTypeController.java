package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.HeContractTypeVo;
import com.ruoyi.system.domain.bo.HeContractTypeBo;
import com.ruoyi.system.service.IHeContractTypeService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 电子合同类型Controller
 *
 * @author henriport
 * @date 2021-10-18
 */
@Validated
@Api(value = "电子合同类型控制器", tags = {"电子合同类型管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/contractType")
public class HeContractTypeController extends BaseController {

    private final IHeContractTypeService iHeContractTypeService;

    /**
     * 查询电子合同类型列表（分页）
     */
    @ApiOperation("查询电子合同类型列表")
    @PreAuthorize("@ss.hasPermi('system:contractType:list')")
    @GetMapping("/list")
    public TableDataInfo<HeContractTypeVo> list(@Validated(QueryGroup.class) HeContractTypeBo bo) {
        return iHeContractTypeService.queryPageList(bo);
    }

    /**
     * 查询电子合同所有类型
     */
    @ApiOperation("查询电子合同所有类型")
    @PreAuthorize("@ss.hasPermi('system:contractType:allList')")
    @GetMapping("/allList")
    public List<HeContractTypeVo> allList(@Validated(QueryGroup.class) HeContractTypeBo bo) {
        return iHeContractTypeService.queryList(bo);
    }

    /**
     * 导出电子合同类型列表
     */
    @ApiOperation("导出电子合同类型列表")
    @PreAuthorize("@ss.hasPermi('system:contractType:export')")
    @Log(title = "电子合同类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated HeContractTypeBo bo, HttpServletResponse response) {
        List<HeContractTypeVo> list = iHeContractTypeService.queryList(bo);
        ExcelUtil.exportExcel(list, "电子合同类型", HeContractTypeVo.class, response);
    }

    /**
     * 获取电子合同类型详细信息
     */
    @ApiOperation("获取电子合同类型详细信息")
    @PreAuthorize("@ss.hasPermi('system:contractType:query')")
    @GetMapping("/{id}")
    public AjaxResult<HeContractTypeVo> getInfo(@NotNull(message = "主键不能为空")
                                                @PathVariable("id") Long id) {
        return AjaxResult.success(iHeContractTypeService.queryById(id));
    }

    /**
     * 新增电子合同类型
     */
    @ApiOperation("新增电子合同类型")
    @PreAuthorize("@ss.hasPermi('system:contractType:add')")
    @Log(title = "电子合同类型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody HeContractTypeBo bo) {
        return toAjax(iHeContractTypeService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改电子合同类型
     */
    @ApiOperation("修改电子合同类型")
    @PreAuthorize("@ss.hasPermi('system:contractType:edit')")
    @Log(title = "电子合同类型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody HeContractTypeBo bo) {
        return toAjax(iHeContractTypeService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除电子合同类型
     */
    @ApiOperation("删除电子合同类型")
    @PreAuthorize("@ss.hasPermi('system:contractType:remove')")
    @Log(title = "电子合同类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                   @PathVariable Long[] ids) {
        return toAjax(iHeContractTypeService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
