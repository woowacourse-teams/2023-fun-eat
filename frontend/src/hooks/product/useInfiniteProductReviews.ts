import { useState, useRef, useEffect } from 'react';

import useProductReview from './useProductReview';
import useIntersectionObserver from '../useIntersectionObserver';

import type { Review } from '@/types/review';

const useInfiniteProductReviews = (productId: string, sort: string) => {
  const [page, setPage] = useState(0);
  const [productReviews, setProductReviews] = useState<Review[]>([]);

  const scrollRef = useRef<HTMLDivElement>(null);
  const { data: productReviewsResponse } = useProductReview(productId, page, sort);

  const getNextPage = () => {
    setPage((page) => page + 1);
  };

  useIntersectionObserver<HTMLDivElement>(getNextPage, scrollRef, productReviewsResponse?.page.lastPage);

  useEffect(() => {
    if (!productReviewsResponse) {
      return;
    }

    setProductReviews((prev) => [...prev, ...productReviewsResponse.reviews]);
  }, [productReviewsResponse?.reviews]);

  return { productReviews, scrollRef };
};

export default useInfiniteProductReviews;
