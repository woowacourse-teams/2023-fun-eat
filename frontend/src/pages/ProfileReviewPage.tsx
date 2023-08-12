import { Spacing } from '@fun-eat/design-system';

import { SectionTitle } from '@/components/Common';
import { MembersTitle } from '@/components/Members';
import MemberReviewList from '@/components/Members/MemberReviewList/MemberReviewList';

const ProfileReviewPage = () => {
  return (
    <>
      <SectionTitle name="내가 작성한 리뷰" />
      {/* <MembersTitle title="내가 작성한 리뷰" routeDestination="review" /> */}
      <Spacing size={24} />
      <MemberReviewList />
    </>
  );
};

export default ProfileReviewPage;
