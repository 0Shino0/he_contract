<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="id" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模板标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入模板标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模板类别" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择合同类别" clearable size="small">
          <el-option
            v-for="type in contractTypes"
            :key="type.id"
            :label="type.type"
            :value="type.type"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否删除" prop="isDelete">
        <el-select
          v-model="queryParams.isDelete"
          placeholder="是否删除"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.he_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:contractTemplate:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:contractTemplate:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          :type="previewListResource ? 'danger' : 'warning'"
          plain
          size="mini"
          @click="handlePreviewListResource(!previewListResource)"
          v-hasPermi="['system:oss:edit']"
        >预览开关 : {{ previewListResource ? "禁用" : "启用" }}
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:contractTemplate:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:contractTemplate:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractTemplateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" v-if="true"/>
      <el-table-column label="模板标题" align="center" prop="title"/>
      <el-table-column label="oss存储" align="center" prop="ossUrl">
        <template slot-scope="scope">
          <el-image
            v-if="previewListResource && checkFileSuffixByUrl(scope.row.ossUrl)"
            style="width: 100px; height: 100px;"
            :src="scope.row.ossUrl"
            :preview-src-list="[scope.row.ossUrl]"/>
          <a v-text="scope.row.ossUrl" @click="preViewFile(scope.row.ossUrl)"
             v-if="!checkFileSuffixByUrl(scope.row.ossUrl) || !previewListResource"/>
        </template>
      </el-table-column>
      <el-table-column label="模板描述" align="center" prop="description"/>
      <el-table-column label="模板类别" align="center" prop="type"/>
      <el-table-column label="是否删除" align="center" prop="isDelete">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.he_yes_no" :value="scope.row.isDelete"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:contractTemplate:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:contractTemplate:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改电子合同模板管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模板标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入模板标题"/>
        </el-form-item>
        <el-form-item label="oss存储" prop="ossUrl">
          <!--          <image-upload v-model="form.ossUrl" :limit="1"/>-->
          <fileUpload v-model="form.ossUrl" :limit="1"/>
        </el-form-item>
        <el-form-item label="模板描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="模板类别" prop="type">
          <el-select v-model="form.type" placeholder="请选择模板类别" clearable size="small">
            <el-option
              v-for="type in contractTypes"
              :key="type.id"
              :label="type.type"
              :value="type.type"
            />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="是否删除" prop="isDelete">-->
        <!--          <el-input v-model="form.isDelete" placeholder="请输入是否删除"/>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listContractTemplate,
  getContractTemplate,
  delContractTemplate,
  addContractTemplate,
  updateContractTemplate
} from "@/api/system/contractTemplate";

import {listAllContractType} from "@/api/system/contractType";
import {changePreviewListResource} from "@/api/system/oss";


export default {
  name: "ContractTemplate",
  dicts: ['he_yes_no'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 电子合同模板管理表格数据
      contractTemplateList: [],
      // 弹出层标题
      title: "",
      type: 0,
      // 是否显示弹出层
      open: false,
      // 预览列表图片
      previewListResource: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
        title: undefined,
        ossUrl: undefined,
        description: undefined,
        type: undefined,
        isDelete: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          {required: true, message: "id不能为空", trigger: "blur"}
        ],
        title: [
          {required: true, message: "模板标题不能为空", trigger: "blur"}
        ],
        ossUrl: [
          {required: true, message: "oss存储不能为空", trigger: "blur"}
        ],
        description: [
          {required: true, message: "模板描述不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "模板类别不能为空", trigger: "change"}
        ],
        // isDelete: [
        //   {required: true, message: "是否删除不能为空", trigger: "blur"}
        // ],
      },
      // 合同类别
      contractTypes: [],
    };
  },
  created() {
    this.getList();
    this.getContractTypes();
  },
  methods: {
    /** 查询电子合同模板管理列表 */
    getList() {
      this.loading = true;
      listContractTemplate(this.queryParams).then(response => {
        this.contractTemplateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询电子合同类别列表 */
    getContractTypes() {
      listAllContractType(null).then(response => {
        this.contractTypes = response;
        console.log(this.contractTypes);
      })
    },
    checkFileSuffixByUrl(ossUrl) {
      let arr = ["png", "jpg", "jpeg"];
      return arr.some(type => {
        return ossUrl.indexOf(type) > -1;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        title: undefined,
        ossUrl: undefined,
        description: undefined,
        type: undefined,
        isDelete: undefined,
        createTime: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加电子合同模板管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getContractTemplate(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改电子合同模板管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateContractTemplate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addContractTemplate(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除电子合同模板管理编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delContractTemplate(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).finally(() => {
        this.loading = false;
      });
    },
    // 预览列表图片状态修改
    handlePreviewListResource(previewListResource) {
      let text = previewListResource ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""预览列表图片"配置吗?').then(() => {
        return changePreviewListResource(previewListResource);
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(text + "成功");
      }).catch(() => {
        this.previewListResource = previewListResource !== true;
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$download.excel('/system/contractTemplate/export', this.queryParams);
    },
    preViewFile(url) {
      if (url.lastIndexOf(".pdf")!=-1) {
        window.open(url, '_blank');
      }else{
      window.open('https://view.officeapps.live.com/op/view.aspx?src=' + url, '_blank');
      }
    }
  }
};
</script>
