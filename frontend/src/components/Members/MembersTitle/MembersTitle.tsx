import { Button, Heading, theme } from '@fun-eat/design-system';
import { styled } from 'styled-components';

import { SvgIcon } from '@/components/Common';
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
        <MemberModifyButton type="button" variant="transparent">
          <SvgIcon variant="pencil" width={20} height={24} color={theme.colors.gray3} />
        </MemberModifyButton>
      </MemberTitleInfoWrapper>
      <Button textColor="disabled" variant="transparent">
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

const MemberModifyButton = styled(Button)`
  margin-left: 5px;
  transform: translateY(1px);
`;

const MembersImage = styled.img`
  margin-right: 16px;
  border-radius: 50%;
  border: 2px solid ${({ theme }) => theme.colors.primary};
`;
