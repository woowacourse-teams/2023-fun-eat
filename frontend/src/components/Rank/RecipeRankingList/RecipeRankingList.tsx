import { Link } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';

import RecipeRankingItem from '../RecipeRankingItem/RecipeRankingItem';

import { Carousel } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useRecipeRankingQuery } from '@/hooks/queries/rank';

const RecipeRankingList = () => {
  const { data: recipeResponse } = useRecipeRankingQuery();

  const carouselList = recipeResponse.recipes.map((recipe, index) => ({
    id: index,
    children: (
      <Link as={RouterLink} to={`${PATH.RECIPE}/${recipe.id}`}>
        <RecipeRankingItem rank={index + 1} recipe={recipe} />
      </Link>
    ),
  }));

  return <Carousel carouselList={carouselList} />;
};

export default RecipeRankingList;
