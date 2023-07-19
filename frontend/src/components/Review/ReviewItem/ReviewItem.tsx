import { Badge, Text, useTheme } from '@fun-eat/design-system';
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
              <SvgIcon variant="star" color={theme.colors.secondary} width={16} height={16} />
              <Text size="xs">{rating.toFixed(1)}</Text>
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
        <FavoriteIconWrapper>
          <SvgIcon variant="favorite" color={favorite ? 'red' : theme.colors.gray4} width={13} height={13} />
          <Text weight="bold">{favoriteCount}</Text>
        </FavoriteIconWrapper>
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

const FavoriteIconWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 4px;
  margin-top: 8px;
`;
