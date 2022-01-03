package com.ruoyi.web.controller.system;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.itextpdf.text.DocumentException;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.service.TokenService;
import com.ruoyi.system.domain.HeContract;
import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import com.ruoyi.system.domain.vo.HeContractVo;
import com.ruoyi.system.domain.bo.HeContractBo;
import com.ruoyi.system.service.IHeContractService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 电子合同管理Controller
 *
 * @author henriport
 * @date 2021-10-18
 */
@Validated
@Api(value = "电子合同管理控制器", tags = {"电子合同管理管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/contract")
public class HeContractController extends BaseController {

    private final IHeContractService iHeContractService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询电子合同管理列表
     */
    @ApiOperation("查询电子合同管理列表")
    @PreAuthorize("@ss.hasPermi('system:contract:list')")
    @GetMapping("/list")
    public TableDataInfo<HeContractVo> list(@Validated(QueryGroup.class) HeContractBo bo) {
        return iHeContractService.queryPageList(bo);
    }

    @ApiOperation("根据用户id查询电子合同列表")
    @PreAuthorize("@ss.hasPermi('system:contract:list')")
    @GetMapping("/listByUserId")
    public List<HeContract> listByUerId(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        return iHeContractService.queryListByUserId(loginUser.getUserId());
    }


    /**
     * 导出电子合同管理列表
     */
    @ApiOperation("导出电子合同管理列表")
    @PreAuthorize("@ss.hasPermi('system:contract:export')")
    @Log(title = "电子合同管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated HeContractBo bo, HttpServletResponse response) {
        List<HeContractVo> list = iHeContractService.queryList(bo);
        ExcelUtil.exportExcel(list, "电子合同管理", HeContractVo.class, response);
    }

    /**
     * 获取电子合同管理详细信息
     */
    @ApiOperation("获取电子合同管理详细信息")
    @PreAuthorize("@ss.hasPermi('system:contract:query')")
    @GetMapping("/{id}")
    public AjaxResult<HeContractVo> getInfo(@NotNull(message = "主键不能为空")
                                            @PathVariable("id") Long id) {
        return AjaxResult.success(iHeContractService.queryById(id));
    }

    /**
     * 新增电子合同管理
     */
    @ApiOperation("新增电子合同管理")
    @PreAuthorize("@ss.hasPermi('system:contract:add')")
    @Log(title = "电子合同管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody HeContractBo bo, HttpServletRequest request) {
        if (bo.getBelong() == null) {
            LoginUser loginUser = tokenService.getLoginUser(request);
            bo.setBelong(loginUser.getUserId());
            System.out.println(bo);
        }
        return toAjax(iHeContractService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改电子合同管理
     */
    @ApiOperation("修改电子合同管理")
    @PreAuthorize("@ss.hasPermi('system:contract:edit')")
    @Log(title = "电子合同管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody HeContractBo bo) {
        return toAjax(iHeContractService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除电子合同管理
     */
    @ApiOperation("删除电子合同管理")
    @PreAuthorize("@ss.hasPermi('system:contract:remove')")
    @Log(title = "电子合同管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                   @PathVariable Long[] ids) {
        return toAjax(iHeContractService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 修改审核状态
     *
     * @param id
     * @param type
     * @return
     */
    @ApiOperation("修改审核状态")
    @PreAuthorize("@ss.hasPermi('system:contract:edit')")
    @Log(title = "电子合同管理", businessType = BusinessType.UPDATE)
    @PutMapping("/state/{id}/{type}")
    public AjaxResult<Void> changeState(@NotNull(message = "主键不能为空")
                                        @PathVariable Long id,
                                        @NotNull(message = "type不能为空")
                                        @PathVariable Boolean type,
                                        String pdfUrl, String sealUrl) {
        return toAjax(iHeContractService.setStateById(id, type, pdfUrl, sealUrl) ? 1 : 0);
    }


}
