import request from '@/utils/request'

// 查询电子合同管理列表
export function listContract(query) {
  return request({
    url: '/system/contract/list',
    method: 'get',
    params: query
  })
}

// 查询电子合同管理详细
export function getContract(id) {
  return request({
    url: '/system/contract/' + id,
    method: 'get'
  })
}

// 新增电子合同管理
export function addContract(data) {
  return request({
    url: '/system/contract',
    method: 'post',
    data: data
  })
}

// 修改电子合同管理
export function updateContract(data) {
  return request({
    url: '/system/contract',
    method: 'put',
    data: data
  })
}

// 删除电子合同管理
export function delContract(id) {
  return request({
    url: '/system/contract/' + id,
    method: 'delete'
  })
}

// 修改审核状态
export function setStateById(id, type, pdfUrl, sealUrl) {
  return request({
    url: '/system/contract/state/' + id + "/" + type,
    method: "put",
    params:{
      "pdfUrl":pdfUrl,
      "sealUrl":sealUrl
    }
  })
}
