import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import ReviewRankingItem from '../ReviewRankingItem/ReviewRankingItem';

import { PATH } from '@/constants/path';
import { useReviewRankingQuery } from '@/hooks/queries/rank';
import useDisplaySlice from '@/utils/displaySlice';

interface ReviewRankingListProps {
  isHomePage?: boolean;
}

const ReviewRankingList = ({ isHomePage = false }: ReviewRankingListProps) => {
  const { data: reviewRankings } = useReviewRankingQuery();
  const reviewsToDisplay = useDisplaySlice(isHomePage, reviewRankings.reviews);

  return (
    <ReviewRankingListContainer>
      {reviewsToDisplay.map((reviewRanking) => (
        <li key={reviewRanking.reviewId}>
          <Link
            as={RouterLink}
            to={`${PATH.PRODUCT_LIST}/${reviewRanking.categoryType}/${reviewRanking.productId}`}
            block
          >
            <ReviewRankingItem reviewRanking={reviewRanking} />
          </Link>
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
