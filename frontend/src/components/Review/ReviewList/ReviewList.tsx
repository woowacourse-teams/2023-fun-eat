import { Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import styled from 'styled-components';

import ReviewItem from '../ReviewItem/ReviewItem';

import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteProductReviewsQuery } from '@/hooks/queries/product';
import type { SortOption } from '@/types/common';

interface ReviewListProps {
  productId: number;
  selectedOption: SortOption;
}

const ReviewList = ({ productId, selectedOption }: ReviewListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);

  const { fetchNextPage, hasNextPage, data } = useInfiniteProductReviewsQuery(productId, selectedOption.value);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  if (!data) {
    return null;
  }

  const reviews = data.pages.flatMap((page) => page.reviews);

  if (reviews.length === 0) {
    return <Text>상품의 첫 리뷰를 작성해주세요</Text>;
  }

  return (
    <>
      <ReviewListContainer>
        {reviews.map((review) => (
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
