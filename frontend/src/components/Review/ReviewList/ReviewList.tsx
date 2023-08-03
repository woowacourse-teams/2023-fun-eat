import { Text } from '@fun-eat/design-system';
import React from 'react';
import styled from 'styled-components';

import ReviewItem from '../ReviewItem/ReviewItem';

import useInfiniteProductReviews from '@/hooks/product/useInfiniteProductReviews';
import type { SortOption } from '@/types/common';

const LOGIN_ERROR_MESSAGE =
  '로그인 해야 상품 리뷰를 볼 수 있어요.\n펀잇에 가입하고 편의점 상품의 리뷰를 확인해보세요 😊';

interface ReviewListProps {
  productId: number;
  selectedOption: SortOption;
}

const ReviewList = ({ productId, selectedOption }: ReviewListProps) => {
  const { productReviews, scrollRef, error } = useInfiniteProductReviews(productId, selectedOption.value);

  if (error) {
    return (
      <ErrorDescription align="center" weight="bold" size="lg">
        {LOGIN_ERROR_MESSAGE}
      </ErrorDescription>
    );
  }

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

const ErrorDescription = styled(Text)`
  padding: 40px 0;
  white-space: pre-line;
  word-break: break-all;
`;
