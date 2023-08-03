import type { PropsWithChildren } from 'react';
import { createContext, useState } from 'react';

import type { Review } from '@/types/review';

interface ProductReviewState {
  productReviews: Review[];
  addProductReviews: (newProductReviews: Review[]) => void;
}

export const ProductReviewContext = createContext<ProductReviewState | null>(null);

const ProductReviewProvider = ({ children }: PropsWithChildren) => {
  const [productReviews, setProductReviews] = useState<Review[]>([]);

  const addProductReviews = (newProductReviews: Review[]) => {
    setProductReviews((prev) => [...prev, ...newProductReviews]);
  };

  const productReviewState = {
    productReviews,
    addProductReviews,
  };

  return <ProductReviewContext.Provider value={productReviewState}>{children}</ProductReviewContext.Provider>;
};

export default ProductReviewProvider;
