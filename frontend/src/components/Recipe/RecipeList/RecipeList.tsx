import { Link, Text } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import RecipeItem from '../RecipeItem/RecipeItem';

import recipeResponse from '@/mocks/data/recipes.json';

const RecipeList = () => {
  // TODO: 임시 데이터, API 연동 후 수정
  const { recipes } = recipeResponse;

  if (recipes.length === 0) {
    return <Text>꿀조합을 작성해보세요</Text>;
  }

  return (
    <RecipeListContainer>
      {recipes.map((recipe) => (
        <li key={recipe.id}>
          <Link as={RouterLink} to={`${recipe.id}`}>
            <RecipeItem recipe={recipe} />
          </Link>
        </li>
      ))}
    </RecipeListContainer>
  );
};

export default RecipeList;

const RecipeListContainer = styled.ul`
  & > li + li {
    margin-top: 40px;
  }
`;
