import { useNavigate } from 'react-router-dom';

import { useMemberActionContext } from '../context';

import { memberApi } from '@/apis';
import type { Member } from '@/types/member';

const useMember = () => {
  const { handleNewMember, resetMember } = useMemberActionContext();
  const navigate = useNavigate();

  const getMember = async (location?: string) => {
    try {
      const response = await memberApi.get({ credentials: true });
      const member: Member = await response.json();

      handleNewMember(member);

      if (location) {
        navigate(location, { replace: true });
      }
      navigate;
    } catch (error) {
      resetMember();
      navigate('/login', { replace: true });
    }
  };

  return getMember;
};

export default useMember;
