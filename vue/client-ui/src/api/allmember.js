import request from '../utils/request'

// 查询会员方法
// export function allmember(member) {
//   const data = {
//     member
//   }
//   return request({
//     url: '/member',
//     headers: {
//       isToken: false
//     },
//     method: 'get',
//     data: data
//   })
// }

export default {
  // 查询用户列表接口
  findUserList(index, size, queryParam) {
    return request({
      url: `/user/${index}/${size}`,
      method: 'post',
      data: queryParam
    })
  }
}
