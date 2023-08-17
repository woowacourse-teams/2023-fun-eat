import { Button, Heading, Link, theme } from '@fun-eat/design-system';
import { useEffect, useState } from 'react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import { logoutApi } from '@/apis';
import { SvgIcon } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useMemberQuery } from '@/hooks/queries/members';

const MembersInfo = () => {
  const { data: member } = useMemberQuery();

  // TODO: suspended query 도입 시 없어질 예정
  // member가 없다면 로그인 페이지로 이동하기 때문에 member는 항상 존재.
  if (!member) {
    return null;
  }

  const { nickname, profileImage } = member;

  const [location, setLocation] = useState('');
  const navigate = useNavigate();

  const logout = async () => {
    const response = await logoutApi.post({
      credentials: true,
    });

    if (!response) {
      throw new Error('로그아웃에 실패했습니다.');
    }

    const location = response.headers.get('Location');

    if (location === null) {
      throw new Error('Location이 없습니다.');
    }

    setLocation(location);
  };

  useEffect(() => {
    if (location === '') {
      return;
    }
    navigate(location, { replace: true });
  }, [location]);

  return (
    <MembersInfoContainer>
      <MemberInfoWrapper>
        <MembersImage src={profileImage} width={45} height={45} alt={`${nickname}의 프로필`} />
        <Heading size="xl" weight="bold">
          {nickname} 님
        </Heading>
        <MemberModifyLink as={RouterLink} to={`${PATH.MEMBER}/modify`}>
          <SvgIcon variant="pencil" width={20} height={24} color={theme.colors.gray3} />
        </MemberModifyLink>
      </MemberInfoWrapper>
      <Button type="button" textColor="disabled" variant="transparent" onClick={logout}>
        로그아웃
      </Button>
    </MembersInfoContainer>
  );
};

export default MembersInfo;

const MembersInfoContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const MemberInfoWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const MemberModifyLink = styled(Link)`
  margin-left: 5px;
  transform: translateY(1px);
`;

const MembersImage = styled.img`
  margin-right: 16px;
  border-radius: 50%;
  border: 2px solid ${({ theme }) => theme.colors.primary};
`;
