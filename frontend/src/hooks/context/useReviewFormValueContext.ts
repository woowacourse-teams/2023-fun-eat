import { useContext } from 'react';

import { ReviewFormValueContext } from '@/contexts/ReviewFormContext';

const useReviewFormValueContext = () => {
  const reviewFormValue = useContext(ReviewFormValueContext);

  if (reviewFormValue === null || reviewFormValue === undefined) {
    throw new Error('useReviewFormValueContext는 ReviewFormProvider 안에서 사용해야 합니다.');
  }

  return reviewFormValue;
};

export default useReviewFormValueContext;
