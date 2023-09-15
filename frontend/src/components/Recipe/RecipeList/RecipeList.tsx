import { Link, Text } from '@fun-eat/design-system';
import type { RefObject } from 'react';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import RecipeItem from '../RecipeItem/RecipeItem';

import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteRecipesQuery } from '@/hooks/queries/recipe';
import type { SortOption } from '@/types/common';

interface RecipeListProps {
  recipeRef: RefObject<HTMLDivElement>;
  selectedOption: SortOption;
}

const RecipeList = ({ selectedOption, recipeRef }: RecipeListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);
  const { fetchNextPage, hasNextPage, data } = useInfiniteRecipesQuery(selectedOption.value);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const recipes = data.pages.flatMap((page) => page.recipes);

  if (recipes.length === 0) {
    return <Text>꿀조합을 작성해보세요</Text>;
  }

  return (
    <RecipeListContainer ref={recipeRef}>
      <RecipeListWrapper>
        {recipes.map((recipe) => (
          <li key={recipe.id}>
            <Link as={RouterLink} to={`${recipe.id}`}>
              <RecipeItem recipe={recipe} />
            </Link>
          </li>
        ))}
      </RecipeListWrapper>
      <div ref={scrollRef} aria-hidden />
    </RecipeListContainer>
  );
};

export default RecipeList;

const RecipeListContainer = styled.div`
  height: calc(100% - 192px);
  overflow-y: auto;
`;

const RecipeListWrapper = styled.ul`
  & > li + li {
    margin-top: 40px;
  }
`;
