import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import LoginForm from '../../components/auth/LoginForm';
import BasicLayout from '../../layout/BasicLayout';

function LoginPage() {
  const location = useLocation();
  const message = location.state?.message;
  
  return (
    <BasicLayout>
      <div className="auth-page login-page">
        <div className="auth-container">
          {message && (
            <div className="auth-message success">{message}</div>
          )}
          <LoginForm />
          <div className="auth-links">
            <p>
              관리자이신가요? <Link to="/admin/login">관리자 로그인</Link>
            </p>
          </div>
        </div>
      </div>
    </BasicLayout>
  );
}

export default LoginPage;