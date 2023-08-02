import { useContext } from 'react';

import { MemberActionContext } from '@/contexts/MemberContext';

const useMemberActionContext = () => {
  const memberAction = useContext(MemberActionContext);

  if (memberAction === null || memberAction === undefined) {
    throw new Error('useMemberActionContext는 MemberProvider 안에서 사용해야 합니다.');
  }

  return memberAction;
};

export default useMemberActionContext;
