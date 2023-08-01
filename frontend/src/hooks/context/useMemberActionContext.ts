import { useContext } from 'react';

import { MemberActionContext } from '@/contexts/MemberContext';

const useMemberActionContext = () => {
  const memberAction = useContext(MemberActionContext);

  if (memberAction === null || memberAction === undefined) {
    throw new Error('useMemberActionContext must be used in MemberProvider');
  }

  return memberAction;
};

export default useMemberActionContext;
