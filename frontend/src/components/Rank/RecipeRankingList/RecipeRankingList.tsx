import RecipeRankingItem from '../RecipeRankingItem/RecipeRankingItem';

import { Carousel } from '@/components/Common';
import mockRecipeList from '@/mocks/data/recipeRankingList.json';

const RecipeRankingList = () => {
  const carouselList = mockRecipeList.recipes.map((recipe, index) => ({
    id: index,
    children: <RecipeRankingItem rank={index + 1} recipe={recipe} />,
  }));

  return <Carousel carouselList={carouselList} />;
};

export default RecipeRankingList;
