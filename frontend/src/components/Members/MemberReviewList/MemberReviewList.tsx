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

const MemberReviewList = ({ isMember }: MemberReviewListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);

  const { fetchNextPage, hasNextPage, data } = useInfiniteMemberReviewQuery();
  const memberReviews = data?.pages.flatMap((page) => page.reviews);
  const reviewsToDisplay = useDisplaySlice(isMember, memberReviews);

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const totalReviewCount = data?.pages.flatMap((page) => page.page.totalDataCount);

  return (
    <>
      {!isMember && (
        <TotalReviewCount color={theme.colors.gray4}>
          총 &apos;{totalReviewCount}&apos;개의 리뷰를 남겼어요!
        </TotalReviewCount>
      )}
      <Spacing size={20} />
      <MemberReviewListContainer>
        {reviewsToDisplay?.map((reviewRanking) => (
          <li key={reviewRanking.reviewId}>
            <ReviewRankingItem reviewRanking={reviewRanking} />
          </li>
        ))}
      </MemberReviewListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default MemberReviewList;

const MemberReviewListContainer = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const TotalReviewCount = styled(Text)`
  display: flex;
  flex-direction: row-reverse;
`;
