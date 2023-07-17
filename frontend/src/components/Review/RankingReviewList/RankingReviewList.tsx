import styled from 'styled-components';

import RankingReviewItem from '../RankingReviewItem/RankingReviewItem';

import rankingReviewList from '@/mocks/data/rankingReviewList.json';

const RankingReviewList = () => {
  const { reviews } = rankingReviewList;

  return (
    <RankingReviewListContainer>
      {reviews.map((rankingReview) => (
        <li key={rankingReview.reviewId}>
          <RankingReviewItem rankingReview={rankingReview} />
        </li>
      ))}
    </RankingReviewListContainer>
  );
};

export default RankingReviewList;

const RankingReviewListContainer = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;
