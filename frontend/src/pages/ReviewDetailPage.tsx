import { Badge, Spacing, Text, useTheme } from '@fun-eat/design-system';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { SectionTitle, SvgIcon, TagList } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useReviewDetailQuery } from '@/hooks/queries/review';
import { getRelativeDate } from '@/utils/date';

export const ReviewDetailPage = () => {
  const { reviewId } = useParams();
  const { data: reviewDetail } = useReviewDetailQuery(Number(reviewId));

  const {
    productName,
    categoryType,
    productId,
    profileImage,
    userName,
    rating,
    createdAt,
    rebuy,
    image,
    tags,
    content,
    favoriteCount,
  } = reviewDetail.reviews;

  const theme = useTheme();

  return (
    <ReviewDetailPageContainer>
      <SectionTitle name={productName} link={`${PATH.PRODUCT_LIST}/${categoryType}/${productId}`} />
      <Spacing size={24} />
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
        {image && <ReviewImage src={image} height={150} alt={`${userName}의 리뷰`} />}
        <TagList tags={tags} />
        <ReviewContent>{content}</ReviewContent>
        <FavoriteWrapper>
          <SvgIcon variant="favoriteFilled" color="red" />
          <Text as="span" weight="bold">
            {favoriteCount}
          </Text>
        </FavoriteWrapper>
      </ReviewItemContainer>
    </ReviewDetailPageContainer>
  );
};

const ReviewDetailPageContainer = styled.div`
  padding: 20px 20px 0;
`;

const ReviewItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 20px;
`;

const ReviewerWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  border: 2px solid ${({ theme }) => theme.colors.primary};
  border-radius: 50%;
  object-fit: cover;
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

const ReviewContent = styled(Text)`
  white-space: pre-wrap;
`;

const FavoriteWrapper = styled.div`
  display: flex;
  align-items: center;
  padding: 0;
  column-gap: 8px;
`;
