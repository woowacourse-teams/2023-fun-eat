import { Text, Link } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import ReviewItem from '../ReviewItem/ReviewItem';

import { PATH } from '@/constants/path';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteProductReviewsQuery } from '@/hooks/queries/product';
import type { SortOption } from '@/types/common';

const LOGIN_ERROR_MESSAGE =
  '로그인 해야 상품 리뷰를 볼 수 있어요.\n펀잇에 가입하고 편의점 상품의 리뷰를 확인해보세요 😊';

interface ReviewListProps {
  productId: number;
  selectedOption: SortOption;
}

const ReviewList = ({ productId, selectedOption }: ReviewListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);

  const { fetchNextPage, hasNextPage, data, isError } = useInfiniteProductReviewsQuery(productId, selectedOption.value);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  if (isError) {
    return (
      <ErrorContainer>
        <ErrorDescription align="center" weight="bold" size="lg">
          {LOGIN_ERROR_MESSAGE}
        </ErrorDescription>
        <LoginLink as={RouterLink} to={PATH.LOGIN} block>
          로그인하러 가기
        </LoginLink>
      </ErrorContainer>
    );
  }

  return (
    <>
      <ReviewListContainer>
        {data?.pages
          .flatMap((page) => page.reviews)
          .map((review) => (
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

const ErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ErrorDescription = styled(Text)`
  padding: 40px 0;
  white-space: pre-line;
  word-break: break-all;
`;

const LoginLink = styled(Link)`
  padding: 16px 24px;
  border: 1px solid ${({ theme }) => theme.colors.gray4};
  border-radius: 8px;
`;
