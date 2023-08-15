import { Heading, Text, useTheme } from '@fun-eat/design-system';
import { Fragment } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import type { MemberRecipe, Recipe } from '@/types/recipe';
import { getFormattedDate } from '@/utils/date';

interface RecipeItemProps {
  recipe: Recipe | MemberRecipe;
}

const RecipeItem = ({ recipe }: RecipeItemProps) => {
  const { image, title, createdAt, favoriteCount, products } = recipe;
  const author = 'author' in recipe ? recipe.author : null;
  const theme = useTheme();

  return (
    <RecipeItemContainer>
      <ImageWrapper>
        <RecipeImage src={image} alt={`조리된 ${title}`} />
        {author && <ProfileImage src={author.profileImage} alt={`${author.nickname}의 프로필`} />}
      </ImageWrapper>
      <RecipeInfoWrapper>
        <Text color={theme.textColors.sub}>
          {author && `${author.nickname} 님 | `}
          {getFormattedDate(createdAt)}
        </Text>
        <Heading as="h3" size="xl" weight="bold">
          {title}
        </Heading>
        <Text as="span" color={theme.textColors.info}>
          {products.map((product, index) => {
            const isStringType = typeof product === 'string';
            const id = isStringType ? `recipeProduct-${index}` : product.id;
            const name = isStringType ? product : product.name;
            return <Fragment key={id}>#{name}</Fragment>;
          })}
        </Text>
        <FavoriteWrapper>
          <SvgIcon variant="favoriteFilled" width={16} height={16} />
          <Text as="span" weight="bold">
            {favoriteCount}
          </Text>
        </FavoriteWrapper>
      </RecipeInfoWrapper>
    </RecipeItemContainer>
  );
};

export default RecipeItem;

const RecipeItemContainer = styled.div`
  height: 280px;
`;

const ImageWrapper = styled.div`
  position: relative;
  width: 100%;
  height: 160px;
`;

const RecipeImage = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 8px;
  object-fit: cover;
`;

const ProfileImage = styled.img`
  position: absolute;
  right: 16px;
  bottom: -20px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.backgroundColors.default};
  border: 2px solid ${({ theme }) => theme.colors.primary};
`;

const RecipeInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  position: relative;
  height: 100px;
  margin-top: 20px;
`;

const FavoriteWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
  position: absolute;
  top: 50%;
  right: 0;
  bottom: 50%;
  transform: translateY(-50%);
`;
