import { Spacing, Text, theme } from '@fun-eat/design-system';
import { useRef } from 'react';
import styled from 'styled-components';

import { ReviewRankingItem } from '@/components/Rank';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteMemberReviewQuery } from '@/hooks/queries/members';
import useDisplaySlice from '@/utils/displaySlice';

interface MemberReviewListProps {
  isMember?: boolean;
}

const MemberReviewList = ({ isMember = false }: MemberReviewListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);

  const { fetchNextPage, hasNextPage, data } = useInfiniteMemberReviewQuery();
  const memberReviews = data?.pages.flatMap((page) => page.reviews);
  const reviewsToDisplay = useDisplaySlice(isMember, memberReviews);

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const totalReviewCount = data?.pages.flatMap((page) => page.page.totalDataCount);

  return (
    <MemberReviewListContainer>
      {!isMember && (
        <TotalReviewCount color={theme.colors.gray4}>
          총 <strong>&apos;{totalReviewCount}&apos;</strong>개의 리뷰를 남겼어요!
        </TotalReviewCount>
      )}
      <Spacing size={20} />
      <MemberReviewListWrapper>
        {reviewsToDisplay?.map((reviewRanking) => (
          <li key={reviewRanking.reviewId}>
            <ReviewRankingItem reviewRanking={reviewRanking} />
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
