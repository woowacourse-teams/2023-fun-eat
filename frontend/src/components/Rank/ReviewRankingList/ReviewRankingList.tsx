import { useLocation } from 'react-router-dom';
import styled from 'styled-components';

import ReviewRankingItem from '../ReviewRankingItem/ReviewRankingItem';

import { useReviewRankingQuery } from '@/hooks/queries/rank';

const ReviewRankingList = () => {
  const location = useLocation();
  const isRootPath = location.pathname === '/';

  const { data: reviewRankings } = useReviewRankingQuery();
  const reviewsToDisplay = isRootPath ? reviewRankings?.reviews.slice(0, 3) : reviewRankings?.reviews;

  return (
    <ReviewRankingListContainer>
      {reviewsToDisplay?.map((reviewRanking) => (
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
