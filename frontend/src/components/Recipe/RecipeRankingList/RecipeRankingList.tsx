import RecipeItem from '../RecipeItem/RecipeItem';

import { Carousel } from '@/components/Common';
import mockRecipeList from '@/mocks/data/recipes.json';

const RecipeRankingList = () => {
  const carouselList = mockRecipeList.recipes.map((recipe, index) => ({
    id: index,
    children: <RecipeItem recipe={recipe} />,
  }));

  return <Carousel carouselList={carouselList} />;
};

export default RecipeRankingList;
