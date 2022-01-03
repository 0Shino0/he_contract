package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.system.service.IHeSealService;
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
import com.ruoyi.system.domain.vo.HeContractTemplateVo;
import com.ruoyi.system.domain.bo.HeContractTemplateBo;
import com.ruoyi.system.service.IHeContractTemplateService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 电子合同模板管理Controller
 *
 * @author henriport
 * @date 2021-10-18
 */
@Validated
@Api(value = "电子合同模板管理控制器", tags = {"电子合同模板管理管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/contractTemplate")
public class HeContractTemplateController extends BaseController {

    private final IHeContractTemplateService iHeContractTemplateService;

    /**
     * 查询电子合同模板管理列表
     */
    @ApiOperation("查询电子合同模板管理列表")
    @PreAuthorize("@ss.hasPermi('system:contractTemplate:list')")
    @GetMapping("/list")
    public TableDataInfo<HeContractTemplateVo> list(@Validated(QueryGroup.class) HeContractTemplateBo bo) {
        return iHeContractTemplateService.queryPageList(bo);
    }

    /**
     * 导出电子合同模板管理列表
     */
    @ApiOperation("导出电子合同模板管理列表")
    @PreAuthorize("@ss.hasPermi('system:contractTemplate:export')")
    @Log(title = "电子合同模板管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated HeContractTemplateBo bo, HttpServletResponse response) {
        List<HeContractTemplateVo> list = iHeContractTemplateService.queryList(bo);
        ExcelUtil.exportExcel(list, "电子合同模板管理", HeContractTemplateVo.class, response);
    }

    /**
     * 获取电子合同模板管理详细信息
     */
    @ApiOperation("获取电子合同模板管理详细信息")
    @PreAuthorize("@ss.hasPermi('system:contractTemplate:query')")
    @GetMapping("/{id}")
    public AjaxResult<HeContractTemplateVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return AjaxResult.success(iHeContractTemplateService.queryById(id));
    }

    /**
     * 新增电子合同模板管理
     */
    @ApiOperation("新增电子合同模板管理")
    @PreAuthorize("@ss.hasPermi('system:contractTemplate:add')")
    @Log(title = "电子合同模板管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody HeContractTemplateBo bo) {
        return toAjax(iHeContractTemplateService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改电子合同模板管理
     */
    @ApiOperation("修改电子合同模板管理")
    @PreAuthorize("@ss.hasPermi('system:contractTemplate:edit')")
    @Log(title = "电子合同模板管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody HeContractTemplateBo bo) {
        return toAjax(iHeContractTemplateService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除电子合同模板管理
     */
    @ApiOperation("删除电子合同模板管理")
    @PreAuthorize("@ss.hasPermi('system:contractTemplate:remove')")
    @Log(title = "电子合同模板管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iHeContractTemplateService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
