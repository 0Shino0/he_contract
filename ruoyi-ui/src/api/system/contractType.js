import request from '@/utils/request'

// 查询电子合同类型列表
export function listContractType(query) {
  return request({
    url: '/system/contractType/list',
    method: 'get',
    params: query
  })
}

// 查询电子合同所有类型
export function listAllContractType(query) {
  return request({
    url: '/system/contractType/allList',
    method: 'get',
    params: query
  })
}

// 查询电子合同类型详细
export function getContractType(id) {
  return request({
    url: '/system/contractType/' + id,
    method: 'get'
  })
}

// 新增电子合同类型
export function addContractType(data) {
  return request({
    url: '/system/contractType',
    method: 'post',
    data: data
  })
}

// 修改电子合同类型
export function updateContractType(data) {
  return request({
    url: '/system/contractType',
    method: 'put',
    data: data
  })
}

// 删除电子合同类型
export function delContractType(id) {
  return request({
    url: '/system/contractType/' + id,
    method: 'delete'
  })
}
