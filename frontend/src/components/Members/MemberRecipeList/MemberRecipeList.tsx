import { Link, Spacing, Text, theme } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { RecipeItem } from '@/components/Recipe';
import { PATH } from '@/constants/path';
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
          총 <strong>&apos;{totalRecipeCount}&apos;</strong>개의 꿀조합을 남겼어요!
        </TotalRecipeCount>
      )}
      <Spacing size={20} />
      <MemberRecipeListWrapper>
        {recipeToDisplay?.map((recipe) => (
          <li key={recipe.id}>
            <Link as={RouterLink} to={`${PATH.RECIPE}/${recipe.id}`}>
              <RecipeItem recipe={recipe} isMemberPage={isMemberPage} />
            </Link>
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
  gap: 20px;
`;

const TotalRecipeCount = styled(Text)`
  text-align: right;
`;
