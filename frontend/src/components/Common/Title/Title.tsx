import { Button, Heading, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

import useRouteBack from '@/hooks/useRouteBack';

interface TitleProps {
  headingTitle: string;
  handleClickTitle: () => void;
}

const Title = ({ headingTitle, handleClickTitle }: TitleProps) => {
  const routeBack = useRouteBack();

  return (
    <TitleContainer>
      <Button type="button" variant="transparent" onClick={routeBack} aria-label="뒤로 가기">
        <SvgIconWrapper>
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={20} height={20} />
        </SvgIconWrapper>
      </Button>
      <Button type="button" variant="transparent" onClick={handleClickTitle}>
        <Heading as="h2" weight="bold">
          {headingTitle}
        </Heading>
      </Button>
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

const SvgIconWrapper = styled.span`
  position: absolute;
  top: 8px;
  left: 0;
`;
