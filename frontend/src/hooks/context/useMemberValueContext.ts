import { useContext } from 'react';

import { MemberValueContext } from '@/contexts/MemberContext';

const useMemberValueContext = () => {
  const member = useContext(MemberValueContext);

  if (member === undefined) {
    throw new Error('useMemberValueContext must be used in MemberProvider');
  }

  return member;
};

export default useMemberValueContext;
