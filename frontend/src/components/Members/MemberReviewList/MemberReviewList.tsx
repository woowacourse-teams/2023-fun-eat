import { Link, Spacing, Text, theme } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import MemberReviewItem from '../MemberReviewItem/MemberReviewItem';

import { PATH } from '@/constants/path';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteMemberReviewQuery } from '@/hooks/queries/members';
import useDisplaySlice from '@/utils/displaySlice';

interface MemberReviewListProps {
  isMemberPage?: boolean;
}

const MemberReviewList = ({ isMemberPage = false }: MemberReviewListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);
  const { fetchNextPage, hasNextPage, data } = useInfiniteMemberReviewQuery();
  const memberReviews = data.pages.flatMap((page) => page.reviews);
  const reviewsToDisplay = useDisplaySlice(isMemberPage, memberReviews);

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const totalReviewCount = data.pages[0].page.totalDataCount;

  if (totalReviewCount === 0) {
    return (
      <ErrorContainer>
        <Text size="lg" weight="bold">
          앗, 작성한 리뷰가 없네요 🥲
        </Text>
        <Spacing size={16} />
        <ReviewLink as={RouterLink} to={`${PATH.PRODUCT_LIST}/food`} block>
          리뷰 작성하러 가기
        </ReviewLink>
      </ErrorContainer>
    );
  }

  return (
    <MemberReviewListContainer>
      {!isMemberPage && (
        <TotalReviewCount color={theme.colors.gray4}>
          총 <strong>{totalReviewCount}</strong>개의 리뷰를 남겼어요!
        </TotalReviewCount>
      )}
      <Spacing size={20} />
      <MemberReviewListWrapper>
        {reviewsToDisplay.map((review) => (
          <li key={review.reviewId}>
            <Link as={RouterLink} to={`${PATH.REVIEW}/${review.reviewId}`} block>
              <MemberReviewItem review={review} isMemberPage={isMemberPage} />
            </Link>
          </li>
        ))}
      </MemberReviewListWrapper>
      <div ref={scrollRef} aria-hidden />
    </MemberReviewListContainer>
  );
};

export default MemberReviewList;

const MemberReviewListContainer = styled.section`
  display: flex;
  flex-direction: column;
`;

const MemberReviewListWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const TotalReviewCount = styled(Text)`
  text-align: right;
`;

const ErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
`;

const ReviewLink = styled(Link)`
  padding: 12px 12px;
  border: 1px solid ${({ theme }) => theme.colors.gray4};
  border-radius: 8px;
`;
