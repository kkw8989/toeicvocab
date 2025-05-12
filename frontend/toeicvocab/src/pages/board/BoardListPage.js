// import React, { useEffect } from 'react';
// import { useDispatch, useSelector } from 'react-redux';
// import { fetchAllPosts } from '../../slices/postSlice';
// import BasicLayout from '../../layout/BasicLayout';
// import PostList from '../../components/post/PostList';
// import Loading from '../../components/common/Loading';
// import ErrorMessage from '../../components/common/ErrorMessage';

// function BoardListPage() {
//   const dispatch = useDispatch();
//   const { posts, loading, error } = useSelector((state) => state.post);
  
//   useEffect(() => {
//     dispatch(fetchAllPosts());
//   }, [dispatch]);
  
//   if (loading) return <Loading />;
//   if (error) return <ErrorMessage message={error} />;
  
//   return (
//     <BasicLayout>
//       <div className="board-list-page">
//         <h1>게시판</h1>
//         <PostList posts={posts} />
//       </div>
//     </BasicLayout>
//   );
// }

// export default BoardListPage;