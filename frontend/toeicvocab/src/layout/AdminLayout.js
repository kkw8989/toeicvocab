import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Header from '../components/common/Header';
import Footer from '../components/common/Footer';
import { isAdmin } from '../utils/tokenUtils';

function AdminLayout({ children }) {
  const navigate = useNavigate();
  const { isAuthenticated } = useSelector((state) => state.auth);
  
  // 관리자 권한 확인
  useEffect(() => {
    if (!isAuthenticated || !isAdmin()) {
      navigate('/login');
    }
  }, [isAuthenticated, navigate]);
  
  return (
    <div className="admin-layout">
      <Header />
      <div className="admin-container">
        <aside className="admin-sidebar">
          <h2>관리자 메뉴</h2>
          <nav className="admin-nav">
            <ul className="admin-nav-list">
              <li className="admin-nav-item">
                <a href="/admin" className="admin-nav-link">
                  대시보드
                </a>
              </li>
              <li className="admin-nav-item">
                <a href="/admin/wordbooks" className="admin-nav-link">
                  단어장 관리
                </a>
              </li>
              <li className="admin-nav-item">
                <a href="/admin/words" className="admin-nav-link">
                  단어 관리
                </a>
              </li>
              <li className="admin-nav-item">
                <a href="/admin/users" className="admin-nav-link">
                  사용자 관리
                </a>
              </li>
            </ul>
          </nav>
        </aside>
        <main className="admin-main">
          <div className="admin-content">{children}</div>
        </main>
      </div>
      <Footer />
    </div>
  );
}

export default AdminLayout;