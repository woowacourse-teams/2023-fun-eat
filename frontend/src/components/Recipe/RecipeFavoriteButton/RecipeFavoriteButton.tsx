import { theme, Button, Text } from '@fun-eat/design-system';
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
  const { mutate } = useRecipeFavoriteMutation(Number(recipeId));

  const handleToggleFavorite = async () => {
    mutate({ favorite: !favorite });
  };

  const [debouncedToggleFavorite] = useTimeout(handleToggleFavorite, 200);

  return (
    <FavoriteButton type="button" variant="transparent" onClick={debouncedToggleFavorite}>
      <SvgIcon
        variant={favorite ? 'favoriteFilled' : 'favorite'}
        color={favorite ? 'red' : theme.colors.gray4}
        width={18}
      />
      <Text weight="bold" size="lg">
        {favoriteCount}
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
