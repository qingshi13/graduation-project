import request from '../utils/request'

// 登录方法
export function login(phone, code) {
  const data = {
    phone,
    code,
  }
  return request({
    url: '/login',
    // headers: {
    //   isToken: false
    // },
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}
// 获取昵称

export function getnickname() {
  return request({
    url: '/member/me',
    // headers: {
    //   isToken: false
    // },
    method: 'get',
    data: data
  })
}

export function f() {

}

