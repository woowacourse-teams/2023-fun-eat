import { Button, Heading, Link, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useLogoutMutation, useMemberQuery } from '@/hooks/queries/members';

const MembersInfo = () => {
  const { data: member } = useMemberQuery();

  // TODO: suspended query 도입 시 없어질 예정
  // member가 없다면 로그인 페이지로 이동하기 때문에 member는 항상 존재.
  if (!member) {
    return null;
  }

  const { nickname, profileImage } = member;

  const { mutate } = useLogoutMutation();

  const handleLogout = () => {
    mutate();
  };

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
      <Button type="button" textColor="disabled" variant="transparent" onClick={handleLogout}>
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
