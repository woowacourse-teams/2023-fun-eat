import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useRef } from 'react';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Loading, ScrollButton, SectionTitle } from '@/components/Common';
import { MemberRecipeList } from '@/components/Members';

const MemberRecipePage = () => {
  const { reset } = useQueryErrorResetBoundary();
  const memberRecipeRef = useRef<HTMLDivElement>(null);

  return (
    <MebmberRecipePageContainer ref={memberRecipeRef}>
      <SectionTitle name="내가 작성한 꿀조합" />
      <Spacing size={18} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <MemberRecipeList />
        </Suspense>
      </ErrorBoundary>
      <ScrollButton targetRef={memberRecipeRef} />
    </MebmberRecipePageContainer>
  );
};

export default MemberRecipePage;

const MebmberRecipePageContainer = styled.div`
  height: 100%;
  overflow-y: auto;
`;
