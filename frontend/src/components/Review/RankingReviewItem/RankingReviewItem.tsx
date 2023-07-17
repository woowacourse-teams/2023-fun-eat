import { Text, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import type { RankingReview } from '@/types/review';

interface RankingReviewProps {
  rankingReview: RankingReview;
}

const RankingReviewItem = ({ rankingReview }: RankingReviewProps) => {
  const { productName, content, rating, favoriteCount } = rankingReview;

  return (
    <RankingReviewContainer>
      <Text size="sm" weight="bold">
        {productName}
      </Text>
      <ReviewText size="sm" color={theme.textColors.info}>
        {content}
      </ReviewText>
      <FavoriteStarWrapper>
        <FavoriteIconWrapper>
          <SvgIcon variant="favorite" color="red" width={11} height={13} />
          <Text size="xs" weight="bold">
            {favoriteCount}
          </Text>
        </FavoriteIconWrapper>
        <RatingIconWrapper>
          <SvgIcon variant="star" color={theme.colors.secondary} width={16} height={16} />
          <Text size="xs" weight="bold">
            {rating.toFixed(1)}
          </Text>
        </RatingIconWrapper>
      </FavoriteStarWrapper>
    </RankingReviewContainer>
  );
};

export default RankingReviewItem;

const RankingReviewContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 315px;
  padding: 12px;
  gap: 4px;
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-radius: ${({ theme }) => theme.borderRadius.sm};
`;

const ReviewText = styled(Text)`
  display: -webkit-inline-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
`;

const FavoriteStarWrapper = styled.div`
  display: flex;
  gap: 4px;
`;

const FavoriteIconWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
`;

const RatingIconWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 2px;

  & > svg {
    padding-bottom: 2px;
  }
`;
