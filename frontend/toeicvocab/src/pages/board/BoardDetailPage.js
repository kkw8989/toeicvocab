// import React, { useEffect } from 'react';
// import { useDispatch, useSelector } from 'react-redux';
// import { useParams } from 'react-router-dom';
// import { fetchPost } from '../../slices/postSlice';
// import BasicLayout from '../../layout/BasicLayout';
// import PostDetail from '../../components/post/PostDetail';
// import Loading from '../../components/common/Loading';
// import ErrorMessage from '../../components/common/ErrorMessage';

// function BoardDetailPage() {
//   const { postId } = useParams();
//   const dispatch = useDispatch();
//   const { currentPost, loading, error } = useSelector((state) => state.post);
  
//   useEffect(() => {
//     dispatch(fetchPost(postId));
//   }, [dispatch, postId]);
  
//   if (loading) return <Loading />;
//   if (error) return <ErrorMessage message={error} />;
  
//   if (!currentPost) {
//     return (
//       <BasicLayout>
//         <div className="board-detail-page">
//           <ErrorMessage message="게시글을 찾을 수 없습니다." />
//         </div>
//       </BasicLayout>
//     );
//   }
  
//   return (
//     <BasicLayout>
//       <div className="board-detail-page">
//         <PostDetail post={currentPost} />
//       </div>
//     </BasicLayout>
//   );
// }

// export default BoardDetailPage;