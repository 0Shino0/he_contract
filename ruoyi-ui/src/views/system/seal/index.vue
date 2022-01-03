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
      <el-form-item label="印章标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入印章标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归属于" prop="belong">
        <el-input
          v-model="queryParams.belong"
          placeholder="请输入归属于"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否入链" prop="isLink">
        <el-select
          v-model="queryParams.isLink"
          placeholder="是否入链"
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
      <el-form-item label="审核状态" prop="state">
        <el-select
          v-model="queryParams.state"
          placeholder="审核状态"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.he_review_status"
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
          v-hasPermi="['system:seal:add']"
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
          v-hasPermi="['system:seal:edit']"
        >修改
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
          v-hasPermi="['system:seal:remove']"
        >删除
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:seal:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sealList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" v-if="true"/>
      <el-table-column label="印章标题" align="center" prop="title"/>
      <el-table-column label="oss存储" align="center" prop="ossUrl">
        <template slot-scope="scope">
          <el-image
            v-if="previewListResource && checkFileSuffixByUrl(scope.row.ossUrl)"
            style="width: 100px; height: 100px;"
            :src="scope.row.ossUrl"
            :preview-src-list="[scope.row.ossUrl]"/>
          <span v-text="scope.row.ossUrl"
                v-if="!checkFileSuffixByUrl(scope.row.ossUrl) || !previewListResource"/>
        </template>
      </el-table-column>
      <el-table-column label="ipfs链" align="center" prop="ipfsHash"/>
      <el-table-column label="归属于" align="center" prop="belong"/>
      <el-table-column label="是否入链" align="center" prop="isLink">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.he_yes_no" :value="scope.row.isLink"/>
        </template>
      </el-table-column>
      <el-table-column label="是否删除" align="center" prop="isDelete">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.he_yes_no" :value="scope.row.isDelete"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.he_review_status" :value="scope.row.state"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:seal:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:seal:remove']"
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

    <!-- 添加或修改印章管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="印章标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入印章标题"/>
        </el-form-item>
        <el-form-item label="oss存储路径" prop="ossUrl">
          <image-upload v-model="form.ossUrl" :limit="1"/>
        </el-form-item>
        <el-form-item label="ipfs链路径" prop="ipfsHash">
          <el-input v-model="form.ipfsHash" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="归属于" prop="belong">
          <el-input v-model="form.belong" placeholder="请输入归属于"/>
        </el-form-item>
        <!--        <el-form-item label="是否入链" prop="isLink">-->
        <!--          <el-input v-model="form.isLink" placeholder="请输入是否入链"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="是否删除" prop="isDelete">-->
        <!--          <el-input v-model="form.isDelete" placeholder="请输入是否删除"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="审核状态" prop="state">
          <el-select
            v-model="form.state"
            placeholder="审核状态"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in dict.type.he_review_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listSeal, getSeal, delSeal, addSeal, updateSeal} from "@/api/system/seal";
import {changePreviewListResource} from "@/api/system/oss";


export default {
  name: "Seal",
  dicts: ['he_yes_no', 'he_review_status'],
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
      // 印章管理表格数据
      sealList: [],
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
        ipfsHash: undefined,
        belong: undefined,
        isLink: undefined,
        isDelete: undefined,
        state: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          {required: true, message: "id不能为空", trigger: "blur"}
        ],
        title: [
          {required: true, message: "印章标题不能为空", trigger: "blur"}
        ],
        ossUrl: [
          {required: true, message: "oss存储路径不能为空", trigger: "blur"}
        ],
        ipfsHash: [
          {required: true, message: "ipfs链路径不能为空", trigger: "blur"}
        ],
        belong: [
          {required: true, message: "归属于不能为空", trigger: "blur"}
        ],
        // isLink: [
        //   {required: true, message: "是否入链不能为空", trigger: "blur"}
        // ],
        // isDelete: [
        //   {required: true, message: "是否删除不能为空", trigger: "blur"}
        // ],
        state: [
          {required: true, message: "审核状态不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询印章管理列表 */
    getList() {
      this.loading = true;
      listSeal(this.queryParams).then(response => {
        this.sealList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        ipfsHash: undefined,
        belong: undefined,
        isLink: undefined,
        isDelete: undefined,
        state: undefined,
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
      this.title = "添加印章管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getSeal(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改印章管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateSeal(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addSeal(this.form).then(response => {
              console.log(this.form);
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
      this.$modal.confirm('是否确认删除印章管理编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delSeal(ids);
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
      this.$download.excel('/system/seal/export', this.queryParams);
    }
  }
};
</script>
