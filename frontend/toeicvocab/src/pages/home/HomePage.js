import React from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import BasicLayout from '../../layout/BasicLayout';

function HomePage() {
  const { isAuthenticated, user } = useSelector((state) => state.auth);
  
  return (
    <BasicLayout>
      <div className="home-page">
        <section className="hero-section">
          <div className="hero-content">
            <h1>TOEIC 단어 학습</h1>
            <p>효율적인 TOEIC 단어 학습과 테스트를 통해 영어 실력을 향상시키세요.</p>
            <div className="hero-buttons">
              <Link to="/wordbooks" className="hero-button primary">
                단어장 보기
              </Link>
              <Link to="/test" className="hero-button secondary">
                테스트 시작
              </Link>
            </div>
          </div>
        </section>
        
        {isAuthenticated ? (
          <section className="welcome-section">
            <h2>안녕하세요, {user.username}님!</h2>
            <p>오늘도 TOEIC 단어 학습을 통해 영어 실력을 향상시켜보세요.</p>
          </section>
        ) : (
          <section className="login-prompt-section">
            <h2>학습을 시작하려면 로그인하세요</h2>
            <p>계정이 없으신가요? 회원가입 후 다양한 기능을 이용해보세요.</p>
            <div className="auth-buttons">
              <Link to="/login" className="btn btn-primary">
                로그인
              </Link>
              <Link to="/signup" className="btn btn-secondary">
                회원가입
              </Link>
            </div>
          </section>
        )}
        
        <section className="features-section">
          <h2>TOEIC 단어 학습 특징</h2>
          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">📚</div>
              <h3>다양한 단어장</h3>
              <p>초급부터 고급까지 다양한 수준의 TOEIC 단어장을 제공합니다.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">✏️</div>
              <h3>맞춤형 테스트</h3>
              <p>단어장별로 테스트를 진행하고 결과를 확인할 수 있습니다.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">📊</div>
              <h3>학습 진도 관리</h3>
              <p>테스트 결과를 통해 학습 진도를 확인하고 관리할 수 있습니다.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">💬</div>
              <h3>커뮤니티</h3>
              <p>다른 사용자들과 게시판을 통해 정보와 팁을 공유할 수 있습니다.</p>
            </div>
          </div>
        </section>
      </div>
    </BasicLayout>
  );
}

export default HomePage;