import { useContext } from 'react';

import { ReviewFormActionContext } from '@/contexts/ReviewFormContext';

const useReviewFormActionContext = () => {
  const reviewFormAction = useContext(ReviewFormActionContext);

  if (reviewFormAction === null || reviewFormAction === undefined) {
    throw new Error('useReviewFormActionContext는 ReviewFormProvider 안에서 사용해야 합니다.');
  }

  return reviewFormAction;
};

export default useReviewFormActionContext;
