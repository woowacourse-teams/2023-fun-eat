import { Badge, Button, Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon, TagList } from '@/components/Common';
import type { Review } from '@/types/review';

interface ReviewItemProps {
  review: Review;
}

const ReviewItem = ({ review }: ReviewItemProps) => {
  const { userName, profileImage, image, rating, tags, content, rebuy, favoriteCount, favorite } = review;
  const theme = useTheme();

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
            </RatingIconWrapper>
          </div>
        </ReviewerInfoWrapper>
        {rebuy && (
          <Badge
            color={theme.colors.primary}
            textColor={theme.textColors.default}
            css={`
              font-weight: ${theme.fontWeights.bold};
            `}
          >
            😝 또 살래요
          </Badge>
        )}
      </ReviewerWrapper>
      {image !== null && <ReviewImage src={image} height={150} alt={`${userName}의 리뷰`} />}
      <TagList tags={tags} />
      <Text css="white-space: pre-wrap">{content}</Text>
      <FavoriteButton type="button" variant="transparent">
        <SvgIcon variant={favorite ? 'favoriteFilled' : 'favorite'} color={favorite ? 'red' : theme.colors.gray4} />
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

const ReviewerImage = styled.img`
  border-radius: 50%;
  border: 2px solid ${({ theme }) => theme.colors.primary};
`;

const RatingIconWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-left: -2px;
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
