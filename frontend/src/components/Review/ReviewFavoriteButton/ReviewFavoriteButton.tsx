import { Text, Button, useTheme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { useTimeout } from '@/hooks/common';
import { useReviewFavoriteMutation } from '@/hooks/queries/review';

interface ReviewFavoriteButtonProps {
  productId: number;
  reviewId: number;
  favorite: boolean;
  favoriteCount: number;
}

const ReviewFavoriteButton = ({ productId, reviewId, favorite, favoriteCount }: ReviewFavoriteButtonProps) => {
  const theme = useTheme();

  const initialFavoriteState = {
    isFavorite: favorite,
    currentFavoriteCount: favoriteCount,
  };

  const [favoriteInfo, setFavoriteInfo] = useState(initialFavoriteState);
  const { isFavorite, currentFavoriteCount } = favoriteInfo;

  const { mutate } = useReviewFavoriteMutation(productId, reviewId);

  const handleToggleFavorite = async () => {
    setFavoriteInfo((prev) => ({
      isFavorite: !prev.isFavorite,
      currentFavoriteCount: isFavorite ? prev.currentFavoriteCount - 1 : prev.currentFavoriteCount + 1,
    }));

    mutate(
      { favorite: !isFavorite },
      {
        onError: () => {
          setFavoriteInfo(initialFavoriteState);
        },
      }
    );
  };

  const [debouncedToggleFavorite] = useTimeout(handleToggleFavorite, 200);

  return (
    <FavoriteButton
      type="button"
      variant="transparent"
      onClick={debouncedToggleFavorite}
      aria-label={`좋아요 ${currentFavoriteCount}개`}
    >
      <SvgIcon variant={isFavorite ? 'favoriteFilled' : 'favorite'} color={isFavorite ? 'red' : theme.colors.gray4} />
      <Text as="span" weight="bold">
        {currentFavoriteCount}
      </Text>
    </FavoriteButton>
  );
};

export default ReviewFavoriteButton;

const FavoriteButton = styled(Button)`
  display: flex;
  align-items: center;
  padding: 0;
  column-gap: 8px;
`;
