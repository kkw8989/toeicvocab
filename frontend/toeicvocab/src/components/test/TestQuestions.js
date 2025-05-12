import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setUserAnswer, submitTestResult } from '../../slices/testSlice';
import Button from '../common/Button';
import Loading from '../common/Loading';
import ErrorMessage from '../common/ErrorMessage';

function TestQuestions({ wordbookId }) {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [shuffledOptions, setShuffledOptions] = useState([]);
  const [isTestFinished, setIsTestFinished] = useState(false);
  
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { testWords, userAnswers, loading, error } = useSelector((state) => state.test);
  
  // 단어별 보기 옵션 생성 (정답 1개 + 오답 3개)
  useEffect(() => {
    if (testWords.length > 0 && currentIndex < testWords.length) {
      const currentWord = testWords[currentIndex];
      
      // 현재 단어를 제외한 다른 단어들 중에서 3개 무작위 선택
      const otherWords = testWords.filter((word) => word.id !== currentWord.id);
      const randomWords = [...otherWords]
        .sort(() => Math.random() - 0.5)
        .slice(0, 3);
      
      // 정답과 오답 4개를 합쳐서 무작위로 배치
      const options = [
        { id: currentWord.id, meaning: currentWord.meaning, isCorrect: true },
        ...randomWords.map((word) => ({
          id: word.id,
          meaning: word.meaning,
          isCorrect: false,
        })),
      ].sort(() => Math.random() - 0.5);
      
      setShuffledOptions(options);
    }
  }, [testWords, currentIndex]);
  
  // 답변 선택 처리
  const handleAnswerSelect = (option) => {
    const currentWord = testWords[currentIndex];
    
    // 사용자 답변 저장
    dispatch(
      setUserAnswer({
        wordId: currentWord.id,
        answer: {
          selectedId: option.id,
          isCorrect: option.isCorrect,
        },
      })
    );
    
    // 지연 후 다음 문제로 이동
    setTimeout(() => {
      if (currentIndex < testWords.length - 1) {
        // 다음 문제로 이동
        setCurrentIndex(currentIndex + 1);
      } else {
        // 테스트 종료
        setIsTestFinished(true);
      }
    }, 500);
  };
  
  // 테스트 결과 제출
  const handleFinishTest = () => {
    // 맞은 문제 수 계산
    const correctCount = Object.values(userAnswers).filter(
      (answer) => answer.isCorrect
    ).length;
    
    // 테스트 결과 저장
    dispatch(
      submitTestResult({
        wordbookId,
        correctCount,
        totalCount: testWords.length,
      })
    )
      .unwrap()
      .then(() => {
        // 결과 페이지로 이동
        navigate(`/test/result/${wordbookId}`);
      });
  };
  
  // 로딩 중 표시
  if (loading) {
    return <Loading />;
  }
  
  // 에러 표시
  if (error) {
    return <ErrorMessage message={error} />;
  }
  
  // 테스트 종료 화면
  if (isTestFinished) {
    // 맞은 문제 수 계산
    const correctCount = Object.values(userAnswers).filter(
      (answer) => answer.isCorrect
    ).length;
    
    return (
      <div className="test-finish">
        <h2>테스트 완료</h2>
        <div className="test-result-summary">
          <p>
            총 {testWords.length}문제 중 {correctCount}문제를 맞추셨습니다.
            (정답률: {((correctCount / testWords.length) * 100).toFixed(1)}%)
          </p>
        </div>
        <Button variant="primary" onClick={handleFinishTest}>
          결과 확인하기
        </Button>
      </div>
    );
  }
  
  // 테스트 문제가 없는 경우
  if (testWords.length === 0) {
    return (
      <div className="no-test-words">
        <p>선택한 단어장에 테스트할 단어가 없습니다.</p>
        <Button variant="primary" onClick={() => navigate('/test')}>
          다른 단어장 선택하기
        </Button>
      </div>
    );
  }
  
  // 현재 문제
  const currentWord = testWords[currentIndex];
  const currentAnswer = userAnswers[currentWord.id];
  
  return (
    <div className="test-questions">
      <div className="test-progress">
        <div className="progress-bar">
          <div
            className="progress-fill"
            style={{
              width: `${((currentIndex + 1) / testWords.length) * 100}%`,
            }}
          ></div>
        </div>
        <div className="progress-text">
          {currentIndex + 1} / {testWords.length}
        </div>
      </div>
      
      <div className="question-container">
        <h2 className="question-word">{currentWord.word}</h2>
        <p className="question-text">다음 단어의 뜻으로 알맞은 것은?</p>
        
        <div className="options-container">
          {shuffledOptions.map((option, index) => (
            <button
              key={index}
              className={`option-button ${
                currentAnswer && currentAnswer.selectedId === option.id
                  ? currentAnswer.isCorrect
                    ? 'correct'
                    : 'incorrect'
                  : ''
              }`}
              onClick={() => !currentAnswer && handleAnswerSelect(option)}
              disabled={!!currentAnswer}
            >
              {index + 1}. {option.meaning}
            </button>
          ))}
        </div>
      </div>
      
      {currentAnswer && (
        <div className={`answer-feedback ${currentAnswer.isCorrect ? 'correct' : 'incorrect'}`}>
          {currentAnswer.isCorrect ? '정답입니다!' : '틀렸습니다!'}
          {!currentAnswer.isCorrect && (
            <p className="correct-answer">
              정답: {testWords.find((word) => word.id === currentAnswer.selectedId)?.meaning}
            </p>
          )}
        </div>
      )}
    </div>
  );
}

export default TestQuestions;