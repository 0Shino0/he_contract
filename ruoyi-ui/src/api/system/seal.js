import request from '@/utils/request'

// 查询印章管理列表
export function listSeal(query) {
  return request({
    url: '/system/seal/list',
    method: 'get',
    params: query
  })
}

//通过belong查询对应印章的title和url
export function listSealByUserId(userId) {
  return request({
    url: '/system/seal/list/' + userId,
    method: 'get',
  })
}

// 查询印章管理详细
export function getSeal(id) {
  return request({
    url: '/system/seal/' + id,
    method: 'get'
  })
}

// 新增印章管理
export function addSeal(data) {
  return request({
    url: '/system/seal',
    method: 'post',
    data: data
  })
}

// 修改印章管理
export function updateSeal(data) {
  return request({
    url: '/system/seal',
    method: 'put',
    data: data
  })
}

// 删除印章管理
export function delSeal(id) {
  return request({
    url: '/system/seal/' + id,
    method: 'delete'
  })
}
