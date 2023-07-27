import { Button, Heading, Link, theme } from '@fun-eat/design-system';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';

interface ProductTitleProps {
  name: string;
  bookmark: boolean;
}

const ProductTitle = ({ name, bookmark }: ProductTitleProps) => {
  return (
    <ProductTitleContainer>
      <ProductTitleWrapper>
        <Link as={RouterLink} to=".." relative="path">
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={15} height={15} />
        </Link>
        <Heading size="xl" css="margin-left: 20px">
          {name}
        </Heading>
      </ProductTitleWrapper>
      <Button type="button" customWidth="32px" variant="transparent">
        <SvgIcon
          variant={bookmark ? 'bookmarkFilled' : 'bookmark'}
          color={bookmark ? theme.colors.primary : theme.colors.gray5}
        />
      </Button>
    </ProductTitleContainer>
  );
};

export default ProductTitle;

const ProductTitleContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const ProductTitleWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 16px;

  svg {
    padding-top: 2px;
  }
`;
