import RecipeRankingItem from '../RecipeRankingItem/RecipeRankingItem';

import { Carousel } from '@/components/Common';
import { useRecipeRankingQuery } from '@/hooks/queries/rank';

const RecipeRankingList = () => {
  const { data: recipeResponse } = useRecipeRankingQuery();

  const carouselList = recipeResponse.recipes.map((recipe, index) => ({
    id: index,
    children: <RecipeRankingItem rank={index + 1} recipe={recipe} />,
  }));

  return <Carousel carouselList={carouselList} />;
};

export default RecipeRankingList;
