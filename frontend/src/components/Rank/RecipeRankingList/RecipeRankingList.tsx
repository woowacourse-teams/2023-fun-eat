import { Carousel, Link, Text } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';

import RecipeRankingItem from '../RecipeRankingItem/RecipeRankingItem';

import { PATH } from '@/constants/path';
import { useGA } from '@/hooks/common';
import { useRecipeRankingQuery } from '@/hooks/queries/rank';

const RecipeRankingList = () => {
  const { data: recipeResponse } = useRecipeRankingQuery();
  const { gaEvent } = useGA();

  if (recipeResponse.recipes.length === 0) return <Text size="lg">아직 랭킹이 없어요!</Text>;

  const handleRecipeRankingLinkClick = () => {
    gaEvent({ category: 'link', action: '꿀조합 랭킹 링크 클릭', label: '랭킹' });
  };

  const carouselList = recipeResponse.recipes.map((recipe, index) => ({
    id: index,
    children: (
      <Link as={RouterLink} to={`${PATH.RECIPE}/${recipe.id}`} onClick={handleRecipeRankingLinkClick}>
        <RecipeRankingItem rank={index + 1} recipe={recipe} />
      </Link>
    ),
  }));

  return <Carousel carouselList={carouselList} />;
};

export default RecipeRankingList;
