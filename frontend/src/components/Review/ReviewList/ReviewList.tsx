import { useRef } from 'react';
import styled from 'styled-components';

import ReviewItem from '../ReviewItem/ReviewItem';

import { Loading } from '@/components/Common';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteProductReviewsQuery } from '@/hooks/queries/product';
import type { SortOption } from '@/types/common';

interface ReviewListProps {
  productId: number;
  selectedOption: SortOption;
}

const ReviewList = ({ productId, selectedOption }: ReviewListProps) => {
  const { fetchNextPage, hasNextPage, data, isFetchingNextPage } = useInfiniteProductReviewsQuery(
    productId,
    selectedOption.value
  );
  const scrollRef = useRef<HTMLDivElement>(null);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const reviews = data.pages.flatMap((page) => page.reviews);

  // TODO: 로그인 에러 페이지로 이동 예정. 다른 브랜치에서 작업중
  //if (isError) {
  //  return (
  //    <ErrorContainer>
  //      <ErrorDescription align="center" weight="bold" size="lg">
  //        {LOGIN_ERROR_MESSAGE}
  //      </ErrorDescription>
  //      <LoginLink as={RouterLink} to={PATH.LOGIN} block>
  //        로그인하러 가기
  //      </LoginLink>
  //    </ErrorContainer>
  //  );
  //}

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
      {isFetchingNextPage && <Loading />}
    </>
  );
};

export default ReviewList;

const ReviewListContainer = styled.ul`
  display: flex;
  flex-direction: column;
  row-gap: 60px;
`;
