import { Badge, Button, Text, useTheme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import { SvgIcon, TagList } from '@/components/Common';
import { useReviewFavorite } from '@/hooks/review';
import type { Review, ReviewFavoriteRequestBody } from '@/types/review';
import { getRelativeDate } from '@/utils/relativeDate';

interface ReviewItemProps {
  productId: number;
  review: Review;
}

const ReviewItem = ({ productId, review }: ReviewItemProps) => {
  const { id, userName, profileImage, image, rating, tags, content, createdAt, rebuy, favoriteCount, favorite } =
    review;
  const [isFavorite, setIsFavorite] = useState(favorite);

  const { request } = useReviewFavorite<ReviewFavoriteRequestBody>(productId, id);
  const theme = useTheme();

  const handleToggleFavorite = async () => {
    await request({ favorite: !isFavorite });
    setIsFavorite((prev) => !prev);
  };

  return (
    <ReviewItemContainer>
      <ReviewerWrapper>
        <ReviewerInfoWrapper>
          <ReviewerImage src={profileImage} width={40} height={40} alt={`${userName}의 프로필`} />
          <div>
            <Text weight="bold">{userName}</Text>
            <RatingIconWrapper>
              {Array.from({ length: 5 }, (_, index) => (
                <SvgIcon
                  key={`rating-${index}`}
                  variant="star"
                  color={index < rating ? theme.colors.secondary : theme.colors.gray2}
                  width={16}
                  height={16}
                />
              ))}
              <Text as="span" size="sm" color={theme.textColors.info}>
                {getRelativeDate(createdAt)}
              </Text>
            </RatingIconWrapper>
          </div>
        </ReviewerInfoWrapper>
        {rebuy && (
          <RebuyBadge color={theme.colors.primary} textColor={theme.textColors.default}>
            😝 또 살래요
          </RebuyBadge>
        )}
      </ReviewerWrapper>
      {image !== null && <ReviewImage src={`/images/${image}`} height={150} alt={`${userName}의 리뷰`} />}
      <TagList tags={tags} />
      <Text css="white-space: pre-wrap">{content}</Text>
      <FavoriteButton
        type="button"
        variant="transparent"
        onClick={handleToggleFavorite}
        aria-label={`좋아요 ${favoriteCount}개`}
      >
        <SvgIcon variant={isFavorite ? 'favoriteFilled' : 'favorite'} color={isFavorite ? 'red' : theme.colors.gray4} />
        <Text as="span" weight="bold">
          {favoriteCount}
        </Text>
      </FavoriteButton>
    </ReviewItemContainer>
  );
};

export default ReviewItem;

const ReviewItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 20px;
`;

const ReviewerWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const ReviewerInfoWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 10px;
`;

const RebuyBadge = styled(Badge)`
  font-weight: ${({ theme }) => theme.fontWeights.bold};
`;

const ReviewerImage = styled.img`
  border-radius: 50%;
  border: 2px solid ${({ theme }) => theme.colors.primary};
`;

const RatingIconWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-left: -2px;

  & > span {
    margin-left: 12px;
  }
`;

const ReviewImage = styled.img`
  align-self: center;
`;

const FavoriteButton = styled(Button)`
  display: flex;
  align-items: center;
  column-gap: 8px;
  padding: 0;
`;
