import styled from 'styled-components';

import ReviewRankingItem from '../ReviewRankingItem/ReviewRankingItem';

import { useReviewRankingQuery } from '@/hooks/queries/rank';
import useDisplaySlice from '@/utils/displaySlice';

interface ReviewRankingListProps {
  isHomePage?: boolean;
}

const ReviewRankingList = ({ isHomePage }: ReviewRankingListProps) => {
  const { data: reviewRankings } = useReviewRankingQuery();
  const reviewsToDisplay = useDisplaySlice(isHomePage, reviewRankings?.reviews);

  return (
    <ReviewRankingListContainer>
      {reviewsToDisplay.map((reviewRanking) => (
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
