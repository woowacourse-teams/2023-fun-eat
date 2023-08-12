import { Heading, Link, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import { styled } from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { PATH } from '@/constants/path';

interface MembersTitleProps {
  title: string;
  routeDestination: string;
}

const MembersTitle = ({ title, routeDestination }: MembersTitleProps) => {
  return (
    <MemberTitleContainer>
      <Heading as="h2" size="xl">
        {title}
      </Heading>
      <Link as={RouterLink} to={`${PATH.PROFILE}/${routeDestination}`}>
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
