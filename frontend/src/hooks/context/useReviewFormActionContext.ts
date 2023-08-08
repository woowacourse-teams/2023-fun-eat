import { useContext } from 'react';

import { ReviewFormActionContext } from '@/contexts/ReviewFormContext';

const useReviewFormActionContext = () => {
  const handleReviewFormValue = useContext(ReviewFormActionContext);

  if (handleReviewFormValue === null || handleReviewFormValue === undefined) {
    throw new Error('useReviewFormActionContext는 ReviewFormProvider 안에서 사용해야 합니다.');
  }

  return handleReviewFormValue;
};

export default useReviewFormActionContext;
