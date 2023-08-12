import { Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { ReviewRankingItem } from '@/components/Rank';
import { useMemberReviewQuery } from '@/hooks/queries/members';
import useDisplaySlice from '@/utils/displaySlice';

interface MemberReviewListProps {
  isMember?: boolean;
}

const MemberReviewList = ({ isMember }: MemberReviewListProps) => {
  const { data: memberReviews } = useMemberReviewQuery();
  const reviewsToDisplay = useDisplaySlice(isMember, memberReviews?.reviews);

  return (
    <MemberReviewListContainer>
      {!isMember && (
        <TotalReviewCount color={theme.colors.gray4}>총 {memberReviews?.page.totalDataCount}개</TotalReviewCount>
      )}

      {reviewsToDisplay?.map((reviewRanking) => (
        <li key={reviewRanking.reviewId}>
          <ReviewRankingItem reviewRanking={reviewRanking} />
        </li>
      ))}
    </MemberReviewListContainer>
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
