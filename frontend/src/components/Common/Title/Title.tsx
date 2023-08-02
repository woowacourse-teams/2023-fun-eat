import { Button, Heading, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import useRouteBack from '@/hooks/useRouteBack';

interface TitleProps {
  headingTitle: string;
}

const Title = ({ headingTitle }: TitleProps) => {
  const routeBack = useRouteBack();

  return (
    <TitleContainer>
      <Button type="button" variant="transparent" onClick={routeBack} aria-label="뒤로 가기">
        <SvgIconWrapper>
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={20} height={20} />
        </SvgIconWrapper>
      </Button>
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
