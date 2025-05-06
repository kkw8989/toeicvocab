import React from 'react';

function ErrorMessage({ message }) {
  return (
    <div className="error-message">
      <div className="error-icon">!</div>
      <p className="error-text">{message || '오류가 발생했습니다.'}</p>
    </div>
  );
}

export default ErrorMessage;