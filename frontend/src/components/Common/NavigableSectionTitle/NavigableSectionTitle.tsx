import { Heading, Link, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import { styled } from 'styled-components';

import { SvgIcon } from '@/components/Common';

interface MembersTitleProps {
  title: string;
  routeDestination: string;
}

const NavigableSectionTitle = ({ title, routeDestination }: MembersTitleProps) => {
  return (
    <NavigableSectionTitleContainer>
      <Heading as="h2" size="xl">
        {title}
      </Heading>
      <Link as={RouterLink} to={routeDestination}>
        <ArrowIcon variant="arrow" color={theme.colors.gray5} width={18} height={18} />
      </Link>
    </NavigableSectionTitleContainer>
  );
};

export default NavigableSectionTitle;

const NavigableSectionTitleContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const ArrowIcon = styled(SvgIcon)`
  transform: translateY(3px) rotate(180deg);
`;
