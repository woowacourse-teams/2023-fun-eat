import { Heading, Link, theme } from '@fun-eat/design-system';
import { styled } from 'styled-components';

import { SvgIcon } from '@/components/Common';

interface MembersTitleProps {
  title: string;
}

const MembersTitle = ({ title }: MembersTitleProps) => {
  return (
    <MemberTitleContainer>
      <Heading size="xl">{title}</Heading>
      <Link>
        <ArrowIcon variant="arrow" color={theme.colors.gray5} width={18} height={18} />
      </Link>
    </MemberTitleContainer>
  );
};

export default MembersTitle;

const MemberTitleContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const ArrowIcon = styled(SvgIcon)`
  transform: translateY(3px) rotate(180deg);
`;
