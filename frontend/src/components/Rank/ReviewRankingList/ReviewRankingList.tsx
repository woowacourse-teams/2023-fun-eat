import styled from 'styled-components';

import ReviewRankingItem from '../ReviewRankingItem/ReviewRankingItem';

import type { ReviewRanking } from '@/types/ranking';

interface ReviewRankingListProps {
  reviewRankings: ReviewRanking[];
}

const ReviewRankingList = ({ reviewRankings }: ReviewRankingListProps) => {
  return (
    <ReviewRankingListContainer>
      {reviewRankings.map((reviewRanking) => (
        <li key={reviewRanking.reviewId}>
          <ReviewRankingItem reviewRanking={reviewRanking} />
        </li>
      ))}
    </ReviewRankingListContainer>
  );
};

export default ReviewRankingList;

const ReviewRankingListContainer = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;
