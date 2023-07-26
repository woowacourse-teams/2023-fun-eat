import { Heading, theme } from '@fun-eat/design-system';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import SvgIcon from '../Svg/SvgIcon';

interface TitleProps {
  headingTitle: string;
}

const Title = ({ headingTitle }: TitleProps) => {
  const navigate = useNavigate();

  const routeBack = () => {
    navigate(-1);
  };

  return (
    <TitleContainer>
      <button onClick={routeBack}>
        <SvgIconWrapper>
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={20} height={20} />
        </SvgIconWrapper>
      </button>
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
