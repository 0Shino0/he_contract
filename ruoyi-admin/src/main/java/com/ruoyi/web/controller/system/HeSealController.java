package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.system.domain.HeSeal;
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
import com.ruoyi.system.domain.vo.HeSealVo;
import com.ruoyi.system.domain.bo.HeSealBo;
import com.ruoyi.system.service.IHeSealService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 印章管理Controller
 *
 * @author henriport
 * @date 2021-10-18
 */
@Validated
@Api(value = "印章管理控制器", tags = {"印章管理管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/seal")
public class HeSealController extends BaseController {

    private final IHeSealService iHeSealService;


    /**
     * 查询印章管理列表
     */
    @ApiOperation("查询印章管理列表")
    @PreAuthorize("@ss.hasPermi('system:seal:list')")
    @GetMapping("/list")
    public TableDataInfo<HeSealVo> list(@Validated(QueryGroup.class) HeSealBo bo) {
        return iHeSealService.queryPageList(bo);
    }

    /**
     * 通过belong查询对应印章的title和url，用于实现用户印章下拉列表
     *
     * @param userId
     * @return
     */
    @ApiOperation("通过belong查询对应印章的title和url")
    @PreAuthorize("@ss.hasPermi('system:seal:query')")
    @GetMapping("/list/{userId}")
    public List<HeSeal> queryListByUserId(@NotNull(message = "用户id不能为空")
                                              @PathVariable Long userId) {
        return iHeSealService.queryListByUserId(userId);
    }

    /**
     * 导出印章管理列表
     */
    @ApiOperation("导出印章管理列表")
    @PreAuthorize("@ss.hasPermi('system:seal:export')")
    @Log(title = "印章管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated HeSealBo bo, HttpServletResponse response) {
        List<HeSealVo> list = iHeSealService.queryList(bo);
        ExcelUtil.exportExcel(list, "印章管理", HeSealVo.class, response);
    }

    /**
     * 获取印章管理详细信息
     */
    @ApiOperation("获取印章管理详细信息")
    @PreAuthorize("@ss.hasPermi('system:seal:query')")
    @GetMapping("/{id}")
    public AjaxResult<HeSealVo> getInfo(@NotNull(message = "主键不能为空")
                                        @PathVariable("id") Long id) {
        return AjaxResult.success(iHeSealService.queryById(id));
    }

    /**
     * 新增印章管理
     */
    @ApiOperation("新增印章管理")
    @PreAuthorize("@ss.hasPermi('system:seal:add')")
    @Log(title = "印章管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody HeSealBo bo) {
        return toAjax(iHeSealService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改印章管理
     */
    @ApiOperation("修改印章管理")
    @PreAuthorize("@ss.hasPermi('system:seal:edit')")
    @Log(title = "印章管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody HeSealBo bo) {
        return toAjax(iHeSealService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除印章管理
     */
    @ApiOperation("删除印章管理")
    @PreAuthorize("@ss.hasPermi('system:seal:remove')")
    @Log(title = "印章管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                   @PathVariable Long[] ids) {

        return toAjax(iHeSealService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }


}
