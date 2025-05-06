import React, { useEffect } from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { configureStore } from '@reduxjs/toolkit';
import authReducer, { setUser } from './slices/authSlice';
import { getUserInfo, isLoggedIn } from './utils/tokenUtils';

// 페이지 컴포넌트
import LoginPage from './pages/auth/LoginPage';
import AdminLoginPage from './pages/auth/AdminLoginPage';
import SignupPage from './pages/auth/SignupPage';
import HomePage from './pages/home/HomePage';

// 리덕스 스토어 설정
const store = configureStore({
  reducer: {
    auth: authReducer,
  },
});

function App() {
  useEffect(() => {
    // 로컬 스토리지에서 사용자 정보 불러오기
    if (isLoggedIn()) {
      const userInfo = getUserInfo();
      if (userInfo) {
        store.dispatch(setUser(userInfo));
      }
    }
  }, []);

  return (
    <Provider store={store}>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/admin/login" element={<AdminLoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
        </Routes>
      </Router>
    </Provider>
  );
}

export default App;