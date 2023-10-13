import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import ReviewRankingItem from '../ReviewRankingItem/ReviewRankingItem';

import { PATH } from '@/constants/path';
import { useGA } from '@/hooks/common';
import { useReviewRankingQuery } from '@/hooks/queries/rank';
import useDisplaySlice from '@/utils/displaySlice';

interface ReviewRankingListProps {
  isHomePage?: boolean;
}

const ReviewRankingList = ({ isHomePage = false }: ReviewRankingListProps) => {
  const { data: reviewRankings } = useReviewRankingQuery();
  const { gaEvent } = useGA();
  const reviewsToDisplay = useDisplaySlice(isHomePage, reviewRankings.reviews);

  const handleReviewRankingLinkClick = () => {
    gaEvent({ category: 'link', action: '리뷰 랭킹 링크 클릭', label: '랭킹' });
  };

  return (
    <ReviewRankingListContainer>
      {reviewsToDisplay.map((reviewRanking) => (
        <li key={reviewRanking.reviewId}>
          <Link
            as={RouterLink}
            to={`${PATH.REVIEW}/${reviewRanking.reviewId}`}
            onClick={handleReviewRankingLinkClick}
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
