<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="id" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入合同标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同类别" prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择合同类别"
          clearable
          size="small"
        >
          <el-option
            v-for="type in contractTypes"
            :key="type.id"
            :label="type.type"
            :value="type.type"
          />
        </el-select>
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
          <!-- :value="dict.value" -->
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
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['system:contract:add']"
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
          v-hasPermi="['system:contract:edit']"
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
          v-hasPermi="['system:contract:remove']"
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
          v-hasPermi="['system:contract:export']"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="contractList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" v-if="true" />
      <el-table-column label="合同标题" align="center" prop="title" />
      <el-table-column label="合同描述" align="center" prop="description" />
      <el-table-column label="合同类别" align="center" prop="type" />
      <el-table-column label="归属于" align="center" prop="belong" />
      <el-table-column label="oss存储" align="center" prop="ossUrl">
        <template slot-scope="scope">
          <el-image
            v-if="previewListResource && checkFileSuffixByUrl(scope.row.ossUrl)"
            style="width: 100px; height: 100px"
            :src="scope.row.ossUrl"
            :preview-src-list="[scope.row.ossUrl]"
          />
          <a
            v-text="scope.row.ossUrl"
            @click="preViewFile(scope.row.ossUrl)"
            v-if="
              !checkFileSuffixByUrl(scope.row.ossUrl) || !previewListResource
            "
          />
        </template>
      </el-table-column>
      <el-table-column label="ipfs链" align="center" prop="ipfsHash" />
      <el-table-column label="是否入链" align="center" prop="isLink">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.he_yes_no" :value="scope.row.isLink" />
        </template>
      </el-table-column>
      <el-table-column label="是否删除" align="center" prop="isDelete">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.he_yes_no"
            :value="scope.row.isDelete"
          />
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.he_review_status"
            :value="scope.row.state"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handlePreview(scope.row)"
            >审批
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:contract:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:contract:remove']"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改电子合同管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="合同标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入合同标题" />
        </el-form-item>
        <el-form-item label="合同描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="合同类别" prop="type">
          <el-select
            v-model="form.type"
            placeholder="请选择合同类别"
            clearable
            size="small"
          >
            <el-option
              v-for="type in contractTypes"
              :key="type.id"
              :label="type.type"
              :value="type.type"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="归属于" prop="belong">
          <el-input v-model.number="form.belong" placeholder="请输入归属于" />
        </el-form-item>
        <el-form-item label="oss存储" prop="ossUrl">
          <!--          <image-upload v-model="form.ossUrl" :limit="1"/>-->
          <fileUpload v-model="form.ossUrl" :limit="1" />
        </el-form-item>
        <el-form-item label="ipfs链" prop="ipfsHash">
          <el-input
            v-model="form.ipfsHash"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <!--        <el-form-item label="是否入链" prop="isLink">-->
        <!--          <span v-for="dict in dict.type.he_yes_no" v-if="form.isLink==dict.value">-->
        <!--            {{dict.label}}-->
        <!--          </span>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="是否删除" prop="isDelete">-->
        <!--          <el-select-->
        <!--            v-model="form.isDelete"-->
        <!--            placeholder="是否删除"-->
        <!--            clearable-->
        <!--            size="small"-->
        <!--          >-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.he_yes_no"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--              :value="dict.value"-->
        <!--            />-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="审核状态" prop="state">
          <el-select
            v-model="form.state"
            placeholder="审核状态"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in checkStatus"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
            <!-- v-for="dict in dict.type.he_review_status"  -->
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm"
          >确 定</el-button
        >
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="合同预览"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
      v-loading="loading"
    >
      <div>
        <PDF v-for="i in pdf.numPages" :key="i" :src="pdf.src" :page="i" />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-select
          style="margin-right: 15px"
          v-model="pdf.seal"
          placeholder="请选择印章"
          clearable
          size="small"
        >
          <el-option
            v-for="seal in seals"
            :key="seal.id"
            :label="seal.title"
            :value="seal.ossUrl"
          />
        </el-select>
        <el-button
          @click="handleCheck(pdf.id, true)"
          :disabled="isCheck"
          type="primary"
          >审核通过</el-button
        >
        <el-button
          @click="handleCheck(pdf.id, false)"
          :disabled="pdf.state == 3 || pdf.state == 0"
          type="danger"
          >审核不通过</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listContract,
  getContract,
  delContract,
  addContract,
  updateContract,
  setStateById,
} from "@/api/system/contract";
import { listAllContractType } from "@/api/system/contractType";
import { changePreviewListResource } from "@/api/system/oss";
import { listSealByUserId } from "@/api/system/seal";
import PDF from "vue-pdf";

export default {
  name: "Contract",
  dicts: ["he_yes_no", "he_review_status"],
  components: {
    PDF,
  },
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
      // 电子合同管理表格数据
      contractList: [],
      // 弹出层标题
      title: "",
      type: 0,
      // 是否显示弹出层
      open: false,
      // 预览(审批)列表图片
      previewListResource: true,
      // pdf弹窗可视
      dialogVisible: false,
      // pdf参数
      pdf: {
        id: 0,
        numPages: 0,
        src: undefined,
        url: undefined,
        seal: "",
        state: 0,
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
        title: undefined,
        description: undefined,
        type: undefined,
        belong: undefined,
        ossUrl: undefined,
        ipfsHash: undefined,
        isLink: undefined,
        isDelete: undefined,
        state: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [{ required: true, message: "id不能为空", trigger: "blur" }],
        title: [
          { required: true, message: "合同标题不能为空", trigger: "blur" },
        ],
        description: [
          { required: true, message: "合同描述不能为空", trigger: "blur" },
        ],
        type: [
          { required: true, message: "合同类别不能为空", trigger: "change" },
        ],
        belong: [
          { required: true, message: "归属于不能为空", trigger: "blur" },
          { type: "number", message: "归属于必须为数字", trigger: "blur" },
        ],
        ossUrl: [
          { required: true, message: "oss存储不能为空", trigger: "blur" },
        ],
        ipfsHash: [
          { required: true, message: "ipfs链不能为空", trigger: "blur" },
        ],
        // isLink: [
        //   {required: true, message: "是否入链不能为空", trigger: "blur"}
        // ],
        // isDelete: [
        //   {required: true, message: "是否删除不能为空", trigger: "blur"}
        // ],
        state: [
          { required: true, message: "审核状态不能为空", trigger: "blur" },
        ],
      },
      // 合同类别
      contractTypes: [],
      // 印章
      seals: [],
      /* 修改补充 */
      // 判断添加还是修改操作
      // isCheckAdd: true,
    };
  },
  created() {
    this.getList();
    this.getContractTypes();
    this.getSealByUserId();
  },
  computed: {
    // 判断 审核按钮
    isCheck: function () {
      if (this.pdf.state == 2 || this.pdf.state == 0) {
        return true;
      } else if (!this.pdf.seal) {
        return true;
      } else {
        return false;
      }
    },
    // 修改电子合同 - 审核状态 - 状态重写
    checkStatus: function () {
      // 修改时的状态
      let updateCheckStatus = [];
      // this.dict.type.he_review_status 为添加时的状态
      this.dict.type.he_review_status.map((currentValue) => {
        if (currentValue.value === "0" || currentValue.value === "1") {
          updateCheckStatus.push(currentValue);
        }
      });

      return updateCheckStatus;
    },
    // 修改电子合同状态 - 审核状态 - 状态显示转变
    // dictValueChange: function () {},
  },
  methods: {
    /** 查询电子合同管理列表 */
    getList() {
      this.loading = true;
      listContract(this.queryParams).then((response) => {
        this.contractList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询电子合同类别列表 */
    getContractTypes() {
      listAllContractType(null).then((response) => {
        this.contractTypes = response;
        console.log(this.contractTypes);
      });
    },
    /** 通过userId查询印章列表 */
    getSealByUserId() {
      listSealByUserId(1).then((res) => {
        this.seals = res;
        console.log(this.seals);
      });
    },
    checkFileSuffixByUrl(ossUrl) {
      let arr = ["png", "jpg", "jpeg"];
      return arr.some((type) => {
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
        description: undefined,
        type: undefined,
        belong: undefined,
        ossUrl: undefined,
        ipfsHash: undefined,
        isLink: undefined,
        isDelete: undefined,
        state: undefined,
        createTime: undefined,
        updateTime: undefined,
      };
      this.resetForm("form");
    },
    // pdf重置
    resetPdf() {
      this.pdf = {
        id: 0,
        numPages: 0,
        src: undefined,
        state: 0,
      };
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 预览(审批)按钮操作 */
    handlePreview(row) {
      this.pdf.id = row.id;
      this.pdf.src = PDF.createLoadingTask(row.ossUrl);
      this.pdf.url = row.ossUrl;
      this.pdf.state = row.state;
      this.pdf.src.promise.then((pdf) => {
        this.pdf.numPages = pdf.numPages;
      });
      this.dialogVisible = true;
    },
    /** 关闭弹窗 */
    handleClose() {
      this.pdf.id = 0;
      this.pdf.src = "";
      this.pdf.url = "";
      this.pdf.numPages = 0;
      this.pdf.seal = "";
      this.dialogVisible = false;
    },
    /** 审核按钮 */
    handleCheck(id, type) {
      this.loading = true;
      let pdfUrl = null;
      let sealUrl = null;
      if (type) {
        pdfUrl = this.pdf.url;
        sealUrl = this.pdf.seal;
      }
      // 设置审核状态（审核通过或者审核不通过）
      setStateById(id, type, pdfUrl, sealUrl).then((res) => {
        console.log(res);
        if (res.code == 200) {
          this.$modal.msgSuccess("操作成功");
        } else {
          this.$modal.msgSuccess("操作失败");
        }
        this.loading = false;
        this.handleClose();
        this.getList();
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      // 标志此时为添加操作
      // this.isCheckAdd = true;
      this.open = true;
      this.title = "添加电子合同";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      // 标志此时为修改操作
      // this.isCheckAdd = false;
      this.reset();
      const id = row.id || this.ids;
      getContract(id).then((response) => {
        // 加工数据
        let data = response.data;
        for (var item in data) {
          // console.log(item, ":", data[item]);
          if (data[item] === "2" || data[item] === "3") {
            console.log(1);
            data[item] = data[item] === "2" ? "已通过" : "未通过";
          }
        }
        console.log(data);
        // console.log(Array.from(data));
        this.loading = false;
        this.form = data;
        this.open = true;
        this.title = "修改电子合同";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateContract(this.form)
              .then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.buttonLoading = false;
              });
          } else {
            addContract(this.form)
              .then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.buttonLoading = false;
              });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除电子合同编号为"' + ids + '"的数据项？')
        .then(() => {
          this.loading = true;
          return delContract(ids);
        })
        .then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    // 预览列表图片状态修改
    handlePreviewListResource(previewListResource) {
      let text = previewListResource ? "启用" : "停用";
      this.$modal
        .confirm('确认要"' + text + '""预览列表图片"配置吗?')
        .then(() => {
          return changePreviewListResource(previewListResource);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(() => {
          this.previewListResource = previewListResource !== true;
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$download.excel("/system/contract/export", this.queryParams);
    },
    preViewFile(url) {
      if (url.indexOf("pdf")) {
        window.open(url, "_blank");
      } else {
        window.open(
          "https://view.officeapps.live.com/op/view.aspx?src=" + url,
          "_blank"
        );
      }
    },
  },
};
</script>
