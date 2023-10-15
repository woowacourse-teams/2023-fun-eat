import { Button, Heading, Link, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import { useRoutePage } from '@/hooks/common';

interface SectionTitleProps {
  name: string;
  link?: string;
}

const SectionTitle = ({ name, link }: SectionTitleProps) => {
  const { routeBack } = useRoutePage();

  return (
    <SectionTitleContainer>
      <SectionTitleWrapper>
        <Button type="button" variant="transparent" onClick={routeBack} aria-label="뒤로 가기">
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={15} height={15} />
        </Button>
        {link ? (
          <Link as={RouterLink} to={link} block>
            <ProductName size="xl">{name}</ProductName>
          </Link>
        ) : (
          <ProductName size="xl">{name}</ProductName>
        )}
        {link && <SvgIcon variant="link" width={20} height={20} />}
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

  svg {
    padding-top: 2px;
  }
`;

const ProductName = styled(Heading)`
  margin: 0 5px 0 16px;
`;
