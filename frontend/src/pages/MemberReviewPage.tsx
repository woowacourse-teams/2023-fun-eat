import { Spacing } from '@fun-eat/design-system';

import { ScrollButton, SectionTitle } from '@/components/Common';
import { MemberReviewList } from '@/components/Members';

const MemberReviewPage = () => {
  return (
    <>
      <SectionTitle name="내가 작성한 리뷰" />
      <Spacing size={18} />
      <MemberReviewList />
      <ScrollButton />
    </>
  );
};

export default MemberReviewPage;
