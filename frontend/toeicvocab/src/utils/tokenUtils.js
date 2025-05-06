// src/utils/tokenUtils.js
// 로컬 스토리지에 토큰 저장
export const saveToken = (token) => {
    localStorage.setItem('accessToken', token);
  };
  
  // 로컬 스토리지에서 토큰 가져오기
  export const getToken = () => {
    return localStorage.getItem('accessToken');
  };
  
  // 로컬 스토리지에서 토큰 제거
  export const removeToken = () => {
    localStorage.removeItem('accessToken');
  };
  
  // 사용자 정보 저장
  export const saveUserInfo = (userInfo) => {
    localStorage.setItem('userInfo', JSON.stringify(userInfo));
  };
  
  // 사용자 정보 가져오기
  export const getUserInfo = () => {
    const userInfo = localStorage.getItem('userInfo');
    return userInfo ? JSON.parse(userInfo) : null;
  };
  
  // 사용자 정보 제거
  export const removeUserInfo = () => {
    localStorage.removeItem('userInfo');
  };
  
  // 관리자 여부 확인
  export const isAdmin = () => {
    const userInfo = getUserInfo();
    return userInfo && userInfo.isAdmin;
  };
  
  // 로그인 여부 확인
  export const isLoggedIn = () => {
    return !!getToken();
  };
  
  // 로그아웃 처리
  export const logout = () => {
    removeToken();
    removeUserInfo();
    window.location.href = '/login';
  };