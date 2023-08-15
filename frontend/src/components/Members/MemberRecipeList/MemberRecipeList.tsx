import { Spacing, Text, theme } from '@fun-eat/design-system';
import { useRef } from 'react';
import styled from 'styled-components';

import { RecipeItem } from '@/components/Recipe';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteMemberRecipeQuery } from '@/hooks/queries/members';
import useDisplaySlice from '@/utils/displaySlice';

interface MemberRecipeListProps {
  isMemberPage?: boolean;
}

const MemberRecipeList = ({ isMemberPage = false }: MemberRecipeListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);

  const { fetchNextPage, hasNextPage, data } = useInfiniteMemberRecipeQuery();
  const memberRecipes = data?.pages.flatMap((page) => page.recipes);
  const recipeToDisplay = useDisplaySlice(isMemberPage, memberRecipes);

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const totalRecipeCount = data?.pages[0].page.totalDataCount;

  return (
    <MemberRecipeListContainer>
      {!isMemberPage && (
        <TotalRecipeCount color={theme.colors.gray4}>
          총 <strong>&apos;{totalRecipeCount}&apos;</strong>개의 레시피를 남겼어요!
        </TotalRecipeCount>
      )}
      <Spacing size={20} />
      <MemberRecipeListWrapper>
        {recipeToDisplay?.map((recipe) => (
          <li key={recipe.id}>
            <RecipeItem recipe={recipe} />
          </li>
        ))}
      </MemberRecipeListWrapper>
      <div ref={scrollRef} aria-hidden />
    </MemberRecipeListContainer>
  );
};

export default MemberRecipeList;

const MemberRecipeListContainer = styled.section`
  display: flex;
  flex-direction: column;
`;

const MemberRecipeListWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 40px;
`;

const TotalRecipeCount = styled(Text)`
  text-align: right;
`;
