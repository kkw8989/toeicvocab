// import React, { useEffect } from 'react';
// import { useNavigate } from 'react-router-dom';
// import { useSelector } from 'react-redux';
// import BasicLayout from '../../layout/BasicLayout';
// import PostForm from '../../components/post/PostForm';

// function BoardWritePage() {
//   const navigate = useNavigate();
//   const { isAuthenticated } = useSelector((state) => state.auth);
  
//   useEffect(() => {
//     // 인증 상태 확인
//     if (!isAuthenticated) {
//       navigate('/login');
//     }
//   }, [isAuthenticated, navigate]);
  
//   return (
//     <BasicLayout>
//       <div className="board-write-page">
//         <PostForm />
//       </div>
//     </BasicLayout>
//   );
// }

// export default BoardWritePage;