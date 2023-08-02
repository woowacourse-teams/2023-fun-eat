import { useContext } from 'react';

import { MemberValueContext } from '@/contexts/MemberContext';

const useMemberValueContext = () => {
  const member = useContext(MemberValueContext);

  if (member === undefined) {
    throw new Error('useMemberValueContext는 MemberProvider 안에서 사용해야 합니다.');
  }

  return member;
};

export default useMemberValueContext;
