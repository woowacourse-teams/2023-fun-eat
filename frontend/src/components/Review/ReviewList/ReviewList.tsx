import React from 'react';
import styled from 'styled-components';

import ReviewItem from '../ReviewItem/ReviewItem';

import useInfiniteProductReviews from '@/hooks/product/useInfiniteProductReviews';
import type { SortOption } from '@/types/common';

interface ReviewListProps {
  productId: number;
  selectedOption: SortOption;
}

const ReviewList = ({ productId, selectedOption }: ReviewListProps) => {
  const { productReviews, scrollRef } = useInfiniteProductReviews(productId, selectedOption.value);

  return (
    <>
      <ReviewListContainer>
        {productReviews.map((review) => (
          <li key={review.id}>
            <ReviewItem productId={productId} review={review} />
          </li>
        ))}
      </ReviewListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default ReviewList;

const ReviewListContainer = styled.ul`
  display: flex;
  flex-direction: column;
  row-gap: 60px;
`;
