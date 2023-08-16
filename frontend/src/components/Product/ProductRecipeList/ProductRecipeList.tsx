import { Link, Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { RecipeItem } from '@/components/Recipe';
import { PATH } from '@/constants/path';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteProductRecipesQuery } from '@/hooks/queries/product';
import type { SortOption } from '@/types/common';

interface ProductRecipeListProps {
  productId: number;
  productName: string;
  selectedOption: SortOption;
}

const ProductRecipeList = ({ productId, productName, selectedOption }: ProductRecipeListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);
  const { fetchNextPage, hasNextPage, data } = useInfiniteProductRecipesQuery(productId, selectedOption.value);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const recipes = data.pages.flatMap((page) => page.recipes);

  if (recipes.length === 0) {
    return (
      <ErrorContainer>
        <ErrorDescription align="center" weight="bold" size="lg">
          {productName}을/를 사용한 꿀조합을 만들어보세요 🍯
        </ErrorDescription>
        <RecipeLink as={RouterLink} to={`PATH.RECIPE`} block>
          꿀조합 작성하러 가기
        </RecipeLink>
      </ErrorContainer>
    );
  }

  return (
    <>
      <ProductRecipeListContainer>
        {recipes.map((recipe) => (
          <li key={recipe.id}>
            <Link as={RouterLink} to={`${PATH.RECIPE}/${recipe.id}`}>
              <RecipeItem recipe={recipe} />
            </Link>
          </li>
        ))}
      </ProductRecipeListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default ProductRecipeList;

const ProductRecipeListContainer = styled.ul`
  & > li + li {
    margin-top: 40px;
  }
`;

const ErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ErrorDescription = styled(Text)`
  padding: 20px 0;
  white-space: pre-line;
  word-break: break-all;
`;

const RecipeLink = styled(Link)`
  padding: 16px 24px;
  border: 1px solid ${({ theme }) => theme.colors.gray4};
  border-radius: 8px;
`;
