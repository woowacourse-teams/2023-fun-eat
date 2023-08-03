import { useContext } from 'react';

import { ProductReviewPageContext } from '@/contexts/ProductReviewContext';

const useProductReviewPageContext = () => {
  const productReviewPageState = useContext(ProductReviewPageContext);

  if (productReviewPageState === null || productReviewPageState === undefined) {
    throw new Error('useProductReviewPageState는 ProductReviewProvider 안에서 사용해야 합니다.');
  }

  return productReviewPageState;
};

export default useProductReviewPageContext;
