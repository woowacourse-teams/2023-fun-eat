import { useState, useRef, useEffect } from 'react';

import useProductReview from './useProductReview';
import useProductReviewContext from '../context/useProductReviewContext';
import useIntersectionObserver from '../useIntersectionObserver';

const useInfiniteProductReviews = (productId: number, sort: string) => {
  const [page, setPage] = useState(0);
  const { productReviews, addProductReviews } = useProductReviewContext();

  const scrollRef = useRef<HTMLDivElement>(null);
  const { data: productReviewsResponse, error } = useProductReview(productId, page, sort);

  const getNextPage = () => {
    setPage((page) => page + 1);
  };

  useIntersectionObserver<HTMLDivElement>(getNextPage, scrollRef, productReviewsResponse?.page.lastPage);

  useEffect(() => {
    if (!productReviewsResponse) {
      return;
    }

    addProductReviews(productReviewsResponse.reviews);
  }, [productReviewsResponse?.reviews]);

  return { productReviews, scrollRef, error };
};

export default useInfiniteProductReviews;
