import styled from 'styled-components';

import ReviewRankingItem from '../ReviewRankingItem/ReviewRankingItem';

import type { ReviewRanking } from '@/types/ranking';

interface ReviewRankingListProps {
  reviewRankingList: ReviewRanking[];
}

const ReviewRankingList = ({ reviewRankingList }: ReviewRankingListProps) => {
  return (
    <ReviewRankingListContainer>
      {reviewRankingList.map((reviewRanking) => (
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
