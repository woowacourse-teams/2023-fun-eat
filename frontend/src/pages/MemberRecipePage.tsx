import { Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';

import { ErrorBoundary, ErrorComponent, Loading, ScrollButton, SectionTitle } from '@/components/Common';
import { MemberRecipeList } from '@/components/Members';

const MemberRecipePage = () => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <>
      <SectionTitle name="내가 작성한 꿀조합" />
      <Spacing size={18} />
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <MemberRecipeList />
        </Suspense>
      </ErrorBoundary>
      <ScrollButton />
    </>
  );
};

export default MemberRecipePage;
