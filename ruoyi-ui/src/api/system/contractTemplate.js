import request from '@/utils/request'

// 查询电子合同模板管理列表
export function listContractTemplate(query) {
  return request({
    url: '/system/contractTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询电子合同模板管理详细
export function getContractTemplate(id) {
  return request({
    url: '/system/contractTemplate/' + id,
    method: 'get'
  })
}

// 新增电子合同模板管理
export function addContractTemplate(data) {
  return request({
    url: '/system/contractTemplate',
    method: 'post',
    data: data
  })
}

// 修改电子合同模板管理
export function updateContractTemplate(data) {
  return request({
    url: '/system/contractTemplate',
    method: 'put',
    data: data
  })
}

// 删除电子合同模板管理
export function delContractTemplate(id) {
  return request({
    url: '/system/contractTemplate/' + id,
    method: 'delete'
  })
}
