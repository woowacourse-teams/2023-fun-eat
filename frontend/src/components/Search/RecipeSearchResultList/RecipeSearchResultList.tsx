import { Link, Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { styled } from 'styled-components';

import { RecipeItem } from '@/components/Recipe';
import { PATH } from '@/constants/path';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteRecipeSearchResultsQuery } from '@/hooks/queries/search';

interface RecipeSearchResultListProps {
  searchQuery: string;
}

const RecipeSearchResultList = ({ searchQuery }: RecipeSearchResultListProps) => {
  const { data: searchResponse, fetchNextPage, hasNextPage } = useInfiniteRecipeSearchResultsQuery(searchQuery);
  const scrollRef = useRef<HTMLDivElement>(null);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const recipes = searchResponse.pages.flatMap((page) => page.recipes);

  if (recipes.length === 0) {
    return <Text>검색한 꿀조합을 찾을 수 없습니다.</Text>;
  }

  return (
    <>
      <RecipeSearchResultListContainer>
        {recipes.map((recipe) => (
          <li key={recipe.id}>
            <Link as={RouterLink} to={`${PATH.RECIPE}/${recipe.id}`}>
              <RecipeItem recipe={recipe} />
            </Link>
          </li>
        ))}
      </RecipeSearchResultListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default RecipeSearchResultList;

const RecipeSearchResultListContainer = styled.ul`
  & > li + li {
    margin-top: 40px;
  }
`;
