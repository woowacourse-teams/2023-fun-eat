import { useRef, useEffect } from 'react';

import useProductReview from './useProductReview';
import useProductReviewContext from '../context/useProductReviewContext';
import useProductReviewPageContext from '../context/useProductReviewPageContext';
import useIntersectionObserver from '../useIntersectionObserver';

const useInfiniteProductReviews = (productId: number, sort: string) => {
  const { page, resetPage, getNextPage } = useProductReviewPageContext();
  const { productReviews, resetProductReviews, addProductReviews } = useProductReviewContext();

  const scrollRef = useRef<HTMLDivElement>(null);
  const { data: productReviewsResponse, error } = useProductReview(productId, page, sort);

  useIntersectionObserver<HTMLDivElement>(getNextPage, scrollRef, productReviewsResponse?.page.lastPage);

  useEffect(() => {
    resetPage();
  }, [sort]);

  useEffect(() => {
    if (page === 0) {
      resetProductReviews();
    }

    if (!productReviewsResponse) {
      return;
    }

    addProductReviews(productReviewsResponse.reviews);
  }, [productReviewsResponse?.reviews]);

  return { productReviews, scrollRef, error, resetPage };
};

export default useInfiniteProductReviews;
