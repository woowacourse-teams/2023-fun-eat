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
