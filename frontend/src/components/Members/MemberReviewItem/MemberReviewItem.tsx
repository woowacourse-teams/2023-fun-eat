import { useTheme, Spacing, Text, Button } from '@fun-eat/design-system';
import type { MouseEventHandler } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { useToastActionContext } from '@/hooks/context';
import { useDeleteReview } from '@/hooks/queries/members';
import type { MemberReview } from '@/types/review';

interface MemberReviewItemProps {
  review: MemberReview;
  isMemberPage: boolean;
}

const MemberReviewItem = ({ review, isMemberPage }: MemberReviewItemProps) => {
  const theme = useTheme();

  const { mutate } = useDeleteReview();

  const { toast } = useToastActionContext();

  const { reviewId, productName, content, rating, favoriteCount } = review;

  const handleReviewDelete: MouseEventHandler<HTMLButtonElement> = (e) => {
    e.preventDefault();

    const result = window.confirm('리뷰를 삭제하시겠습니까?');
    if (!result) {
      return;
    }

    mutate(reviewId, {
      onSuccess: () => {
        toast.success('리뷰를 삭제했습니다.');
      },
      onError: (error) => {
        if (error instanceof Error) {
          toast.error(error.message);
          return;
        }

        toast.error('리뷰 좋아요를 다시 시도해주세요.');
      },
    });
  };

  return (
    <ReviewRankingItemContainer>
      <ProductNameIconWrapper>
        <Text size="sm" weight="bold">
          {productName}
        </Text>
        {!isMemberPage && (
          <Button variant="transparent" customHeight="auto" onClick={handleReviewDelete}>
            <SvgIcon variant="trashcan" width={20} height={20} />
          </Button>
        )}
      </ProductNameIconWrapper>
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

const ProductNameIconWrapper = styled.div`
  display: flex;
  justify-content: space-between;
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
