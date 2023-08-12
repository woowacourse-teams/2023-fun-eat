import { Button, Heading, Link, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { PATH } from '@/constants/path';
import type { Member } from '@/types/member';

interface MembersTitleProps {
  member: Member;
}

const MembersTitle = ({ member }: MembersTitleProps) => {
  const { nickname, profileImage } = member;

  return (
    <MembersTitleContainer>
      <MemberTitleInfoWrapper>
        <MembersImage src={profileImage} width={45} height={45} alt={`${nickname}의 프로필`} />
        <Heading size="xl" weight="bold">
          {nickname} 님
        </Heading>
        <MemberModifyLink as={RouterLink} to={`${PATH.PROFILE}/modify`}>
          <SvgIcon variant="pencil" width={20} height={24} color={theme.colors.gray3} />
        </MemberModifyLink>
      </MemberTitleInfoWrapper>
      <Button type="button" textColor="disabled" variant="transparent">
        로그아웃
      </Button>
    </MembersTitleContainer>
  );
};

export default MembersTitle;

const MembersTitleContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const MemberTitleInfoWrapper = styled.div`
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
