import type { PropsWithChildren } from 'react';
import { createContext, useEffect, useState } from 'react';

import type { Member } from '@/types/member';

interface MemberAction {
  handleNewMember: (newMember: Member) => void;
  resetMember: () => void;
}

export const MemberValueContext = createContext<Member | null>(null);
export const MemberActionContext = createContext<MemberAction | null>(null);

const MemberProvider = ({ children }: PropsWithChildren) => {
  const funEatMember = localStorage.getItem('funEatMember');
  const [member, setMember] = useState<Member | null>(funEatMember ? JSON.parse(funEatMember) : null);

  useEffect(() => {
    localStorage.setItem('funEatMember', JSON.stringify(member));
  }, [member]);

  const handleNewMember = (newMember: Member) => {
    setMember(newMember);
  };

  const resetMember = () => {
    setMember(null);
    localStorage.removeItem('funEatMember');
  };

  const memberAction = {
    handleNewMember,
    resetMember,
  };

  return (
    <MemberActionContext.Provider value={memberAction}>
      <MemberValueContext.Provider value={member}>{children}</MemberValueContext.Provider>
    </MemberActionContext.Provider>
  );
};

export default MemberProvider;
