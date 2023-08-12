import { Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { ReviewRankingItem } from '@/components/Rank';
import { useProfileReviewQuery } from '@/hooks/queries/members';
import useDisplaySlice from '@/utils/displaySlice';

interface MemberReviewListProps {
  isMember?: boolean;
}

const MemberReviewList = ({ isMember }: MemberReviewListProps) => {
  const { data: profileReviews } = useProfileReviewQuery();
  const reviewsToDisplay = useDisplaySlice(isMember, profileReviews?.reviews);

  return (
    <MemberReviewListContainer>
      {!isMember && (
        <TotalReviewCount color={theme.colors.gray4}>총 {profileReviews?.page.totalDataCount}개</TotalReviewCount>
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
