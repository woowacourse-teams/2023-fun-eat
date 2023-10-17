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
  isPreview?: boolean;
}

const MemberRecipeList = ({ isPreview = false }: MemberRecipeListProps) => {
  const scrollRef = useRef<HTMLDivElement>(null);

  const { fetchNextPage, hasNextPage, data } = useInfiniteMemberRecipeQuery();
  const memberRecipes = data?.pages.flatMap((page) => page.recipes);
  const recipeToDisplay = useDisplaySlice(isPreview, memberRecipes);

  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const totalRecipeCount = data?.pages[0].page.totalDataCount;

  if (totalRecipeCount === 0) {
    return (
      <ErrorContainer>
        <Text size="lg" weight="bold">
          앗, 작성한 꿀조합이 없네요 🥲
        </Text>
        <Spacing size={16} />
        <RecipeLink as={RouterLink} to={`${PATH.RECIPE}`} block>
          꿀조합 작성하러 가기
        </RecipeLink>
      </ErrorContainer>
    );
  }

  return (
    <MemberRecipeListContainer>
      {!isPreview && (
        <TotalRecipeCount color={theme.colors.gray4}>
          총 <strong>{totalRecipeCount}</strong>개의 꿀조합을 남겼어요!
        </TotalRecipeCount>
      )}
      <Spacing size={20} />
      <MemberRecipeListWrapper>
        {recipeToDisplay?.map((recipe) => (
          <li key={recipe.id}>
            <Link as={RouterLink} to={`${PATH.RECIPE}/${recipe.id}`}>
              <RecipeItem recipe={recipe} isMemberPage={isPreview} />
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

const ErrorContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
`;

const RecipeLink = styled(Link)`
  padding: 12px 12px;
  border: 1px solid ${({ theme }) => theme.colors.gray4};
  border-radius: 8px;
`;
