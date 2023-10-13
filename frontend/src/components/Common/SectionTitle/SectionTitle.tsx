import { Button, Heading, theme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { useRoutePage } from '@/hooks/common';

interface SectionTitleProps {
  name: string;
}

const SectionTitle = ({ name }: SectionTitleProps) => {
  const { routeBack } = useRoutePage();

  return (
    <SectionTitleContainer>
      <SectionTitleWrapper>
        <Button type="button" variant="transparent" onClick={routeBack} aria-label="뒤로 가기">
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={15} height={15} />
        </Button>
        <Heading size="xl" css="margin-left: 20px">
          {name}
        </Heading>
      </SectionTitleWrapper>
    </SectionTitleContainer>
  );
};

export default SectionTitle;

const SectionTitleContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const SectionTitleWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 16px;

  svg {
    padding-top: 2px;
  }
`;
