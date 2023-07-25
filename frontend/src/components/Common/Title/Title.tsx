import { Link, Heading, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

interface TitleProps {
  headingTitle: string;
}

const Title = ({ headingTitle }: TitleProps) => {
  return (
    <TitleContainer>
      <Link as={RouterLink} to=".." relative="path">
        <SvgIconWrapper>
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={20} height={20} />
        </SvgIconWrapper>
      </Link>
      <Heading as="h2" weight="bold">
        {headingTitle}
      </Heading>
    </TitleContainer>
  );
};

export default Title;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  position: relative;
`;

const SvgIconWrapper = styled.button`
  position: absolute;
  top: 8px;
  left: 0;
`;
