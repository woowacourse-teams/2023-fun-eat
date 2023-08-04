import type { Dispatch, PropsWithChildren, SetStateAction } from 'react';
import { createContext, useState } from 'react';

import type { Review } from '@/types/review';

interface ProductReviewState {
  productReviews: Review[];
  setProductReviews: Dispatch<SetStateAction<Review[]>>;
  resetProductReviews: () => void;
  addProductReviews: (newProductReviews: Review[]) => void;
}

interface ProductReviewPageState {
  page: number;
  resetPage: () => void;
  getNextPage: () => void;
}

export const ProductReviewContext = createContext<ProductReviewState | null>(null);
export const ProductReviewPageContext = createContext<ProductReviewPageState | null>(null);

const ProductReviewProvider = ({ children }: PropsWithChildren) => {
  const [page, setPage] = useState(0);
  const [productReviews, setProductReviews] = useState<Review[]>([]);

  const resetPage = () => {
    setPage(0);
  };

  const getNextPage = () => {
    setPage((page) => page + 1);
  };

  const resetProductReviews = () => {
    setProductReviews([]);
  };

  const addProductReviews = (newProductReviews: Review[]) => {
    setProductReviews((prev) => [...prev, ...newProductReviews]);
  };

  const productReviewState = {
    productReviews,
    setProductReviews,
    resetProductReviews,
    addProductReviews,
  };

  const productReviewPageState = {
    page,
    resetPage,
    getNextPage,
  };

  return (
    <ProductReviewPageContext.Provider value={productReviewPageState}>
      <ProductReviewContext.Provider value={productReviewState}>{children}</ProductReviewContext.Provider>
    </ProductReviewPageContext.Provider>
  );
};

export default ProductReviewProvider;
