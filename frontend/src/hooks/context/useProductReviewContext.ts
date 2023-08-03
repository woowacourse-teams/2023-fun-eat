import { useContext } from 'react';

import { ProductReviewContext } from '@/contexts/ProductReviewContext';

const useProductReviewContext = () => {
  const productReviewState = useContext(ProductReviewContext);

  if (productReviewState === null || productReviewState === undefined) {
    throw new Error('useProductReviewContext는 ProductReviewProvider 안에서 사용해야 합니다.');
  }

  return productReviewState;
};

export default useProductReviewContext;
