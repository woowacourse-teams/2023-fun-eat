import React from 'react';
import styled from 'styled-components';

import RecipeItem from '../RecipeItem/RecipeItem';

import recipeResponse from '@/mocks/data/recipes.json';

const RecipeList = () => {
  const { recipes } = recipeResponse;

  return (
    <RecipeListContainer>
      {recipes.map((recipe) => (
        <li key={recipe.id}>
          <RecipeItem recipe={recipe} />
        </li>
      ))}
    </RecipeListContainer>
  );
};

export default RecipeList;

const RecipeListContainer = styled.ul`
  & > li {
    margin-top: 40px;
  }
`;
