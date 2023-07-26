import { Button, Heading, theme } from '@fun-eat/design-system';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';

interface ProductTitleProps {
  name: string;
  bookmark: boolean;
}

const ProductTitle = ({ name, bookmark }: ProductTitleProps) => {
  const navigate = useNavigate();

  const routeBack = () => {
    navigate(-1);
  };

  return (
    <ProductTitleContainer>
      <ProductTitleWrapper>
        <button onClick={routeBack}>
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={15} height={15} />
        </button>
        <Heading size="xl" css="margin-left: 20px">
          {name}
        </Heading>
      </ProductTitleWrapper>
      <Button color="white" variant="filled" css="width: 2rem">
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
  column-gap: 8px;

  svg {
    padding-top: 2px;
  }
`;
