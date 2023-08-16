import { Spacing } from '@fun-eat/design-system';

import { ScrollButton, SectionTitle } from '@/components/Common';
import { MemberRecipeList } from '@/components/Members';

const MemberRecipePage = () => {
  return (
    <>
      <SectionTitle name="내가 작성한 레시피" />
      <Spacing size={18} />
      <MemberRecipeList />
      <ScrollButton />
    </>
  );
};

export default MemberRecipePage;
