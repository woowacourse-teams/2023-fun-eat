import { useTheme, Spacing, Text } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import type { MemberReview } from '@/types/review';

interface MemberReviewItemProps {
  review: MemberReview;
}

const MemberReviewItem = ({ review }: MemberReviewItemProps) => {
  const theme = useTheme();

  const { productName, content, rating, favoriteCount } = review;

  return (
    <ReviewRankingItemContainer>
      <Text size="sm" weight="bold">
        {productName}
      </Text>
      <ReviewText size="sm" color={theme.textColors.info}>
        {content}
      </ReviewText>
      <Spacing size={4} />
      <FavoriteStarWrapper>
        <FavoriteIconWrapper aria-label={`좋아요 ${favoriteCount}개`}>
          <SvgIcon variant="favoriteFilled" color="red" width={11} height={13} />
          <Text size="xs" weight="bold">
            {favoriteCount}
          </Text>
        </FavoriteIconWrapper>
        <RatingIconWrapper aria-label={`${rating.toFixed(1)}점`}>
          <SvgIcon variant="star" color={theme.colors.secondary} width={16} height={16} />
          <Text size="xs" weight="bold">
            {rating.toFixed(1)}
          </Text>
        </RatingIconWrapper>
      </FavoriteStarWrapper>
    </ReviewRankingItemContainer>
  );
};

export default MemberReviewItem;

const ReviewRankingItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
  padding: 12px 0;
  border-bottom: ${({ theme }) => `1px solid ${theme.borderColors.disabled}`};
`;

const ReviewText = styled(Text)`
  display: -webkit-inline-box;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
`;

const FavoriteStarWrapper = styled.div`
  display: flex;
  gap: 4px;
`;

const FavoriteIconWrapper = styled.div`
  display: flex;
  gap: 4px;
  align-items: center;
`;

const RatingIconWrapper = styled.div`
  display: flex;
  gap: 2px;
  align-items: center;

  & > svg {
    padding-bottom: 2px;
  }
`;
