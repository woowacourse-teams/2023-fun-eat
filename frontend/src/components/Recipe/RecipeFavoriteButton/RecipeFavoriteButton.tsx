import { theme, Button, Text } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { useTimeout } from '@/hooks/common';
import { useRecipeFavoriteMutation } from '@/hooks/queries/recipe';

interface RecipeFavoriteProps {
  favorite: boolean;
  favoriteCount: number;
  recipeId: number;
}

const RecipeFavoriteButton = ({ recipeId, favorite, favoriteCount }: RecipeFavoriteProps) => {
  const initialFavoriteState = {
    isFavorite: favorite,
    currentFavoriteCount: favoriteCount,
  };

  const [favoriteInfo, setFavoriteInfo] = useState(initialFavoriteState);

  const { mutate } = useRecipeFavoriteMutation(Number(recipeId));
  const { isFavorite, currentFavoriteCount } = favoriteInfo;

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
    <FavoriteButton type="button" variant="transparent" onClick={debouncedToggleFavorite}>
      <SvgIcon
        variant={isFavorite ? 'favoriteFilled' : 'favorite'}
        color={isFavorite ? 'red' : theme.colors.gray4}
        width={18}
      />
      <Text weight="bold" size="lg">
        {currentFavoriteCount}
      </Text>
    </FavoriteButton>
  );
};

export default RecipeFavoriteButton;

const FavoriteButton = styled(Button)`
  display: flex;
  gap: 8px;
  align-items: center;
`;
