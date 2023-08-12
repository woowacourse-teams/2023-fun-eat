import { Spacing } from '@fun-eat/design-system';

import { SectionTitle } from '@/components/Common';
import MemberReviewList from '@/components/Members/MemberReviewList/MemberReviewList';

const MemberReviewPage = () => {
  return (
    <>
      <SectionTitle name="내가 작성한 리뷰" />
      <Spacing size={18} />
      <MemberReviewList />
    </>
  );
};

export default MemberReviewPage;
