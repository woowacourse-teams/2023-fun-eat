import { theme, Button, Text } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { useTimeout } from '@/hooks/common';
import { useToastActionContext } from '@/hooks/context';
import { useRecipeFavoriteMutation } from '@/hooks/queries/recipe';

interface RecipeFavoriteProps {
  favorite: boolean;
  favoriteCount: number;
  recipeId: number;
}

const RecipeFavorite = ({ recipeId, favorite, favoriteCount }: RecipeFavoriteProps) => {
  const [isFavorite, setIsFavorite] = useState(favorite);
  const [currentFavoriteCount, setCurrentFavoriteCount] = useState(favoriteCount);
  const { toast } = useToastActionContext();

  const { mutate } = useRecipeFavoriteMutation(Number(recipeId));

  const handleToggleFavorite = async () => {
    mutate(
      { favorite: !isFavorite },
      {
        onSuccess: () => {
          setIsFavorite((prev) => !prev);
          setCurrentFavoriteCount((prev) => (isFavorite ? prev - 1 : prev + 1));
          toast.success('🍯 꿀조합이 등록 됐어요');
        },
        onError: () => {
          toast.error('꿀조합 좋아요를 다시 시도해주세요.');
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

export default RecipeFavorite;

const FavoriteButton = styled(Button)`
  display: flex;
  gap: 8px;
  align-items: center;
`;
