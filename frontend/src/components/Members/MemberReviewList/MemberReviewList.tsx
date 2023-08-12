import styled from 'styled-components';

import { ReviewRankingItem } from '@/components/Rank';
import memberReviews from '@/mocks/data/profileReviews.json';

const MemberReviewList = () => {
  return (
    <MemberReviewListContainer>
      {memberReviews.reviews.map((reviewRanking) => (
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
