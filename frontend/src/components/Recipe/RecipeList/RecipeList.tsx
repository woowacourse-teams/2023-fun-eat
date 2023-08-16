import { Link } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import RecipeItem from '../RecipeItem/RecipeItem';

import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteRecipesQuery } from '@/hooks/queries/recipe';
import type { SortOption } from '@/types/common';

interface RecipeListProps {
  selectedOption: SortOption;
}

const RecipeList = ({ selectedOption }: RecipeListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);
  const { fetchNextPage, hasNextPage, data } = useInfiniteRecipesQuery(selectedOption.value);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const recipes = data.pages.flatMap((page) => page.recipes);

  return (
    <>
      <RecipeListContainer>
        {recipes.map((recipe) => (
          <li key={recipe.id}>
            <Link as={RouterLink} to={`${recipe.id}`}>
              <RecipeItem recipe={recipe} />
            </Link>
          </li>
        ))}
      </RecipeListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default RecipeList;

const RecipeListContainer = styled.ul`
  & > li + li {
    margin-top: 40px;
  }
`;
