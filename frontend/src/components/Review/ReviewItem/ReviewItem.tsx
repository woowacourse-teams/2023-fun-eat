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
          <img src={profileImage} width={40} height={40} alt={`${userName}의 프로필`} />
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
      <img src={image} height={150} alt={`${userName}의 리뷰`} />
      <TagList tags={tags} />
      <ReviewContentWrapper>
        <Text css="white-space: pre-wrap">{content}</Text>
        <FavoriteButton type="button" color="white" variant="filled">
          <SvgIcon
            variant={favorite ? 'favoriteFilled' : 'favorite'}
            color={favorite ? 'red' : theme.colors.gray4}
            width={24}
            height={24}
          />
          <Text as="span" weight="bold">
            {favoriteCount}
          </Text>
        </FavoriteButton>
      </ReviewContentWrapper>
    </ReviewItemContainer>
  );
};

export default ReviewItem;

const ReviewItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 20px;

  & > img {
    align-self: center;
  }
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

  & > img {
    border-radius: 50%;
    border: 2px solid ${({ theme }) => theme.colors.primary};
  }
`;

const RatingIconWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-left: -2px;

  & > svg {
    padding-bottom: 2px;
  }
`;

const ReviewContentWrapper = styled.div``;

const FavoriteButton = styled(Button)`
  display: flex;
  align-items: center;
  column-gap: 8px;
  margin-top: 8px;
  padding: 0;
`;
