import styled from 'styled-components';

import ReviewRankingItem from '../ReviewRankingItem/ReviewRankingItem';

import { PATH } from '@/constants/path';
import { useReviewRankingQuery } from '@/hooks/queries/rank';
import useDisplaySlice from '@/hooks/useDisplaySlice';

const ReviewRankingList = () => {
  const { data: reviewRankings } = useReviewRankingQuery();
  const reviewsToDisplay = useDisplaySlice(PATH.HOME, reviewRankings?.reviews);

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
